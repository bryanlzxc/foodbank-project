package foodbank.backup;

import java.util.ArrayList;

import foodbank.inventory.entity.FoodItem;

public class AllocationUpdate {
	
	// Represents the beneficiary's name
	public String name;
	public ArrayList<FoodItem> foodItems;
	
	public AllocationUpdate() {
		
	}
	public AllocationUpdate(String name, ArrayList<FoodItem> foodItems) {
		this.name = name;
		this.foodItems = foodItems;
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<FoodItem> getFoodItems() {
		return foodItems;
	}
	public void setFoodItems(ArrayList<FoodItem> foodItems) {
		this.foodItems = foodItems;
	}
	
	

}
