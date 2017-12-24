package foodbank.allocation.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import foodbank.allocation.entity.AllocatedFoodItems;
import foodbank.inventory.entity.FoodItem;

public class AllocationDTO {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("beneficiary")
	private String beneficiary;
	
	@NotNull
	@JsonProperty("allocatedItems")
	private List<FoodItem> allocatedItems;
	
	public AllocationDTO() {}

	public AllocationDTO(@JsonProperty("id") String id, @JsonProperty("beneficiary") String beneficiary, @JsonProperty("allocatedItems") List<FoodItem> allocatedItems) {
		this.id = id;
		this.beneficiary = beneficiary;
		this.allocatedItems = allocatedItems;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public List<FoodItem> getAllocatedItems() {
		return allocatedItems;
	}

	public void setAllocatedItems(List<FoodItem> allocatedItems) {
		this.allocatedItems = allocatedItems;
	}
	
	public String toString() {
		return id + " " + beneficiary;
	}

}
