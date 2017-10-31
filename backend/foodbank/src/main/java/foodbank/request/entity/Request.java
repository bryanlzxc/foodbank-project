package foodbank.request.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.inventory.entity.FoodItem;
import foodbank.util.DateParser;

/*
 * Done By Ng Shirong
 */

@Document(collection = "Request")
public class Request {
	//request will be called under beneficiary, when they can see a list of current window requested food stuff
	//request will be called under allocation, when we need to see who requested what and allocate
	
	//request will have a post where we delete all after allocation is done for window to reset for next window
	
	@Id
	private String id;
	
	private Beneficiary beneficiary;
	private FoodItem foodItem;
	private final String requestCreationDate = DateParser.getCurrentDate(new Date());
	
	public Request(Beneficiary beneficiary, FoodItem foodItem) {
		this.beneficiary = beneficiary;
		this.foodItem = foodItem;
	}
	
	public Request() {
		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Beneficiary getBeneficiary() {
		return beneficiary;
	}
	
	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}
	
	public String getBeneficiaryName() {
		return this.beneficiary.getName();
	}
	
	public void setBeneficiaryName(String name) {
		this.beneficiary.setName(name);
	}
	
	public FoodItem getFoodItem() {
		return foodItem;
	}
	
	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
	}
	
	public String getFoodItemDescription() {
		return this.foodItem.getDescription();
	}
	
	public void setFoodItemDescription(String description) {
		this.foodItem.setDescription(description);
	}
	
	public int getFoodItemQuantity() {
		return foodItem.getQuantity();
	}
	
	public void setFoodItemQuantity(int quantity) {
		this.foodItem.setQuantity(quantity);
	}
	
	public String getRequestCreationDate() {
		return requestCreationDate;
	}
	
}