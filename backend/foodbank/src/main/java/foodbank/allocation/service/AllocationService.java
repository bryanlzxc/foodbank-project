package foodbank.allocation.service;

import java.util.List;

import foodbank.allocation.dto.AllocationDTO;
import foodbank.allocation.dto.BatchAllocationDTO;
import foodbank.allocation.entity.Allocation;
import foodbank.inventory.entity.FoodItem;

public interface AllocationService {
	
	List<Allocation> retrieveAllAllocations();
	
	List<FoodItem> retrieveAllocationByBeneficiary(final String beneficiary);
	
	void generateAllocationList();
	
	void createAllocation(final AllocationDTO allocation);
	
	void updateAllocation(final AllocationDTO allocation);
	
	void batchAllocationUpdate(final BatchAllocationDTO batchAllocation);

}
