package foodbank.allocation.service;

import java.util.List;

import foodbank.allocation.dto.AllocatedFoodItemDTO;
import foodbank.allocation.dto.AllocationResponseDTO;
import foodbank.allocation.dto.AllocationUpdateDTO;

/**
 * 
 * @author Bryan Lau <bryan.lau.2015@sis.smu.edu.sg>
 * @version 1.0
 *
 */
public interface AllocationService {

	/**
	 * 
	 * @return List of DTO objects wrapped for the client viewing
	 */
	List<AllocationResponseDTO> retrieveAllAllocations();
	
	/**
	 * Retrieves List of AllocatedFoodItemDTO that belongs to the specified Beneficiary
	 * @param beneficiary
	 * @return List of DTO objects wrapped for the client viewing, for the specific Beneficiary
	 */
	List<AllocatedFoodItemDTO> retrieveAllocationByBeneficiary(final String beneficiary);
	
	/**
	 * Generate the Allocation based on all Requests made in the current window
	 */
	void generateAllocationList();
	
	/**
	 * Update the respective allocation as defined in the AllocationUpdateDTO
	 * @param allocation
	 */
	void updateAllocation(final AllocationUpdateDTO allocation);
	
	/**
	 * Update the approval status of all allocations to TRUE
	 */
	void approveAllocations();
	
	/**
	 * Evaluate the overall approval status of all allocations
	 * @return Boolean value that will be TRUE if all allocations have been approved, FALSE otherwise
	 */
	Boolean checkApproveStatus();
	
}
