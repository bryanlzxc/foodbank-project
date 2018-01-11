package foodbank.beneficiary.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BeneficiaryAccountDTO {

	// Core Details required for a User
	@NotNull
	@JsonProperty("username")
	private String username;
	
	@NotNull
	@JsonProperty("password")
	private String password;
	
	private final String usertype = "beneficiary";
	
	@NotNull
	@JsonProperty("name")
	private String name;
	
	@NotNull
	@JsonProperty("email")
	private String email;
	
	// Core Details required for a Beneficiary
	@NotNull
	@JsonProperty("numBeneficiary")
	private Integer numBeneficiary;
	
	@NotNull
	@JsonProperty("address")
	private String address;
		
	@NotNull
	@JsonProperty("contactPerson")
	private String contactPerson;
	
	@NotNull
	@JsonProperty("contactNumber")
	private String contactNumber;
	
	@NotNull
	@JsonProperty("memberType")
	private String memberType;
	
	@NotNull
	@JsonProperty("hasTransport")
	private Boolean hasTransport;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsertype() {
		return usertype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNumBeneficiary() {
		return numBeneficiary;
	}

	public void setNumBeneficiary(int numBeneficiary) {
		this.numBeneficiary = numBeneficiary;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Boolean getHasTransport() {
		return hasTransport;
	}

	public void setHasTransport(Boolean hasTransport) {
		this.hasTransport = hasTransport;
	}

	public void setNumBeneficiary(Integer numBeneficiary) {
		this.numBeneficiary = numBeneficiary;
	}
}
