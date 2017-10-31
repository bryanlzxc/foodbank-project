package foodbank.dashboard.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbank.admin.controller.AdminController;
import foodbank.admin.repository.AdminRepository;
import foodbank.dashboard.entity.AdminDashboard;
import foodbank.dashboard.entity.Demand;
import foodbank.dashboard.entity.Supply;
import foodbank.inventory.controller.FoodController;
import foodbank.inventory.entity.Category;
import foodbank.inventory.entity.Classification;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;
import foodbank.request.controller.RequestController;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private RequestRepository requestRepository;
	
    @GetMapping("/admin")
    public AdminDashboard getAdminDashboard() {
    	RequestController requestController = new RequestController(requestRepository);
    	FoodController foodController = new FoodController(foodRepository);
    	//Retrieve request window status
    	Map<String, String> windowStatus = getWindowStatus();
    	//Generate top-k demand list of Classification and FoodItem name
    	List<Demand> topKDemandList = getTopKDemand(requestController, foodController);
    	Map<String, List<Map<String, String>>> topKDemandMap = demandMapper(topKDemandList);
    	//Retrieve top-k supply list of Classification and FoodItem name
    	List<Supply> topKSupplyList = getTopKSupply(foodController);
    	Map<String, List<Map<String, String>>> topKSupplyMap = supplyMapper(topKSupplyList);
    	//Retrieve request history list
    	//List<Request> requests = getRequestHistory();
    	//Retrieve number of distinct beneficiary requests
    	Map<String, Integer> uniqueRequestReturn = countDistinctBeneficiaryRequests(requestController);
    	AdminDashboard adminDashboard = new AdminDashboard(topKDemandMap, topKSupplyMap, windowStatus, uniqueRequestReturn);
    	return adminDashboard;
    }
    
    /* Implementable at a future date
    private List<Request> getRequestHistory() {
    	RequestController requestController = new RequestController(requestRepository);
    	List<Request> 
    	return null;
    }
    */
    
    private Map<String, String> getWindowStatus() {
    	AdminController adminController = new AdminController(adminRepository);
    	boolean isWindowActive = adminController.getWindowStatus(); //false for close, true for open
    	return Collections.singletonMap("current-window", isWindowActive == true? "open" : "close");
    }
    
    /*
     * Code needs to be re-factored after acceptance, shell object for demand and supply?
     */
    private List<Demand> getTopKDemand(RequestController requestController, FoodController foodController) {
    	Demand demandRankOne = null;
    	Demand demandRankTwo = null;
    	Demand demandRankThree = null;
    	List<Request> requests = requestController.getAllRequest();
    	for(Request request : requests) {
    		int quantity = request.getFoodItemQuantity();
    		FoodItem foodItem = request.getFoodItem();
    		if(quantity > Demand.first) {
    			Demand.third = Demand.second;
    			demandRankThree = demandRankTwo;
    			Demand.second = Demand.first;
    			demandRankTwo = demandRankOne;
    			Demand.first = quantity;
    			demandRankOne = new Demand(foodController.findClassificationOfFoodItem(foodItem), foodItem);
    		} else if (quantity > Demand.second) {
    			Demand.third = Demand.second;
    			demandRankThree = demandRankTwo;
    			Demand.second = quantity;
    			demandRankTwo = new Demand(foodController.findClassificationOfFoodItem(foodItem), foodItem);
    		} else if (quantity > Demand.third) {
    			Demand.third = quantity;
    			demandRankThree = new Demand(foodController.findClassificationOfFoodItem(foodItem), foodItem);
    		}
    	}
    	return Arrays.asList(demandRankOne, demandRankTwo, demandRankThree);
    }
    
    private Map<String, List<Map<String, String>>> demandMapper(List<Demand> demands) {
    	List<Map<String, String>> outputArray = new ArrayList<Map<String, String>>();
    	Map<String, List<Map<String, String>>> demandMap = Collections.singletonMap("demand", outputArray);
    	for(Demand demand : demands) {
    		HashMap<String, String> output = new HashMap<String, String>();
    		output.put("classification", demand.getClassification());
    		output.put("item", demand.getFoodItem().getDescription());
    		outputArray.add(output);
		}
    	return demandMap;
    }
    
    /*
     * Code needs to be re-factored after acceptance, shell object for demand and supply?
     */
    private List<Supply> getTopKSupply(FoodController foodController) {
    	Supply supplyRankOne = null;
    	Supply supplyRankTwo = null;
    	Supply supplyRankThree = null;
    	List<Category> categories = foodController.getAllCategories();
    	for(Category category : categories) {
    		List<Classification> classifications = category.getClassification();
    		for(Classification classification : classifications) {
    			String classificationString = classification.getClassification();
    			List<FoodItem> foodItems = classification.getFoodItems();
    			for(FoodItem foodItem : foodItems) {
    				int quantity = foodItem.getQuantity();
    				if(quantity > Supply.first) {
    					Supply.third = Supply.second;
    					supplyRankThree = supplyRankTwo;
    					Supply.second = Supply.first;
    					supplyRankTwo = supplyRankOne;
    					Supply.first = quantity;
    					supplyRankOne = new Supply(classificationString, foodItem);
    				} else if (quantity > Supply.second) {
    					Supply.third = Supply.second;
    					Supply.second = quantity;
    					supplyRankTwo = new Supply(classificationString, foodItem);
    				} else if (quantity > Supply.third) {
    					Supply.third = quantity;
    					supplyRankThree = new Supply(classificationString, foodItem);
    				}
    			}
    		}
    	}
    	return Arrays.asList(supplyRankOne, supplyRankTwo, supplyRankThree);
    }
    
    private Map<String, List<Map<String, String>>> supplyMapper(List<Supply> supplies) {
    	List<Map<String, String>> outputArray = new ArrayList<Map<String, String>>();
    	Map<String, List<Map<String, String>>> supplyMap = Collections.singletonMap("supply", outputArray);
    	for(Supply supply : supplies) {
    		HashMap<String, String> output = new HashMap<String, String>();
    		output.put("classification", supply.getClassification());
    		output.put("item", supply.getFoodItem().getDescription());
    		outputArray.add(output);
		}
    	return supplyMap;
    }
    
    private Map<String, Integer> countDistinctBeneficiaryRequests(RequestController requestController) {
    	List<Request> requests = requestController.getAllRequest();
    	List<String> beneficiaryList = new ArrayList<String>();
    	for(Request request : requests) {
    		String requestBeneficiaryName = request.getBeneficiaryName();
    		if(!beneficiaryList.contains(requestBeneficiaryName)) {
    			beneficiaryList.add(requestBeneficiaryName);
    		}
    	}
    	return Collections.singletonMap("distinct-requests", beneficiaryList.size());
    }

}
