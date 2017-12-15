package foodbank.inventory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.exceptions.InvalidFoodException;
import foodbank.inventory.dto.FoodItemDTO;
import foodbank.inventory.entity.Category;
import foodbank.inventory.entity.Classification;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;
import foodbank.inventory.service.FoodService;
import foodbank.util.MessageConstants.ErrorMessages;

@Service
public class FoodServiceImpl implements FoodService {
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Override
	public List<Category> retrieveAllCategories() {
		// TODO Auto-generated method stub
		return foodRepository.findAll();
	}

	@Override
	public List<Classification> retrieveAllClassificationsInCategory(String categoryName) {
		// TODO Auto-generated method stub
		List<Category> categories = foodRepository.findAll();
		for(Category category : categories) {
			if(category.getCategory().equals(categoryName)) {
				return category.getClassification();
			}
		}
		return null;
	}

	@Override
	public List<FoodItem> retrieveFoodItemsByCategoryAndClassification(String categoryName, String classificationName) {
		// TODO Auto-generated method stub
		List<Category> categories = foodRepository.findAll();
		for(Category category : categories) {
			if(category.getCategory().equals(categoryName)) {
				List<Classification> classifications = category.getClassification();
				for(Classification classification : classifications) {
					if(classification.getClassification().equals(classificationName)) {
						return classification.getFoodItems();
					}
				}
			}
		}
		return null;
	}

	@Override
	public int retrieveFoodItemQuantity(String foodItemDescription) {
		// TODO Auto-generated method stub
		List<Category> categories = foodRepository.findAll();
		for(Category category : categories) {
			List<Classification> classifications = category.getClassification();
			for(Classification classification : classifications) {
				List<FoodItem> foodItems = classification.getFoodItems();
				for(FoodItem foodItem : foodItems) {
					if(foodItem.getDescription().equals(foodItemDescription)) {
						return foodItem.getQuantity();
					}
				}
			}
		}
		return 0;
	}

	@Override
	public void overwriteFoodItem(FoodItemDTO foodItem) {
		// TODO Auto-generated method stub
		// This method overwrites the existing object in DB
		boolean itemFound = false;
		List<Category> categories = foodRepository.findAll();
		for(Category category : categories) {
			List<Classification> classifications = category.getClassification();
			for(Classification classification : classifications) {
				List<FoodItem> foodItems = classification.getFoodItems();
				for(FoodItem dbFoodItem : foodItems) {
					if(dbFoodItem.getDescription().equals(foodItem.getDescription())) {
						itemFound = true;
						dbFoodItem.setQuantity(foodItem.getQuantity());
						foodRepository.save(category);
						break;
					}
				}
			}
		}
		if(!itemFound) {
			throw new InvalidFoodException(ErrorMessages.NO_SUCH_ITEM);
		}
	}

	@Override
	public void amendFoodItemQuantity(FoodItemDTO foodItem) {
		// TODO Auto-generated method stub
		// This method increments/decrements the existing DB object's quantity
		boolean itemFound = false;
		List<Category> categories = foodRepository.findAll();
		for(Category category : categories) {
			List<Classification> classifications = category.getClassification();
			for(Classification classification : classifications) {
				List<FoodItem> foodItems = classification.getFoodItems();
				for(FoodItem dbFoodItem : foodItems) {
					if(dbFoodItem.getDescription().equals(foodItem.getDescription())) {
						itemFound = true;
						dbFoodItem.setQuantity(foodItem.getQuantity() + dbFoodItem.getQuantity());
						foodRepository.save(category);
						break;
					}
				}
			}
		}
		if(!itemFound) {
			throw new InvalidFoodException(ErrorMessages.NO_SUCH_ITEM);
		}
	}

	@Override
	public void overwriteFoodItems(FoodItemDTO[] foodItems) {
		// TODO Auto-generated method stub
		for(int i = 0; i < foodItems.length; i++) {
			FoodItemDTO foodItem = foodItems[i];
			overwriteFoodItem(foodItem);
		}
	}
	
	@Override
	public void amendFoodItemsQuantity(FoodItemDTO[] foodItems) {
		// TODO Auto-generated method stub
		for(int i = 0; i < foodItems.length; i++) {
			FoodItemDTO foodItem = foodItems[i];
			amendFoodItemQuantity(foodItem);
		}
	}

}
