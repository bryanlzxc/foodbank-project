package foodbank.allocation.entity;

import java.util.List;

import foodbank.inventory.entity.FoodItem;

public class AllocationUpdate {
	
	// Represents the beneficiary's name
	public String name;
	public List<FoodItem> fiList;
	
	public AllocationUpdate(String name, List<FoodItem> fiList) {
		this.name = name;
		this.fiList = fiList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FoodItem> getFiList() {
		return fiList;
	}
	public void setFiList(List<FoodItem> fiList) {
		this.fiList = fiList;
	}
	
	

}
