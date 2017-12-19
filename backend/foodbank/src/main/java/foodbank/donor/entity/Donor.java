package foodbank.donor.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Donors")
public class Donor {
	
	@Id
	private String id;
	
	private String name;
	private List<Donation> donations;
	
	public Donor() {}
	
	public Donor(String name, List<Donation> donations) {
		this.name = name;
		this.donations = donations;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Donation> getDonations() {
		return donations;
	}
	
	public void setDonations(List<Donation> donations) {
		this.donations = donations;
	}
	
}
