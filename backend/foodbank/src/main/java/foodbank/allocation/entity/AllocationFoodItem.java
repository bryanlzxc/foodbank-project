package foodbank.allocation.entity;


public class AllocationFoodItem {
	private String category;
	private String classification;
	private String description;
	int req_qty;
	int al_qty;
	
	public AllocationFoodItem(String category, String classification, String description, int req_qty, int al_qty) {
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.req_qty = req_qty;
		this.al_qty = al_qty;
	}
	
	// Getters Setters
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
	public int getReq_qty() {
		return req_qty;
	}
	public void setReq_qty(int req_qty) {
		this.req_qty = req_qty;
	}
	public int getAl_qty() {
		return al_qty;
	}
	public void setAl_qty(int al_qty) {
		this.al_qty = al_qty;
	}
	
	
}
