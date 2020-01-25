import java.util.*;

public class ResourceAllocator {

	HashMap<String,Integer> cpusPerServer;
	HashMap<String,HashMap<String,Double>> regionToCost;
	
	//get_Costs module
	public ArrayList<ResultObject> getCosts(int hours , int cpus , double price)
	{
		//Negative or zero value inputs dont make sense
		if(hours<=0 || cpus<=0 || price<=0)
			return new ArrayList<>();
		
		//Result of the module
		ArrayList<ResultObject> sol = new ArrayList<>();
		
		//Solve for each region
		for(String region : regionToCost.keySet())
		{	
			//Available server types per region
			ArrayList<String> serverTypes = new ArrayList<>(regionToCost.get(region).keySet());
			
			//Tracking array to track number of units per server type allocated
			int[] arr = new int[serverTypes.size()];
			
			if(helper(0,price,cpus,hours,serverTypes,arr,region))
			{
				ResultObject r = new ResultObject();
				
				r.setRegion(region);
				
				double totalcost = 0.0;
				HashMap<String,Integer> servers = new HashMap<>();
				
				for(int i=0;i<arr.length;i++)
				{
				   if(arr[i]==0)
					   continue;
				   
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
		//satisfied
		 if(cpus<=0)
			 return true;
		 
	     double cost = regionToCost.get(region).get(serverTypes.get(i))*hours;
		 
	     int noOfCpus = cpusPerServer.get(serverTypes.get(i));
	     
	    //Max units of current server type that can be allocated
		int count = (noOfCpus>cpus)?1 : cpus/noOfCpus;
	    
		//Capacity can be satisfied entirely by this server type alone
		if(count*cost<=price)
		{
			arr[i] = count;
			return true;
		}
		
		//end of list
		if(i==serverTypes.size()-1)
			return false;
		
		for(;count>=1;count--)
		{
			if(count*cost>price)
				continue;
			 
			arr[i] = count;
			double curCost = count*cost;
						
			if(helper(i+1,price-curCost,cpus-count*noOfCpus,hours,serverTypes,arr,region))
				return true;
			
			arr[i] = 0;
		}
		
		if(helper(i+1,price,cpus,hours,serverTypes,arr,region))
		{
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
		
		//Barbara needs to pay 175 dollars 
		ArrayList<ResultObject> list = r.getCosts(24,115,175);
		
		for(ResultObject o : list)
		{
		     System.out.println(o.getRegion());
		     System.out.println(o.getTotalCost());
		     System.out.println(o.getServers());
		     System.out.println();
		}
		
		System.out.println(list);
		
		//Jack will have to pay 105 dollars
                list = r.getCosts(7,214,105);
		
                System.out.println();
        
		for(ResultObject o : list)
		{
		     System.out.println(o.getRegion());
		     System.out.println(o.getTotalCost());
		     System.out.println(o.getServers());
		     System.out.println();
		}
		
		System.out.println(list);
		
		//Tom can 70 cpus
		list = r.getCosts(8,70,29);
			
	        System.out.println();
	        
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
