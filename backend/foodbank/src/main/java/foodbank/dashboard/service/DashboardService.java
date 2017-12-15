package foodbank.dashboard.service;


import java.util.List;

import foodbank.inventory.entity.Category;
import foodbank.inventory.entity.Classification;
import foodbank.inventory.entity.FoodItem;

public interface DashboardService {
	
	List<Category> retrieveTopKCategoriesInDemand(final int topKPosition);
	
	List<Category> retrieveTopKCategoriesInSupply(final int topKPosition);
	
	List<Classification> retrieveTopKClassificationsInDemand(final String category, final int topKPosition);
	
	List<Classification> retrieveTopKClassificationsInSupply(final String category, final int topKPosition);
	
	List<FoodItem> retrieveTopKItemsInDemand(final String category, final String classification, final int topKPosition);
	
	List<FoodItem> retrieveTopKItemsInSupply(final String category, final String classification, final int topKPosition);
	
	List<FoodItem> retrieveTopKItemsInDemandAcrossCategories(final int topKPosition);
	
	List<FoodItem> retrieveTopKItemsInSupplyAcrossCategories(final int topKPosition);

}