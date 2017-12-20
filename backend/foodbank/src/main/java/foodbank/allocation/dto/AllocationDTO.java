package foodbank.allocation.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import foodbank.inventory.entity.FoodItem;

public class AllocationDTO {
	
	@JsonProperty("id")
	private String id;
	
	private String beneficiary;
	
	@NotNull
	@JsonProperty("allocated-items")
	private List<FoodItem> allocatedItems;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<FoodItem> getAllocatedItems() {
		return allocatedItems;
	}

	public void setAllocatedItems(List<FoodItem> allocatedItems) {
		this.allocatedItems = allocatedItems;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}
	
}
