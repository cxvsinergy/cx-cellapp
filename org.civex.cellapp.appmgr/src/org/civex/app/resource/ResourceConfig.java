package org.civex.app.resource;

import java.util.HashMap;
import java.util.Map;

public class ResourceConfig 
{
	private CellResourceManager mng;
	private String configPath;
	private long   timestamp;
	private Map<String,String> propMap=new HashMap<String,String>();
	
	
	public String getPropertyValue(String name)
	{
		return propMap.get(name);
	}
	
	

}
