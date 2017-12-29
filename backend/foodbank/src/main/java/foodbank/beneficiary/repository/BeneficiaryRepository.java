package foodbank.beneficiary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import foodbank.beneficiary.entity.Beneficiary;

/*
 * Done by Shirong
 */

public interface BeneficiaryRepository extends MongoRepository<Beneficiary, String>{
	
	Beneficiary findById(String id);
	
	Beneficiary findByUsername(String beneficiary);
	
}
