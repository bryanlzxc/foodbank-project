package foodbank.history.dto;

import java.util.Date;

import foodbank.request.dto.RequestDTO;
import foodbank.util.DateParser;

public class RequestHistoryDTO extends RequestDTO {
	
	private Integer allocatedQuantity;
	private Date requestCreationDate;

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

	public String getRequestCreationDate() {
		return DateParser.displayDayMonthYearOnly(requestCreationDate);
	}

	public void setRequestCreationDate(Date requestCreationDate) {
		this.requestCreationDate = requestCreationDate;
	}

}
