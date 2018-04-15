package foodbank.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.user.entity.User;

/**
 * 
 * @author Bryan Lau <bryan.lau.2015@sis.smu.edu.sg>
 * @version 1.0
 *
 */
public interface UserRepository extends JpaRepository<User, Long>{

	/**
	 * 
	 * @param username
	 * @return The user that has been found by JPA Repository's find by method
	 */
	User findByUsernameIgnoreCase(String username);
	
	/**
	 * 
	 * @param usertype
	 * @return All the users that have been found by JPA Repository's find by method
	 */
	List<User> findUsersByUsertype(String usertype);
	
	/**
	 * 
	 * @param email
	 * @return The user that has been found by JPA Repository's find by method
	 */
	User findByEmailIgnoreCase(String email);
	
}
