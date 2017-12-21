package foodbank.allocation.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.allocation.dto.AllocationDTO;
import foodbank.allocation.dto.BatchAllocationDTO;
import foodbank.allocation.entity.Allocation;
import foodbank.allocation.repository.AllocationRepository;
import foodbank.allocation.service.AllocationService;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.exceptions.InvalidAllocationException;
import foodbank.exceptions.InvalidBeneficiaryException;
import foodbank.inventory.entity.FoodItem;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;
import foodbank.util.InventorySerializer;
import foodbank.util.MessageConstants.ErrorMessages;

@Service
public class AllocationServiceImpl implements AllocationService {
	
	@Autowired
	private AllocationRepository allocationRepository;
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
		
	@Override
	public List<Allocation> retrieveAllAllocations() {
		// TODO Auto-generated method stub
		return allocationRepository.findAll();
	}
	
	@Override
	public List<FoodItem> retrieveAllocationByBeneficiary(String beneficiary) {
		List<Allocation> allocations = allocationRepository.findAll();
		List<FoodItem> foodItemsAllocated = new ArrayList<FoodItem>();
		for(Allocation allocation : allocations) {
			if(allocation.getBeneficiary().getUser().getName().equals(beneficiary)) {
				foodItemsAllocated.addAll(allocation.getAllocatedItems());
			}
		}
		return foodItemsAllocated;
	}

	@Override
	public void generateAllocationList() {
		// TODO Auto-generated method stub
		List<Request> requests = requestRepository.findAll();
		HashMap<String, Allocation> allocationMap = generateAllocationMapping(generateMapping(requests));
		for(Allocation allocation : allocationMap.values()) {
			allocationRepository.insert(allocation);
		}
	}
	
	// Helper method to generate the mapping for items by description
	private HashMap<String, List<Request>> generateMapping(List<Request> requests) {
		HashMap<String, List<Request>> requestsByFoodItems = new HashMap<String, List<Request>>();
		for(Request request : requests) {
			String description = request.getFoodItem().getDescription();
			List<Request> requestsContainingFoodItem = requestsByFoodItems.get(description);
			if(requestsContainingFoodItem != null) {
				requestsContainingFoodItem.add(request);
				/* Testing comparing function to avoid writing of a comparator */
				requestsContainingFoodItem.sort(Comparator.comparing(Request::getBeneficiary, Comparator.comparingDouble(Beneficiary::getScore)));
				Collections.reverse(requestsContainingFoodItem);
				requestsByFoodItems.replace(description, requestsContainingFoodItem);
			} else {
				requestsContainingFoodItem = new ArrayList<Request>();
				requestsContainingFoodItem.add(request);
				requestsByFoodItems.put(description, requestsContainingFoodItem);
			}
		}
		return requestsByFoodItems;
	}
	
	// Helper method to generate the mapping for allocations for each beneficiary using their name as the key
	private HashMap<String, Allocation> generateAllocationMapping(Map<String, List<Request>> requestsByFoodItems) {
		HashMap<String, Allocation> allocationMap = new HashMap<String, Allocation>();
		for(Map.Entry<String, List<Request>> entry : requestsByFoodItems.entrySet()) {
			String description = entry.getKey();
			List<Request> requestsForFoodItem = entry.getValue();
			Request request = requestsForFoodItem.size() > 0 ? requestsForFoodItem.get(0) : null;
			if(request != null) {
				int inventoryQuantity = retrieveInventoryDetails(
						request.getCategory(), request.getClassification(), description).getQuantity();
				for(Request currentRequest : requestsForFoodItem) {
					int allocatedQuantity = 0;
					int requestedQuantity = currentRequest.getFoodItem().getQuantity();
					/** Current logic is to assign as much as possible to priority beneficiary, which is flawed! **/
					if(requestedQuantity > inventoryQuantity) {
						allocatedQuantity = inventoryQuantity;
					} else {
						allocatedQuantity = requestedQuantity;
					}
					inventoryQuantity -= allocatedQuantity;
					String beneficiaryName = currentRequest.getBeneficiary().getUser().getName();
					Allocation allocation = allocationMap.get(beneficiaryName);
					if(allocation != null) {
						allocation.getAllocatedItems().add(new FoodItem(currentRequest.getFoodItem().getDescription(), allocatedQuantity));
						allocationMap.replace(beneficiaryName, allocation);
					} else {
						ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
						foodItems.add(new FoodItem(description, allocatedQuantity));
						allocation = new Allocation(currentRequest.getBeneficiary(), foodItems);
						allocationMap.put(beneficiaryName, allocation);
					}
				}
			}
		}
		return allocationMap;
	}
	
	// Helper method to generate a mapping for every item and the available quantity in store
	private FoodItem retrieveInventoryDetails(String category, String classification, String description) {
		UUID uniqueId = InventorySerializer.serials.get(category+classification+description);
		return InventorySerializer.foodItemMap.get(uniqueId);
	}
	
	@Override
	public void createAllocation(AllocationDTO allocation) {
		// TODO Auto-generated method stub
		if(allocation.getId() == null) {
			Beneficiary beneficiary = beneficiaryRepository.findByUsername(allocation.getBeneficiary());
			if(beneficiary == null) {
				throw new InvalidBeneficiaryException(ErrorMessages.NO_SUCH_BENEFICIARY);
			}
			allocationRepository.insert(new Allocation(beneficiary, allocation.getAllocatedItems()));
		}
	}

	@Override
	public void updateAllocation(AllocationDTO allocation) {
		// TODO Auto-generated method stub
		Allocation dbAllocation = allocationRepository.findById(allocation.getId());
		if(dbAllocation == null) {
			throw new InvalidAllocationException(ErrorMessages.INVALID_ALLOCATION);
		}
		List<FoodItem> dbAllocatedItems = dbAllocation.getAllocatedItems();
		Map<String, Integer> dbAllocationMap = new HashMap<String, Integer>();
		for(int i = 0; i < dbAllocatedItems.size(); i++) {
			dbAllocationMap.put(dbAllocatedItems.get(i).getDescription(), i);
		}
		List<FoodItem> updatedAllocatedItems = allocation.getAllocatedItems();
		for(FoodItem foodItem : updatedAllocatedItems) {
			dbAllocatedItems.get(dbAllocationMap.get(foodItem.getDescription())).setQuantity(foodItem.getQuantity());
		}
		dbAllocation.setAllocatedItems(dbAllocatedItems);
		allocationRepository.save(dbAllocation);
	}

	@Override
	public void batchAllocationUpdate(BatchAllocationDTO batchAllocation) {
		// TODO Auto-generated method stub
		
	}
	
}
