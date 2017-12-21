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
	
	private String category;
	private String classification;
	private FoodItem foodItem;
	private Integer inventoryQuantity;
	private final String requestCreationDate = DateParser.getCurrentDate(new Date());
	
	public Request() {}
	
	public Request(Beneficiary beneficiary, String category, String classification, FoodItem foodItem) {
		this.beneficiary = beneficiary;
		this.category = category;
		this.classification = classification;
		this.foodItem = foodItem;
		this.inventoryQuantity = InventorySerializer.retrieveQuantityOfItem(
				category, classification, foodItem.getDescription());
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
	
}