package foodbank.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foodbank.response.dto.ResponseDTO;
import foodbank.user.dto.UserDTO;
import foodbank.user.service.UserService;
import foodbank.user.entity.User;
import foodbank.util.MessageConstants;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/display-all")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/display-all-by")
	public List<User> getAllUsersByType(@RequestParam(value = "usertype", required = true) String usertype) {
		return userService.getAllUsersByType(usertype);
	}
	
	@GetMapping("/display-user")
	public User getUserDetails(@RequestParam(value = "username", required = true) String username) {
		return userService.getUserDetails(username);
	}
	
	@PutMapping("/insert-user")
	public ResponseDTO insertUser(@RequestBody UserDTO user) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.USER_ADD_SUCCESS);
		try {
			userService.insertUser(user);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-user")
	public ResponseDTO updateUser(@RequestBody UserDTO user) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.USER_UPDATE_SUCCESS);
		try {
			userService.updateUser(user);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@DeleteMapping("/delete-user")
	public ResponseDTO deleteUser(@RequestParam(value = "username", required = true) String username) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.USER_DELETE_SUCCESS);
		try {
			userService.deleteUser(username);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
}
