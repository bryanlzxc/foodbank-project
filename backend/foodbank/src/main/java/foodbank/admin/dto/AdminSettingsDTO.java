package foodbank.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminSettingsDTO {
	
	@JsonProperty("closing-date")
	private String closingDate;
	
	@JsonProperty("toggle")
	private Boolean windowToggle;
	
	@JsonProperty("decay:.+")
	private Double decayRate;
	
	@JsonProperty("multiplier:.+")
	private Double multiplierRate;
	
	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	public Boolean getWindowToggle() {
		return windowToggle;
	}

	public void setWindowToggle(Boolean windowToggle) {
		this.windowToggle = windowToggle;
	}

	public Double getDecayRate() {
		return decayRate;
	}

	public void setDecayRate(Double decayRate) {
		this.decayRate = decayRate;
	}

	public Double getMultiplierRate() {
		return multiplierRate;
	}

	public void setMultiplierRate(Double multiplierRate) {
		this.multiplierRate = multiplierRate;
	}

}
