package foodbank.allocation.entity;

/*
 * Created by: Lim Jian Quan, Jaren
 */

public class AllocationOutcome {
	
	private boolean isAllocationSuccessful;
	
	public boolean getAllocationOutcome() {
		return isAllocationSuccessful;
	}
	
	public void setAllocationOutcome(boolean isAllocationSuccessful) {
		this.isAllocationSuccessful = isAllocationSuccessful;
	}
	
	public AllocationOutcome(boolean isAllocationSuccessful) {
		this.isAllocationSuccessful = isAllocationSuccessful;
	}
	

}
