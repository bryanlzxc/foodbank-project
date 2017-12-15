package foodbank.admin.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Admin")
public class AdminSettings {

	@Id
	private String id;
	
	public enum WindowStatus {
		ACTIVE, INACTIVE
	}
	
	private WindowStatus windowStatus;
	private Date windowEndDateTime;	//change this to date later
	private double decayRate;
	private double multiplierRate;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public WindowStatus getWindowStatus() {
		return windowStatus;
	}

	public void setWindowStatus(WindowStatus windowStatus) {
		this.windowStatus = windowStatus;
	}

	public Date getWindowEndDateTime() {
		return windowEndDateTime;
	}

	public void setWindowEndDateTime(Date windowEndDateTime) {
		this.windowEndDateTime = windowEndDateTime;
	}

	public double getDecayRate() {
		return decayRate;
	}

	public void setDecayRate(double decayRate) {
		this.decayRate = decayRate;
	}

	public double getMultiplierRate() {
		return multiplierRate;
	}

	public void setMultiplierRate(double multiplierRate) {
		this.multiplierRate = multiplierRate;
	}
	
}
