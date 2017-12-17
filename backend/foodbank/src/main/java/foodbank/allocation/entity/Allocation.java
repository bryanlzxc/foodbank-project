package foodbank.allocation.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
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
	
	private Beneficiary beneficiary;
	private List<FoodItem> allocatedItems;
	
	public Allocation() {}
	
	public Allocation(Beneficiary beneficiary, List<FoodItem> allocatedItems) {
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
	
	public List<FoodItem> getAllocatedItems() {
		return allocatedItems;
	}
	
	public void setAllocatedItems(List<FoodItem> allocatedItems) {
		this.allocatedItems = allocatedItems;
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