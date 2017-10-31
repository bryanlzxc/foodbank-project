package foodbank.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

/*
 * Created by: Lau Peng Liang, Bryan
 */

public class Classification {
	
	@Id 
	private String classification;
	
	private List<FoodItem> foodItems;
	
	public String getClassification() {
		return classification;
	}
	
	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	public List<FoodItem> getFoodItems() {
		return foodItems;
	}
	
	protected Classification() {
		this.foodItems = new ArrayList<FoodItem>();
	}
	
	public Classification(String classification, List<FoodItem> foodItems) {
		
		this.classification = classification;
		this.foodItems = foodItems;
	}

}
