package foodbank.request.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.exceptions.InvalidRequestException;
import foodbank.inventory.entity.FoodItem;
import foodbank.request.dto.BatchRequestDTO;
import foodbank.request.dto.RequestDTO;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;
import foodbank.request.service.RequestService;
import foodbank.util.MessageConstants.ErrorMessages;

@Service
public class RequestServiceImpl implements RequestService {
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Override
	public List<Request> getAllRequests() {
		// TODO Auto-generated method stub
		return requestRepository.findAll();
	}

	@Override
	public List<Request> getAllRequestsByBeneficiary(String beneficiary) {
		// TODO Auto-generated method stub
		List<Request> requests = requestRepository.findAll();
		List<Request> requestsByBeneficiary = new ArrayList<Request>();
		for(Request request : requests) {
			if(request.getBeneficiary().getUser().getUsername().equals(beneficiary)) {
				requestsByBeneficiary.add(request);
			}
		}
		return requestsByBeneficiary;
	}

	@Override
	public void insertRequest(RequestDTO request) {
		// TODO Auto-generated method stub
		// This method should be used to update IF and ONLY IF we are overwriting the quantity
		List<Request> requestsByBeneficiary = getAllRequestsByBeneficiary(request.getBeneficiary());
		boolean foundPreviousRequest = false;
		for(Request dbRequest : requestsByBeneficiary) {
			if(dbRequest.getFoodItem().getCategory().equals(request.getCategory()) && dbRequest.getFoodItem().getClassification().equals(request.getClassification())
					&& dbRequest.getFoodItem().getDescription().equals(request.getDescription())) {
				foundPreviousRequest = true;
				dbRequest.getFoodItem().setQuantity(request.getQuantity());
				requestRepository.save(dbRequest);
				break;
			}
		}
		if(!foundPreviousRequest) {
			requestRepository.save(new Request(beneficiaryRepository.findByUsername(request.getBeneficiary()), 
					new FoodItem(request.getCategory(), request.getClassification(), request.getDescription(), request.getQuantity())));
		}
	}

	@Override
	public void updateRequest(RequestDTO request) {
		// TODO Auto-generated method stub
		// This method should be used as the actual increment/decrement update, and is NOT the overwriting function
		List<Request> requestsByBeneficiary = getAllRequestsByBeneficiary(request.getBeneficiary());
		boolean foundPreviousRequest = false;
		for(Request dbRequest : requestsByBeneficiary) {
			if(dbRequest.getFoodItem().getCategory().equals(request.getCategory()) && dbRequest.getFoodItem().getClassification().equals(request.getClassification())
					&& dbRequest.getFoodItem().getDescription().equals(request.getDescription())) {
				foundPreviousRequest = true;
				dbRequest.getFoodItem().setQuantity(request.getQuantity() + dbRequest.getFoodItem().getQuantity());
				requestRepository.save(dbRequest);
				break;
			}
		}
		if(!foundPreviousRequest) {
			throw new InvalidRequestException(ErrorMessages.NO_SUCH_REQUEST);
		}
	}

	@Override
	public void updateOverwriteRequest(RequestDTO request) {
		// TODO Auto-generated method stub
		insertRequest(request);
	}
	
	@Override
	public void deleteRequest(String id) {
		// TODO Auto-generated method stub
		Request request = requestRepository.findById(id);
		if(request == null) {
			throw new InvalidRequestException(ErrorMessages.NO_SUCH_REQUEST);
		}
		requestRepository.delete(request);
	}

	@Override
	public void batchInsertRequest(BatchRequestDTO batchRequest) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> requestsMap = batchRequest.getRequests();
		List<Request> requests = new ArrayList<Request>();
		for(Map<String, Object> requestMap : requestsMap) {
			Set<String> keys = requestMap.keySet();
			String category = null;
			String classification = null;
			String description = null;
			Integer quantity = null;
			for(String key : keys) {
				category = key.equals("category") ? (String)requestMap.get(key) : category;
				classification = key.equals("classification") ? (String)requestMap.get(key) : classification;
				description = key.equals("description") ? (String)requestMap.get(key) : description;
				quantity = key.equals("quantity") ? (Integer)requestMap.get(key) : quantity;
			}
			requests.add(new Request(beneficiaryRepository.findByUsername(batchRequest.getBeneficiary()), 
					new FoodItem(category, classification, description, quantity)));
		}
		requests.forEach(request -> requestRepository.save(request));
	}

	@Override
	public Integer countDistinctBeneficiaryRequests() {
		// TODO Auto-generated method stub
		List<Request> requests = requestRepository.findAll();
		Set<String> uniqueBeneficiaryList = new HashSet<String>();
		for(Request request : requests) {
			uniqueBeneficiaryList.add(request.getBeneficiary().getUser().getUsername());
		}
		return uniqueBeneficiaryList.size();
	}

}
