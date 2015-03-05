package org.civex.mavenapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.civex.app.AppUtils;

public class MavenAppDeployMain extends MavenAppLauncherMain
{
	 protected static final String[] app_file_struct={"lib","log","bin","config"};
	 protected static final String[] app_stg_struct={"data","tmp"};
	 
	 public static void main(final String[] args) 
	 {
		 final List<String> argl=new ArrayList<String>(Arrays.asList(args));
		 final String appName=AppUtils.resolveCmdLineParam(argl,MavenAppLauncherMain.PARAM_APPNAME, null);
		 final String appVersion=AppUtils.resolveCmdLineParam(argl,MavenAppLauncherMain.PARAM_APPVER, "1.0.0.0");
		 final String appArtifactId=MavenAppDeployMain.resolveArtifactId(appName);
		 final String appMavenRepo=MavenAppLauncherMain.resolveResourceConfigParam("cell-config","maven.repo.local");
		 final File basef=new File(AppUtils.resolveCmdLineParam(argl,"cellpath", ".")).getAbsoluteFile();
		 final File appHome=createAppStructure(basef,appName,appVersion);
		 //
		 deployAppJar(appHome,appName,appArtifactId,appVersion,appMavenRepo);
		 deployAppCellConfig(appHome,appArtifactId,appVersion);
		 //
	 }
	 
	 protected static File createAppStructure(File base, String appName, String appVersion)
	 {
		 final File appHome=new File(base,appName);
		 if (!appHome.exists()) appHome.mkdir();
		 for (String s:app_file_struct)
		 {
			 File ff=new File(appHome,s);
			 if (!ff.exists()) ff.mkdir();
		 }
		 cleanLib(appHome);
		 return appHome;
	 }
	 
	 protected static void cleanLib(File appHome)
	 { 
		 final File flib=new File(appHome,"lib");
		 final File ff[]=flib.listFiles();
		 for (File f:ff)
		 {
			 if (f.isDirectory()) continue;
			 f.delete();
		 }
	 }
	 
	 protected static void deployAppJar(File appHome, String appName, String artifactId,String version, String mavenURL)
	 {
		 
		 //C:\local\1\devrepo\mavenrepo\
	 }
	 
	 protected static void deployAppCellConfig(File appHome, String artifactId, String appVersion)
	 {
		 
	 }
}
