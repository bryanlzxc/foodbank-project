package foodbank.user.service.impl;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.user.dto.PasswordDTO;
import foodbank.user.dto.UserDTO;
import foodbank.user.entity.User;
import foodbank.user.repository.UserRepository;
import foodbank.user.service.UserService;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.UserException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
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
		dbUser = user.transformToUser();
		dbUser.setPassword(BCrypt.hashpw(dbUser.getPassword(), BCrypt.gensalt()));
		userRepository.insert(dbUser);
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
		if(dbUser.getUsertype().equalsIgnoreCase("beneficiary")) {
			beneficiaryRepository.delete(beneficiaryRepository.findByUsername(username));
		}
		userRepository.delete(dbUser);
	}

	@Override
	public User getUserDetails(String username) {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByUsername(username);
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER);
		}
		return dbUser;
	}

	@Override
	public void changePassword(PasswordDTO passwordDetails) {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByUsername(passwordDetails.getUsername());
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER);
		}
		if(BCrypt.checkpw(passwordDetails.getOldPassword(), dbUser.getPassword())) {
			dbUser.setPassword(BCrypt.hashpw(passwordDetails.getNewPassword(), BCrypt.gensalt()));
		}
	}

}
