package foodbank.beneficiary.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import foodbank.user.entity.User;

/*
 * Done by Ng Shirong
 */

@Document(collection = "Beneficiary")
public class Beneficiary implements Comparable<Beneficiary> {
	
	@Id
	private String id;
	
	@DBRef
	private User user;
	
	private String username;
	private String sector;
	private int numBeneficiary;
	private String address;
	private double score;
	private long membershipNumber;
	private long acraRegistrationNumber;
	private String memberType;
	
	public Beneficiary(User user, String sector, int numBeneficiary, String address, double score,
			long membershipNumber, long acraRegistrationNumber, String memberType) {
		super();
		this.user = user;
		this.username = user.getUsername();
		this.sector = sector;
		this.numBeneficiary = numBeneficiary;
		this.address = address;
		this.score = score;
		this.membershipNumber = membershipNumber;
		this.acraRegistrationNumber = acraRegistrationNumber;
		this.memberType = memberType;
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
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	// Natural sort according to descending order of score
	// A higher score denotes a higher priority to receive FoodItem allocation
	public int compareTo(Beneficiary o) {
		return Double.compare(o.score, this.score);
	}
	
}
