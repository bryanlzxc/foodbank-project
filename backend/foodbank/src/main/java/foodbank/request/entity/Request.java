package foodbank.request.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.inventory.entity.FoodItem;
import foodbank.util.DateParser;
import foodbank.util.InventorySerializer;

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
	
	@DBRef
	private Beneficiary beneficiary;
	
	private FoodItem foodItem;
	private Integer inventoryQuantity;
	private String requestCreationDate = DateParser.getCurrentDate(new Date());
	
	public Request() {}
	
	public Request(String id, Beneficiary beneficiary, FoodItem foodItem, String requestCreationDate) {
		this(beneficiary, foodItem);
		this.id = id;
		this.requestCreationDate = requestCreationDate;
	}
	
	public Request(Beneficiary beneficiary, FoodItem foodItem) {
		this.beneficiary = beneficiary;
		this.foodItem = foodItem;
		this.inventoryQuantity = InventorySerializer.retrieveQuantityOfItem(
				foodItem.getCategory(), foodItem.getClassification(), foodItem.getDescription());
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
	
	public FoodItem getFoodItem() {
		return foodItem;
	}
	
	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
	}
	
	public String getRequestCreationDate() {
		return requestCreationDate;
	}

	public Integer getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(Integer inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
	
	@Override 
	public String toString() {
		return id + ","
				+ foodItem.getCategory() + ","
				+ foodItem.getClassification() + ","
				+ foodItem.getDescription() + ","
				+ inventoryQuantity + ","
				+ requestCreationDate + ","
				+ beneficiary.getId();
	}
	
}