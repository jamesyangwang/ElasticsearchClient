package my.ex.elasticsearch.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ElasticSearchUtils {

	private static String EMPLOYEE_MAPPING_FILE = "classpath:mappings/employee.json";
	
    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;
    
	private RestHighLevelClient client;
	
	@PostConstruct
	private void init() {
		log.info("Initializing client...");
		client = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port)));
	}
	
	@PreDestroy
	private void destroy() {
		log.info("Releasing resources...");
		try {
			client.close();
		} catch (IOException e) {}
	}
	
	public ClusterHealthResponse getClusterHealth() {
		ClusterHealthResponse response = null;
		try {
			val request = new ClusterHealthRequest();
			response = client.cluster().health(request, RequestOptions.DEFAULT);
		} catch (IOException e) {}
		return response;
	}
	
	public boolean isIndexExists(String index) {
		try {
			GetIndexRequest request = new GetIndexRequest(index);
			return client.indices().exists(request, RequestOptions.DEFAULT);
		} catch (IOException e) {}
		return false;
	}

	public boolean createIndex(String index) {
		try {
			CreateIndexRequest request = new CreateIndexRequest(index);
			request.settings(Settings.builder()
					.put("index.number_of_shards", 2)
					.put("index.number_of_replicas", 0));
			request.mapping(readMappings(), XContentType.JSON);
		
			CreateIndexResponse indexResponse = client.indices().create(request, RequestOptions.DEFAULT);
			return indexResponse.isAcknowledged();
			
		} catch (IOException e) {}
		
		return false;
	}

    private String readMappings() {
		try {
	        File file = ResourceUtils.getFile(EMPLOYEE_MAPPING_FILE);
			return new String(Files.readAllBytes(file.toPath()));
		} catch (IOException ex) {}
		return "";
    }
    
    @SuppressWarnings("unchecked")
	private Map<String, Object> convertProfileDocumentToMap(Object obj) {
    	ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(obj, Map.class);
    }
    
    public String add2Index(Object obj, String index) {
        IndexRequest indexRequest = new IndexRequest(index)
                .source(convertProfileDocumentToMap(obj));

		try {
			IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
	        return indexResponse.getResult().name();
		} catch (IOException e) {}
		
		return "";
    }
    
    public SearchHit[] search(String index, String keyword) {
    	SearchRequest request = new SearchRequest(index);
    	SearchSourceBuilder builder = new SearchSourceBuilder();
    	builder.query(QueryBuilders.multiMatchQuery(keyword, "*"));
    	request.source(builder);
    	
    	try {
			SearchResponse response = client.search(request, RequestOptions.DEFAULT);
			if (response.status().equals(RestStatus.OK)) {
				SearchHits hits = response.getHits();
				log.info("Total hits: " + hits.getTotalHits());
				return hits.getHits();
			}
		} catch (IOException e) {}
    	
    	return new SearchHit[0];
    }
}
