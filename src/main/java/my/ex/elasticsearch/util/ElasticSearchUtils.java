package my.ex.elasticsearch.util;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ElasticSearchUtils {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;
    
	private RestHighLevelClient client;
	
	@PostConstruct
	public void init() {
		log.info("Initializing client...");
		client = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port)));
	}
	
	@PreDestroy
	public void destroy() {
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
}
