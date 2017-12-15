package foodbank.inventory.service;

import java.util.List;

import foodbank.inventory.dto.FoodItemDTO;
import foodbank.inventory.entity.Category;
import foodbank.inventory.entity.Classification;
import foodbank.inventory.entity.FoodItem;

public interface FoodService {
	
	List<Category> retrieveAllCategories();
	
	List<Classification> retrieveAllClassificationsInCategory(final String categoryName);
	
	List<FoodItem> retrieveFoodItemsByCategoryAndClassification(final String categoryName, final String classificationName);
	
	int retrieveFoodItemQuantity(String foodItemDescription);
	
	void overwriteFoodItem(final FoodItemDTO foodItem);
	
	void amendFoodItemQuantity(final FoodItemDTO foodItem);
	
	void overwriteFoodItems(final FoodItemDTO[] foodItems);
	
	void amendFoodItemsQuantity(final FoodItemDTO[] foodItems);

}
