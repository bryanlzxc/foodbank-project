package foodbank.inventory.service;

import java.util.List;

import foodbank.inventory.dto.FoodItemDTO;
import foodbank.inventory.entity.FoodItem;

public interface FoodService {
	
	List<FoodItem> retrieveAllFoodItems();
	
	List<FoodItem> retrieveAllFoodItemsInCategory(final String categoryName);
	
	List<FoodItem> retrieveFoodItemsByCategoryAndClassification(final String categoryName, final String classificationName);
	
	int retrieveFoodItemQuantity(final String categoryName, final String classificationName, final String description);
	
	void createFoodItem(final FoodItemDTO foodItem);
	
	void overwriteFoodItem(final FoodItemDTO foodItem);
	
	void amendFoodItemQuantity(final FoodItemDTO foodItem);
	
	void overwriteFoodItems(final FoodItemDTO[] foodItems);
	
	void amendFoodItemsQuantity(final FoodItemDTO[] foodItems);

}
