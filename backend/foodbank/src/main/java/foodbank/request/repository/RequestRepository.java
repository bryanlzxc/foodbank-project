package foodbank.request.repository;
/*
 * Done By Shirong
 */

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import foodbank.request.entity.Request;

@Repository
public interface RequestRepository extends MongoRepository<Request, String>{

	List<Request> findRequestsByBeneficiary(String beneficary);

	Request findById(String id);

	List<Request> findRequestsByDescription(String description);

	
}
