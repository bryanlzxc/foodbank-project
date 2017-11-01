package foodbank.allocation.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbank.allocation.entity.Allocation;
import foodbank.allocation.entity.AllocationFoodItem;
import foodbank.allocation.repository.AllocationRepository;
import foodbank.beneficiary.controller.BeneficiaryController;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.inventory.controller.FoodController;
import foodbank.inventory.repository.FoodRepository;
import foodbank.request.controller.RequestController;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;
import foodbank.util.RequestingBeneficiaryComparator;

/*
 * Created by: Lim Jian Quan, Jaren
 */

@RestController
@RequestMapping("/allocation")
public class AllocationController {
	
	@Autowired
	private AllocationRepository allocationRepository;
	private RequestRepository requestRepository;
	private BeneficiaryRepository beneficiaryRepository;
	private FoodRepository foodRepository;
	private RequestController requestController = new RequestController(requestRepository);
	private BeneficiaryController beneficiaryController = new BeneficiaryController(beneficiaryRepository);
	private FoodController foodController = new FoodController(foodRepository);
	
	public AllocationController(AllocationRepository allocationRepository) {
		this.allocationRepository = allocationRepository;
	}
	
	@GetMapping("/display-all")
	public List<Allocation> getAllAllocations() {
		return this.allocationRepository.findAll();
	}
	
	@PostMapping("/allocation-list")
	public List<Allocation> getAllocationList() {
		
		List<Allocation> allocationList = new ArrayList<>();
		
		// get all the requests, grouped by FoodItem and sorted by priority
		HashMap<String, List<Request>> requestByFoodItemMap = groupRequestByFoodItem();
		Set<String> keySet = requestByFoodItemMap.keySet();
		
		// loop through the HashMap
		/** KIV: This needs to be changed, incorrect use of data structure **/
		for (String description : keySet) {
			List<Request> currentRequestFoodItemList = requestByFoodItemMap.get(description);
			int inventoryQty = getInventoryQty(description);
			
			// loop through the list of Requests, start allocating
			for (Request currentRequest : currentRequestFoodItemList) {
				int allocatedQty = 0;
				int reqQty = currentRequest.getFoodItemQuantity(); // get the requested qty
				
				// logic: assign as much as possible to priority Beneficiary
				if (reqQty > inventoryQty) {
					allocatedQty = inventoryQty;
				} else {
					allocatedQty = reqQty;
				}
				inventoryQty -= allocatedQty;	// decrement
				
				// create the AllocationFoodItem object
				String[] itemGroup = getFoodItemGroup(description);
				AllocationFoodItem afi = new AllocationFoodItem(itemGroup[0], itemGroup[1], description, reqQty, allocatedQty);
				
				addIntoAllocationList(allocationList, afi, currentRequest.getBeneficiaryName());
			}
		}
		
		// Add all Allocations into the database
		for (Allocation allocation : allocationList) {
			this.allocationRepository.save(allocation);
		}
		
		return allocationList;
		
	}
	
	@PostMapping("/update-allocation")
	public void updateAllocation() {
		/** 
		 * TODO
		 * This should return me the success status of the allocation
		 */
		
	}
	
	////////////////////////// Helper methods ////////////////////////////////
	
	/* Groups the requests based on FoodItem */
	protected HashMap<String, List<Request>> groupRequestByFoodItem() {
		
		// setup: HashMap to return & List of all Request objects
		HashMap<String, List<Request>> requestByFoodItemMap = new HashMap<>();
		List<Request> requestList = requestController.getAllRequest();
		
		// loop through the List of Request and store them in the HashMap
		for (Request request : requestList) {
			String description = request.getFoodItemDescription();
			
			if (requestByFoodItemMap.containsKey(description)) {
				// Multiple Requests for the same FoodItem
				// add this Request to existing FoodItem description
				List<Request> currentFoodItemRequestList = requestByFoodItemMap.get(description);
				currentFoodItemRequestList.add(request);
				
				// Sort by priority of requesting Beneficiary
				Collections.sort(currentFoodItemRequestList, new RequestingBeneficiaryComparator()); 
			} else {
				// First Request for the FoodItem
				// create a new key:value pairing
				List<Request> newFoodItemRequestList = new ArrayList<>();
				newFoodItemRequestList.add(request);
				requestByFoodItemMap.put(description, newFoodItemRequestList);
			}
		}
		
		return requestByFoodItemMap;
	}
	
	/* Get the current inventory level */
	protected int getInventoryQty(String description) {
		return foodController.getFoodItemQuantity(description);
	}
	
	/* Get the FoodItem group */
	protected String[] getFoodItemGroup(String description) {
		// [0]: category, [1]: classification
		return foodController.findItemGroup(description);
	}
	
	/* Add the AllocationFoodItem into the AllocationList */
	protected void addIntoAllocationList(List<Allocation> allocationList, AllocationFoodItem afi, String bvName) {
		boolean hasToCreateAllocation = true;
		for (Allocation a : allocationList) {
			if (a.getName().equals(bvName)) {
				a.addToList(afi);
				hasToCreateAllocation = false;
				break;
			}
		}
		if (hasToCreateAllocation) {
			List<AllocationFoodItem> afiList = new ArrayList<>();
			afiList.add(afi);
			allocationList.add(new Allocation(bvName, afiList));
		}
	}
	
}
