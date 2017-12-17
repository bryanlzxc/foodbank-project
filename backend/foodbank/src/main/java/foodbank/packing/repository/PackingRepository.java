package foodbank.packing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import foodbank.packing.entity.PackingList;

public interface PackingRepository extends MongoRepository<PackingList, String> {
	
}
