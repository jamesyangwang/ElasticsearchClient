package my.ex.elasticsearch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.ex.elasticsearch.model.Field;
import my.ex.elasticsearch.model.User;
import my.ex.elasticsearch.service.UserService;
import my.ex.elasticsearch.util.ElasticSearchUtils;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService us;
	
	@Autowired
	private ElasticSearchUtils esu;
	
	@GetMapping("/keyword/{keyword}")
	public List<User> findByName(@PathVariable("keyword") String keyword) {
		return us.searchUsers(keyword);
	}
	
	@GetMapping("/")
	public List<User> getAll() {
		return us.getAllUsers();
	}
	
	@GetMapping("/field")
	public List<User> findByName(@RequestBody Field field) {
		return us.getUsersByField(field);
	}
	
	@GetMapping("/async")
	public void async() {
		esu.async("users");
	}
	
	@GetMapping("/multi/{keyword}")
	public List<User> multiSearch(@PathVariable("keyword") String keyword, @RequestBody Field field) {
		return us.multiSearch(keyword, field);
	}
	
	@GetMapping("/template")
	public List<User> searchTemplate(@RequestBody Field field) {
		return us.searchTemplate(field);
	}
	
	@GetMapping("/bool/{keyword}")
	public List<User> boolSearch(@PathVariable("keyword") String keyword, @RequestBody Field field) {
		return us.boolSearchUsers(field, keyword);
	}
}
