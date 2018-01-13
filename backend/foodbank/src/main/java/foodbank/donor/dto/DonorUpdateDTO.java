package foodbank.donor.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DonorUpdateDTO {

	@NotNull
	@JsonProperty("oldName")
	private String oldName;
	
	@NotNull
	@JsonProperty("newName")
	private String newName;
	
	@NotNull
	@JsonProperty("address")
	private String address;

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
