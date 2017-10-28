package foodbank.admin.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import foodbank.admin.entity.Admin;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String>{

	Admin findById(String id);
	
	Admin findByWindowEndDateTime(String windowEndDateTime);
	
	List<Admin> findAdminByWindowEndDateTime(String windowEndDateTime);
	
}