package foodbank.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbank.user.entity.User;
import foodbank.user.repository.UserRepository;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserRepository userRepository;
	
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/display-all")
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}
	
	@GetMapping("/id={id}")
	public User getById(@PathVariable("id") String id) {
		return this.userRepository.findById(id);
	}
	
	@GetMapping("/username={username}")
	public User getByUsername(@PathVariable("username") String username) {
		return this.userRepository.findByUsername(username);
	}
	
	@GetMapping("/usertype={usertype}")
	public List<User> getAllUsersFromUsertype(@PathVariable("usertype") String usertype) {
		return this.userRepository.findUsersByUsertype(usertype);
	}
	
	@PutMapping("/insert-user")
	public void insert(@RequestBody User user) {
		User storedUser = getByUsername(user.getUsername());
		if (storedUser == null) {
			this.userRepository.insert(user);
		}
	}
	
	@PostMapping("/update-user")
	public void update(@RequestBody User user) {
		User storedUser = getByUsername(user.getUsername());
		if(storedUser != null) {
			user.setId(storedUser.getId());
		}
		this.userRepository.save(user);
	}
	
	@DeleteMapping("/delete-user={username}")
	public void delete(@PathVariable("username") String username) {
		this.userRepository.delete(getByUsername(username));
	}
	
}
