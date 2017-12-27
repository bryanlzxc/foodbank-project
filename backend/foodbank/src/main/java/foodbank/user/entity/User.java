package foodbank.user.entity;

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
	private String name;
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User() {
		// empty constructor required for put & post mappings
	}
	
	public User(String username, String password, String usertype, String name, String email) {
		this.username = username;
		this.password = password;
		this.usertype = usertype;
		this.name = name;
		this.email = email;
	}
	
	public User(String id, String username, String password, String usertype, String name, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.usertype = usertype;
		this.name = name;
		this.email = email;
	}
	
	@Override
	public String toString() {
		return id + ","
				+ username + ","
				+ password + ","
				+ usertype + ","
				+ name + ","
				+ email;
	}
}
