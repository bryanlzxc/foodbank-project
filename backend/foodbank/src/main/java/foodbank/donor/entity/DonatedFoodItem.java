package foodbank.donor.entity;

import foodbank.inventory.entity.FoodItem;

public class DonatedFoodItem extends FoodItem {
	
	public DonatedFoodItem() {}
	
	public DonatedFoodItem(String category, String classification, String description, Integer quantity) {
		super(category, classification, description, quantity);
	}

}
