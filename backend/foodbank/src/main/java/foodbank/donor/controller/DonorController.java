package foodbank.donor.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import foodbank.donor.dto.DonorDTO;
import foodbank.donor.entity.Donor;
import foodbank.donor.service.DonorService;
import foodbank.inventory.dto.FoodItemDTO;
import foodbank.inventory.repository.FoodRepository;
import foodbank.response.dto.ResponseDTO;
import foodbank.user.dto.UserDTO;
import foodbank.util.DateParser;
import foodbank.util.MessageConstants;

@RestController
@CrossOrigin
@RequestMapping("/donor")
public class DonorController {
	
	@Autowired
	private DonorService donorService;
	
	@Autowired
	private FoodRepository foodRepository;
	
	@GetMapping("/display-all")
	public List<Donor> getAllDonors() {
		return donorService.getAllDonors();
	}
	
	@GetMapping("/display-donors")
	public List<String> getDonorNames(){
		return donorService.getDonorNames();
	}
	
	//this is used to create a new donor with empty list of PerishableDonation and NonperishableDonation
	@PutMapping("/create-donor")
	public ResponseDTO createDonor(@RequestBody DonorDTO donor) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.DONOR_ADD_SUCCESS);
		try {
			donorService.createDonor(donor);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	//this should not be called by front end, used for testing purposes
	//this will be called by FoodService
	@PostMapping("/update-donor-nonperishable")
	public ResponseDTO updateDonorNonperishable(@RequestBody FoodItemDTO foodItem) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.DONOR_UPDATE_SUCCESS);
		try {
			donorService.updateDonorNonperishable(foodItem);
		}catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
		
	}
    
}
