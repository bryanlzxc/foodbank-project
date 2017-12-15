package foodbank.inventory.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodItemDTO {
	
	@NotNull
	@JsonProperty("description")
	private String description;
	
	@NotNull
	@JsonProperty("quantity")
	private int quantity;
	
	public FoodItemDTO(@JsonProperty("description") String description, @JsonProperty("quantity") int quantity) {
		this.description = description;
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
