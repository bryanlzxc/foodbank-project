package foodbank.login.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.admin.entity.AdminSettings;
import foodbank.admin.repository.AdminRepository;
import foodbank.login.dto.LoginDTO;
import foodbank.login.service.LoginService;
import foodbank.user.entity.User;
import foodbank.user.repository.UserRepository;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.InvalidLoginException;
import foodbank.util.exceptions.UserException;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;

	private static final String ADMIN_ID = "5a45bb3ff36d287dc13af228";
	
	@Override
	public void authenticate(LoginDTO loginDetails) {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByUsername(loginDetails.getUsername());
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER);
		}
		if(!BCrypt.checkpw(loginDetails.getPassword(), dbUser.getPassword())) {
			throw new InvalidLoginException(ErrorMessages.INVALID_CREDENTIALS);
		}
		loginDetails.setUsertype(dbUser.getUsertype());
	}

	@Override
	public void authenticateVolunteers(String dailyPassword) {
		// TODO Auto-generated method stub
		AdminSettings adminSettings = adminRepository.findOne(ADMIN_ID);
		if(!adminSettings.getDailyPassword().equals(dailyPassword)) {
			throw new InvalidLoginException(ErrorMessages.INVALID_CREDENTIALS);
		}
	}

}
