package foodbank.donor.entity;

import foodbank.inventory.entity.FoodItem;

public class Donation {
	
	private String category;
	private String classification;
	private FoodItem foodItem;

	public Donation(String category, String classification, FoodItem foodItem) {
		this.category = category;
		this.classification = classification;
		this.foodItem = foodItem;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public FoodItem getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
	}
	
}
