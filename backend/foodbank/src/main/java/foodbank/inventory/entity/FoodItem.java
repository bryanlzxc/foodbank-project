package foodbank.inventory.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "FoodItems")
public class FoodItem {

	@Id
	private String id;
	
	private String category;
	
	private String classification;
	
	private String description;
	
	private Integer quantity;
	
	private Double value = 0.0;

	public FoodItem() {}
	
	public FoodItem(String id, String category, String classification, String description, Integer quantity, Double value) {
		this(category, classification, description, quantity, value);
		this.id = id;
	}
	
	/* 
	 * This constructor should only be used for PackedFoodItem and RequestServiceImpl
	 */
	public FoodItem(String category, String classification, String description, Integer quantity) {
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.quantity = quantity;
	}
	
	public FoodItem(String category, String classification, String description, Integer quantity, Double value) {
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.quantity = quantity;
		this.value = value;
	}
	
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
	
	public Double getValue() {
		return value;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return id + "," 
				+ category + "," 
				+ classification + ","
				+ description + ","
				+ quantity + "," +
				+ value;
	}
}
