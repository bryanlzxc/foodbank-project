package foodbank.request.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.dsl.BooleanExpression;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.request.entity.QRequest;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;

/*
 * Created by: Ng Shirong
 */

@RestController
@RequestMapping("/request")
public class RequestController {

	RequestRepository requestRepository;

	public RequestController(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}
	
	@GetMapping("/display-all")
	public List<Request> getAllRequest(){
		return this.requestRepository.findAll();
	}
	
	@GetMapping("/beneficiary-name={beneficiaryName}")
	public List<Request> getAllRequestFromBeneficiary(@PathVariable("beneficiaryName") String beneficiaryName){
		List<Request> beneficiaryRequests = new ArrayList<Request>();
		List<Request> requests = this.requestRepository.findAll();
		for(Request request : requests) {
			if(request.getBeneficiaryName().equals(beneficiaryName)) {
				beneficiaryRequests.add(request);
			}
		}
		return beneficiaryRequests;
	}
	
	@GetMapping("/beneficiary-name={beneficiaryName}/fooditem={description}")
	public Request getRequestOfBeneficiary(@PathVariable("beneficiaryName") String beneficiaryName, @PathVariable("description") String description) {
		List<Request> requests = getAllRequestFromBeneficiary(beneficiaryName);
		for(Request request : requests) {
			if(request.getFoodItemDescription().equals(description)) {
				return request;
			}
		}
		return null;
	}
	
	@GetMapping("/find-all/fooditem={description}")
	public List<Request> getAllRequestsByDescription(@PathVariable("description") String description) {
		QRequest qRequest = new QRequest("request");
		BooleanExpression filter = qRequest.foodItem.description.eq(description);
		List<Request> requests = (List<Request>) this.requestRepository.findAll(filter);
		return requests;
	}
	
	@PutMapping("/create-request")
	public void createRequest(@RequestBody Request request) {
		String id = request.getId();
		String requesterName = request.getBeneficiaryName();
		String requestedItem = request.getFoodItemDescription();
		Request storedRequest = getRequestOfBeneficiary(requesterName, requestedItem);
		if(storedRequest == null) {
			this.requestRepository.save(request);
		}
	}
	
	@PostMapping("/update-request")
	public void updateRequest(@RequestBody Request request) {
		String id = request.getId();
		String requesterName = request.getBeneficiaryName();
		String requestedItem = request.getFoodItemDescription();
		Request storedRequest = getRequestOfBeneficiary(requesterName, requestedItem);
		if(storedRequest == null) {
			this.requestRepository.save(request);
		} else {
			storedRequest = request;
			this.requestRepository.save(storedRequest);
		}
	}
	
	@DeleteMapping("/delete-request={id}")
	public void delete(@PathVariable("id") String id) {
		this.requestRepository.delete(id);
	}
	
}
