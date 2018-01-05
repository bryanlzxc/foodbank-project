package foodbank.donor.entity;

public class PerishableDonation {
	
	private String[] donationDetails;
	
	private String donationDate;
	
	public PerishableDonation() {}
	
	public PerishableDonation(String[] donationDetails, String donationDate) {
		this.donationDetails = donationDetails;
		this.donationDate = donationDate;
	}

	public String[] getDonationDetails() {
		return donationDetails;
	}

	public void setDonationDetails(String[] donationDetails) {
		this.donationDetails = donationDetails;
	}

	public String getDonationDate() {
		return donationDate;
	}

	public void setDonationDate(String date) {
		this.donationDate = date;
	}

}
