package foodbank.login.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Created by Lau Peng Liang, Bryan
 */

@Document(collection = "Users")
public class User {
	
	@Id
	private String id;
	
	private String username;
	private String password;
	private String usertype;
	
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
	
	public String getUsertype() {
		return usertype;
	}
	
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	public User(String username, String password, String usertype) {
		this.username = username;
		this.password = password;
		this.usertype = usertype;
	}
	

}
