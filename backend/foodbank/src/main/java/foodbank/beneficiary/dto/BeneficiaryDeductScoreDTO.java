package foodbank.beneficiary.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BeneficiaryDeductScoreDTO {
	
	@NotNull
	@JsonProperty("beneficiary")
	private String beneficiary;
	
	@NotNull
	@JsonProperty("quantity")
	private Integer quantity;

	public BeneficiaryDeductScoreDTO(@JsonProperty("beneficiary") String beneficiary, @JsonProperty("quantity") Integer quantity) {
		this.beneficiary = beneficiary;
		this.quantity = quantity;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}