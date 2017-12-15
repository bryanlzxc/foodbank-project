package foodbank.dashboard.dto;

import foodbank.inventory.entity.FoodItem;

/*
 * Created by: Lau Peng Liang, Bryan
 * Creation date: 06/12/2017
 */

public class DashboardDTO {
	
	private String classification;
	private FoodItem foodItem;
	
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
