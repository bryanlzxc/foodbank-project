package foodbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import foodbank.inventory.repository.FoodRepository;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@SpringBootApplication
@EnableMongoRepositories
@EnableScheduling
public class MainApp {
	
	private static final String DEVELOPMENT_BACKUP_BUCKET = "foodbank-backup";
	private static final String DEPLOYMENT_BACKUP_BUCKET = "foodbank-backup-data";
	public static boolean deploymentStatus = false;
	public static String bucket;
	
	public static void main(String[] args) {
		bucket = deploymentStatus ? DEPLOYMENT_BACKUP_BUCKET : DEVELOPMENT_BACKUP_BUCKET;
		SpringApplication.run(MainApp.class, args);
	}
	
	/*
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            public void addCorsMapping(CorsRegistry registry) {
                registry.addMapping("/users/display-all").allowedMethods("GET","OPTIONS").allowedOrigins("http://localhost:4200").allowedHeaders("*");
                registry.addMapping("/authenticate").allowedMethods("POST","OPTIONS").allowedOrigins("http://localhost:4200").allowedHeaders("*").allowCredentials(true);
            }
        };   
    }
	*/

}
