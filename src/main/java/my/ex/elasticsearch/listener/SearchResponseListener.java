package my.ex.elasticsearch.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SearchResponseListener implements ActionListener<SearchResponse> {

	@Override
	public void onResponse(SearchResponse response) {
		SearchHits hits = response.getHits();
		log.info("Total hits: " + hits.getTotalHits());
	}

	@Override
	public void onFailure(Exception ex) {
		log.error(ex.getMessage(), ex);
	}
}
