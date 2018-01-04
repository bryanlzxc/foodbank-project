package foodbank.packing.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import foodbank.allocation.entity.Allocation;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.inventory.entity.FoodItem;

@Document(collection = "PackingList")
public class PackingList {
	
	@Id
	private String id;
	
	@DBRef
	private Beneficiary beneficiary;
	
	private List<PackedFoodItem> packedItems = new ArrayList<PackedFoodItem>();

	public PackingList() {}
	
	public PackingList(Beneficiary beneficiary, List<PackedFoodItem> packedItems) {
		this.beneficiary = beneficiary;
		this.packedItems = packedItems;
	}
	
	public PackingList(String id, Beneficiary beneficiary, List<PackedFoodItem> packedItems) {
		this.id = id;
		this.beneficiary = beneficiary;
		this.packedItems = packedItems;
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

	public List<PackedFoodItem> getPackedItems() {
		return packedItems;
	}

	public void setPackedItems(List<PackedFoodItem> packedItems) {
		this.packedItems = packedItems;
	}
	
	@Override
	public String toString() {
		return id + ","
				+ packedItems;
	}
}
