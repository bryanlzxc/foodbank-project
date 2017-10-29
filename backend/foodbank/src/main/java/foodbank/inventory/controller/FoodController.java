package foodbank.inventory.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbank.inventory.entity.Category;
import foodbank.inventory.entity.Classification;
import foodbank.inventory.repository.FoodRepository;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@RestController
@RequestMapping("/food-items")
public class FoodController {
	
	private FoodRepository foodRepository;
	
	public FoodController(FoodRepository foodRepository) {
		this.foodRepository = foodRepository;
	}
	
	@GetMapping("/display-all")
	public List<Category> getAllCategories() {
		
		List<Category> toReturn = foodRepository.findAll();
		for(Category c : toReturn) {
			System.out.println("Category name = " + c.getCategory());
			List<Classification> classifications = c.getClassification();
			for(Classification cls : classifications) {
				System.out.println("The lists within the categories are = " + cls.getClassification());
			}
		}
		return this.foodRepository.findAll();
	}
	
	@GetMapping("/category={category}/display-all")
	public List<Classification> getAllClassificationsInCategory(@PathVariable("category") String category) {
		List<Category> cat = this.foodRepository.findAll();
		for(Category c : cat) {
			if(c.getCategory().equals(category)) {
				return c.getClassification();
			}
		}
		return new ArrayList<Classification>();
	}
	
	@GetMapping("/{id}")
	public Category getById(@PathVariable("id") String id) {
		return this.foodRepository.findOne(id);
	}
	
	@GetMapping("/category-id={id}/display-all")
	public List<Classification> displayAllClassificationsInCategory(@PathVariable("id") String id) {
		Category category = this.foodRepository.findOne(id);
		return category.getClassification();
	}

}
