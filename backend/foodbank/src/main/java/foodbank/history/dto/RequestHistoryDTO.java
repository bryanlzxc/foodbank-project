package foodbank.history.dto;

import foodbank.request.dto.RequestDTO;

public class RequestHistoryDTO extends RequestDTO {
	
	private Integer allocatedQuantity;
		
	public RequestHistoryDTO(String beneficiary, String category, String classification, String description,
			Integer requestedQuantity, Integer allocatedQuantity) {
		// TODO Auto-generated constructor stub
		super(beneficiary, category, classification, description, requestedQuantity);
		this.allocatedQuantity = allocatedQuantity;
	}

	public Integer getAllocatedQuantity() {
		return allocatedQuantity;
	}

	public void setAllocatedQuantity(Integer allocatedQuantity) {
		this.allocatedQuantity = allocatedQuantity;
	}

}
