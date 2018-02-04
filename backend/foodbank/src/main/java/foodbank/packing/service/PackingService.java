package foodbank.packing.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.context.request.WebRequest;

import foodbank.packing.dto.PackingListDTO;
import foodbank.packing.entity.PackingList;

public interface PackingService {
	
	List<PackingList> retrieveAllPackingLists();
	
	PackingList findByBeneficiary(final String beneficiary);
	
	void generatePackingList();	
	
	void updatePackedQuantities(final PackingListDTO packingListDTO);
	
	void updateBeneficiaryPackingList(final Map<String, Object> details);
	
	void updatePackingStatus(final String beneficiary);
	
	Boolean reviewAllPackingStatus();
	
}
