package foodbank.packing.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import foodbank.packing.dto.PackingListDTO;
import foodbank.packing.entity.PackingList;
import foodbank.packing.service.PackingService;
import foodbank.response.dto.ResponseDTO;
import foodbank.util.MessageConstants;

@RestController
@CrossOrigin
@RequestMapping("/packing")
public class PackingController {
	
	@Autowired
	private PackingService packingService;
	
	@GetMapping("/display-all")
	public List<PackingList> getAllPackingLists() {
		return packingService.retrieveAllPackingLists();
	}
	
	@GetMapping("/display-by")
	public PackingList getBeneficiaryPackingList(@RequestParam(value = "beneficiary", required = true) String beneficiary) {
		return packingService.findByBeneficiary(beneficiary);
	}
	
	@PostMapping("/generate-list")
	public ResponseDTO generatePackingList() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.PACKING_LIST_GENERATE_SUCCESS);
		try {
			packingService.generatePackingList();
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-list")
	public ResponseDTO updateList(@RequestBody PackingListDTO packingList) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.PACKING_LIST_UPDATE_SUCCESS);
		try {
			packingService.updatePackedQuantities(packingList);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-by-item")
	public ResponseDTO updateByItem(@RequestBody Map<String, Object> details) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.PACKING_LIST_UPDATE_SUCCESS);
		try {
			packingService.updateBeneficiaryPackingList(details);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-packing-status")
	public ResponseDTO updatePackingStatus(@RequestBody WebRequest data) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.PACKING_LIST_UPDATE_SUCCESS);
		try {
			packingService.updatePackingStatus(data);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}

}
