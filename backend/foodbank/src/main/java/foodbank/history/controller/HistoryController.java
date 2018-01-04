package foodbank.history.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foodbank.history.dto.RequestHistoryDTO;
import foodbank.history.service.HistoryService;
import foodbank.response.dto.ResponseDTO;

@RestController
@CrossOrigin
@RequestMapping("/history")
public class HistoryController {

	@Autowired
	private HistoryService historyService;
	
	@GetMapping("/requests/display-all")
	public List<RequestHistoryDTO> retrieveAllRequestHistory() {
		return historyService.retrieveAllPastRequest();
	}
	
	@GetMapping("/requests/display")
	public Object retrieveAllPastRequestsByBeneficiary(@RequestParam(value = "beneficiary", required = true) String beneficiary) {
		Object response = null;
		try {
			response = historyService.retrieveAllPastRequestsByBeneficiary(beneficiary);
		} catch (Exception e) {
			response = new ResponseDTO(ResponseDTO.Status.FAIL, e.getMessage());
		}
		return response;
	}
	
}
