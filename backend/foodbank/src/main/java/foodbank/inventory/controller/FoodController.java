package foodbank.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbank.inventory.dto.FoodItemDTO;
import foodbank.inventory.entity.Category;
import foodbank.inventory.entity.Classification;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.service.FoodService;
import foodbank.response.dto.ResponseDTO;
import foodbank.util.MessageConstants;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@RestController
@CrossOrigin
@RequestMapping("/food-items")
public class FoodController {
	
	@Autowired
	private FoodService foodService;
	
	@GetMapping("/display-all")
	public List<Category> getAllCategories() {
		return foodService.retrieveAllCategories();
	}
	
	@GetMapping("/category={category}/display-all")
	public List<Classification> getAllClassificationsInCategory(@PathVariable("category") String category) {
		return foodService.retrieveAllClassificationsInCategory(category);
	}
	
	@GetMapping("/category={category}/classification={classification}/display-all")
	public List<FoodItem> getAllFoodItemsInClassification(@PathVariable("category") String category, @PathVariable("classification") String classification) {
		return foodService.retrieveFoodItemsByCategoryAndClassification(category, classification);
	}
	
	@PostMapping("/update-item")
	public ResponseDTO updateFoodItem(@RequestBody FoodItemDTO foodItem) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.ITEM_OVERWRITE_SUCCESS);
		try {
			foodService.overwriteFoodItem(foodItem);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-item-quantity")
	public ResponseDTO updateFoodItemQuantity(@RequestBody FoodItemDTO foodItem) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.ITEM_UPDATE_SUCCESS);
		try {
			foodService.amendFoodItemQuantity(foodItem);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/batch/update-items")
	public ResponseDTO updateFoodItems(@RequestBody FoodItemDTO[] foodItems) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.ITEM_OVERWRITE_SUCCESS);
		try {
			foodService.overwriteFoodItems(foodItems);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/batch/update-items-quantity")
	public ResponseDTO updateFoodItemsQuantity(@RequestBody FoodItemDTO[] foodItems) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.ITEM_UPDATE_SUCCESS);
		try {
			foodService.amendFoodItemsQuantity(foodItems);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}

}
