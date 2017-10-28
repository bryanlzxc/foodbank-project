package foodbank.admin.entity;



import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Admin")
public class Admin {

	@Id
	private String id;
	
	private boolean windowStatus;	//false for close, true for open
	private Date windowEndDateTime;	//change this to date later
	private double decayRate;
	private double multiplierRate;
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isWindowStatus() {
		return windowStatus;
	}
	public void setWindowStatus(boolean windowStatus) {
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
	public Admin(boolean windowStatus, Date windowEndDateTime, double decayRate, double multiplierRate) {
		this.windowStatus = windowStatus;
		this.windowEndDateTime = windowEndDateTime;
		this.decayRate = decayRate;
		this.multiplierRate = multiplierRate;
	}
	public Admin() {
		
	}
	
	
	
	
	
	
	
}
