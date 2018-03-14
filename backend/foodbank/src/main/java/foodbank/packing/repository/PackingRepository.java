package foodbank.packing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.packing.entity.PackingList;

public interface PackingRepository extends JpaRepository<PackingList, Long> {

	PackingList findByBeneficiaryUserUsername(String beneficiary);
	
	PackingList findById(Long id);
	
}
