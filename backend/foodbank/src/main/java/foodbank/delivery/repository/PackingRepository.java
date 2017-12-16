package foodbank.delivery.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import foodbank.delivery.entity.PackingList;

public interface PackingRepository extends MongoRepository<PackingList, String> {
	
}
