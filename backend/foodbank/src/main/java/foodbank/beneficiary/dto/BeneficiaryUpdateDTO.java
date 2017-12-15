package foodbank.beneficiary.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BeneficiaryUpdateDTO {
	
	@NotNull
	@JsonProperty("beneficiary")
	private String beneficiary;
	
	@NotNull
	@JsonProperty("score")
	private Double score;

	public BeneficiaryUpdateDTO(@JsonProperty("beneficiary") String beneficiary, @JsonProperty("score") Double score) {
		this.beneficiary = beneficiary;
		this.score = score;
	}
	
	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}
