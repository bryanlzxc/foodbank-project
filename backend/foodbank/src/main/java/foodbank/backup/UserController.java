package foodbank.backup;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
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
import foodbank.util.Status;

/*
 * Created by: Lau Peng Liang, Bryan
 */

/*
@RestController
@CrossOrigin
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
	
	//used for internal call under RequestControl
	@GetMapping("/get-name{username}")
	public String getNameByUsername(@PathVariable String username) {
		return getByUsername(username).getName();
	}
	
	@PutMapping("/insert-user")
	public void insert(@RequestBody User user) {
		User storedUser = getByUsername(user.getUsername());
		if (storedUser == null) {
			this.userRepository.insert(user);
		}
	}
	
	@PostMapping("/update-user")
	public Status update(@RequestBody User user) {
		User storedUser = getByUsername(user.getUsername());
		if(storedUser != null) {
			user.setId(storedUser.getId());
			return new Status("success");
		}
		this.userRepository.save(user);
		return new Status("success");
	}
	
	@DeleteMapping("/delete-user={username}")
	public Status delete(@PathVariable("username") String username) {
		this.userRepository.delete(getByUsername(username));
		return new Status("success");
	}
	
}
*/