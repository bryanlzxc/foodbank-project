package foodbank.packing.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foodbank.packing.dto.LivePackingDTO;
import foodbank.packing.dto.PackingListDTO;
import foodbank.packing.entity.PackedFoodItem;
import foodbank.packing.entity.PackingList;
import foodbank.packing.service.PackingService;
import foodbank.reporting.service.ReportService;
import foodbank.response.dto.ResponseDTO;
import foodbank.util.MessageConstants;

@RestController
@CrossOrigin
@RequestMapping("/packing")
public class PackingController {
	
	@Autowired
	private PackingService packingService;
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/display-all")
	public ResponseDTO getAllPackingLists() {
		List<PackingList> result = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, result, MessageConstants.PACKING_LIST_RETRIEVE_SUCCESS);
		try {
			result = packingService.retrieveAllPackingLists();
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		responseDTO.setResult(result);
		return responseDTO;
	}
	
	@MessageMapping("/receiver/{listId}")
	@SendTo("/topic/{listId}")
	public PackingList getReactiveBeneficiaryPackingList(LivePackingDTO livePackingDTO, @DestinationVariable String listId) {
		PackingList result = packingService.findById(listId);
		String category = livePackingDTO.getCategory();
		String classification = livePackingDTO.getClassification();
		String description = livePackingDTO.getDescription();
		Integer packedQuantity = livePackingDTO.getPackedQuantity();
		Boolean checkboxStatus = livePackingDTO.getCheckboxStatus();
		List<PackedFoodItem> packedFoodItems = result.getPackedItems();
		for(PackedFoodItem item : packedFoodItems) {
			if(item.getCategory().equals(category) && item.getClassification().equals(classification) && item.getDescription().equals(description)) {
				item.setPackedStatus(checkboxStatus);
				item.setQuantity(packedQuantity);
			}
		}
		return result;
	}
	
	@GetMapping("/display-by")
	public ResponseDTO getBeneficiaryPackingList(@RequestParam(value = "beneficiary", required = true) String beneficiary) {
		PackingList result = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, result, MessageConstants.PACKING_LIST_RETRIEVE_SUCCESS);
		try {
			result = packingService.findByBeneficiary(beneficiary);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		responseDTO.setResult(result);
		return responseDTO;
	}
	
	@GetMapping("/review-statuses")
	public ResponseDTO viewAllPackingStatus() {
		Boolean result = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, result, MessageConstants.ALL_PACKING_STATUS_RETRIEVE_SUCCESS);
		try {
			result = packingService.reviewAllPackingStatus();
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/generate-list")
	public ResponseDTO generatePackingList() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.PACKING_LIST_GENERATE_SUCCESS);
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
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.PACKING_LIST_UPDATE_SUCCESS);
		try {
			packingService.updatePackedQuantities(packingList);
			PackingList dbPackingList = packingService.findByBeneficiary(packingList.getBeneficiary());
			reportService.generateDbInvoice(dbPackingList);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-by-item")
	public ResponseDTO updateByItem(@RequestBody Map<String, Object> details) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.PACKING_LIST_UPDATE_SUCCESS);
		try {
			packingService.updateBeneficiaryPackingList(details);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-packing-status")
	public ResponseDTO updatePackingStatus(@RequestBody Map<String, String> beneficiary) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.PACKING_LIST_UPDATE_SUCCESS);
		try {
			packingService.updatePackingStatus(beneficiary.get("beneficiary"));
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}

}
