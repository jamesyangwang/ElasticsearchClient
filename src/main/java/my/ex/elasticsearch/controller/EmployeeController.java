package my.ex.elasticsearch.controller;

import java.util.Arrays;
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
import my.ex.elasticsearch.util.ElasticSearchUtils;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private ElasticSearchUtils esu;
	
	@PostMapping
	public Employee add(@RequestBody Employee employee) {
		return new Employee("James", "Wang");
	}

	@GetMapping("/{name}")
	public List<Employee> findByName(@PathVariable("name") String name) {
		return Arrays.asList(new Employee[] {new Employee("James", "Wang"), new Employee("Peter", "Tang")});
	}
	
	@GetMapping("/clusterHealth")
	public ClusterHealthResponse getClusterHealth() {
		return esu.getClusterHealth();
	}
}
