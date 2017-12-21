package foodbank.request.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbank.request.dto.BatchRequestDTO;
import foodbank.request.dto.RequestDTO;
import foodbank.request.entity.Request;
import foodbank.request.service.RequestService;
import foodbank.response.dto.ResponseDTO;
import foodbank.util.MessageConstants;

/*
 * Created by: Ng Shirong
 */

@RestController
@CrossOrigin
@RequestMapping("/request")
public class RequestController {
	
	@Autowired
	private RequestService requestService;
	
	@GetMapping("/display-all")
	public List<Request> getAllRequests() {
		return requestService.getAllRequests();
	}
	
	@GetMapping("/display/beneficiary={beneficiary}")
	public List<Request> getAllRequestsFromBeneficiary(@PathVariable("beneficiary") String beneficiary) {
		return requestService.getAllRequestsByBeneficiary(beneficiary);
	}
	
	@GetMapping("/display-count/unique-beneficiaries")
	public Integer retrieveUniqueBeneficiaryCount() {
		return requestService.countDistinctBeneficiaryRequests();
	}
	
	@PutMapping("/create-request")
	public ResponseDTO createRequest(@RequestBody RequestDTO request) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.REQUEST_CREATE_SUCCESS);
		try {
			requestService.insertRequest(request);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-request")
	public ResponseDTO updateRequest(@RequestBody RequestDTO request) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.REQUEST_UPDATE_SUCCESS);
		try {
			requestService.updateRequest(request);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@DeleteMapping("/delete-request")
	public ResponseDTO deleteRequest(@RequestBody RequestDTO request) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.REQUEST_DELETE_SUCCESS);
		try {
			requestService.deleteRequest(request);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PutMapping("/batch/create-request")
	public ResponseDTO createRequest(@RequestBody BatchRequestDTO batchRequest) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, MessageConstants.BATCH_REQUEST_CREATE_SUCCESS);
		try {
			requestService.batchInsertRequest(batchRequest);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	/*
	RequestRepository requestRepository;

	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	@Autowired
	private UserRepository userRepository;
	
	public RequestController(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}
	
	@GetMapping("/display-all")
	public List<Request> getAllRequest(){
		//System.out.println("Request Repository findAll(): " + this.requestRepository.findAll());
		return this.requestRepository.findAll();
	}
	
	@GetMapping("/beneficiary-name={beneficiaryName}")
	public List<Request> getAllRequestFromBeneficiaryName(@PathVariable("beneficiaryName") String beneficiaryName){
		List<Request> beneficiaryRequests = new ArrayList<Request>();
		List<Request> requests = this.requestRepository.findAll();
		for(Request request : requests) {
			if(request.getBeneficiaryName().equals(beneficiaryName)) {
				beneficiaryRequests.add(request);
			}
		}
		return beneficiaryRequests;
	}
	
	
	@GetMapping("/beneficiary-name={username}")
	public List<Request> getAllRequestFromBeneficiary(@PathVariable("username") String username){
		List<Request> beneficiaryRequests = new ArrayList<Request>();
		List<Request> requests = this.requestRepository.findAll();
		UserController userController = new UserController(userRepository);
		String beneficiaryName = userController.getByUsername(username).getName();
		
		for(Request request : requests) {
			if(request.getBeneficiaryName().equals(beneficiaryName)) {
				beneficiaryRequests.add(request);
			}
		}
		return beneficiaryRequests;
	}
	
	
	@GetMapping("/beneficiary-name={beneficiaryName}/fooditem={description}")
	public Request getRequestOfBeneficiary(@PathVariable("beneficiaryName") String beneficiaryName, @PathVariable("description") String description) {
		List<Request> requests = getAllRequestFromBeneficiaryName(beneficiaryName);
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
	public Status updateRequest(@RequestBody Request request) {
		String id = request.getId();
		String requesterName = request.getBeneficiaryName();
		String requestedItem = request.getFoodItemDescription();
		Request storedRequest = getRequestOfBeneficiary(requesterName, requestedItem);
		if(storedRequest == null) {	//no such request before
			this.requestRepository.save(request);	//save this as a new request
			return new Status("success");
		} else {		//storedRequest != null, there is such a request before
			//storedRequest = request;
			//this.requestRepository.save(storedRequest);
			request.setId(storedRequest.getId());
			this.requestRepository.save(request);
			return new Status("success");
		}
		//return new Status("fail"); this is unreachable yet, when we are refactoring code after acceptance, we will need to catch exception
	}
	
	
	@PostMapping("/display-checkout-fooditemlist")
	public List<FoodItem> checkoutName(@RequestBody Checkout checkout) {
		BeneficiaryController beneficiaryController = new BeneficiaryController(beneficiaryRepository);
		UserController userController = new UserController(userRepository);
		
		String username = checkout.getUsername();
		String name = userController.getNameByUsername(username);
		
		Beneficiary beneficiary = beneficiaryController.getByName(name);
		List<FoodItem> listFoodItem = checkout.getList();
		return listFoodItem;
	}
	
	@PostMapping("/checkout")
	public Status checkout(@RequestBody Checkout checkout) {
		BeneficiaryController beneficiaryController = new BeneficiaryController(beneficiaryRepository);
		UserController userController = new UserController(userRepository);
		
		
		String username = checkout.getUsername();
		//need to query users for name of beneficiary with the given username;
		String name = userController.getNameByUsername(username);
		
		//get Beneficiary object
		Beneficiary beneficiary = beneficiaryController.getByName(name);
		
		List<FoodItem> listFoodItem = checkout.getList();
		
		for(FoodItem foodItem : listFoodItem) {
			Request request = new Request(beneficiary, foodItem);
				updateRequest(request);
		}
		return new Status("success");
	}
	
	@DeleteMapping("/delete-request={id}")
	public Status delete(@PathVariable("id") String id) {
		this.requestRepository.delete(id);
		return new Status("success");
	}
	*/
	
}
