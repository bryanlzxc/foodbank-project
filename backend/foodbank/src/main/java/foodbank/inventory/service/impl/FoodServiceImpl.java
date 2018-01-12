package foodbank.inventory.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.donor.entity.Donor;
import foodbank.donor.entity.NonperishableDonation;
import foodbank.donor.repository.DonorRepository;
import foodbank.inventory.dto.FoodItemDTO;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;
import foodbank.inventory.service.FoodService;
import foodbank.response.dto.ResponseDTO;
import foodbank.util.FileManager;
import foodbank.util.InventorySerializer;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.InvalidDonorException;
import foodbank.util.exceptions.InvalidFoodException;

@Service
public class FoodServiceImpl implements FoodService {
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private DonorRepository donorRepository;
	
	private Map<String, String[]> barcodeMap;
	
	@Override
	public List<FoodItem> retrieveAllFoodItems() {
		// TODO Auto-generated method stub
		return foodRepository.findAll();
	}

	@Override
	public List<FoodItem> retrieveAllFoodItemsInCategory(String categoryName) {
		// TODO Auto-generated method stub
		return foodRepository.findByCategory(categoryName);
	}

	@Override
	public List<FoodItem> retrieveFoodItemsByCategoryAndClassification(String categoryName, String classificationName) {
		// TODO Auto-generated method stub
		return foodRepository.findByCategoryAndClassification(categoryName, classificationName);
	}

	@Override
	public int retrieveFoodItemQuantity(String categoryName, String classificationName, String description) {
		// TODO Auto-generated method stub
		return foodRepository.findByCategoryAndClassificationAndDescription(categoryName, classificationName, description).getQuantity();
	}
	
	@Override
	public void overwriteFoodItem(FoodItemDTO foodItem) {
		// TODO Auto-generated method stub
		// This method overwrites the existing object in DB
		String category = foodItem.getCategory();
		String classification = foodItem.getClassification();
		String description = foodItem.getDescription();
		FoodItem dbFoodItem = foodRepository.findByCategoryAndClassificationAndDescription(category, classification, description);
		if(dbFoodItem != null) {
			dbFoodItem.setQuantity(foodItem.getQuantity());
			foodRepository.save(dbFoodItem);
			InventorySerializer.updateQuantity(category, classification, description, foodItem.getQuantity());
		} else {
			throw new InvalidFoodException(ErrorMessages.NO_SUCH_ITEM);
		}
	}

	@Override
	public void amendFoodItemQuantity(FoodItemDTO foodItem) {
		// TODO Auto-generated method stub
		// This method increments/decrements the existing DB object's quantity
		String category = foodItem.getCategory();
		String classification = foodItem.getClassification();
		String description = foodItem.getDescription();
		String barcode = foodItem.getBarcode();
		String[] itemDetailsArray = barcodeMap.get(barcode);
		if(itemDetailsArray == null) {
			itemDetailsArray = new String[] { category, classification, description };
			barcodeMap.put(barcode, itemDetailsArray);
		}
		System.out.println("Debugging statement, does it run here?");
		FoodItem dbFoodItem = foodRepository.findByCategoryAndClassificationAndDescription(category, classification, description);
		if(dbFoodItem != null) {
			dbFoodItem.setQuantity(dbFoodItem.getQuantity() + foodItem.getQuantity());
			foodRepository.save(dbFoodItem);
			InventorySerializer.updateQuantity(category, classification, description, foodItem.getQuantity());
			if(foodItem.getDonorName() != null) {
				//call DonorController's method to add food item into non-perishable list for specific donor
				updateDonorNonperishable(foodItem);
			}
		} else {
			dbFoodItem = new FoodItem(category, classification, description, foodItem.getQuantity());
			foodRepository.save(dbFoodItem);
			InventorySerializer.updateQuantity(category, classification, description, foodItem.getQuantity());
			if(foodItem.getDonorName() != null) {
				updateDonorNonperishable(foodItem);
			}
		}
	}
	
	private void updateDonorNonperishable(FoodItemDTO foodItem) {
		Donor dbDonor = donorRepository.findByName(foodItem.getDonorName());
		if(dbDonor == null) {
			dbDonor = new Donor();
			dbDonor.setName(foodItem.getDonorName());
			dbDonor.setAddress("Testing Address, Singapore 123910");
			donorRepository.save(dbDonor);
			System.out.println("Successfully created Donor!");
			System.out.println("Donor details = " + foodItem.getDonorName());
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
	
	// Code marked for deletion upon testing
	/*
	@Override
	public List<Category> retrieveAllCategories() {
		// TODO Auto-generated method stub
		return foodRepository.findAll();
	}

	@Override
	public List<Classification> retrieveAllClassificationsInCategory(String categoryName) {
		// TODO Auto-generated method stub
		List<Category> categories = foodRepository.findAll();
		for(Category category : categories) {
			if(category.getCategory().equals(categoryName)) {
				return category.getClassification();
			}
		}
		return null;
	}

	@Override
	public List<FoodItem> retrieveFoodItemsByCategoryAndClassification(String categoryName, String classificationName) {
		// TODO Auto-generated method stub
		List<Category> categories = foodRepository.findAll();
		for(Category category : categories) {
			if(category.getCategory().equals(categoryName)) {
				List<Classification> classifications = category.getClassification();
				for(Classification classification : classifications) {
					if(classification.getClassification().equals(classificationName)) {
						return classification.getFoodItems();
					}
				}
			}
		}
		return null;
	}

	@Override
	public int retrieveFoodItemQuantity(String foodItemDescription) {
		// TODO Auto-generated method stub
		List<Category> categories = foodRepository.findAll();
		for(Category category : categories) {
			List<Classification> classifications = category.getClassification();
			for(Classification classification : classifications) {
				List<FoodItem> foodItems = classification.getFoodItems();
				for(FoodItem foodItem : foodItems) {
					if(foodItem.getDescription().equals(foodItemDescription)) {
						return foodItem.getQuantity();
					}
				}
			}
		}
		return 0;
	}

	@Override
	public void overwriteFoodItem(FoodItemDTO foodItem) {
		// TODO Auto-generated method stub
		// This method overwrites the existing object in DB
		boolean itemFound = false;
		List<Category> categories = foodRepository.findAll();
		for(Category category : categories) {
			List<Classification> classifications = category.getClassification();
			for(Classification classification : classifications) {
				List<FoodItem> foodItems = classification.getFoodItems();
				for(FoodItem dbFoodItem : foodItems) {
					if(dbFoodItem.getDescription().equals(foodItem.getDescription())) {
						itemFound = true;
						dbFoodItem.setQuantity(foodItem.getQuantity());
						foodRepository.save(category);
						break;
					}
				}
			}
		}
		if(!itemFound) {
			throw new InvalidFoodException(ErrorMessages.NO_SUCH_ITEM);
		}
	}

	@Override
	public void amendFoodItemQuantity(FoodItemDTO foodItem) {
		// TODO Auto-generated method stub
		// This method increments/decrements the existing DB object's quantity
		boolean itemFound = false;
		List<Category> categories = foodRepository.findAll();
		for(Category category : categories) {
			List<Classification> classifications = category.getClassification();
			for(Classification classification : classifications) {
				List<FoodItem> foodItems = classification.getFoodItems();
				for(FoodItem dbFoodItem : foodItems) {
					if(dbFoodItem.getDescription().equals(foodItem.getDescription())) {
						itemFound = true;
						dbFoodItem.setQuantity(foodItem.getQuantity() + dbFoodItem.getQuantity());
						foodRepository.save(category);
						break;
					}
				}
			}
		}
		if(!itemFound) {
			throw new InvalidFoodException(ErrorMessages.NO_SUCH_ITEM);
		}
	}
	*/

	@Override
	public void overwriteFoodItems(FoodItemDTO[] foodItems) {
		// TODO Auto-generated method stub
		for(int i = 0; i < foodItems.length; i++) {
			FoodItemDTO foodItem = foodItems[i];
			overwriteFoodItem(foodItem);
		}
	}
	
	@Override
	public void amendFoodItemsQuantity(FoodItemDTO[] foodItems) {
		// TODO Auto-generated method stub
		for(int i = 0; i < foodItems.length; i++) {
			FoodItemDTO foodItem = foodItems[i];
			amendFoodItemQuantity(foodItem);
		}
	}

	@Override
	public Map<String, String> readBarcode(String barcode) {
		// TODO Auto-generated method stub
		if(barcodeMap == null) {
			barcodeMap = FileManager.generateBarcodeMap();
		}
		String[] itemDetailsArray = barcodeMap.get(barcode);
		Map<String, String> itemDetails = new HashMap<String, String>();
		if(itemDetailsArray != null) {
			itemDetails.put("status", ResponseDTO.Status.SUCCESS.toString());
			itemDetails.put("category", itemDetailsArray[0]);
			itemDetails.put("classification", itemDetailsArray[1]);
			itemDetails.put("description", itemDetailsArray[2]);
		} else {
			itemDetails.put("status", ResponseDTO.Status.FAIL.toString());
		}
		return itemDetails;
	}
	
	/*
	@Override
	public void incrementFoodItem(FoodItemDTO foodItem) {
		// TODO Auto-generated method stub
		// This method increments the existing object in DB
		String category = foodItem.getCategory();
		String classification = foodItem.getClassification();
		String description = foodItem.getDescription();
		FoodItem dbFoodItem = foodRepository.findByCategoryAndClassificationAndDescription(category, classification, description);
		if(dbFoodItem != null) {
			int updatedQuantity = dbFoodItem.getQuantity() + foodItem.getQuantity();
			dbFoodItem.setQuantity(updatedQuantity); // increment
			foodRepository.save(dbFoodItem);
			InventorySerializer.updateQuantity(category, classification, description, updatedQuantity);
		} else {
			throw new InvalidFoodException(ErrorMessages.NO_SUCH_ITEM);
		}
	}
	*/

}
