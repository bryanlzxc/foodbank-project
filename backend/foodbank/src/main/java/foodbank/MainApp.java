package foodbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@SpringBootApplication
public class MainApp {
	
	public static void main(String[] args) {
		
		SpringApplication.run(MainApp.class, args);
		
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            public void addCorsMapping(CorsRegistry registry) {
                registry.addMapping("/users/display-all").allowedMethods("GET","OPTIONS").allowedOrigins("http://localhost:4200").allowedHeaders("*");
                registry.addMapping("/authenticate").allowedMethods("POST","OPTIONS").allowedOrigins("http://localhost:4200").allowedHeaders("*").allowCredentials(true);
            }
        };
        
    }

}
