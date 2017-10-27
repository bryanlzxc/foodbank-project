package foodbank.login.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import foodbank.login.entity.User;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	//User findByUsername(@Param("username") String username);

}
