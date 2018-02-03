package foodbank.reporting.entity;

import foodbank.util.InventorySerializer;

public class InvoiceLineItem {
	
	private String itemNo;
	private String category;
	private String classification;
	private String description;
	private int quantity;
	private double value;
	private double totalValue;
	
	public InvoiceLineItem() {}
	
	public InvoiceLineItem(String category, String classification, String description, int quantity) {
		super();
		this.itemNo = InventorySerializer.retrieveItemId(category, classification, description);
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.quantity = quantity;
		this.value = InventorySerializer.retrieveValueOfItem(category, classification, description);
		this.totalValue = value * quantity;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
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
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public double getTotalValue() {
		return totalValue;
	}
	
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	
}
