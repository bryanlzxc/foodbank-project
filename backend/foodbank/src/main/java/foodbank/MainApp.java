package foodbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author Bryan Lau <bryan.lau.2015@sis.smu.edu.sg>
 * @version 1.0
 *
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@EnableTransactionManagement
@EnableScheduling
@EnableCaching
public class MainApp {
	
	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
	}

}
