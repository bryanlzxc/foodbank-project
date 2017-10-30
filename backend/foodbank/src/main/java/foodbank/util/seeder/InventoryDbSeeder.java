package foodbank.util.seeder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import foodbank.inventory.entity.Category;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;
import foodbank.util.InventoryDbUtil;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@Component
public class InventoryDbSeeder implements CommandLineRunner {
	
	private FoodRepository foodRepository;
	
	public InventoryDbSeeder(FoodRepository foodRepository) {
		this.foodRepository = foodRepository;
	}
	
	/*
	@Override
	public void run(String... strings) throws Exception {
		//Populating Baby Food Category
		Category babyFood = new Category("Baby_Food", Arrays.asList(
				new Classification("Baby_Cereals", Arrays.asList(
					new FoodItem("Baby_Cereals-250g-Halal", 2),
					new FoodItem("Baby_Cereals-450g", 1),
					new FoodItem("Baby_Cereals-50g-Halal", 2))),
				new Classification("Baby_Food", Arrays.asList(
					new FoodItem("Baby_Food-150g",5),
					new FoodItem("Baby_Food-50g", 13),
					new FoodItem("Baby_Food-50g-Halal", 4))),
				new Classification("Baby_Milk_Powder", Arrays.asList(
					new FoodItem("Baby_Milk_Powder-350g", 1),
					new FoodItem("Baby_Milk_Powder-350g-Halal", 1),
					new FoodItem("Baby_Milk_Powder-450g-Halal", 7),
					new FoodItem("Baby_Milk_Powder-50g", 5),
					new FoodItem("Baby_Milk_Powder-50g-Halal", 5),
					new FoodItem("Baby_Milk_Powder-650g-Halal", 2),
					new FoodItem("Baby_Milk_Powder-750g-Halal", 1),
					new FoodItem("Baby_Milk_Powder-850g", 1),
					new FoodItem("Baby_Milk_Powder-850g-Halal", 1)))
		));
		//Populating Baking Needs Category
		Category bakingNeeds = new Category("Baking_Needs", Arrays.asList(
				new Classification("Baking_Powder", Arrays.asList(
					new FoodItem("Baking_Powder-50g-Halal", 1))),
				new Classification("Flour", Arrays.asList(
					new FoodItem("Corn_Flour-450g-Halal", 1),
					new FoodItem("Flour-1.5kg", 1),
					new FoodItem("Flour-950g-Halal", 2),
					new FoodItem("Oat_Flour-650g", 1),
					new FoodItem("Premix Powder-1.5kg", 1),
					new FoodItem("Rice_Flour-1.5kg-Halal", 1))),
				new Classification("Other_Baking_Needs", Arrays.asList(
					new FoodItem("Agar_Agar_Powder-10g-Halal", 1),
					new FoodItem("Jelly_Crystals-150g-Halal", 1),
					new FoodItem("Sago-550g", 1),
					new FoodItem("Skimmed Milk Powder-250g", 1),
					new FoodItem("Pancake_Mix-450g",1),
					new FoodItem("Premix_Powder-150g", 2),
					new FoodItem("Premix_Powder-250g", 1),
					new FoodItem("Premix_Powder-250g-Halal", 1),
					new FoodItem("Premix_Powder-350g", 1),
					new FoodItem("Premix_Powder-450g", 1)))
				));
		
		//Dropping entire repository to ensure no duplicates entries crash the app
		this.foodRepository.deleteAll();
		//Bootstrapping the repository with the categories defined
		List<Category> categories = Arrays.asList(babyFood, bakingNeeds);
		this.foodRepository.save(categories);
	}
	*/
	
	@Override
	public void run(String... strings) throws Exception {
		
		ConcurrentHashMap<String, ConcurrentHashMap<String, ArrayList<FoodItem>>> inventoryMap = 
				InventoryDbUtil.readFromInventoryFile(new File(InventoryDbUtil.class.getClassLoader().getResource("inventory-data.csv").getFile()));
		List<Category> categories = InventoryDbUtil.generateCategoryList(inventoryMap);
		//Dropping entire repository to ensure no duplicates entries crash the app
		this.foodRepository.deleteAll();
		//Bootstrapping the repository with the categories defined
		this.foodRepository.save(categories);
		
	}
	
}
