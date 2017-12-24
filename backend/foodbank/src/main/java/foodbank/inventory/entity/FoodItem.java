package foodbank.inventory.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "FoodItems")
public class FoodItem {

	private String category;
	
	private String classification;
	
	private String description;
	
	private Integer quantity;

	public FoodItem() {}
	
	public FoodItem(String category, String classification, String description, Integer quantity) {
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.quantity = quantity;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
