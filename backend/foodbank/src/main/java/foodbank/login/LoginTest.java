package foodbank.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("foodbank.login.controller")
public class LoginTest {
	
	public static void main(String[] args) {
		
		SpringApplication.run(LoginTest.class, args);
		
	}

}
