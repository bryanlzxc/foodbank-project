package foodbank.beneficiary.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BeneficiaryDTO {
	
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
	@JsonProperty("sector")
	private String sector;
	
	@NotNull
	@JsonProperty("numBeneficiary")
	private Integer numBeneficiary;
	
	@NotNull
	@JsonProperty("address")
	private String address;
	
	@NotNull
	@JsonProperty("score")
	private Double score;
	
	@NotNull
	@JsonProperty("membershipNumber")
	private Long membershipNumber;
	
	@NotNull
	@JsonProperty("acraRegistrationNumber")
	private Long acraRegistrationNumber;
	
	@NotNull
	@JsonProperty("memberType")
	private String memberType;
	
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

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public long getMembershipNumber() {
		return membershipNumber;
	}

	public void setMembershipNumber(long membershipNumber) {
		this.membershipNumber = membershipNumber;
	}

	public long getAcraRegistrationNumber() {
		return acraRegistrationNumber;
	}

	public void setAcraRegistrationNumber(long acraRegistrationNumber) {
		this.acraRegistrationNumber = acraRegistrationNumber;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

}
