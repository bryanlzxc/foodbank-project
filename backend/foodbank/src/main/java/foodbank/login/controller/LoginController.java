package foodbank.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbank.login.dto.LoginDTO;
import foodbank.login.dto.LoginResponseDTO;
import foodbank.login.service.LoginService;
import foodbank.response.dto.ResponseDTO;
import foodbank.util.MessageConstants;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@RestController
@CrossOrigin
@RequestMapping("/authenticate")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@PostMapping
	public LoginResponseDTO authenticateUser(@RequestBody LoginDTO login) {
		LoginResponseDTO loginResponseDTO = new LoginResponseDTO(LoginResponseDTO.Status.SUCCESS, MessageConstants.LOGIN_SUCCESS, null);
		try {
			loginService.authenticate(login);
			loginResponseDTO.setUsertype(LoginResponseDTO.Usertype.valueOf(login.getUsertype().toUpperCase()));
		} catch (Exception e) {
			loginResponseDTO.setStatus(LoginResponseDTO.Status.FAIL);
			loginResponseDTO.setMessage(e.getMessage());
		}
		return loginResponseDTO;
	}
	
	@PostMapping("/volunteer-app")
	public ResponseDTO authenticateVolunteers(@RequestBody String dailyPassword) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.LOGIN_SUCCESS);
		try {
			loginService.authenticateVolunteers(dailyPassword);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	/*
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
	
	*/
	
}