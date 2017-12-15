package foodbank.user.service;

import java.util.List;

import foodbank.user.dto.UserDTO;
import foodbank.user.entity.User;

public interface UserService {
	
	List<User> getAllUsers();
	
	List<User> getAllUsersByType(final String usertype);
	
	void insertUser(final UserDTO user);
	
	void updateUser(final UserDTO user);
	
	void deleteUser(final String username);

}