package foodbank.donor.entity;

import foodbank.inventory.entity.FoodItem;
import foodbank.util.InventorySerializer;

public class DonatedFoodItem extends FoodItem {
	
	public DonatedFoodItem() {}
	
	public DonatedFoodItem(String category, String classification, String description, Integer quantity) {
		super(category, classification, description, quantity, 
				InventorySerializer.foodItemMap.get(InventorySerializer.serials.get(category+classification+description)).getValue());
	}

}
