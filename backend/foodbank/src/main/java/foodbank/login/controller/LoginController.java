package foodbank.login.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import foodbank.login.entity.LoginOutcome;
import foodbank.user.entity.User;
import foodbank.user.repository.UserRepository;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@RestController
@CrossOrigin
@RequestMapping("/authenticate")
public class LoginController {

	UserRepository userRepository;
	
	public LoginController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@PostMapping(produces = "application/json; charset=UTF-8")
	// Uncomment the CrossOrigins annotation to allow frontend app to authenticate itself into backend
	//@CrossOrigin(origins = "http://localhost:4200")
	@ResponseBody
	public LoginOutcome authenticateUser(@RequestBody User user) {
		User storedUserDetails = this.userRepository.findByUsername(user.getUsername());
		if(storedUserDetails != null) {
			if(storedUserDetails.getPassword().equals(user.getPassword())) {
				return new LoginOutcome(true, storedUserDetails.getUsertype());
			}
		}
		return new LoginOutcome(false, null);
	}
	
}