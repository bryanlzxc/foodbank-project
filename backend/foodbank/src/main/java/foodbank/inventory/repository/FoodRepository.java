package foodbank.inventory.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import foodbank.inventory.entity.FoodItem;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@Repository
public interface FoodRepository extends MongoRepository<FoodItem, String>, QueryDslPredicateExecutor<FoodItem> {
	
	List<FoodItem> findByCategory(String category);
	
	List<FoodItem> findByCategoryAndClassification(String category, String classification);
	
	FoodItem findByCategoryAndClassificationAndDescription(String category, String classification, String description);
	
}
