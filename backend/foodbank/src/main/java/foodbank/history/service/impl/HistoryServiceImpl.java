package foodbank.history.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.allocation.entity.Allocation;
import foodbank.allocation.repository.AllocationRepository;
import foodbank.history.dto.RequestHistoryDTO;
import foodbank.history.entity.RequestHistory;
import foodbank.history.repository.HistoryRepository;
import foodbank.history.service.HistoryService;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;
import foodbank.util.DateParser;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.InvalidBeneficiaryException;

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
					requestHistory.getAllocatedQuantity(), requestHistory.getRequestCreationDate()));
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
		requestHistoryList.sort(Comparator.comparing(RequestHistory::getRequestCreationDate));
		Collections.reverse(requestHistoryList);
		for(RequestHistory requestHistory : requestHistoryList) {
			Map<String, Object> foodItemMapping = new HashMap<String, Object>();
			foodItemMapping.put("category", requestHistory.getCategory());
			foodItemMapping.put("classification", requestHistory.getClassification());
			foodItemMapping.put("description", requestHistory.getDescription());
			foodItemMapping.put("requestedQuantity", requestHistory.getRequestedQuantity());
			foodItemMapping.put("allocatedQuantity", requestHistory.getAllocatedQuantity());
			foodItemMapping.put("requestCreationDate", DateParser.displayDayMonthYearOnly(requestHistory.getRequestCreationDate()));
			pastRequestsByBeneficiary.add(foodItemMapping);
		}
		return pastRequestsByBeneficiary;
	}
	
}
