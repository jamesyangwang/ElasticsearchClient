package my.ex.elasticsearch.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import my.ex.elasticsearch.model.User;
import my.ex.elasticsearch.util.ElasticSearchUtils;

@Slf4j
@Service
public class UserService {

    @Value("${user.index}")
    private String index;
    
    @Autowired
    private ElasticSearchUtils esu;
    
    public List<User> searchUsers(String keyword) {
    	SearchHit[] hits = esu.search(index, keyword);
    	List<User> res = new ArrayList<>();
    	if (hits != null && hits.length != 0) {
    		for (SearchHit hit : hits) {
    			String source = hit.getSourceAsString();
    			log.info(source);
    	    	ObjectMapper objectMapper = new ObjectMapper();
    	        try {
					User user = objectMapper.readValue(source, User.class);
					res.add(user);
				} catch (IOException e) {}
    		}
    	}
   		return res;
    }

}
