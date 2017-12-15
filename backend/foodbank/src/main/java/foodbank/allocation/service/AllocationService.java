package foodbank.allocation.service;

import java.util.List;

import foodbank.allocation.entity.Allocation;
import foodbank.inventory.entity.FoodItem;

public interface AllocationService {
	
	List<Allocation> retrieveAllAllocations();
	
	List<FoodItem> retrieveAllocationByBeneficiary(final String beneficiary);
	
	void generateAllocationList();

}
