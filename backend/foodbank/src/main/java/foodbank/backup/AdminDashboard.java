package foodbank.backup;

import java.util.List;
import java.util.Map;

/*
 * Created by: Lau Peng Liang, Bryan
 */

public class AdminDashboard {

	private Map<String, List<Map<String, String>>> topKDemand;
	private Map<String, List<Map<String, String>>> topKSupply;
	private Map<String, String> requestWindowStatus;
	private Map<String, Integer> uniqueRequestReturn;
	
	public Map<String, List<Map<String, String>>> getTopKDemand() {
		return topKDemand;
	}

	public void setTopKDemand(Map<String, List<Map<String, String>>> topKDemand) {
		this.topKDemand = topKDemand;
	}

	public Map<String, List<Map<String, String>>> getTopKSupply() {
		return topKSupply;
	}

	public void setTopKSupply(Map<String, List<Map<String, String>>> topKSupply) {
		this.topKSupply = topKSupply;
	}

	public Map<String, String> getRequestWindowStatus() {
		return requestWindowStatus;
	}
	
	public void setRequestWindowStatus(Map<String, String> requestWindowStatus) {
		this.requestWindowStatus = requestWindowStatus;
	}
	
	public Map<String, Integer> getUniqueRequestReturn() {
		return uniqueRequestReturn;
	}
	
	public void setUniqueRequestReturn(Map<String, Integer> uniqueRequestReturn) {
		this.uniqueRequestReturn = uniqueRequestReturn;
	}

	public AdminDashboard(Map<String, List<Map<String, String>>> topKDemand,
			Map<String, List<Map<String, String>>> topKSupply, Map<String, String> requestWindowStatus,
			Map<String, Integer> uniqueRequestReturn) {
		super();
		this.topKDemand = topKDemand;
		this.topKSupply = topKSupply;
		this.requestWindowStatus = requestWindowStatus;
		this.uniqueRequestReturn = uniqueRequestReturn;
	}
	
}
