package foodbank.allocation.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
/*
 * Created by Lim Jian Quan, Jaren
 */

import foodbank.allocation.entity.Allocation;

@Repository
public interface AllocationRepository extends MongoRepository<Allocation, String>, QueryDslPredicateExecutor<Allocation> {
	
	Allocation findById(String id);
	
	/*
	 * @param isAllocated
	 * true denotes successful allocation
	 * false denotes unsuccessful allocation  
	 */
	//List<Allocation> findAllocationBySuccessStatus(boolean isAllocated);	
	
}
