package foodbank.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@Document(collection = "Items")
public class Category {

	@Id
	private String id;
	
	private String category;
	private List<Classification> classifications;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public List<Classification> getClassification() {
		return classifications;
	}
	
	protected Category() {
		this.classifications = new ArrayList<Classification>();
	}
	
	public Category(String category, List<Classification> classifications) {
		this.category = category;
		this.classifications = classifications;
	}
	
	public void updateFoodItem(FoodItem foodItem) {
		for(Classification classification : classifications) {
			List<FoodItem> foodItems = classification.getFoodItems();
			for(FoodItem toModify : foodItems) {
				if(toModify.getDescription().equals(foodItem.getDescription())) {
					toModify.setQuantity(foodItem.getQuantity());
				}
			}
		}
	}
	
}
