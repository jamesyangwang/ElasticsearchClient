package my.ex.elasticsearch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import my.ex.elasticsearch.model.Employee;
import my.ex.elasticsearch.util.ElasticSearchUtils;

@Service
public class EmployeeService {

    @Value("${employee.index}")
    private String index;
    
    @Autowired
    private ElasticSearchUtils esu;
    
	@PostConstruct
	private void init() {
		if (!isEmployeeIndexExists()) createEmployeeIndex();
	}
	
	private boolean createEmployeeIndex() {
		return esu.createIndex(index);
	}

	private boolean isEmployeeIndexExists() {
		return esu.isIndexExists(index);
	}
	
	public String addEmployee(Employee employee) {
		return esu.add2Index(employee, index);
	}
	
    public List<Employee> searchEmployees(String keyword) {
    	SearchHit[] hits = esu.search(index, keyword);
    	List<Employee> res = new ArrayList<>();
    	if (hits != null && hits.length != 0) {
    		for (SearchHit hit : hits) {
    			Map<String, Object> map = hit.getSourceAsMap();
    			String firstName = (String) map.get("firstname");
    			String lastName = (String) map.get("lastname");
    			res.add(new Employee(firstName, lastName));
    		}
    	}
   		return res;
    }
}
