package foodbank.allocation.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.inventory.entity.FoodItem;

/*
 * Created by Lim Jian Quan, Jaren
 */
@Document(collection = "Allocation")
public class Allocation {
	
	@Id
	private String id;
	
	@DBRef
	private Beneficiary beneficiary;
	
	private List<AllocatedFoodItems> allocatedItems;
	
	private Boolean approvalStatus = false;
	
	public Allocation() {}
	
	public Allocation(String id, Beneficiary beneficiary, List<AllocatedFoodItems> allocatedItems,
			Boolean approvalStatus) {
		this(beneficiary, allocatedItems);
		this.id = id;
		this.approvalStatus = approvalStatus;
	}
	
	public Allocation(Beneficiary beneficiary, List<AllocatedFoodItems> allocatedItems) {
		this.beneficiary = beneficiary;
		this.allocatedItems = allocatedItems;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Beneficiary getBeneficiary() {
		return beneficiary;
	}
	
	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}
	
	public List<AllocatedFoodItems> getAllocatedItems() {
		return allocatedItems;
	}
	
	public void setAllocatedItems(List<AllocatedFoodItems> allocatedItems) {
		this.allocatedItems = allocatedItems;
	}
	
	public Boolean getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	@Override
	public String toString() {
		String allocatedItemString = "";
		for(AllocatedFoodItems allocatedItem : allocatedItems) {
			allocatedItemString += "{" + allocatedItem.getCategory() + "+" 
				+ allocatedItem.getClassification() + "+" 
				+ allocatedItem.getDescription() + "+"
				+ allocatedItem.getAllocatedQuantity() + "+"
				+ allocatedItem.getRequestedQuantity() + "}";
		}
		return id + "," 
				+ "[" + allocatedItemString + "]" + ","
				+ beneficiary.getId() + ","
				+ approvalStatus;
	}
	
	/*
	// BV : Description+Qty
	private HashMap<String, ArrayList<FoodItem>> details;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public HashMap<String, ArrayList<FoodItem>> getDetails() {
		return details;
	}
	
	public void setDetails(HashMap<String, ArrayList<FoodItem>> details) {
		this.details = details;
	}
	
	//public Set<Beneficiary> getAllBeneficiary() {
	//	return details.keySet();
	//}
	
	public Allocation() {
		// empty constructor required for put & post mappings
	}
	
	public Allocation(HashMap<String, ArrayList<FoodItem>> details) {
		this.details = details;
	}
	*/

}