package foodbank.backup;

import foodbank.inventory.entity.FoodItem;

/*
 * Created by: Lau Peng Liang, Bryan
 */

public class Supply {

	public static int first = 0;
	public static int second = 0;
	public static int third = 0;
	
	private String classification;
	private FoodItem foodItem;
	
	public Supply(String classification, FoodItem foodItem) {
		this.classification = classification;
		this.foodItem = foodItem;
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
