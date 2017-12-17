package foodbank.packing.dto;

import java.util.List;
import java.util.Map;

import foodbank.request.dto.BatchRequestDTO;

public class PackingListDTO extends BatchRequestDTO {

	public PackingListDTO(String beneficiary, List<Map<String, Object>> requests) {
		super(beneficiary, requests);
	}

}
