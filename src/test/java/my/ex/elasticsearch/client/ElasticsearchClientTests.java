package my.ex.elasticsearch.client;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElasticsearchClientTests {

	@Test
	public void testRestHighLevelClient() throws IOException {
		
		val client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost.digicert.com", 9200, "http")));
		
		val request = new ClusterHealthRequest();
		ClusterHealthResponse response = client.cluster().health(request, RequestOptions.DEFAULT);
		log.info(response.toString());
		
		client.close();
	}
}
