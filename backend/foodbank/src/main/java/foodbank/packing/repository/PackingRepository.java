package foodbank.packing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import foodbank.packing.entity.PackingList;

@Repository
public interface PackingRepository extends MongoRepository<PackingList, String> {
	
	
	
}
