package foodbank.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		List<FoodItem> foodItems = foodRepository.findAll();
		for(FoodItem foodItem : foodItems) {
			String identifierKey = foodItem.getCategory() + foodItem.getClassification() + foodItem.getDescription();
			UUID serialNo = UUID.randomUUID();
			InventorySerializer.serials.put(identifierKey, serialNo);
			InventorySerializer.foodItemMap.put(serialNo, foodItem);
		}
	}
	
	public static String retrieveItemId(String category, String classification, String description) {
		return InventorySerializer.foodItemMap.get(InventorySerializer.serials.get(category+classification+description)).getId();
	}
	
	public static Integer retrieveQuantityOfItem(String category, String classification, String description) {
		return InventorySerializer.foodItemMap.get(InventorySerializer.serials.get(category+classification+description)).getQuantity();
	}
	
	public static Double retrieveValueOfItem(String category, String classification, String description) {
		return InventorySerializer.foodItemMap.get(InventorySerializer.serials.get(category+classification+description)).getValue();
	}
	
	public static void updateQuantity(String category, String classification, String description, Integer quantity) {
		UUID key = InventorySerializer.serials.get(category+classification+description);
		if(key == null) {
			key = UUID.randomUUID();
			InventorySerializer.serials.put(category+classification+description, key);
			InventorySerializer.foodItemMap.put(key, new FoodItem(category, classification, description, quantity));
		}
		FoodItem foodItem = InventorySerializer.foodItemMap.get(key);
		foodItem.setQuantity(quantity);
		InventorySerializer.foodItemMap.replace(key, foodItem);
	}
	
}
