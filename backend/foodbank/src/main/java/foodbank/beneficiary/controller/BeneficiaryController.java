package foodbank.beneficiary.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;

/*
 * Created by: Ng Shirong
 */
@RestController
@RequestMapping("/beneficiary")
public class BeneficiaryController {

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
}
