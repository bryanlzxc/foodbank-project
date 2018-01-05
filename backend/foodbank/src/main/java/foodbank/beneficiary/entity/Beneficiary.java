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
	private int numBeneficiary; 
	private String address;
	private double score;
	private String contactPerson;
	private String contactNumber;
	private String memberType;
	private boolean hasTransport;
	
	public Beneficiary() {}
	
	public Beneficiary(String id, User user, int numBeneficiary, String address, double score,
			String contactPerson, String contactNumber, String memberType, Boolean hasTransport) {
		this(user, numBeneficiary, address, score, contactPerson, contactNumber, memberType, hasTransport);
		this.id = id;
	}
	
	public Beneficiary(User user, int numBeneficiary, String address, double score,
			String contactPerson, String contactNumber, String memberType, Boolean hasTransport) {
		this.user = user;
		this.username = user.getUsername();
		this.numBeneficiary = numBeneficiary;
		this.address = address;
		this.score = score;
		this.contactPerson = contactPerson;
		this.contactNumber = contactNumber;
		this.memberType = memberType;
		this.hasTransport = hasTransport;
	}
	
	public String getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public boolean isHasTransport() {
		return hasTransport;
	}

	public void setHasTransport(boolean hasTransport) {
		this.hasTransport = hasTransport;
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
	
	@Override
	public String toString() {
		return id + "," 
				+ user.getId() + ","
				+ numBeneficiary + ","
				+ address + ","
				+ score + ","
				+ contactPerson + ","
				+ contactNumber + ","
				+ memberType + ","
				+ hasTransport;
	}
	
}
