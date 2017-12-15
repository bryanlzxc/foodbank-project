package foodbank.dashboard.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.dashboard.service.DashboardService;
import foodbank.inventory.entity.Category;
import foodbank.inventory.entity.Classification;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;

@Service
public class DashboardServiceImpl implements DashboardService {
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private RequestRepository requestRepository;

	@Override
	public List<Category> retrieveTopKCategoriesInDemand(int topKPosition) {
		// TODO Auto-generated method stub
		List<Request> requests = requestRepository.findAll();
		List<Category> categories = foodRepository.findAll();
		TreeMap<String, Integer> requestCategoryMap = new TreeMap<String, Integer>();
		for(Request request : requests) {
			Integer storedValue = requestCategoryMap.get(request.getCategory());
			if(storedValue != null) {
				storedValue += request.getFoodItem().getQuantity();
				requestCategoryMap.replace(request.getCategory(), storedValue);
			} else {
				requestCategoryMap.put(request.getCategory(), request.getFoodItem().getQuantity());
			}
		}
		TreeMap<Integer, Category> categoriesMap = new TreeMap<Integer, Category>();
		for(Category category : categories) {
			String categoryName = category.getCategory();
			if(requestCategoryMap.containsKey(categoryName)) {
				categoriesMap.put(requestCategoryMap.get(categoryName), category);
			}
		}
		List<Category> topKCategoriesInDemand = new ArrayList<Category>();
		Iterator<Integer> iterator = categoriesMap.keySet().iterator();
		for(int i = 0; i < categoriesMap.size(); i++) {
			if(iterator.hasNext()) {
				topKCategoriesInDemand.add(categoriesMap.get(iterator.next()));
			}
		}
		Collections.reverse(topKCategoriesInDemand);
		if(topKPosition > topKCategoriesInDemand.size()) {
			topKPosition = topKCategoriesInDemand.size();
		}
		return topKCategoriesInDemand.subList(0, topKPosition);
	}

	@Override
	public List<Category> retrieveTopKCategoriesInSupply(int topKPosition) {
		// TODO Auto-generated method stub
		List<Category> categories = foodRepository.findAll();
		TreeMap<Integer, Category> categoriesMap = new TreeMap<Integer, Category>();
		for(Category category : categories) {
			int combinedQuantity = 0;
			List<Classification> classifications = category.getClassification();
			for(Classification classification : classifications) {
				List<FoodItem> foodItems = classification.getFoodItems();
				for(FoodItem foodItem : foodItems) {
					combinedQuantity += foodItem.getQuantity();
				}
			}
			categoriesMap.put(combinedQuantity, category);
		}
		List<Category> topKCategoriesInSupply = new ArrayList<Category>();
		Iterator<Integer> iterator = categoriesMap.keySet().iterator();
		for(int i = 0; i < categoriesMap.size(); i++) {
			if(iterator.hasNext()) {
				topKCategoriesInSupply.add(categoriesMap.get(iterator.next()));
			}
		}
		Collections.reverse(topKCategoriesInSupply);
		if(topKPosition > topKCategoriesInSupply.size()) {
			topKPosition = topKCategoriesInSupply.size();
		}
		return topKCategoriesInSupply.subList(0, topKPosition);
	}

	@Override
	public List<Classification> retrieveTopKClassificationsInDemand(String categoryName, int topKPosition) {
		// TODO Auto-generated method stub
		List<Request> requests = requestRepository.findAll();
		Iterator<Request> requestIterator = requests.iterator();
		List<Request> requestsInCategory = new ArrayList<Request>();
		while(requestIterator.hasNext()) {
			Request request = requestIterator.next();
			if(request.getCategory().equals(categoryName)) {
				requestsInCategory.add(request);
			}
		}
		List<Classification> classifications = foodRepository.findByCategory(categoryName);
		TreeMap<String, Integer> requestClassificationMap = new TreeMap<String, Integer>();
		for(Request request : requests) {
			Integer storedValue = requestClassificationMap.get(request.getClassification());
			if(storedValue != null) {
				storedValue += request.getFoodItem().getQuantity();
				requestClassificationMap.replace(request.getClassification(), storedValue);
			} else {
				requestClassificationMap.put(request.getClassification(), request.getFoodItem().getQuantity());
			}
		}
		TreeMap<Integer, Classification> classificationsMap = new TreeMap<Integer, Classification>();
		for(Classification classification : classifications) {
			String classificationName = classification.getClassification();
			if(requestClassificationMap.containsKey(classificationName)) {
				classificationsMap.put(requestClassificationMap.get(classificationName), classification);
			}
		}
		List<Classification> topKClassificationsInDemand = new ArrayList<Classification>();
		Iterator<Integer> iterator = classificationsMap.keySet().iterator();
		for(int i = 0; i < classificationsMap.size(); i++) {
			if(iterator.hasNext()) {
				topKClassificationsInDemand.add(classificationsMap.get(iterator.next()));
			}
		}
		Collections.reverse(topKClassificationsInDemand);
		if(topKPosition > topKClassificationsInDemand.size()) {
			topKPosition = topKClassificationsInDemand.size();
		}
		return topKClassificationsInDemand.subList(0, topKPosition);
	}

	@Override
	public List<Classification> retrieveTopKClassificationsInSupply(String categoryName, int topKPosition) {
		// TODO Auto-generated method stub
		List<Category> categories = foodRepository.findAll();
		TreeMap<Integer, Classification> classificationsMap = new TreeMap<Integer, Classification>();
		for(Category category : categories) {
			if(category.getCategory().equals(categoryName)) {
				List<Classification> classifications = category.getClassification();
				for(Classification classification : classifications) {
					int combinedQuantity = 0;
					List<FoodItem> foodItems = classification.getFoodItems();
					for(FoodItem foodItem : foodItems) {
						combinedQuantity += foodItem.getQuantity();
					}
					classificationsMap.put(combinedQuantity, classification);
				}
			}
		}
		List<Classification> topKClassificationsInSupply = new ArrayList<Classification>();
		Iterator<Integer> iterator = classificationsMap.keySet().iterator();
		for(int i = 0; i < classificationsMap.size(); i++) {
			if(iterator.hasNext()) {
				topKClassificationsInSupply.add(classificationsMap.get(iterator.next()));
			}
		}
		Collections.reverse(topKClassificationsInSupply);
		if(topKPosition > topKClassificationsInSupply.size()) {
			topKPosition = topKClassificationsInSupply.size();
		}
		return topKClassificationsInSupply.subList(0, topKPosition);
	}

	@Override
	public List<FoodItem> retrieveTopKItemsInDemand(String categoryName, String classificationName, int topKPosition) {
		// TODO Auto-generated method stub
		List<Request> requests = requestRepository.findAll();
		Iterator<Request> requestIterator = requests.iterator();
		List<FoodItem> requestsInCategoryAndClassification = new ArrayList<FoodItem>();
		while(requestIterator.hasNext()) {
			Request request = requestIterator.next();
			if(request.getCategory().equals(categoryName) && request.getClassification().equals(classificationName)) {
				requestsInCategoryAndClassification.add(request.getFoodItem());
			}
		}
		requestsInCategoryAndClassification.sort(Comparator.comparingInt(FoodItem::getQuantity));
		Collections.reverse(requestsInCategoryAndClassification);
		if(topKPosition > requestsInCategoryAndClassification.size()) {
			topKPosition = requestsInCategoryAndClassification.size();
		}
		return requestsInCategoryAndClassification.subList(0, topKPosition);
	}

	@Override
	public List<FoodItem> retrieveTopKItemsInSupply(String categoryName, String classificationName, int topKPosition) {
		// TODO Auto-generated method stub
		List<Category> categories = foodRepository.findAll();
		TreeMap<Integer, FoodItem> foodItemsMap = new TreeMap<Integer, FoodItem>();
		for(Category category : categories) {
			if(category.getCategory().equals(categoryName)) {
				List<Classification> classifications = category.getClassification();
				for(Classification classification : classifications) {
					if(classification.getClassification().equals(classificationName)) {
						List<FoodItem> foodItems = classification.getFoodItems();
						for(FoodItem foodItem : foodItems) {
							foodItemsMap.put(foodItem.getQuantity(), foodItem);
						}
					}
				}
			}
		}
		List<FoodItem> topKFoodItemsInSupply = new ArrayList<FoodItem>();
		Iterator<Integer> iterator = foodItemsMap.keySet().iterator();
		for(int i = 0; i < foodItemsMap.size(); i++) {
			if(iterator.hasNext()) {
				topKFoodItemsInSupply.add(foodItemsMap.get(iterator.next()));
			}
		}
		Collections.reverse(topKFoodItemsInSupply);
		if(topKPosition > topKFoodItemsInSupply.size()) {
			topKPosition = topKFoodItemsInSupply.size();
		}
		return topKFoodItemsInSupply.subList(0, topKPosition);
	}

	@Override
	public List<FoodItem> retrieveTopKItemsInDemandAcrossCategories(int topKPosition) {
		// TODO Auto-generated method stub
		List<Request> requests = requestRepository.findAll();
		List<FoodItem> sortedRequests = new ArrayList<FoodItem>();
		requests.forEach(request -> sortedRequests.add(request.getFoodItem()));
		sortedRequests.sort(Comparator.comparingInt(FoodItem::getQuantity));
		Collections.reverse(sortedRequests);
		if(topKPosition > sortedRequests.size()) {
			topKPosition = sortedRequests.size();
		}
		return sortedRequests.subList(0, topKPosition);
	}

	@Override
	public List<FoodItem> retrieveTopKItemsInSupplyAcrossCategories(int topKPosition) {
		// TODO Auto-generated method stub
		List<Category> categories = foodRepository.findAll();
		TreeMap<Integer, FoodItem> foodItemsMap = new TreeMap<Integer, FoodItem>();
		for(Category category : categories) {
			List<Classification> classifications = category.getClassification();
			for(Classification classification : classifications) {
				List<FoodItem> foodItems = classification.getFoodItems();
				for(FoodItem foodItem : foodItems) {
					foodItemsMap.put(foodItem.getQuantity(), foodItem);
				}
			}
		}
		List<FoodItem> topKFoodItemsInSupplyAcrossCategories = new ArrayList<FoodItem>();
		Iterator<Integer> iterator = foodItemsMap.keySet().iterator();
		for(int i = 0; i < foodItemsMap.size(); i++) {
			if(iterator.hasNext()) {
				topKFoodItemsInSupplyAcrossCategories.add(foodItemsMap.get(iterator.next()));
			}
		}
		Collections.reverse(topKFoodItemsInSupplyAcrossCategories);
		if(topKPosition > topKFoodItemsInSupplyAcrossCategories.size()) {
			topKPosition = topKFoodItemsInSupplyAcrossCategories.size();
		}
		return topKFoodItemsInSupplyAcrossCategories.subList(0, topKPosition);
	}

}
