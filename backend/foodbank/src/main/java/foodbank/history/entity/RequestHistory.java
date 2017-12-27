package foodbank.history.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.util.DateParser;

@Document(collection = "PastRequests")
public class RequestHistory {
	
	@Id
	private String id;
	
	@DBRef
	private Beneficiary beneficiary;
	
	private String username;
	private Date requestCreationDate;
	private String category;
	private String classification;
	private String description;
	private Integer requestedQuantity;
	private Integer allocatedQuantity;
	
	public RequestHistory() {}
	
	public RequestHistory(Beneficiary beneficiary, Date requestCreationDate, 
			String category, String classification, String description,
			Integer requestedQuantity, Integer allocatedQuantity) {
		this.beneficiary = beneficiary;
		this.username = beneficiary.getUser().getUsername();
		this.requestCreationDate = requestCreationDate;
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.requestedQuantity = requestedQuantity;
		this.allocatedQuantity = allocatedQuantity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getRequestCreationDate() {
		return requestCreationDate;
	}

	public void setRequestCreationDate(Date requestCreationDate) {
		this.requestCreationDate = requestCreationDate;
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

	@Override
	public String toString() {
		return id + ","
				+ beneficiary.getId() + ","
				+ DateParser.displayDayMonthYearOnly(requestCreationDate) + ","
				+ category + ","
				+ classification + ","
				+ description + ","
				+ requestedQuantity + ","
				+ allocatedQuantity;
	}
}
