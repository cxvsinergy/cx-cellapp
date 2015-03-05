package org.civex.japp;


public class SimpleSpringApp implements Runnable
{
	private String name; 
	
	public void setUser(final String name)
	{
		this.name=name;
	}
	
   public void run()
   {
	   System.out.println("Simple Spring Application running as "+name);
   }
   
   public String getName()
   {
	   return name;
   }
}
