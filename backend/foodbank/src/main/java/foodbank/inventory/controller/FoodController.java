package foodbank.inventory.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import foodbank.inventory.dto.FoodItemDTO;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.service.FoodService;
import foodbank.response.dto.ResponseDTO;
import foodbank.util.MessageConstants;

/*
 * Created by: Lau Peng Liang, Bryan
 */

@RestController
@CrossOrigin
@RequestMapping("/inventory")
public class FoodController {
	
	@Autowired
	private FoodService foodService;
	
	@GetMapping("/display-all")
	public List<FoodItem> getAllFoodItems() {
		return foodService.retrieveAllFoodItems();
	}
	
//	@GetMapping("/category={category}/display-all")
	@GetMapping("/display-by-category")
	public List<FoodItem> getAllFoodItemsInCategory(@RequestParam(value = "category", required = true) String category) {
		return foodService.retrieveAllFoodItemsInCategory(category);
	}
	
//	@GetMapping("/category={category}/classification={classification}/**/display-all")
	@GetMapping("/display-by-category-classification")
	public List<FoodItem> getAllFoodItemsInClassification(@RequestParam(value = "category", required = true) String category, @RequestParam(value = "classification", required = true) String classification,
			HttpServletRequest request) {
		String url = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
//		classification = url.substring(url.indexOf("classification=")+"classification=".length(), url.indexOf("/display-all"));
		return foodService.retrieveFoodItemsByCategoryAndClassification(category, classification);
	}
	
	// This method is used to overwrite the quantity of the object in DB
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
	
	// This method is used to amend the quantity of the object in DB
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
	
	// This method is used for BATCH overwrite of the quantities of the objects in DB
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
	
	// This method is used for BATCH amendment of the quantities of the objects in DB
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
	
	@GetMapping("/scanner")
	public Map<String, String> getBarcodeDetails(@RequestParam(value = "barcode", required = true) String barcode) {
		return foodService.readBarcode(barcode);
	}
	
	/*
	@GetMapping("/display-all")
	public List<FoodItem> getAllCategories() {
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
	*/

}
