package foodbank.backup;

/*
 * Created by: Lau Peng Liang, Bryan
 */

/*
 * This class is marked for deletion.
 * Used to be in package: foodbank.login.entity
 */
public class LoginOutcome {
	
	private boolean isLoginSuccessful;
	private String usertype;
	
	public String getUsertype() {
		return usertype;
	}
	
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	public boolean getLoginOutcome() {
		return isLoginSuccessful;
	}
	
	public void setLoginOutcome(boolean isLoginSuccessful) {
		this.isLoginSuccessful = isLoginSuccessful;
	}
	
	public LoginOutcome(boolean isLoginSuccessful, String usertype) {
		this.isLoginSuccessful = isLoginSuccessful;
		this.usertype = usertype;
	}

}
