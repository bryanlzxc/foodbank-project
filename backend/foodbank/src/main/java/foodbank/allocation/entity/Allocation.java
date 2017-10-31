package foodbank.allocation.entity;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import foodbank.inventory.entity.FoodItem;

/*
 * Created by Lim Jian Quan, Jaren
 */
@Document(collection = "Allocation")
public class Allocation {
	
	@Id
	private String id;
	
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
	

}