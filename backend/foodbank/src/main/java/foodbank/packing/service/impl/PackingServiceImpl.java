package foodbank.packing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.allocation.entity.AllocatedFoodItems;
import foodbank.allocation.entity.Allocation;
import foodbank.allocation.repository.AllocationRepository;
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
			if(packingList.getBeneficiary().getUser().getName().equals(beneficiary)) {
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
			foodItems.forEach(foodItem -> packedItems.add(new PackedFoodItem(foodItem.getCategory(), foodItem.getClassification(), 
					foodItem.getDescription(), 0, foodItem.getAllocatedQuantity())));
			packingLists.add(new PackingList(allocation.getBeneficiary(), packedItems));
		}
	}

	@Override
	public void updatePackedQuantities(PackingListDTO packingListDTO) {
		// TODO Auto-generated method stub
		List<PackingList> packingLists = packingRepository.findAll();
		PackingList dbPackingList = null;
		for(PackingList packingList : packingLists) {
			if(packingList.getBeneficiary().getUser().getName().equals(packingListDTO.getBeneficiary())) {
				dbPackingList = packingList;
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
			
			FoodItemDTO foodItemDTO = new FoodItemDTO(category, classification, description, packedQuantity, null);
			amendFoodItemQuantity(foodItemDTO);
			
			BeneficiaryUpdateDTO beneficiaryUpdateDTO = new BeneficiaryUpdateDTO(beneficiary, (double)packedQuantity);
			modifyBeneficiaryScore(beneficiaryUpdateDTO);
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
	public void modifyBeneficiaryScore(BeneficiaryUpdateDTO beneficiaryUpdate) {
		// TODO Auto-generated method stub
		Beneficiary dbBeneficiary = beneficiaryRepository.findByUsername(beneficiaryUpdate.getBeneficiary());
		if(dbBeneficiary == null) {
			throw new InvalidBeneficiaryException(ErrorMessages.NO_SUCH_BENEFICIARY);
		}
		double inventoryQuantityPacked = beneficiaryUpdate.getScore();
		double scoreToDeduct = inventoryQuantityPacked;			//currently the score which is deducted from beneficiary is the quantity of packed items
		dbBeneficiary.setScore(dbBeneficiary.getScore() - scoreToDeduct);
		beneficiaryRepository.save(dbBeneficiary);
	}
	
}
