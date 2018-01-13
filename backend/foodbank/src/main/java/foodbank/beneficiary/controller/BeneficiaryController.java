package foodbank.beneficiary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foodbank.beneficiary.dto.BeneficiaryAccountDTO;
import foodbank.beneficiary.dto.BeneficiaryDTO;
import foodbank.beneficiary.dto.BeneficiaryUpdateDTO;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.beneficiary.service.BeneficiaryService;
import foodbank.response.dto.ResponseDTO;
import foodbank.util.MessageConstants;

/*
 * Created by: Ng Shirong
 */
@RestController
@CrossOrigin
@RequestMapping("/beneficiary")
public class BeneficiaryController {
	
	@Autowired
	private BeneficiaryService beneficiaryService;
	
	@GetMapping("/display-all")
	public List<Beneficiary> getAllBeneficiaries() {
		return beneficiaryService.getAllBeneficiaries();
	}
	
	@GetMapping("/get-score")
	public ResponseDTO getBeneficiaryScore(@RequestParam(value = "beneficiary", required = true)  String beneficiary) {
		Double score = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.BENEFICIARY_RETRIEVE_SUCCESS);
		try {
			score = beneficiaryService.getBeneficiaryScore(beneficiary);
			responseDTO.setResult(score);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@GetMapping("/get-details")
	public ResponseDTO getBeneficiaryDetails(@RequestParam(value = "beneficiary", required = true) String beneficiary) {
		Beneficiary dbBeneficiary = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.BENEFICIARY_RETRIEVE_SUCCESS);
		try {
			dbBeneficiary = beneficiaryService.getBeneficiaryDetails(beneficiary);
			responseDTO.setResult(dbBeneficiary);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PutMapping("/insert-beneficiary")
	public ResponseDTO insertBeneficiary(@RequestBody BeneficiaryDTO beneficiary) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.BENEFICIARY_ADD_SUCCESS);
		try {
			beneficiaryService.createBeneficiary(beneficiary);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-score")
	public ResponseDTO updateBeneficiaryScore(@RequestBody BeneficiaryUpdateDTO beneficiaryUpdate) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.BENEFICIARY_UPDATE_SUCCESS);
		try {
			beneficiaryService.modifyBeneficiaryScore(beneficiaryUpdate);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-beneficiary")
	public ResponseDTO updateBeneficiary(@RequestBody BeneficiaryDTO beneficiary) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.BENEFICIARY_UPDATE_SUCCESS);
		try {
			beneficiaryService.updateBeneficiary(beneficiary);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-beneficiary-account")
	public ResponseDTO updateBeneficiaryAccount(@RequestBody BeneficiaryAccountDTO beneficiaryAccount) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.BENEFICIARY_UPDATE_SUCCESS);
		// TODO: The try-catch for service call
		try {
			beneficiaryService.updateBeneficiaryAccount(beneficiaryAccount);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	
	/*
	@Autowired
	BeneficiaryRepository beneficiaryRepository;

	public BeneficiaryController(BeneficiaryRepository beneficiaryRepository) {
		this.beneficiaryRepository = beneficiaryRepository;
	}
	
	@GetMapping("/display-all")
	public List<Beneficiary> getAllRequest(){
		return this.beneficiaryRepository.findAll();
	}
	
	@GetMapping("/{beneficiary}score")
	public double getBeneficiaryScore(@PathVariable("beneficiary") String beneficiary) {
		return getByName(beneficiary).getScore();
	}
	
	@GetMapping("/name={beneficiary}")
	public Beneficiary getByName(@PathVariable("beneficiary") String beneficiary) {
		return this.beneficiaryRepository.findByName(beneficiary);
	}
	
	//This method allows user to change beneficiary score by proving the beneficiary name and the new score
	@PostMapping("/{beneficiary}score={score}")
	public void changeBeneficiaryScore(@PathVariable("beneficiary") String beneficiary, @PathVariable("score") double score) {
		Beneficiary bene = getByName(beneficiary);
		bene.setScore(score);
		this.beneficiaryRepository.save(bene);
	}
		
	@PostMapping("/insert-beneficiary")
	public void insert(@RequestBody Beneficiary beneficiary) {
		Beneficiary bene = getByName(beneficiary.getName());
		if(bene != null) {
			beneficiary.setId(bene.getId());
		}
		this.beneficiaryRepository.save(beneficiary);		
	}
	*/
}
