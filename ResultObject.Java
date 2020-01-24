import java.util.*;

public class ResultObject {

	String region;
	double totalCost;
	HashMap<String,Integer> servers;
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public HashMap<String, Integer> getServers() {
		return servers;
	}
	public void setServers(HashMap<String, Integer> servers) {
		this.servers = servers;
	}
}
