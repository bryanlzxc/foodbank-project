package foodbank.util;

public class Status {
	
	// This will hold the values of either 
	// success || fail
	private String status;
	
	public Status() {
		this.status = "fail";
	}
	
	public Status(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
