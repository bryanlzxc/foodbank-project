package foodbank.packing.entity;

import foodbank.inventory.entity.FoodItem;

public class PackedFoodItem extends FoodItem {
	
	private Integer allocatedQuantity = 0;
	private Boolean packedStatus = false;
	
	public PackedFoodItem() {}

	public PackedFoodItem(String category, String classification, String description, Integer allocatedQuantity, Integer packedQuantity) {
		super(category, classification, description, packedQuantity);
		this.allocatedQuantity = allocatedQuantity;
	}
	
	public Integer getAllocatedQuantity() {
		return allocatedQuantity;
	}

	public void setAllocatedQuantity(Integer allocatedQuantity) {
		this.allocatedQuantity = allocatedQuantity;
	}
	
	public Boolean getPackedStatus() {
		return packedStatus;
	}

	public void setPackedStatus(Boolean packedStatus) {
		this.packedStatus = packedStatus;
	}
	
	@Override
	public String toString() {
		return  "{" + getCategory() + "+" + getClassification() + "+" + getDescription() + "+"
				+ getAllocatedQuantity() + "+" + getQuantity() + "}";
	}

}
