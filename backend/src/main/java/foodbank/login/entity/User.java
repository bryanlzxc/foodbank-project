package foodbank.login.entity;

import org.springframework.data.annotation.Id;

/*
 * Created by Lau Peng Liang, Bryan
 */

public class User {
	
	@Id
	private String id;
	
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
