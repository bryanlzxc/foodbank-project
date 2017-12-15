package foodbank.login.service;

import foodbank.login.dto.LoginDTO;

public interface LoginService {

	void authenticate(final LoginDTO loginDetails);
	
}
