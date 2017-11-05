package foodbank.request.entity;
import java.util.List;

import foodbank.inventory.entity.FoodItem;

/*
 * Created By: Ng Shirong
 */

public class Checkout {

	private String username;
	private List<FoodItem> list;
	
	public Checkout() {
		
	}
	
	public Checkout(String username, List<FoodItem> list) {
		this.username = username;
		this.list = list;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<FoodItem> getList() {
		return list;
	}
	public void setList(List<FoodItem> list) {
		this.list = list;
	}
	
	
	
	
}
