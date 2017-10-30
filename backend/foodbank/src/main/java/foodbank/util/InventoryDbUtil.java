package foodbank.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentHashMap;

import foodbank.inventory.entity.Category;
import foodbank.inventory.entity.Classification;
import foodbank.inventory.entity.FoodItem;

/*
 * Created by: Lau Peng Liang, Bryan
 */

public class InventoryDbUtil {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ConcurrentHashMap<String, ConcurrentHashMap<String, ArrayList<FoodItem>>> readFromInventoryFile(File file) {
		ArrayList<String> knownCategories = generateCategoryMapping();
		ConcurrentHashMap<String, ConcurrentHashMap<String, ArrayList<FoodItem>>> masterList = new ConcurrentHashMap(new HashMap<String, HashMap<String, ArrayList<FoodItem>>>());
		try(BufferedReader reader = new BufferedReader(new FileReader(file));) {
			//Clear headers
			String currentLine = reader.readLine();
			//Start of Read Process
			while((currentLine = reader.readLine()) != null) {
				String[] currentLineArray = currentLine.split(",");
				String convertedCategoryString = currentLineArray[0].replaceAll(" ", "_");
				String convertedClassificationString = currentLineArray[1].replaceAll(" ", "_");
				if(knownCategories.contains(convertedCategoryString)) {
					//Retrieve the HashMap<String, ArrayList<FoodItem>> from the master list
					ConcurrentHashMap<String, ArrayList<FoodItem>> classificationList = masterList.get(convertedCategoryString);
					if(classificationList == null) {
						classificationList = new ConcurrentHashMap(new HashMap<String, ArrayList<FoodItem>>());
						masterList.put(convertedCategoryString, classificationList);
					}
					if(classificationList.containsKey(convertedClassificationString)) {
						//Retrieve the ArrayList<FoodItem>
						ArrayList<FoodItem> foodItems = classificationList.get(convertedClassificationString);
						boolean doesFoodItemExistInList = false;
						for(ListIterator<FoodItem> iterator = foodItems.listIterator(); iterator.hasNext();) {
							FoodItem foodItem = iterator.next();
							if(foodItem.getDescription().equals(currentLineArray[2])) {
								doesFoodItemExistInList = true;
								foodItem.setQuantity(Integer.parseInt(currentLineArray[3]) + foodItem.getQuantity());
								break;
							} else {
								//System.out.println("Adding this food item to the list = " + currentLineArray[2]);
								//iterator.add(new FoodItem(currentLineArray[2], Integer.parseInt(currentLineArray[3])));
							}
						}
						if(!doesFoodItemExistInList) {
							System.out.println("Adding this food item to the list = " + currentLineArray[2] + " in this quantity = " + currentLineArray[3]);
							foodItems.add(new FoodItem(currentLineArray[2], Integer.parseInt(currentLineArray[3])));
						}
					} else {
						//Add a new Key:Value pairing
						ArrayList<FoodItem> newClassificationFoodItemsList = new ArrayList<FoodItem>();
						newClassificationFoodItemsList.add(new FoodItem(currentLineArray[2], Integer.parseInt(currentLineArray[3])));
						classificationList.put(convertedClassificationString, newClassificationFoodItemsList);
					}
				} else {
					//Whole new category, create everything from scratch
					ConcurrentHashMap<String, ArrayList<FoodItem>> newCategoryClassificationMap = new ConcurrentHashMap(new HashMap<String, ArrayList<FoodItem>>());
					ArrayList<FoodItem> newCategoryFoodItems = new ArrayList<FoodItem>();
					newCategoryFoodItems.add(new FoodItem(currentLineArray[2], Integer.parseInt(currentLineArray[3])));
					newCategoryClassificationMap.put(convertedClassificationString, newCategoryFoodItems);
					masterList.put(convertedCategoryString, newCategoryClassificationMap);
				}
			}
		} catch (FileNotFoundException e) {
			// Error Handling
			e.printStackTrace(); //Temporary
		} catch (IOException e) {
			// Error Handling
			e.printStackTrace(); //Temporary
		}
		return masterList;
	}
	
	public static List<Category> generateCategoryList(ConcurrentHashMap<String, ConcurrentHashMap<String, ArrayList<FoodItem>>> inventoryMap) {
		List<Category> categories = new ArrayList<Category>();
		inventoryMap.forEach(
				(categoryName, listOfClassificationsInCategory) -> categories.add(new Category(categoryName, generateClassificationList(listOfClassificationsInCategory))));
		return categories;
	}
	
	private static List<Classification> generateClassificationList(ConcurrentHashMap<String, ArrayList<FoodItem>> concurrentMap) {
		List<Classification> classifications = new ArrayList<Classification>();
		concurrentMap.forEach((classificationName, foodItemsInClassification) -> classifications.add(new Classification(classificationName, foodItemsInClassification)));
		return classifications;
	}
	
	/*
	 * This is where we hard-code the category names for easier mapping
	 */
	private static ArrayList<String> generateCategoryMapping() {
		
		ArrayList<String> categories = new ArrayList<String>();
		categories.add("Baby_Food");
		categories.add("Baking_Needs");
		categories.add("Beverages");
		categories.add("Canned_Food");
		categories.add("Condiments");
		categories.add("Dried_Food");
		categories.add("Preserved_Food");
		categories.add("Snacks");
		categories.add("Staples");
		categories.add("Others");
		return categories;
		
	}

}
