package foodbank.request.repository;

/*
 * Created by: Ng Shirong
 */

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.request.entity.Request;

@Repository
public interface RequestRepository extends MongoRepository<Request, String>, QueryDslPredicateExecutor<Request> {

	Request findById(String id);

}
