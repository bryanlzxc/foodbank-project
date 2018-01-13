package foodbank.donor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import foodbank.donor.entity.Donor;

@Repository
public interface DonorRepository extends MongoRepository<Donor, String> {
	
	Donor findByName(String name);
	
	Donor findById(String id);

}
