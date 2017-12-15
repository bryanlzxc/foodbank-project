package foodbank.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.exceptions.UserException;
import foodbank.user.dto.UserDTO;
import foodbank.user.entity.User;
import foodbank.user.repository.UserRepository;
import foodbank.user.service.UserService;
import foodbank.util.MessageConstants.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
	
	@Override
	public List<User> getAllUsersByType(String usertype) {
		return userRepository.findUsersByUsertype(usertype);
	}
	
	@Override
	public void insertUser(UserDTO user) {
		User dbUser = userRepository.findByUsername(user.getUsername());
		if(dbUser != null) {
			throw new UserException(ErrorMessages.USER_ALREADY_EXISTS);
		}
		userRepository.insert(user.transformToUser());		
	}
	
	@Override
	public void updateUser(UserDTO user) {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByUsername(user.getUsername());
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER);
		}
		User updatedUser = user.transformToUser();
		updatedUser.setId(dbUser.getId());
		userRepository.save(updatedUser);
	}
	
	@Override
	public void deleteUser(String username) {
		User dbUser = userRepository.findByUsername(username);
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER);
		}
		userRepository.delete(dbUser);
	}

}
