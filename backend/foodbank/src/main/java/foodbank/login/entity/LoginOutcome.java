package foodbank.login.entity;

/*
 * Created by: Lau Peng Liang, Bryan
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
