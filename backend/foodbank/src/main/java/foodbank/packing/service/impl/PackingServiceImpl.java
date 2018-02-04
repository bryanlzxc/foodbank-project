package foodbank.packing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import foodbank.allocation.entity.AllocatedFoodItems;
import foodbank.allocation.entity.Allocation;
import foodbank.allocation.repository.AllocationRepository;
import foodbank.beneficiary.dto.BeneficiaryDeductScoreDTO;
import foodbank.beneficiary.dto.BeneficiaryUpdateDTO;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.inventory.dto.FoodItemDTO;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;
import foodbank.packing.dto.PackingListDTO;
import foodbank.packing.entity.PackedFoodItem;
import foodbank.packing.entity.PackingList;
import foodbank.packing.repository.PackingRepository;
import foodbank.packing.service.PackingService;
import foodbank.util.InventorySerializer;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.InvalidBeneficiaryException;
import foodbank.util.exceptions.InvalidFoodException;
import foodbank.util.exceptions.PackingUpdateException;

@Service
public class PackingServiceImpl implements PackingService {

	@Autowired
	private PackingRepository packingRepository;
	
	@Autowired
	private AllocationRepository allocationRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Override
	public List<PackingList> retrieveAllPackingLists() {
		return packingRepository.findAll();
	}
	
	@Override
	public PackingList findByBeneficiary(String beneficiary) {
		// TODO Auto-generated method stub
		List<PackingList> packingLists = packingRepository.findAll();
		PackingList dbPackingList = null;
		for(PackingList packingList : packingLists) {
			if(packingList.getBeneficiary().getUser().getUsername().equals(beneficiary)) {
				dbPackingList = packingList;
			}
		}
		return dbPackingList;
	}

	@Override
	public void generatePackingList() {
		// TODO Auto-generated method stub
		List<Allocation> allocations = allocationRepository.findAll();
		List<PackingList> packingLists = new ArrayList<PackingList>();
		for(Allocation allocation : allocations) {
			List<AllocatedFoodItems> foodItems = allocation.getAllocatedItems();
			List<PackedFoodItem> packedItems = new ArrayList<PackedFoodItem>();
			for(AllocatedFoodItems foodItem: foodItems) {
				if(foodItem.getAllocatedQuantity() > 0) {	//packing list only contains food items which allocated qty are more than 0
					packedItems.add(new PackedFoodItem(foodItem.getCategory(), foodItem.getClassification(), 
							foodItem.getDescription(), foodItem.getAllocatedQuantity(), 0));
				}
			}
//			foodItems.forEach(foodItem -> packedItems.add(new PackedFoodItem(foodItem.getCategory(), foodItem.getClassification(), 
//					foodItem.getDescription(), foodItem.getAllocatedQuantity(), 0)));
			if(!packedItems.isEmpty()) {
				packingLists.add(new PackingList(allocation.getBeneficiary(), packedItems));
			}
		}
		packingRepository.insert(packingLists);
	}

	@Override
	public void updatePackedQuantities(PackingListDTO packingListDTO) {
		// TODO Auto-generated method stub
		List<PackingList> packingLists = packingRepository.findAll();
		PackingList dbPackingList = null;
		for(PackingList packingList : packingLists) {
			if(packingList.getBeneficiary().getUser().getUsername().equals(packingListDTO.getBeneficiary())) {
				dbPackingList = packingList;
				break;
			}
		}
		if(dbPackingList == null) { throw new PackingUpdateException(ErrorMessages.PACKING_UPDATE_ERROR); }
		String beneficiary = packingListDTO.getBeneficiary();
		List<PackedFoodItem> beneficiaryPackedItems = dbPackingList.getPackedItems();
		HashMap<String, PackedFoodItem> packedItemsMap = new HashMap<String, PackedFoodItem>();
		beneficiaryPackedItems.forEach(item -> packedItemsMap.put(item.getCategory() + item.getClassification() + item.getDescription(), item));
		List<Map<String, Object>> beneficiaryPackedItemsUpdates = packingListDTO.getPackedItems();
		for(Map<String, Object> map : beneficiaryPackedItemsUpdates) {
			String category = (String)map.get("category");
			String classification = (String)map.get("classification");
			String description = (String)map.get("description");
			Integer packedQuantity = (Integer)map.get("packedQuantity");
			packedItemsMap.get(category + classification + description).setQuantity(packedQuantity);		//need to check if we are saving this to the correct map
			FoodItemDTO foodItemDTO = new FoodItemDTO(category, classification, description, packedQuantity, 0, null);
			amendFoodItemQuantity(foodItemDTO);
			BeneficiaryDeductScoreDTO beneficiaryDeductScoreDTO = new BeneficiaryDeductScoreDTO(beneficiary, -packedQuantity);
			modifyBeneficiaryScore(beneficiaryDeductScoreDTO);
		}
		dbPackingList.setPackingStatus(true);
		packingRepository.save(dbPackingList);
	}
	
	@Override
	public void updateBeneficiaryPackingList(Map<String, Object> details) {
		List<PackingList> packingLists = packingRepository.findAll();
		PackingList dbPackingList = null;
		for(PackingList packingList : packingLists) {
			if(packingList.getBeneficiary().getUsername().equals(String.valueOf(details.get("beneficiary")))) {
				dbPackingList = packingList;
				break;
			}
		}
		if(dbPackingList == null) { throw new PackingUpdateException(ErrorMessages.PACKING_UPDATE_ERROR); }
		List<PackedFoodItem> beneficiaryPackedItems = dbPackingList.getPackedItems();
		int previouslyPackedAmount = 0;
		String category = String.valueOf(details.get("category"));
		String classification = String.valueOf(details.get("classification"));
		String description = String.valueOf(details.get("description"));
		for(PackedFoodItem packedItem : beneficiaryPackedItems) {
			if(packedItem.getCategory().equals(category) && packedItem.getClassification().equals(classification)
					&& packedItem.getDescription().equals(description)) {
				previouslyPackedAmount = packedItem.getQuantity();
				packedItem.setQuantity((int)details.get("packedQuantity"));
				break;
			}
		}
		if(previouslyPackedAmount - (int)details.get("packedQuantity") != 0) {
			FoodItem foodItem = foodRepository.findByCategoryAndClassificationAndDescription(category, classification, description);
			foodItem.setQuantity(foodItem.getQuantity() + previouslyPackedAmount - (int)details.get("packedQuantity"));
			foodRepository.save(foodItem);
			// InventorySerializer.updateQuantity(category, classification, description, foodItem.getQuantity());
			modifyBeneficiaryScore(new BeneficiaryDeductScoreDTO(String.valueOf(details.get("beneficiary")), previouslyPackedAmount - (Integer)details.get("packedQuantity")));
		} 
		packingRepository.save(dbPackingList);
	}

	@Override
	public void updatePackingStatus(String beneficiary) {
		// TODO Auto-generated method stub
		List<PackingList> packingLists = packingRepository.findAll();
		PackingList dbPackingList = null;
		for(PackingList packingList : packingLists) {
			if(packingList.getBeneficiary().getUsername().equals(beneficiary)) {
				dbPackingList = packingList;
				break;
			}
		}
		if(dbPackingList == null) { throw new PackingUpdateException(ErrorMessages.PACKING_UPDATE_ERROR); }
		if(dbPackingList.getPackingStatus() == false) {
			dbPackingList.setPackingStatus(true);
		} else {
			dbPackingList.setPackingStatus(false);
		}
		packingRepository.save(dbPackingList);
	}
		
	//This method is for internal call to deduct food item quantity when fb volunteer pressed packed
	private void amendFoodItemQuantity(FoodItemDTO foodItem) {
		// This method increments/decrements the existing DB object's quantity
		String category = foodItem.getCategory();
		String classification = foodItem.getClassification();
		String description = foodItem.getDescription();
		FoodItem dbFoodItem = foodRepository.findByCategoryAndClassificationAndDescription(category, classification, description);
		if(dbFoodItem != null) {
			dbFoodItem.setQuantity(dbFoodItem.getQuantity() - foodItem.getQuantity());		//this is the deduction from db
			foodRepository.save(dbFoodItem);
			InventorySerializer.updateQuantity(category, classification, description, foodItem.getQuantity());
		} else {
			throw new InvalidFoodException(ErrorMessages.NO_SUCH_ITEM);
		}
	}
	
	//This method is for internal call to deduct beneficiary score once fb volunteer pressed packed
	private void modifyBeneficiaryScore(BeneficiaryDeductScoreDTO beneficiaryUpdate) {
		// TODO Auto-generated method stub
		Beneficiary dbBeneficiary = beneficiaryRepository.findByUsername(beneficiaryUpdate.getBeneficiary());
		if(dbBeneficiary == null) { throw new InvalidBeneficiaryException(ErrorMessages.NO_SUCH_BENEFICIARY); }
		double inventoryQuantityPacked = beneficiaryUpdate.getQuantity();
		double scoreToDeduct = inventoryQuantityPacked;			//currently the score which is deducted from beneficiary is the quantity of packed items
		double newScore = dbBeneficiary.getScore() + scoreToDeduct;
		if(newScore < 0) {		//score will never be less than 0;
			newScore = 0;
		}
		dbBeneficiary.setScore(newScore);
		beneficiaryRepository.save(dbBeneficiary);
	}

	@Override
	public Boolean reviewAllPackingStatus() {
		// TODO Auto-generated method stub
		List<PackingList> packingLists = packingRepository.findAll();
		for(PackingList packingList : packingLists) {
			if(!packingList.getPackingStatus()) {
				return false;
			}
		}
		return true;
	}
	
}
