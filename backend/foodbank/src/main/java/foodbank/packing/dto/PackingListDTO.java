package foodbank.packing.dto;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PackingListDTO {

	@NotNull
	@JsonProperty("beneficiary")
	private String beneficiary;
	
	@NotNull
	@JsonProperty("packedItems")
	private List<Map<String, Object>> packedItems;
	
	public PackingListDTO(@JsonProperty("beneficiary") String beneficiary, @JsonProperty("packedItems") List<Map<String, Object>> packedItems) {
		this.beneficiary = beneficiary;
		this.packedItems = packedItems;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public List<Map<String, Object>> getPackedItems() {
		return packedItems;
	}

	public void setPackedItems(List<Map<String, Object>> packedItems) {
		this.packedItems = packedItems;
	}

}
