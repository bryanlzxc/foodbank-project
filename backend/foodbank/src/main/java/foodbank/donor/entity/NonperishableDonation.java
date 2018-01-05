package foodbank.donor.entity;

import foodbank.inventory.entity.FoodItem;

public class NonperishableDonation {
	
	private FoodItem foodItem;

	private String donationDate;
	
	public NonperishableDonation() {}
	
	public NonperishableDonation(FoodItem foodItem, String donationDate) {
		this.foodItem = foodItem;
		this.donationDate = donationDate;
	}
	public FoodItem getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
	}
	
	public String getDonationDate() {
		return donationDate;
	}

	public void setDonationDate(String donationDate) {
		this.donationDate = donationDate;
	}
	
}
