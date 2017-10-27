package foodbank.login.controller;

import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbank.login.entity.User;
import foodbank.login.repository.UserRepository;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@SpringBootApplication
@RestController
@RequestMapping("/users")
public class LoginController {
	
	private UserRepository userRepository;
	
	public LoginController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/all")
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}
	
}
