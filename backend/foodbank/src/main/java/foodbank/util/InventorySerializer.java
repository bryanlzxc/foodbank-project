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
	
	public static final Map<String, UUID> serials = new HashMap<String, UUID>();
		
	public static final Map<UUID, FoodItem> foodItemMap = new HashMap<UUID, FoodItem>();
	
	@PostConstruct
	private void initializeSerials() {
		List<Category> categories = foodRepository.findAll();
		for(Category category : categories) {
			List<Classification> classifications = category.getClassification();
			for(Classification classification : classifications) {
				List<FoodItem> foodItems = classification.getFoodItems();
				for(FoodItem foodItem : foodItems) {
					String identifierKey = category.getCategory() + classification.getClassification() + foodItem.getDescription();
					UUID serialNo = UUID.randomUUID();
					InventorySerializer.serials.put(identifierKey, serialNo);
					InventorySerializer.foodItemMap.put(serialNo, foodItem);
				}
			}
		}
	}
	
	public static Integer retrieveQuantityOfItem(String category, String classification, String description) {
		return InventorySerializer.foodItemMap.get(InventorySerializer.serials.get(category+classification+description)).getQuantity();
	}

}
