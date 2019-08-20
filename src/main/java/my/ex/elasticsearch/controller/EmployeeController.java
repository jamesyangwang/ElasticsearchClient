package my.ex.elasticsearch.controller;

import java.util.List;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.ex.elasticsearch.model.Employee;
import my.ex.elasticsearch.service.EmployeeService;
import my.ex.elasticsearch.util.ElasticSearchUtils;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private ElasticSearchUtils esu;
	
	@Autowired
	private EmployeeService es;
	
	@PostMapping
	public String add(@RequestBody Employee employee) {
		if (employee.getFirstname() == null || employee.getLastname() == null) return "Invalid input!!!";
		return es.addEmployee(employee);
	}

	@GetMapping("/{keyword}")
	public List<Employee> findByName(@PathVariable("keyword") String keyword) {
		return es.searchEmployees(keyword);
	}
	
	@GetMapping("/clusterHealth")
	public ClusterHealthResponse getClusterHealth() {
		return esu.getClusterHealth();
	}
}
