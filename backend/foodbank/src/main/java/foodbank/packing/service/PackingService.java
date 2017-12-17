package foodbank.packing.service;

import java.util.List;

import foodbank.allocation.entity.Allocation;
import foodbank.packing.entity.PackingList;

public interface PackingService {
	
	List<PackingList> retrieveAllPackingLists();
	
	PackingList findByBeneficiary(final String beneficiary);
	
	void generatePackingList();	
	
}
