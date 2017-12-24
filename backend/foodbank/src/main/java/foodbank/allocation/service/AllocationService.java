package foodbank.allocation.service;

import java.util.List;

import foodbank.allocation.dto.AllocationDTO;
import foodbank.allocation.dto.BatchAllocationDTO;
import foodbank.allocation.entity.AllocatedFoodItems;
import foodbank.allocation.entity.Allocation;

public interface AllocationService {
	
	List<Allocation> retrieveAllAllocations();
	
	List<AllocatedFoodItems> retrieveAllocationByBeneficiary(final String beneficiary);
	
	void generateAllocationList();
		
	void updateAllocation(final AllocationDTO allocation);
	
	void batchAllocationUpdate(final BatchAllocationDTO batchAllocation);

}
