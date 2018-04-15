package foodbank.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.admin.entity.AdminSettings;

/**
 * 
 * @author Bryan Lau <bryan.lau.2015@sis.smu.edu.sg>
 * @version 1.0
 *
 */
public interface AdminRepository extends JpaRepository<AdminSettings, Long>{
	
	/**
	 * Retrieves the admin settings with the assistance of JPA Repository's find by id function
	 * @param id
	 * @return Admin Settings that have been stored in the database
	 */
	AdminSettings findById(Long id);
	
}
