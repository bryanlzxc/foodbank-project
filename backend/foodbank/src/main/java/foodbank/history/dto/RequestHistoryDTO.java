package foodbank.history.dto;

import java.util.Date;

import foodbank.request.dto.RequestDTO;
import foodbank.util.DateParser;

public class RequestHistoryDTO {
	
	private String beneficiary;
	private String category;
	private String classification;
	private String description;
	private Integer requestedQuantity;
	private Integer allocatedQuantity;
	private Date requestCreationDate;

	public RequestHistoryDTO(String beneficiary, String category, String classification, String description,
			Integer requestedQuantity, Integer allocatedQuantity, Date requestCreationDate) {
		// TODO Auto-generated constructor stub
		this.beneficiary = beneficiary;
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.requestedQuantity = requestedQuantity;
		this.allocatedQuantity = allocatedQuantity;
		this.requestCreationDate = requestCreationDate;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(Integer requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

	public Integer getAllocatedQuantity() {
		return allocatedQuantity;
	}

	public void setAllocatedQuantity(Integer allocatedQuantity) {
		this.allocatedQuantity = allocatedQuantity;
	}

	public String getRequestCreationDate() {
		return DateParser.displayDayMonthYearOnly(requestCreationDate);
	}

	public void setRequestCreationDate(Date requestCreationDate) {
		this.requestCreationDate = requestCreationDate;
	}

}
