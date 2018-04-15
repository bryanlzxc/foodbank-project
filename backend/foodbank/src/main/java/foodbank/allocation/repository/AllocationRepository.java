package foodbank.allocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.allocation.entity.Allocation;

/**
 * 
 * @author Bryan Lau <bryan.lau.2015@sis.smu.edu.sg>
 * @version 1.0
 *
 */
public interface AllocationRepository extends JpaRepository<Allocation, Long>{
	
	Allocation findByBeneficiaryUserUsername(String beneficiary);
	
	Allocation findById(String id);

}
