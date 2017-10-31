package foodbank.allocation.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import foodbank.allocation.entity.Allocation;
import foodbank.allocation.entity.AllocationOutcome;
import foodbank.allocation.repository.AllocationRepository;
import foodbank.beneficiary.controller.BeneficiaryController;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;
import foodbank.login.entity.LoginOutcome;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;
import foodbank.user.entity.User;

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
	
	public AllocationController(AllocationRepository allocationRepository) {
		this.allocationRepository = allocationRepository;
	}
	
	@GetMapping("/display-all")
	public List<Allocation> getAllAllocations() {
		return this.allocationRepository.findAll();
	}
	
	@PostMapping("/update-allocation")
	public AllocationOutcome getAllocationOutcome() {
		/** 
		 * This should return me the success status of the allocation
		 */
		
		return null;
		/*
		Allocation storedAllocationDetails = this.allocationRepository.findByUsername(user.getUsername());
		if(storedUserDetails != null) {
			if(storedUserDetails.getPassword().equals(user.getPassword())) {
				return new LoginOutcome(true, storedUserDetails.getUsertype());
			}
		}
		return new LoginOutcome(false, null);
		*/
	}
	
	@PostMapping("/allocation-list")
	public String runAllocationAlgorithm() {
		
		HashMap<Beneficiary, ArrayList<FoodItem>> allocatedDetail = allocateFoodItem();
		
		if (allocatedDetail.isEmpty()) {
			return "Unable to allocate";
		} else {
			return "Allocation successful with id=<some_id>";
		}
		
	}
	
	/* Main Algo for allocation */
	public HashMap<Beneficiary, ArrayList<FoodItem>> allocateFoodItem() {
		
		HashMap<Beneficiary, ArrayList<FoodItem>> allocationDetail = new HashMap<>();
		
		// Obtain the necessary lists
		List<Beneficiary> bvList = getAllBeneficiarySortedByScore();
		HashMap<String, FoodItem> fiMap = getAllFoodItemsForDonation();
		
		// for each bv in the bvList, check for their requests and see if FoodItems are available for it
		for (Beneficiary bv : bvList) {
			
			List<Request> reqList = getAllRequestsByBeneficiary(bv.getName());
			ArrayList<FoodItem> fiForBvList = new ArrayList<>();
			if (reqList != null) {
				/** 
				 * << KIV >>
				 * Is there a priority for requests?? 
				 * If so, will need a Comparable/Comparator
				 * **/
				for (Request req : reqList) {
					// check if it exists in list
					FoodItem fi = fiMap.get(req.getDescription());
					
					/** 
					 * << KIV >>
					 * Write a method to do a check for allocative efficiency
					 * If there are 100 FoodItem
					 * First priority bv requests 95 FoodItem, next ten priorities ask for 
					 * Maybe some kind of weighted ratio --> their score lets them have % of the current stock they ask for 
					 * **/
					
					if (fi != null) fiForBvList.add(fi);
				}
			}
			allocationDetail.put(bv, fiForBvList);
		}
		
		return allocationDetail;
	}
	
	/* Obtain all the Beneficiary for use */
	public List<Beneficiary> getAllBeneficiarySortedByScore() {
		/** Find a way to implement the following **/
		// Get the beneficiaries from the database
		// Sort by scores descending --> Collections.sort() method due to natural Comparable<Beneficiary>
		// Return the sorted list
		
		BeneficiaryController bvController = new BeneficiaryController(beneficiaryRepository);
		List<Beneficiary> bvList = bvController.getAllRequest();
		Collections.sort(bvList);
		
		return bvList;
	}
	
	/* Obtain the Requests that a Beneficiary makes*/
	public List<Request> getAllRequestsByBeneficiary(String beneficiaryName) {
		
		/** Find a way to implement the following **/
		// Search for the Requests by Beneficiary name
		// List<Request> getAllRequestFromBeneficiary(@PathVariable("beneficiary") String beneficiary)
		
		
		
		List<Request> requestList = null;
		if (requestList == null || requestList.isEmpty()) {
			return null;
		} else {
			return requestList;
		}
	}
	
	/* Obtain all the FoodItem available for donation */
	public HashMap<String, FoodItem> getAllFoodItemsForDonation() {
		
		/** Find a way to implement the following **/
		// get all the fooditems from the database
		
		// convert into HashMap with description as key faster searching 
		// --> otherwise will need to constantly loop through the list of food items for each beneficiary with request
		// return the HashMap
		
		return null;
	}
	
}
