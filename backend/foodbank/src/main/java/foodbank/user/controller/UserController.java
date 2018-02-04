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
import foodbank.user.dto.PasswordDTO;
import foodbank.user.dto.UserDTO;
import foodbank.user.service.UserService;
import foodbank.user.entity.User;
import foodbank.util.MessageConstants;
import foodbank.util.MessageConstants.ErrorMessages;

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
	public ResponseDTO getAllUsers() {
		List<User> allUsers = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.USER_LIST_RETRIEVE_SUCCESS);
		try {
			allUsers = userService.getAllUsers();
		} catch ( Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		responseDTO.setResult(allUsers);
		return responseDTO;
	}
	
	@GetMapping("/display-all-by")
	public ResponseDTO getAllUsersByType(@RequestParam(value = "usertype", required = true) String usertype) {
		List<User> allUsersByType = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.USER_LIST_RETRIEVE_SUCCESS);
		try {
			allUsersByType = userService.getAllUsersByType(usertype);
		} catch ( Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		responseDTO.setResult(allUsersByType);
		return responseDTO;
	}
	
	@GetMapping("/display-user")
	public ResponseDTO getUserDetails(@RequestParam(value = "username", required = true) String username) {
//		return userService.getUserDetails(username);
		User user = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.USER_RETRIEVE_SUCCESS);
		try {
			user = userService.getUserDetails(username);
		} catch ( Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		responseDTO.setResult(user);
		return responseDTO;
	}
	
	@PutMapping("/insert-user")
	public ResponseDTO insertUser(@RequestBody UserDTO user) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.USER_ADD_SUCCESS);
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
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.USER_UPDATE_SUCCESS);
		try {
			userService.updateUser(user);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/change-password")
	public ResponseDTO changePassword(@RequestBody PasswordDTO passwordDetails) {
		Boolean result = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, result, MessageConstants.PASSWORD_CHANGE_SUCCESS);
		try {
			result = userService.changePassword(passwordDetails);
		} catch (Exception e) {
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
		}
		if(result == false) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.PASSWORD_NOT_UPDATED);
		}
		return responseDTO;
	}
	
	@DeleteMapping("/delete-user")
	public ResponseDTO deleteUser(@RequestParam(value = "username", required = true) String username) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.USER_DELETE_SUCCESS);
		try {
			userService.deleteUser(username);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}	
}