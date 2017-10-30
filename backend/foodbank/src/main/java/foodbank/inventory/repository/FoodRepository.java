package foodbank.inventory.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import foodbank.inventory.entity.Category;
import foodbank.inventory.entity.Classification;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@Repository
public interface FoodRepository extends MongoRepository<Category, String>, QueryDslPredicateExecutor<Category> {
	
	List<Classification> findByCategory(String category);
	
}
