package foodbank.donor.entity;

import foodbank.inventory.entity.FoodItem;

public class NonperishableDonation {
	
	private DonatedFoodItem donatedFoodItem;

	private String donationDate;
	
	public NonperishableDonation() {}
	
	public NonperishableDonation(DonatedFoodItem donatedFoodItem, String donationDate) {
		this.donatedFoodItem = donatedFoodItem;
		this.donationDate = donationDate;
	}
	
	public FoodItem getDonatedFoodItem() {
		return donatedFoodItem;
	}

	public void setFoodItem(DonatedFoodItem donatedFoodItem) {
		this.donatedFoodItem = donatedFoodItem;
	}
	
	public String getDonationDate() {
		return donationDate;
	}

	public void setDonationDate(String donationDate) {
		this.donationDate = donationDate;
	}
	
}
