package foodbank.request.service;

import java.util.List;

import foodbank.request.dto.BatchRequestDTO;
import foodbank.request.dto.RequestDTO;
import foodbank.request.entity.Request;

public interface RequestService {
	
	List<Request> getAllRequests();
	
	List<Request> getAllRequestsByBeneficiary(final String beneficiary);
	
	void insertRequest(final RequestDTO request);
	
	void updateRequest(final RequestDTO request);
	
	void updateOverwriteRequest(final RequestDTO request);
	
	void deleteRequest(final String id);
	
	void batchInsertRequest(final BatchRequestDTO batchRequest);
	
	Integer countDistinctBeneficiaryRequests();
	
}
