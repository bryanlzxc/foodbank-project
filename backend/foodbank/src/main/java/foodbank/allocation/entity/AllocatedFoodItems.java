package foodbank.allocation.entity;

public class AllocatedFoodItems {
	
	private String category;
	private String classification;
	private String description;
	private Integer allocatedQuantity;
	private Integer requestedQuantity;
	private Integer inventoryQuantity;
	
	public AllocatedFoodItems() {}
	
	public AllocatedFoodItems(String category, String classification, String description, Integer allocatedQuantity,
			Integer requestedQuantity, Integer inventoryQuantity) {
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.allocatedQuantity = allocatedQuantity;
		this.requestedQuantity = requestedQuantity;
		this.inventoryQuantity = inventoryQuantity;
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
	
	public Integer getAllocatedQuantity() {
		return allocatedQuantity;
	}
	
	public void setAllocatedQuantity(Integer allocatedQuantity) {
		this.allocatedQuantity = allocatedQuantity;
	}
	
	public Integer getRequestedQuantity() {
		return requestedQuantity;
	}
	
	public void setRequestedQuantity(Integer requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}
	
	public Integer getInventoryQuantity() {
		return inventoryQuantity;
	}
	
	public void setInventoryQuantity(Integer inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	@Override
	public String toString() {
		return category + ","
				+ classification + ","
				+ description + ","
				+ allocatedQuantity + ","
				+ requestedQuantity + ","
				+ inventoryQuantity;
	}
}
