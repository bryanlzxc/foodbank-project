package foodbank.inventory.entity;

import org.springframework.data.annotation.Id;

/*
 * Created by: Lau Peng Liang, Bryan
 */

public class FoodItem {
	
	@Id
	/*
	 * description is the unique identifier which contains foodname, weight and halal status
	 */
	private String description;
	private int quantity;
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public FoodItem() {
		
	}
	
	public FoodItem(String description, int quantity) {
		this.description = description;
		this.quantity = quantity;
	}

}
