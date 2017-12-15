package foodbank.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import foodbank.admin.entity.AdminSettings;

@Repository
public interface AdminRepository extends MongoRepository<AdminSettings, String> {

	
	
}