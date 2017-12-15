package foodbank.beneficiary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.beneficiary.dto.BeneficiaryDTO;
import foodbank.beneficiary.dto.BeneficiaryUpdateDTO;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.beneficiary.service.BeneficiaryService;
import foodbank.exceptions.InvalidBeneficiaryException;
import foodbank.exceptions.UserException;
import foodbank.user.entity.User;
import foodbank.user.repository.UserRepository;
import foodbank.util.MessageConstants.ErrorMessages;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<Beneficiary> getAllBeneficiaries() {
		// TODO Auto-generated method stub
		return beneficiaryRepository.findAll();
	}

	@Override
	public Double getBeneficiaryScore(String beneficiary) {
		// TODO Auto-generated method stub
		Beneficiary dbBeneficiary = beneficiaryRepository.findByName(beneficiary);
		if(dbBeneficiary == null) {
			throw new InvalidBeneficiaryException(ErrorMessages.NO_SUCH_BENEFICIARY);
		}
		return dbBeneficiary.getScore();
	}

	@Override
	public Beneficiary getBeneficiaryDetails(String beneficiary) {
		// TODO Auto-generated method stub
		Beneficiary dbBeneficiary = beneficiaryRepository.findByName(beneficiary);
		if(dbBeneficiary == null) {
			throw new InvalidBeneficiaryException(ErrorMessages.NO_SUCH_BENEFICIARY);
		}
		return dbBeneficiary;
	}

	@Override
	public void modifyBeneficiaryScore(BeneficiaryUpdateDTO beneficiaryUpdate) {
		// TODO Auto-generated method stub
		Beneficiary dbBeneficiary = beneficiaryRepository.findByName(beneficiaryUpdate.getBeneficiary());
		if(dbBeneficiary == null) {
			throw new InvalidBeneficiaryException(ErrorMessages.NO_SUCH_BENEFICIARY);
		}
		dbBeneficiary.setScore(beneficiaryUpdate.getScore());
		beneficiaryRepository.save(dbBeneficiary);
	}

	@Override
	public void createBeneficiary(BeneficiaryDTO beneficiary) {
		// TODO Auto-generated method stub
		Beneficiary dbBeneficiary = beneficiaryRepository.findByName(beneficiary.getName());
		if(dbBeneficiary != null) {
			throw new InvalidBeneficiaryException(ErrorMessages.BENEFICIARY_ALREADY_EXISTS);
		}
		User dbUser = userRepository.findByUsername(beneficiary.getUsername());
		if(dbUser != null) {
			throw new UserException(ErrorMessages.USER_ALREADY_EXISTS);
		}
		User newUser = new User(beneficiary.getUsername(), beneficiary.getPassword(), beneficiary.getUsertype(), beneficiary.getName(), beneficiary.getEmail());
		userRepository.insert(newUser);
		beneficiaryRepository.insert(new Beneficiary(newUser, beneficiary.getSector(), beneficiary.getNumBeneficiary(), beneficiary.getAddress(),
				beneficiary.getScore(), beneficiary.getMembershipNumber(), beneficiary.getAcraRegistrationNumber(), beneficiary.getMemberType()));
	}

}
