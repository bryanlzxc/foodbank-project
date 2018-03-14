package foodbank.donor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.donor.dto.DonorDTO;
import foodbank.donor.entity.Donor;
import foodbank.donor.repository.DonorRepository;
import foodbank.donor.service.DonorService;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.InvalidDonorException;

@Service
public class DonorServiceImpl implements DonorService {
	
	@Autowired
	private DonorRepository donorRepository;

	@Override
	public List<Donor> getAllDonors() {
		// TODO Auto-generated method stub
		return donorRepository.findAll();
	}

	@Override
	public List<String> getDonorNames() {
		// TODO Auto-generated method stub
		List<String> donorNames = new ArrayList<>();
		List<Donor> donorList = donorRepository.findAll();
		for(Donor donor: donorList) {
			donorNames.add(donor.getName());
		}
		return donorNames;
	}

	@Override
	public void createDonor(DonorDTO donor) {
		// TODO Auto-generated method stub
		Donor dbDonor = donorRepository.findByName(donor.getName());
		if(dbDonor != null) {
			throw new InvalidDonorException(ErrorMessages.DONOR_ALREADY_EXISTS);
		}
		donorRepository.save(new Donor(donor.getName()));
	}

	@Override
	public void deleteDonor(String id) {
		// TODO Auto-generated method stub
		Donor dbDonor = donorRepository.findById(Long.valueOf(id));
		if(dbDonor == null) {
			throw new InvalidDonorException(ErrorMessages.DONOR_DOES_NOT_EXIST);
		}
		donorRepository.delete(dbDonor);
	}

}
