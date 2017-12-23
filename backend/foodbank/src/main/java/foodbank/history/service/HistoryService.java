package foodbank.history.service;

import java.util.List;
import java.util.Map;

import foodbank.history.dto.RequestHistoryDTO;

public interface HistoryService {
	
	List<RequestHistoryDTO> retrieveAllPastRequest();
	
	List<Map<String, Object>> retrieveAllPastRequestsByBeneficiary(final String beneficiary);
	
}
