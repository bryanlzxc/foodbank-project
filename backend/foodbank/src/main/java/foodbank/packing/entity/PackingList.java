package foodbank.packing.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import foodbank.allocation.entity.Allocation;
import foodbank.inventory.entity.FoodItem;

@Document(collection = "PackingList")
public class PackingList {
	
	@Id
	private String id;
	
	@DBRef
	private Allocation allocation;
	
	private List<FoodItem> packedItems = new ArrayList<FoodItem>();

	public PackingList() {}
	
	public PackingList(Allocation allocation, List<FoodItem> packedItems) {
		this.allocation = allocation;
		this.packedItems = packedItems;
	}

	public Allocation getAllocation() {
		return allocation;
	}

	public void setAllocation(Allocation allocation) {
		this.allocation = allocation;
	}

	public List<FoodItem> getPackedItems() {
		return packedItems;
	}

	public void setPackedItems(List<FoodItem> packedItems) {
		this.packedItems = packedItems;
	}
	
}
