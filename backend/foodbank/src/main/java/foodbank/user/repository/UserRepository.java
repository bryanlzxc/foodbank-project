package foodbank.user.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import foodbank.user.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	User findById(String id);
	
	User findByUsername(String username);
		
	List<User> findUsersByUsertype(String usertype);

}
