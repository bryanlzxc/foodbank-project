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
	
	private static final String DEVELOPMENT_BACKUP_DATA_BUCKET = "foodbank-backup";
	private static final String DEPLOYMENT_BACKUP_DATA_BUCKET = "foodbank-backup-data";
	private static final String DEVELOPMENT_BACKUP_REPORT_BUCKET = "foodbank-backup-reporting";
	private static final String DEPLOYMENT_BACKUP_REPORT_BUCKET = "foodbank-backup-reports";
	public static boolean deploymentStatus = false;
	public static String dataBucket;
	public static String reportBucket;
	
	public static void main(String[] args) {
		dataBucket = deploymentStatus ? DEPLOYMENT_BACKUP_DATA_BUCKET : DEVELOPMENT_BACKUP_DATA_BUCKET;
		reportBucket = deploymentStatus ? DEPLOYMENT_BACKUP_REPORT_BUCKET : DEVELOPMENT_BACKUP_REPORT_BUCKET;
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
