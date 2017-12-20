package foodbank.allocation.dto;

import java.util.List;

public class BatchAllocationDTO {
	
	List<AllocationDTO> allocations;

	public List<AllocationDTO> getAllocations() {
		return allocations;
	}

	public void setAllocations(List<AllocationDTO> allocations) {
		this.allocations = allocations;
	}

}
