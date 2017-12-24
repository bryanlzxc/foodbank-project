/*package foodbank.inventory.db.seeder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;


 * Created by: Lau Peng Liang, Bryan
 

@Component
public class InventoryDbSeeder implements CommandLineRunner {
	
	@Autowired
	private FoodRepository foodRepository;

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		foodRepository.deleteAll();
		List<String[]> inventoryData = Files.lines(Paths.get(ClassLoader.getSystemResource("inventory-data.csv").getFile().substring(1)))
			.skip(1)
			.map(currentLine -> currentLine.split(","))
			.collect(Collectors.toList());
		List<FoodItem> foodItems = new ArrayList<FoodItem>();
		inventoryData.forEach(line -> foodItems.add(new FoodItem(line[0], line[1], line[2], Integer.parseInt(line[3].trim()))));
		foodRepository.insert(foodItems);
	}
	
	
	@Override
	public void run(String... strings) throws Exception {
		ConcurrentHashMap<String, ConcurrentHashMap<String, ArrayList<FoodItem>>> inventoryMap = 
				InventoryDbUtil.readFromInventoryFile(new File(InventoryDbUtil.class.getClassLoader().getResource("inventory-data.csv").getFile()));
		List<Category> categories = InventoryDbUtil.generateCategoryList(inventoryMap);
		//Dropping entire repository to ensure no duplicates entries crash the app
		foodRepository.deleteAll();
		//Bootstrapping the repository with the categories defined
		foodRepository.save(categories);
	}
	
	
}
*/