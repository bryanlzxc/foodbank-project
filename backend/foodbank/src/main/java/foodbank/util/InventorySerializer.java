package foodbank.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Component;

import foodbank.inventory.entity.Category;
import foodbank.inventory.entity.Classification;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;

@Component
public class InventorySerializer {
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Transient
	private static Map<String, UUID> transientSerials;
	
	public static final Map<String, UUID> serials = transientSerials;
	
	@Transient
	private static Map<UUID, FoodItem> transientFoodItemMap;
	
	public static final Map<UUID, FoodItem> foodItemMap = transientFoodItemMap;
	
	@PostConstruct
	private void initializeSerials() {
		Map<String, UUID> serials = new HashMap<String, UUID>();
		Map<UUID, FoodItem> foodItemMap = new HashMap<UUID, FoodItem>();
		List<Category> categories = foodRepository.findAll();
		for(Category category : categories) {
			List<Classification> classifications = category.getClassification();
			for(Classification classification : classifications) {
				List<FoodItem> foodItems = classification.getFoodItems();
				for(FoodItem foodItem : foodItems) {
					String identifierKey = category.getCategory() + classification.getClassification() + foodItem.getDescription();
					UUID serialNo = UUID.randomUUID();
					serials.put(identifierKey, serialNo);
					foodItemMap.put(serialNo, foodItem);
				}
			}
		}
		transientSerials = serials;
		transientFoodItemMap = foodItemMap;
	}

}
