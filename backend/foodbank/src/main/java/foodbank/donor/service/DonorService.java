package foodbank.donor.service;

import java.util.List;

import foodbank.donor.dto.DonorDTO;
import foodbank.donor.entity.Donor;

public interface DonorService {
	
	List<Donor> getAllDonors();
	
	List<String> getDonorNames();
	
	void createDonor(DonorDTO donor);
		
	void deleteDonor(String id);

}
