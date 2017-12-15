/*package foodbank.backup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbank.allocation.entity.Allocation;
import foodbank.allocation.entity.AllocationFoodItem;
import foodbank.allocation.entity.AllocationUpdate;
import foodbank.allocation.repository.AllocationRepository;
import foodbank.beneficiary.controller.BeneficiaryController;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.inventory.controller.FoodController;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;
import foodbank.request.controller.RequestController;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;
import foodbank.util.RequestingBeneficiaryComparator;
import foodbank.util.Status;


 
  * Created by: Lim Jian Quan, Jaren
  
 

@RestController
@CrossOrigin
@RequestMapping("/allocation")
public class AllocationController {
	
	@Autowired
	private AllocationRepository allocationRepository;
	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	@Autowired
	private FoodRepository foodRepository;
	
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
		
		// Create the repos
		RequestController requestController = new RequestController(requestRepository);
		BeneficiaryController beneficiaryController = new BeneficiaryController(beneficiaryRepository);
		FoodController foodController = new FoodController(foodRepository);
		
		// get all the requests, grouped by FoodItem and sorted by priority
		HashMap<String, List<Request>> requestByFoodItemMap = groupRequestByFoodItem(requestController);
		Set<String> keySet = requestByFoodItemMap.keySet();
		
		// loop through the HashMap
		*//** KIV: This needs to be changed, incorrect use of data structure **//*
		for (String description : keySet) {
			List<Request> currentRequestFoodItemList = requestByFoodItemMap.get(description);
			int inventoryQty = getInventoryQty(description, foodController);
			
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
				String[] itemGroup = getFoodItemGroup(description, foodController);
				AllocationFoodItem afi = new AllocationFoodItem(itemGroup[0], itemGroup[1], description, reqQty, allocatedQty);
				
				addIntoAllocationList(allocationList, afi, currentRequest.getBeneficiaryName());
			}
		}
		
		// Add all Allocations into the database
		for (Allocation allocation : allocationList) {
			List<Allocation> existingAllocations = getAllAllocations();
			if (!existingAllocations.contains(allocation)) {
				// add into database
				this.allocationRepository.save(allocation);
			}
		}
		
		return allocationList;
		
	}
	
	@PostMapping("/update-allocation")
	// you need a way to get from the body
	public Status updateAllocation(@RequestBody ArrayList<AllocationUpdate> auList) { 
		*//** 
		 * TODO
		 * This should return me the success status of the allocation
		 *//* 
		
		*//** TODO: Determine when there is a failure. If so, return "fail" **//*
		// use this to determine if there are any fails
		String updateStatus = "success";
		
		// Obtain the necessary Allocations
		List<Allocation> aList = getAllAllocations();
				
		// Refactor this inefficient code
		for (AllocationUpdate au : auList) {
			String bvName = au.getName();
			List<FoodItem> fiList = au.getFoodItems();
			
			for (Allocation a : aList) {
				// if the bvName is the same, update the allocated qty
				if (a.getName().equals(bvName)) {
					List<AllocationFoodItem> afiList = a.getList();
					System.out.println("Enters line 137 ");
					// go through the list of FoodItems that need to be updated
					for (FoodItem fi : fiList) {
						String description = fi.getDescription();
						int new_allocated_qty = fi.getQuantity();	// in context
						
						System.out.println("Enters line 142");
						// go through the list of AllocationFoodItems to update
						for (AllocationFoodItem afi : afiList) {
							System.out.println(afi.getDescription());
							System.out.println(description);
							if (afi.getDescription().equals(description)) {
								afi.setAl_qty(new_allocated_qty);
								this.allocationRepository.save(a);
							}
						}
					}
					
				}
			}
		}
		*//** PLEASE REDO THIS IN THE FUTURE **//*
		// Add all Allocations into the database
		
		for (Allocation allocation : aList) {
			List<Allocation> existingAllocations = getAllAllocations();
			for (Allocation existingAllocation : existingAllocations) {
				// if the allocation is the same, update the value
				if (existingAllocation.equals(allocation)) {
					
				}
			}
		}

		// update the Allocations into the database
		for (Allocation allocation : aList) {
			this.allocationRepository.save(allocation);
		} 
		

		return new Status(updateStatus);
	}
	
	////////////////////////// Helper methods ////////////////////////////////
	
	 Groups the requests based on FoodItem 
	protected HashMap<String, List<Request>> groupRequestByFoodItem(RequestController requestController) {
		
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
	
	 Get the current inventory level 
	protected int getInventoryQty(String description, FoodController foodController) {
		return foodController.getFoodItemQuantity(description);
	}
	
	 Get the FoodItem group 
	protected String[] getFoodItemGroup(String description, FoodController foodController) {
		// [0]: category, [1]: classification
		return foodController.findItemGroup(description);
	}
	
	 Add the AllocationFoodItem into the AllocationList 
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
*/