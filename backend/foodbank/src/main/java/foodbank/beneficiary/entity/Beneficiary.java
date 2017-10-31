package foodbank.beneficiary.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Done by Ng Shirong
 */

@Document(collection = "Beneficiary")
public class Beneficiary {

	@Id
	private String id;
	
	private String name;
	private String sector;
	private int numBeneficiary;
	private String address;
	private double score;
	
	//Constructors
	public Beneficiary(String name, String sector, int numBeneficiary, String address, double score) {
		this.name = name;
		this.sector = sector;
		this.numBeneficiary = numBeneficiary;
		this.address = address;
		this.score = score;
	}
	
	public Beneficiary() {
		
	}
	
	//Getters and setters
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSector() {
		return sector;
	}
	
	public void setSector(String sector) {
		this.sector = sector;
	}
	
	public int getNumbeneficiary() {
		return numBeneficiary;
	}
	
	public void setNumbeneficiary(int numBeneficiary) {
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
	
}
