package foodbank.request.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Done By Shirong
 */
@Document(collection = "Request")
public class Request {
	//request will be called under beneficiary, when they can see a list of current window requested food stuff
	//request will be called under allocation, when we need to see who requested what and allocate
	
	//request will have a post where we delete all after allocation is done for window to reset for next window
	
	@Id
	private String id;
	
	private String beneficiary;		//this may be changed in the future to contain a beneficiary object
	private String description;		//this will include halal or non halal as well
	private int qty;				//number of specific description this beneficiary requested
	
	public Request(String id, String beneficiary, String description, int qty) {
		this.id = id;
		this.beneficiary = beneficiary;
		this.description = description;
		this.qty = qty;
	}
	public Request() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBeneficiary() {
		return beneficiary;
	}
	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	
	
	
	
}
