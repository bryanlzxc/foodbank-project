package foodbank.request.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	//used for beneficiary page to view their own requests
	@GetMapping("/beneficiary={beneficiary}")
	public List<Request> getAllRequestFromBeneficiary(@PathVariable("beneficiary") String beneficiary){
		return this.requestRepository.findRequestsByBeneficiary(beneficiary);
	}
	
	@DeleteMapping("/delete-request={id}")
	public void delete(@PathVariable("id") String id) {
		this.requestRepository.delete(id);
	}
	
	//used for beneficiary to change their qty for current window request qty
	@PostMapping("/changeqty{id}={qty}")
	public void changeQty(@PathVariable("id") String id, @PathVariable("qty") int qty) {
		Request request = this.requestRepository.findById(id);
		request.setQty(qty);
		this.requestRepository.save(request);
	}
	
	//check to see if this beneficiary has this specific request, is there is, return it
	@GetMapping("/{beneficiary}={description}")
	public Request getRequestOfBeneficiary(@PathVariable("beneficiary") String beneficiary, @PathVariable("description") String description) {
		List<Request> requestsOfBeneficiary = getAllRequestFromBeneficiary(beneficiary);
		for(Request r : requestsOfBeneficiary) {
			if(r.getDescription().equals(description)) {
				return r;
			}
		}
		return null;	//null means no such request by this beneficiary
		
	}
	
	@GetMapping("/description={description}")
	public List<Request> getAllRequestByDescription(@PathVariable("description")String description){
		return this.requestRepository.findRequestsByDescription(description);
	}
	
	@PostMapping("/insert-request")
	public void insert(@RequestBody Request request) {
		String id = request.getId();
		Request r = getRequestOfBeneficiary(request.getBeneficiary(), request.getDescription());
		if(r != null) {		//there is such a request by beneficiary yet
			request.setId(r.getId());	//we want to replace witht he old id to replace
		}
		this.requestRepository.save(request);
	}
	
	
	
}
