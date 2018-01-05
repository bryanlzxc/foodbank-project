package foodbank.donor.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Donors")
public class Donor {
	
	@Id
	private String id;
	
	private String name;
	private String address;
	private List<NonperishableDonation> nonperishableDonations;
	private List<PerishableDonation> perishableDonations;
	
	public Donor() {}
	
	public Donor(String name, String address, List<NonperishableDonation> nonperishableDonations, List<PerishableDonation> perishableDonations) {
		this.name = name;
		this.address = address;
		this.nonperishableDonations = nonperishableDonations;
		this.perishableDonations = perishableDonations;
	}
	
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<NonperishableDonation> getNonperishableDonations() {
		return nonperishableDonations;
	}

	public void setNonperishableDonations(List<NonperishableDonation> nonperishableDonations) {
		this.nonperishableDonations = nonperishableDonations;
	}

	public List<PerishableDonation> getPerishableDonations() {
		return perishableDonations;
	}

	public void setPerishableDonations(List<PerishableDonation> perishableDonations) {
		this.perishableDonations = perishableDonations;
	}
	
}
