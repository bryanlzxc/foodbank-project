package foodbank.history.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.exceptions.InvalidBeneficiaryException;
import foodbank.history.dto.RequestHistoryDTO;
import foodbank.history.entity.RequestHistory;
import foodbank.history.repository.HistoryRepository;
import foodbank.history.service.HistoryService;
import foodbank.util.MessageConstants.ErrorMessages;

@Service
public class HistoryServiceImpl implements HistoryService {

	@Autowired
	private HistoryRepository historyRepository;
	
	@Override
	public List<RequestHistoryDTO> retrieveAllPastRequest() {
		// TODO Auto-generated method stub
		List<RequestHistory> requestHistoryList = historyRepository.findAll();
		List<RequestHistoryDTO> requestHistoryDTOList = new ArrayList<RequestHistoryDTO>();
		for(RequestHistory requestHistory : requestHistoryList) {
			requestHistoryDTOList.add(new RequestHistoryDTO(requestHistory.getUsername(), 
					requestHistory.getCategory(), requestHistory.getClassification(), 
					requestHistory.getDescription(), requestHistory.getRequestedQuantity(), 
					requestHistory.getAllocatedQuantity()));
		}
		return requestHistoryDTOList;
	}

	@Override
	public List<Map<String, Object>> retrieveAllPastRequestsByBeneficiary(String beneficiary) {
		// TODO Auto-generated method stub
		List<RequestHistory> requestHistoryList = historyRepository.findByUsername(beneficiary);
		if(requestHistoryList == null || requestHistoryList.isEmpty()) {
			throw new InvalidBeneficiaryException(ErrorMessages.NO_SUCH_BENEFICIARY);
		}
		List<Map<String, Object>> pastRequestsByBeneficiary = new ArrayList<Map<String, Object>>();
		for(RequestHistory requestHistory : requestHistoryList) {
			Map<String, Object> foodItemMapping = new HashMap<String, Object>();
			foodItemMapping.put("category", requestHistory.getCategory());
			foodItemMapping.put("classification", requestHistory.getClassification());
			foodItemMapping.put("description", requestHistory.getDescription());
			foodItemMapping.put("requestedQuantity", requestHistory.getRequestedQuantity());
			foodItemMapping.put("allocatedQuantity", requestHistory.getAllocatedQuantity());
			pastRequestsByBeneficiary.add(foodItemMapping);
		}
		return pastRequestsByBeneficiary;
	}

}
