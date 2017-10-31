package foodbank.inventory.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbank.inventory.entity.Category;
import foodbank.inventory.entity.Classification;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@RestController
@RequestMapping("/food-items")
public class FoodController {
	
	private FoodRepository foodRepository;
	
	public FoodController(FoodRepository foodRepository) {
		this.foodRepository = foodRepository;
	}
	
	@GetMapping("/display-all")
	public List<Category> getAllCategories() {
		return this.foodRepository.findAll();
	}
	
	@GetMapping("/category={category}/display-all")
	public List<Classification> getAllClassificationsInCategory(@PathVariable("category") String category) {
		List<Category> categories = this.foodRepository.findAll();
		for(Category categoryObj : categories) {
			if(categoryObj.getCategory().equals(category)) {
				return categoryObj.getClassification();
			}
		}
		return new ArrayList<Classification>();
	}
	
	@GetMapping("/{id}")
	public Category getById(@PathVariable("id") String id) {
		return this.foodRepository.findOne(id);
	}
	
	@GetMapping("/category-id={id}/display-all")
	public List<Classification> displayAllClassificationsInCategory(@PathVariable("id") String id) {
		Category category = this.foodRepository.findOne(id);
		return category.getClassification();
	}
	
	@GetMapping("/category={category}/classification={classification}/display-all")
	public List<FoodItem> getFoodItemsInClassification(@PathVariable("category") String category, @PathVariable("classification") String classification) {
		List<Classification> classifications = getAllClassificationsInCategory(category);
		for(Classification c : classifications) {
			if(c.getClassification().equals(classification)) {
				return c.getFoodItems();
			}
		}
		return new ArrayList<FoodItem>();
	}
	
	@GetMapping("/item={description}/get-quantity")
	public int getFoodItemQuantity(@PathVariable("description") String description) {
		System.out.println("Testing = " + getFoodItemByDescription(description).toString());
		return getFoodItemByDescription(description).getQuantity();
	}
	
	@PostMapping("/update-quantity")
	public void updateFoodItem(@RequestBody FoodItem foodItem) {
		String description = foodItem.getDescription();
		String[] indexer = findItemGroup(description);
		FoodItem itemToModify = null;
		if(indexer != null) {
			itemToModify = getFoodItem(indexer[0], indexer[1], description);
			if(itemToModify != null) {
				itemToModify.setQuantity(foodItem.getQuantity());
				Category category = retrieveCategoryObject(indexer[0]);
				category.updateFoodItem(itemToModify);
				this.foodRepository.save(category);
			}
		}
	}
	
	@PostMapping("/update-batch")
	public void updateFoodItems(@RequestBody FoodItem[] foodItems) {
		for(int i = 0; i < foodItems.length; i++) {
			FoodItem foodItem = foodItems[i];
			updateFoodItem(foodItem);
		}
	}
	
	/*
	 * Lazy method of retrieving classification string of food item
	 */	
	public String findClassificationOfFoodItem(FoodItem foodItem) {
		String[] indexer = findItemGroup(foodItem.getDescription());
		return indexer[1];
	}
	
	private Category retrieveCategoryObject(String category) {
		List<Category> categories = getAllCategories();
		for(Category categoryObj : categories) {
			if(categoryObj.getCategory().equals(category)) {
				return categoryObj;
			}
		}
		return null;
	}
	
	/*
	 * Lazy method of getting FoodItem by description
	 */
	private FoodItem getFoodItemByDescription(String description) {
		String[] indexer = findItemGroup(description);
		return getFoodItem(indexer[0], indexer[1], description);
	}
	
	private FoodItem getFoodItem(String category, String classification, String description) {
		List<FoodItem> foodItems = getFoodItemsInClassification(category, classification);
		for(FoodItem foodItem : foodItems) {
			if(foodItem.getDescription().equals(description)) {
				return foodItem;
			}
		}
		return null;
	}
		
	private String[] findItemGroup(String description) {
		List<Category> categories = getAllCategories();
		for(Category category : categories) {
			List<Classification> classifications = category.getClassification();
			for(Classification classification : classifications) {
				List<FoodItem> foodItems = classification.getFoodItems();
				for(FoodItem foodItem : foodItems) {
					if(foodItem.getDescription().equals(description)) {
						String[] toReturn = {category.getCategory(), classification.getClassification()};
						return toReturn;
					}
				}
			}
		}
		return null;
	}

}
