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
import foodbank.packing.dto.PackingListDTO;
import foodbank.packing.entity.PackedFoodItem;
import foodbank.packing.entity.PackingList;
import foodbank.packing.repository.PackingRepository;
import foodbank.packing.service.PackingService;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.PackingUpdateException;

@Service
public class PackingServiceImpl implements PackingService {

	@Autowired
	private PackingRepository packingRepository;
	
	@Autowired
	private AllocationRepository allocationRepository;
	
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
				break;
			}
		}
		if(dbPackingList == null) { throw new PackingUpdateException(ErrorMessages.PACKING_UPDATE_ERROR); }
		List<PackedFoodItem> beneficiaryPackedItems = dbPackingList.getPackedItems();
		HashMap<String, PackedFoodItem> packedItemsMap = new HashMap<String, PackedFoodItem>();
		beneficiaryPackedItems.forEach(item -> packedItemsMap.put(item.getCategory() + item.getClassification() + item.getDescription(), item));
		List<Map<String, Object>> beneficiaryPackedItemsUpdates = packingListDTO.getPackedItems();
		for(Map<String, Object> map : beneficiaryPackedItemsUpdates) {
			String category = (String)map.get("category");
			String classification = (String)map.get("classification");
			String description = (String)map.get("description");
			Integer packedQuantity = (Integer)map.get("packedQuantity");
			packedItemsMap.get(category + classification + description).setQuantity(packedQuantity);
		}
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
		for(PackedFoodItem packedItem : beneficiaryPackedItems) {
			if(packedItem.getCategory().equals(String.valueOf(details.get("category"))) 
					&& packedItem.getClassification().equals(String.valueOf(details.get("classification")))
					&& packedItem.getDescription().equals(String.valueOf(details.get("description")))) {
				packedItem.setQuantity((Integer)details.get("packedQuantity"));
				break;
			}
		}
		packingRepository.save(dbPackingList);
	}

	@Override
	public void updatePackingStatus(WebRequest data) {
		// TODO Auto-generated method stub
		List<PackingList> packingLists = packingRepository.findAll();
		PackingList dbPackingList = null;
		for(PackingList packingList : packingLists) {
			if(packingList.getBeneficiary().getUsername().equals(data.getParameter("beneficiary"))) {
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
	
}
