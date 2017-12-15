package foodbank.request.dto;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import foodbank.request.entity.Request;

public class BatchRequestDTO {
	
	@NotNull
	@JsonProperty("beneficiary")
	private String beneficiary;
	
	@NotNull
	@JsonProperty("requests")
	private List<Map<String, Object>> requests;

	public BatchRequestDTO(@JsonProperty("beneficiary") String beneficiary, @JsonProperty("requests") List<Map<String, Object>> requests) {
		this.beneficiary = beneficiary;
		this.requests = requests;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public List<Map<String, Object>> getRequests() {
		return requests;
	}

	public void setRequests(List<Map<String, Object>> requests) {
		this.requests = requests;
	}

}

class BatchRequest {
	
	@NotNull
	@JsonProperty("category")
	private String category;
	
	@NotNull
	@JsonProperty("classification")
	private String classification;
	
	@NotNull
	@JsonProperty("description")
	private String description;
	
	@NotNull
	@JsonProperty("quantity")
	private Integer quantity;

	public BatchRequest(@JsonProperty("category") String category, @JsonProperty("classification") String classification, 
			@JsonProperty("description") String description, @JsonProperty("quantity") Integer quantity) {
		this.category = category;
		this.classification = classification;
		this.description = description;
		this.quantity = quantity;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
