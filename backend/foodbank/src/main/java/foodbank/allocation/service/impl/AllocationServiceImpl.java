package foodbank.allocation.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.allocation.dto.AllocationDTO;
import foodbank.allocation.dto.BatchAllocationDTO;
import foodbank.allocation.entity.AllocatedFoodItems;
import foodbank.allocation.entity.Allocation;
import foodbank.allocation.repository.AllocationRepository;
import foodbank.allocation.service.AllocationService;
import foodbank.backup.AllocationFoodItem;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.exceptions.InvalidAllocationException;
import foodbank.exceptions.InvalidBeneficiaryException;
import foodbank.inventory.entity.FoodItem;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;
import foodbank.user.entity.User;
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
	
	private final int maxNumberBeneficiariesPerFoodItem = 5;
	
		
	@Override
	public List<Allocation> retrieveAllAllocations() {
		// TODO Auto-generated method stub
		return allocationRepository.findAll();
	}
	
	@Override
	public List<AllocatedFoodItems> retrieveAllocationByBeneficiary(String beneficiary) {
		List<Allocation> allocations = allocationRepository.findAll();
		List<AllocatedFoodItems> foodItemsAllocated = new ArrayList<AllocatedFoodItems>();
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
		List<Beneficiary> beneficiaries = beneficiaryRepository.findAll();
		HashMap<String, Double> beneficiariesScoreTable = generateBeneficiaryScoreTable(beneficiaries);
		HashMap<String, Allocation> allocationMap = generateAllocationMapping(generateMapping(requests),beneficiariesScoreTable);
		for(Allocation allocation : allocationMap.values()) {
			allocationRepository.insert(allocation);
		}
	}
	
	// Helper method to generate the mapping for items by description
	// Takes in all current request when window is closed, and returns a HashMap - Key:FoodItem Description; Value:Requests
	private HashMap<String, List<Request>> generateMapping(List<Request> requests) {
		HashMap<String, List<Request>> requestsByFoodItems = new HashMap<String, List<Request>>();
		for(Request request : requests) {
			String category = request.getFoodItem().getCategory();
			String classification = request.getFoodItem().getClassification();
			String description = request.getFoodItem().getDescription();
			String key = category + "," + classification + "," + description;
			List<Request> requestsContainingFoodItem = requestsByFoodItems.get(key);
			if(requestsContainingFoodItem != null) {
				requestsContainingFoodItem.add(request);
				/* Testing comparing function to avoid writing of a comparator */
//				requestsContainingFoodItem.sort(Comparator.comparing(Request::getBeneficiary, Comparator.comparingDouble(Beneficiary::getScore)));	//should we do the sorting at the end outside this for loop? So we dont sort everytime we add one
//				Collections.reverse(requestsContainingFoodItem);
				requestsByFoodItems.replace(key, requestsContainingFoodItem);
			} else {
				requestsContainingFoodItem = new ArrayList<Request>();
				requestsContainingFoodItem.add(request);
				requestsByFoodItems.put(key, requestsContainingFoodItem);
			}
		}
		return requestsByFoodItems;
	}
	
	// Helper method to generate the mapping for allocations for each beneficiary using their name as the key
	private HashMap<String, Allocation> generateAllocationMapping(Map<String, List<Request>> requestsByFoodItems, HashMap<String,Double> beneficiariesScoreTable) {
		
		HashMap<String, Allocation> allocationMap = new HashMap<String, Allocation>();	//key = beneficiary username; value= ALlocation
		
		for(Map.Entry<String, List<Request>> entry : requestsByFoodItems.entrySet()) {
			String key = entry.getKey();
			List<Request> requestsForFoodItem = entry.getValue();		//if this is non-null, this fooditem has some requests
			Request request = requestsForFoodItem.size() > 0 ? requestsForFoodItem.get(0) : null;
			if(request != null) {
				int inventoryQuantity = retrieveInventoryDetails(				//this variable is the max quantity which we can allocate out
						request.getFoodItem().getCategory(), request.getFoodItem().getClassification(), request.getFoodItem().getDescription()).getQuantity();
				
				//Now this requestsForFoodItem is sorted in accordance to highest score bene to lowest. We want to check if >5 request, make list only contain
				//the highest 5, then we need to get the total score of the beneficiaries to use as denominator.
				
				int numberOfBeneficiaryAllocated = requestsForFoodItem.size();	//this variable is the number of beneficiaries which will be allocated for this specific food item
				double totalScore = 0.0;
				
				if(numberOfBeneficiaryAllocated > maxNumberBeneficiariesPerFoodItem) {
					numberOfBeneficiaryAllocated = maxNumberBeneficiariesPerFoodItem;
				}
				
				for(int i = 0; i<numberOfBeneficiaryAllocated; i++) {			//this loop is used to get the total score of all beneficiaries which will be allocated for this food item
					totalScore += requestsForFoodItem.get(i).getBeneficiary().getScore();
				}
				
				requestsForFoodItem.sort((Request r1, Request r2)->(int)(beneficiariesScoreTable.get(r1.getBeneficiary().getUser().getUsername()) - beneficiariesScoreTable.get(r2.getBeneficiary().getUser().getUsername())));	
				Collections.reverse(requestsForFoodItem);
				System.out.println("--------------------------------------");
				System.out.println(request.getFoodItem().getDescription());
				for(Request r: requestsForFoodItem) {
					System.out.print(r.getBeneficiary().getUser().getUsername());
					System.out.println(" |Score: "+beneficiariesScoreTable.get(r.getBeneficiary().getUser().getUsername()));
				}
				
				LinkedHashMap<String, AllocatedFoodItems> unfulfilledRequests = new LinkedHashMap<>();
				
				for(int i = 0; i<requestsForFoodItem.size(); i++) {				
					Request currentRequest = requestsForFoodItem.get(i);
					
					
					beneficiariesScoreTable.get(currentRequest.getBeneficiary().getUser().getUsername());
					
					double beneficiaryScore = currentRequest.getBeneficiary().getScore();
					double allocationRatio = beneficiaryScore/totalScore;
					
					int maxAllocatedQuantity = (int)Math.ceil(allocationRatio*inventoryQuantity);		//this rounds up the number, calculated by their weightage of score against other beneficiaries scores
					
					int allocatedQuantity = 0;
					int requestedQuantity = currentRequest.getFoodItem().getQuantity();
					
					if(inventoryQuantity > 0) {			//inventoryQuantity will not fall below 0, once it reaches 0, all beneficiaries from this iter will have allocation of 0
						if(maxAllocatedQuantity <= inventoryQuantity) {	//when inventory is sufficient to allocate to this beneficiary
							if(requestedQuantity > maxAllocatedQuantity) {		//when beneficiary request more than what they can get
								allocatedQuantity = maxAllocatedQuantity;
							}else {
								allocatedQuantity = requestedQuantity;
							}
						}else {		//when not enough inventory to meet maxAllocatedQuantity
							maxAllocatedQuantity = inventoryQuantity;
							if(requestedQuantity > maxAllocatedQuantity) {
								allocatedQuantity = maxAllocatedQuantity;
							}else {
								allocatedQuantity = requestedQuantity;
							}
						}
					}else {	//when inventoryQuantity is 0, allocatedQuantity will always be 0
						//do nothing
					}
					
					inventoryQuantity -= allocatedQuantity;
					
					String beneficiaryUsername = currentRequest.getBeneficiary().getUser().getUsername();
					Allocation allocation = allocationMap.get(beneficiaryUsername);
					FoodItem foodItem = currentRequest.getFoodItem();
					String category = foodItem.getCategory();
					String classification = foodItem.getClassification();
					String description = foodItem.getDescription();
					
					//Changing the score after allocation of this food item
					Double oldScore = beneficiariesScoreTable.get(beneficiaryUsername);
					beneficiariesScoreTable.put(beneficiaryUsername, (double)(oldScore-allocatedQuantity));
					
					AllocatedFoodItems allocationByAlgo =  new AllocatedFoodItems(category, classification, description, 
							allocatedQuantity, requestedQuantity, InventorySerializer.retrieveQuantityOfItem(category, classification, description));
					
					if(allocation != null) {		//if this beneficiary already has allocations of other food items
//						allocation.getAllocatedItems().add(new AllocatedFoodItems(category, classification, description, 
//								allocatedQuantity, requestedQuantity, InventorySerializer.retrieveQuantityOfItem(category, classification, description)));
						allocation.getAllocatedItems().add(allocationByAlgo);
						allocationMap.replace(beneficiaryUsername, allocation);
					} else {						//if beneficiary has no other allocation of food items
						ArrayList<AllocatedFoodItems> foodItems = new ArrayList<AllocatedFoodItems>();
						String[] keyArray = key.split(",");
//						foodItems.add(new AllocatedFoodItems(keyArray[0], keyArray[1], keyArray[2], allocatedQuantity, requestedQuantity, 
//								InventorySerializer.retrieveQuantityOfItem(keyArray[0], keyArray[1], keyArray[2])));
						foodItems.add(allocationByAlgo);
						allocation = new Allocation(currentRequest.getBeneficiary(), foodItems);
						allocationMap.put(beneficiaryUsername, allocation);
					}
					
					if(requestedQuantity > allocatedQuantity) {
						System.out.println("---Not fulfilled reqests---");
						System.out.println("requested: "+requestedQuantity+" | allocated: "+allocatedQuantity);
						unfulfilledRequests.put(beneficiaryUsername, allocationByAlgo);
					}
					System.out.println("****************");
					for(String k : unfulfilledRequests.keySet()) {
						AllocatedFoodItems afi = unfulfilledRequests.get(k);
						System.out.println("allocated: "+afi.getAllocatedQuantity()+" | requested: "+afi.getRequestedQuantity());
					}
				}
				
				System.out.println("###############################");
				
				//this chunk of code is to allocate leftovers
				if(!unfulfilledRequests.keySet().isEmpty()) {
					
					int counter = 0;
					while(inventoryQuantity > 0) {
						System.out.println("----------------");
						if(unfulfilledRequests.keySet().isEmpty()) {
							break;
						}
						String usernameKey = unfulfilledRequests.keySet().iterator().next();
						
						AllocatedFoodItems unfulfilledRequest = unfulfilledRequests.get(usernameKey);
						unfulfilledRequests.remove(usernameKey);
						int allocatedQuantity = unfulfilledRequest.getAllocatedQuantity();
						int requestedQuantity = unfulfilledRequest.getRequestedQuantity();
						int difference = requestedQuantity - allocatedQuantity;
						System.out.println("allocated qty: "+allocatedQuantity);
						System.out.println("requested qty: "+requestedQuantity);
						System.out.println("difference: "+difference);
						
						System.out.println(inventoryQuantity);
						
						if(difference >= inventoryQuantity) {
							unfulfilledRequest.setAllocatedQuantity(unfulfilledRequest.getAllocatedQuantity()+inventoryQuantity);
							inventoryQuantity -= inventoryQuantity;
							break;
						}else {
							unfulfilledRequest.setAllocatedQuantity(unfulfilledRequest.getAllocatedQuantity()+difference);
							inventoryQuantity -= difference;
						}
						System.out.println(inventoryQuantity);
						
						String cat = unfulfilledRequest.getCategory();
						String classi = unfulfilledRequest.getClassification();
						String des = unfulfilledRequest.getDescription();
						
						Allocation allocation = allocationMap.get(usernameKey);

						//iterator used to remove previously added AllocatedFoodItem
						Iterator<AllocatedFoodItems> iterAllocationList = allocation.getAllocatedItems().iterator();
						while(iterAllocationList.hasNext()) {
							AllocatedFoodItems allocatedFoodItem = iterAllocationList.next();
							if(allocatedFoodItem.getCategory().equals(cat) && allocatedFoodItem.getClassification().equals(classi) && allocatedFoodItem.getDescription().equals(des)) {
								iterAllocationList.remove();
								allocation.getAllocatedItems().add(unfulfilledRequest);
								allocationMap.replace(usernameKey, allocation);
								System.out.println("ENTERED");
								System.out.println("allocated - "+unfulfilledRequest.getAllocatedQuantity()+" | requested - "+unfulfilledRequest.getRequestedQuantity());
								break;
							}
						}
						
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
	
	private HashMap<String, Double> generateBeneficiaryScoreTable(List<Beneficiary> beneficiaries){
		HashMap<String, Double> beneficiariesScoreTable = new HashMap<>();
		for(Beneficiary beneficiary: beneficiaries) {
			beneficiariesScoreTable.put(beneficiary.getUser().getUsername(), beneficiary.getScore());
		}
		return beneficiariesScoreTable;
	}
	

	@Override
	public void updateAllocation(AllocationDTO allocation) {
		// TODO Auto-generated method stub
		Allocation dbAllocation = allocationRepository.findById(allocation.getId());
		if(dbAllocation == null) {
			throw new InvalidAllocationException(ErrorMessages.INVALID_ALLOCATION);
		}
		List<AllocatedFoodItems> dbAllocatedItems = dbAllocation.getAllocatedItems();
		Map<String, Integer> dbAllocationMap = new HashMap<String, Integer>();
		for(int i = 0; i < dbAllocatedItems.size(); i++) {
			AllocatedFoodItems allocatedFoodItem = dbAllocatedItems.get(i);
			String key = allocatedFoodItem.getCategory() + "," + allocatedFoodItem.getClassification() + "," + allocatedFoodItem.getDescription();
			dbAllocationMap.put(key, i);
		}
		List<FoodItem> updatedAllocatedItems = allocation.getAllocatedItems();
		for(FoodItem foodItem : updatedAllocatedItems) {
			String key = foodItem.getCategory() + "," + foodItem.getClassification() + "," + foodItem.getDescription();
			dbAllocatedItems.get(dbAllocationMap.get(key)).setAllocatedQuantity(foodItem.getQuantity());
		}
		dbAllocation.setAllocatedItems(dbAllocatedItems);
		allocationRepository.save(dbAllocation);
	}

	@Override
	public void batchAllocationUpdate(BatchAllocationDTO batchAllocation) {
		// TODO Auto-generated method stub
		
	}
	
}
