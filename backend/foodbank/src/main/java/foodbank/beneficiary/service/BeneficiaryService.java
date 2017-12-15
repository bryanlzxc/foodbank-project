package foodbank.beneficiary.service;

import java.util.List;

import foodbank.beneficiary.dto.BeneficiaryDTO;
import foodbank.beneficiary.dto.BeneficiaryUpdateDTO;
import foodbank.beneficiary.entity.Beneficiary;

public interface BeneficiaryService {
	
	List<Beneficiary> getAllBeneficiaries();
	
	Double getBeneficiaryScore(final String beneficiary);
	
	Beneficiary getBeneficiaryDetails(final String beneficiary);
	
	void modifyBeneficiaryScore(final BeneficiaryUpdateDTO beneficiaryUpdate);
	
	void createBeneficiary(final BeneficiaryDTO beneficiary);

}
