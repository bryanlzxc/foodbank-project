package foodbank.donor.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.donor.dto.DonorDTO;
import foodbank.donor.entity.Donor;
import foodbank.donor.entity.NonperishableDonation;
import foodbank.donor.entity.PerishableDonation;
import foodbank.donor.repository.DonorRepository;
import foodbank.donor.service.DonorService;
import foodbank.inventory.dto.FoodItemDTO;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.InvalidDonorException;

@Service
public class DonorServiceImpl implements DonorService {
	
	@Autowired
	private DonorRepository donorRepository;
	
	@Autowired
	private FoodRepository foodRepository;

	@Override
	public List<Donor> getAllDonors() {
		return donorRepository.findAll();
	}

	@Override
	public List<String> getDonorNames() {
		List<String> donorNames = new ArrayList<>();
		List<Donor> donorList = donorRepository.findAll();
		for(Donor donor: donorList) {
			donorNames.add(donor.getName());
		}
		return donorNames;
	}

	@Override
	public void createDonor(DonorDTO donor) {
		Donor dbDonor = donorRepository.findByName(donor.getName());
		if(dbDonor != null) {
			throw new InvalidDonorException(ErrorMessages.DONOR_ALREADY_EXISTS);
		}
		List<NonperishableDonation> newNonperishableDonationList = new ArrayList<>();
		List<PerishableDonation> newPerishableDonationList = new ArrayList<>();
		Donor newDonor = new Donor(donor.getName(), donor.getAddress(), newNonperishableDonationList, newPerishableDonationList);
		donorRepository.insert(newDonor);
	}

	@Override
	public void updateDonorNonperishable(FoodItemDTO foodItem) {
		Donor dbDonor = donorRepository.findByName(foodItem.getDonorName());
		if(dbDonor == null) {
			throw new InvalidDonorException(ErrorMessages.DONOR_DOES_NOT_EXIST);
		}
		List<NonperishableDonation> dbNonperishableDonationList = dbDonor.getNonperishableDonations();
		
		FoodItem dbFoodItem = foodRepository.findByCategoryAndClassificationAndDescription(foodItem.getCategory(), foodItem.getClassification(), foodItem.getDescription());
		dbFoodItem.setQuantity(foodItem.getQuantity());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String donationDate = dateFormat.format(date);
		
		NonperishableDonation newNonperishableDonation = new NonperishableDonation(dbFoodItem, donationDate);
		dbNonperishableDonationList.add(newNonperishableDonation);
		dbDonor.setNonperishableDonations(dbNonperishableDonationList);
		donorRepository.save(dbDonor);
		
	}
	

	
}
