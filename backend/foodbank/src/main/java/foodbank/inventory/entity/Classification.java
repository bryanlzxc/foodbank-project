package foodbank.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Created by: Lau Peng Liang, Bryan
 */

public class Classification {
	
	@Id
	private String id;
	
	private String classification;
	private List<FoodItem> foodItems;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
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
		
		switch(classification) {
			case "Baby_Cereals": this.id = "BF001"; break;
			case "Baby_Food": this.id = "BF002"; break;
			case "Baby_Milk_Powder": this.id = "BF003"; break;
			case "Baking_Powder": this.id = "BN001"; break;
			case "Flour": this.id = "BN002"; break;
			case "Other_Baking Needs": this.id = "BN003"; break;
			case "Premix_Powder": this.id = "BN004"; break;
		}
		this.classification = classification;
		this.foodItems = foodItems;
	}

}
