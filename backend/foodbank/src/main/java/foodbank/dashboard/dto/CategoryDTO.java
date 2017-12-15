package foodbank.dashboard.dto;

import java.util.List;

import foodbank.inventory.entity.Classification;

public class CategoryDTO {
	
	private String category;
	private List<Classification> classifications;

	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public List<Classification> getClassifications() {
		return classifications;
	}
	
	public void setClassifications(List<Classification> classifications) {
		this.classifications = classifications;
	}

}
