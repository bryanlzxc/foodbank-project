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