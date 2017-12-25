package foodbank.admin.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import foodbank.util.DateParser;
import foodbank.util.MessageConstants.ErrorMessages;

@Document(collection = "Admin")
public class AdminSettings {

	@Id
	private String id;
	
	public enum WindowStatus {
		ACTIVE, INACTIVE
	}
	
	private WindowStatus windowStatus;
	private Date windowStartDateTime;
	private Date windowEndDateTime;	
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
	
	public String getWindowStartDateTime() {
		String date = null;
		try {
			date = DateParser.getCurrentDate(windowStartDateTime);
		} catch (NullPointerException e) {
			date = ErrorMessages.INACTIVE_WINDOW;
		} catch (Exception e) {
			date = e.getMessage();
		}
		return date;
	}
	
	public void setWindowStartDateTime(Date windowStartDateTime) {
		this.windowStartDateTime = windowStartDateTime;
	}

	public String getWindowEndDateTime() {
		String date = null;
		try {
			date = DateParser.getCurrentDate(windowEndDateTime);
		} catch (NullPointerException e) {
			date = ErrorMessages.INACTIVE_WINDOW;
		} catch (Exception e) {
			date = e.getMessage();
		}
		return date;
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
	
	@Override
	public String toString() {
		return windowStatus + ","
				+ windowStartDateTime + ","
				+ windowEndDateTime + ","
				+ decayRate + ","
				+ multiplierRate;
	}
	
}
