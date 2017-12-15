package foodbank.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.exceptions.InvalidLoginException;
import foodbank.exceptions.UserException;
import foodbank.login.dto.LoginDTO;
import foodbank.login.service.LoginService;
import foodbank.user.entity.User;
import foodbank.user.repository.UserRepository;
import foodbank.util.MessageConstants.ErrorMessages;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void authenticate(LoginDTO loginDetails) {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByUsername(loginDetails.getUsername());
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER);
		}
		if(!dbUser.getPassword().equals(loginDetails.getPassword())) {
			throw new InvalidLoginException(ErrorMessages.INVALID_CREDENTIALS);
		}
		loginDetails.setUsertype(dbUser.getUsertype());
	}

}
