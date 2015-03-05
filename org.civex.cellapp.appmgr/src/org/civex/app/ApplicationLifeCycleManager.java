package org.civex.app;

public class ApplicationLifeCycleManager 
{
		final private int port;
		private String appHome;
		
		public ApplicationLifeCycleManager(int port)
		{
			this.port=port;
		}
	
		private String appCell;
		private String appName;
		private String appVersion;
		private String artifactId;
		//
		private long   tstampConfig;
		private long   tstampApp;
		private long   tstampStarted;
		
		
		
		public void kill() 		  {};
		public void reconfigure() {};
		public void pause()  	  {};
		public void resume() 	  {};
		public void restart()	  {};
		public void stop()		  {};
		
		
		public String getAppHome() {return appHome;}
		public String getAppName() {return appName;}
		public String getVersion() {return appVersion;}
		
		
		public void setupApp(String appHome, String appName,String appVersion, String appMain) 
		{
			System.out.println("Launching app "+appName+"  ver="+appVersion+" main="+appMain);
			this.appName=appName;
			this.appHome=appHome;
			this.appVersion=appVersion;
			
		}
		public void fireAppAboutToStart() 
		{
			tstampStarted=System.currentTimeMillis();
		}
		
}
