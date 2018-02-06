package foodbank.packing.dto;

public class LivePackingDTO {
	
	private String category;
	private String classification;
	private String description;
	private Integer packedQuantity;
	private Boolean checkboxStatus;
	
	public LivePackingDTO() {}
	
	public LivePackingDTO(String category, String classification, String description, Integer packedQuantity, Boolean checkboxStatus) {
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.packedQuantity = packedQuantity;
		this.checkboxStatus = checkboxStatus;
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

	public Integer getPackedQuantity() {
		return packedQuantity;
	}

	public void setPackedQuantity(Integer packedQuantity) {
		this.packedQuantity = packedQuantity;
	}

	public Boolean getCheckboxStatus() {
		return checkboxStatus;
	}

	public void setCheckboxStatus(Boolean checkboxStatus) {
		this.checkboxStatus = checkboxStatus;
	}

}
