import java.util.*;

public class ResourceAllocator {

	HashMap<String,Integer> cpusPerServer;
	HashMap<String,HashMap<String,Double>> regionToCost;
	
	public ArrayList<ResultObject> getCosts(int hours , int cpus , double price)
	{
		if(hours<=0 || cpus<=0 || price<=0)
			return new ArrayList<>();
		
		ArrayList<ResultObject> sol = new ArrayList<>();
		
		//System.out.println("here");
		
		for(String region : regionToCost.keySet())
		{	
			ArrayList<String> serverTypes = new ArrayList<>(regionToCost.get(region).keySet());
			int[] arr = new int[serverTypes.size()];
			
			//System.out.println("inside");
			
			if(helper(0,price,cpus,hours,serverTypes,arr,region))
			{
				//System.out.println("success");
				ResultObject r = new ResultObject();
				
				r.setRegion(region);
				
				double totalcost = 0.0;
				HashMap<String,Integer> servers = new HashMap<>();
				
				for(int i=0;i<arr.length;i++)
				{
				   if(arr[i]==0)
					   continue;
				   
				//   System.out.println(arr[i]);
				//   System.out.println(regionToCost.get(region).get(serverTypes.get(i)));
				   
				   totalcost += arr[i]*regionToCost.get(region).get(serverTypes.get(i))*hours;
				   servers.put(serverTypes.get(i),arr[i]);
				}
				
				r.setTotalCost(totalcost);
				r.setServers(servers);
				
				sol.add(r);
			}
		}
		
		return sol;
	}
	
	public boolean helper(int i , double price , int cpus , int hours , ArrayList<String> serverTypes , int[] arr , String region)
	{
		//System.out.println("inside helper " + i + " price " + price + " cpus " + cpus + " hours " + hours);
		
		 if(cpus<=0)
			 return true;
		 
		 //System.out.println(regionToCost.get(region).get(serverTypes.get(i)));
		 //System.out.println("hours " + hours);
	     double cost = regionToCost.get(region).get(serverTypes.get(i))*hours;
		 
	     int noOfCpus = cpusPerServer.get(serverTypes.get(i));
		  int count = (noOfCpus>cpus)?1 : cpus/noOfCpus;
	    //System.out.println("cost " + cost);
	     
		if(count*cost<=price)
		{
			//System.out.println("cost matches");
			arr[i] = count;
			return true;
		}
		
		if(i==serverTypes.size()-1)
			return false;
		
		
		
		for(;count>=1;count--)
		{
			if(count*cost>price)
				continue;
			 
			arr[i] = count;
			double curCost = count*cost;
			
			//System.out.println("count " + count);
			//System.out.println("curCost " + curCost);
			
			if(helper(i+1,price-curCost,cpus-count,hours,serverTypes,arr,region))
				return true;
			
			arr[i] = 0;
		}
		
		if(helper(i+1,price,cpus,hours,serverTypes,arr,region))
		{
			//System.out.println("success here " + (i+1));
			return true;
		}
		
		return false;
	}
	
	
	
	public static void main(String[] args)
	{
		ResourceAllocator r = new ResourceAllocator();
		
		 r.cpusPerServer = new HashMap<>();
		 r.cpusPerServer.put("large",1);
		 r.cpusPerServer.put("xlarge",2);
		 r.cpusPerServer.put("2xlarge",4);
		 r.cpusPerServer.put("4xlarge",8);
		 r.cpusPerServer.put("8xlarge",16);
		 r.cpusPerServer.put("10xlarge",32);
		 
		 r.regionToCost = new HashMap<>();
		 
		 HashMap<String,Double> temp = new HashMap<>();
		 
		 temp.put("large", 0.12);
		 temp.put("xlarge", 0.23);
		 temp.put("2xlarge", 0.45);
		 temp.put("4xlarge", 0.774);
		 temp.put("8xlarge", 1.4);
		 temp.put("10xlarge", 2.82);
		 
		 r.regionToCost.put("us-east",temp);
		 
		 temp = new HashMap<>();
		 
		 temp.put("large", 0.14);
		 temp.put("2xlarge", 0.413);
		 temp.put("4xlarge", 0.89);
		 temp.put("8xlarge", 1.3);
		 temp.put("10xlarge", 2.97);
		 
		 r.regionToCost.put("us-west",temp);
		 
		 temp = new HashMap<>();
		 
		 temp.put("large", 0.11);
		 temp.put("xlarge", 0.20);
		 temp.put("4xlarge", 0.67);
		 temp.put("8xlarge", 1.18);
		 
		 r.regionToCost.put("asia",temp);
		 
		// System.out.println(r.cpusPerServer);
		// System.out.println(r.regionToCost);
		 
		ArrayList<ResultObject> list = r.getCosts(24,115,184);
		
		for(ResultObject o : list)
		{
		     System.out.println(o.getRegion());
		     System.out.println(o.getTotalCost());
		     System.out.println(o.getServers());
		     System.out.println();
		}
		
		System.out.println(list);
	}
}
