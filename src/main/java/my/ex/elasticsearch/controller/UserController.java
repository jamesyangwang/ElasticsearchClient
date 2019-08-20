package my.ex.elasticsearch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.ex.elasticsearch.model.User;
import my.ex.elasticsearch.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService us;
	
	@GetMapping("/{keyword}")
	public List<User> findByName(@PathVariable("keyword") String keyword) {
		return us.searchUsers(keyword);
	}
}
