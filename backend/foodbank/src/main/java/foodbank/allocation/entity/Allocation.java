package foodbank.allocation.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Created by Lim Jian Quan, Jaren
 */
@Document(collection = "Allocation")
public class Allocation {
	
	@Id
	private String id;
	
	// name: Beneficiary name
	// list: List of allocated foodItem with requested qty and allocated qty
	private String name;
	private List<AllocationFoodItem> list;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<AllocationFoodItem> getList() {
		return list;
	}
	public void setList(List<AllocationFoodItem> list) {
		this.list = list;
	}
	
	public void addToList(AllocationFoodItem afi) {
		list.add(afi);
	}
	
	public Allocation() {
		// empty constructor required for put & post mappings
	}
	
	public Allocation(String name, List<AllocationFoodItem> list) {
		this.name = name;
		this.list = list;
	} 
	
	@Override
	/* Allocation deemed equal if the name is the same */
	public boolean equals(Object obj) {
		Allocation other = null;
		if (obj instanceof Allocation) other = (Allocation) obj;
		return this.name.equals(other.name);
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