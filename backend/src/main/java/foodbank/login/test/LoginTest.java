package foodbank.login.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import foodbank.login.controller.LoginController;

@SpringBootApplication
public class LoginTest {
	
	public static void main(String[] args) {
		
		SpringApplication.run(LoginController.class, args);
		
	}

}
