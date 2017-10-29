package foodbank.beneficiary.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Done by Shirong
 */

@Document(collection = "Beneficiary")
public class Beneficiary {

	@Id
	private String id;
	
	private String name;
	private String sector;
	private int numbeneficiary;
	private String address;
	private double score;
	
	//Constructors
	public Beneficiary(String id, String name, String sector, int numbeneficiary, String address, double score) {
		this.id = id;
		this.name = name;
		this.sector = sector;
		this.numbeneficiary = numbeneficiary;
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
		return numbeneficiary;
	}
	public void setNumbeneficiary(int numbeneficiary) {
		this.numbeneficiary = numbeneficiary;
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
