package org.civex.app;

public abstract class ApplicationMain

{
	 public static void runMain(String[] args, Class<?> clazz) 
	  {
		 int runCounter=0;
		 ApplicationMain app=null;
		 try
		 {
			 app=(ApplicationMain)clazz.newInstance();
			 if (app.validateParameters(args))
			 {
				 app.printLegend();
				 System.exit(-1);
			 }
			 	 
		 }
		 catch(Exception x)
		 {
			 x.printStackTrace();
			 System.exit(-1);
		 }
		 // runnable part
		 while(true)
		 {
			 try
			 {
				
				
				 runCounter++;
				 app.loadConfig(null);
				 app.runInstance(args);	 
			 }
			 catch(Exception x)
			 { 
				 app.handleInstanceError(x,runCounter,0);
			 }	 
		 }
		 
		 
	  }
	 
	 
	 
	 public abstract void runInstance(String[] args) throws Exception;
	
	 
	 protected  void printLegend()
	 { }
	 
	 public void printMessage(int level, String msg, Object params[])
	 {System.out.println(msg);	}
	 
	 
	 public static String resolveParam(String args[] ,String name, String defaultValue)
	 {
		 return defaultValue;
	 }
	 
	 
	 public static double resolveParam(String args[] ,String name, double defaultValue)
	 {
		 return defaultValue;
	 }
	 
	 public void loadConfig(String[] args)
	 {
		 
	 }
	 
	 
	 boolean validateParameters(String args[])
	 {
		 return args.length>1;
	 }
	 
	 
	 public static void sleep(long time)
	 {
		 try
		 {
			 Thread.sleep(time);			 
		 }
		 catch(Exception x)
		 {
			 x.printStackTrace();
		 }

	 }
	 
	 
	 public long getCurrentTime()
	 {
		 return System.currentTimeMillis();
	 }
	 
	 
	 protected void handleInstanceError(Exception x, long  runCounter, long prevTime)
	 {
		 x.printStackTrace();
		 System.exit(0);
	 }
	  
}
