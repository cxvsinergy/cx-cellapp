package org.civex.mavenapp;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarFile;

import org.civex.app.AppUtils;
import org.civex.app.ApplicationLifeCycleManager;
/***
 * 
 * @TODO support log
 * @TODO 
 *
 */
public class MavenAppLauncherMain 
{

	//protected static final Logger logger=Logger.getLogger(MavenAppLauncherMain.class.getName());
	
	protected static final String LAUNCHER_VERSION="1.0.0";
	protected static final String JAR_EXT=".jar";
	protected static final String LIB_PATH="lib";
	
	protected static final String[] EMPTY_ARR={};
	protected static final Class<?>[] MAIN_PARAM_TYPES=new Class[]{String[].class};
	protected static final String PARAM_APPHOME="appHome";
	protected static final String PARAM_APPCELL="appCell";
	protected static final String PARAM_APPNAME="appName";
	protected static final String PARAM_APPMAIN="appMain";
	protected static final String PARAM_APPCONF="appConfig";
	protected static final String PARAM_APPLOG="appLog";
	
	protected static final String PARAM_APPVER="appVer";
	protected static final String PARAM_MAVENSRV="mavenSrv";
	protected static final String PARAM_MAVENREPO="repo";
    
    protected static final ApplicationLifeCycleManager mngLifeCycle=new ApplicationLifeCycleManager(7771);
    
	public static void main(String [] arg) throws Exception
	{
		 final List<String> argl=new ArrayList<String>(Arrays.asList(arg));
		 final String mavenSrv=AppUtils.resolveCmdLineParam(argl,PARAM_MAVENSRV,"http://localhost:8080/civex/mavenrun");
		 final String appHome=AppUtils.resolveCmdLineParam(argl,PARAM_APPHOME,new File(".").getAbsolutePath());
		 
		 
		 final String appName=resolveAppName(appHome,argl);
		 final String artifactId=resolveArtifactId(appName);
		 final String appVersion=resolveAppVersion(appHome,artifactId);
		 String appMain=AppUtils.resolveCmdLineParam(argl,PARAM_APPMAIN,null);
		 appMain=appMain!=null?appMain:resolveAppMain(appHome,artifactId,appVersion,argl);
		 
		 //
		 mngLifeCycle.setupApp(appHome, appName, appVersion,appMain);		 
		 //
		 final ClassLoader cloader=createClassLoader(mavenSrv,appHome,appName, appVersion,true);
		 final Class<?> clazz=cloader.loadClass(appMain);
		 final Method m=clazz.getMethod("main", MAIN_PARAM_TYPES);
		 final String[] appParams=argl.toArray(EMPTY_ARR);
		 
		 mngLifeCycle.fireAppAboutToStart();
		 m.invoke(null, new Object[]{appParams});
	}
	
	protected static String resolveArtifactId(String appName) 
	{
		int index=appName.lastIndexOf('.');
		return index<0?appName:appName.substring(index+1);
	}


	protected static String resolveAppMain(String appHome,String artifactId, String appVersion, List<String> argl) throws Exception
	{
		final File f=AppUtils.findFile(appHome+"/lib",artifactId+"-"+appVersion,".jar");
		final JarFile jf=new JarFile(f); 
		return jf.getManifest().getMainAttributes().getValue("Main-Class");
	}
		
	protected static String resolveAppName(String appHome,List<String> argl)
	{
		appHome=appHome.replace('\\', '/');
		int last=appHome.lastIndexOf('/');
		if (last<0) return null;
		return appHome.substring(last+1);
	}
	
	
	
	protected final static File resolveAppLibPath(final String appHome)
	{
		return new File(appHome,LIB_PATH);
	}
	
	protected  final  static File[] resolveAppLibFiles(final String appHome)
	{
		final File libDir=resolveAppLibPath(appHome);
		if (!libDir.isDirectory()) return new File[]{};
		return libDir.listFiles();
	}
	
	protected static String resolveAppVersion(final String appHome, final String appName)
	{
		final File files[]=resolveAppLibFiles(appHome);
		for (final File f:files)
		{
			if (!f.isFile()) continue;
			final String name=f.getName();
			if (!name.endsWith(JAR_EXT)) continue;
			String version=AppUtils.removeFromStringRight(name,JAR_EXT);
			//remove snapshot, if we have -SNAPHOT-X
			version=AppUtils.removeFromStringRight(version,"-SNAPSHOT");
			int index=version.lastIndexOf('-');
			return version.substring(index+1);
		}	
		return null;
	}
	
	protected static ClassLoader createClassLoader(String mavenService, String appHome,String appName, String appVersion, boolean useRepo) throws IOException
	{
		final MavenRepoClassLoader classLoader=new MavenRepoClassLoader();
		final StringBuilder webAppClassPath=new StringBuilder(100); 
		requestAppMavenClassPath(mavenService,appName,appVersion,webAppClassPath);
		classLoader.addUrlList(new java.io.StringReader(webAppClassPath.toString()));
		// first get libraries from maven
		final File f=resolveAppLibPath(appHome);		
		final File classpathFile=new File(f,".classpath.repo");
		if (classpathFile.exists()) 
		{
			classLoader.addUrlList(classpathFile);	
		}
		//
		final File[] ff=resolveAppLibFiles(appHome);
		for (final File jf:ff)
		{
			if (!jf.isFile() || !jf.getName().endsWith(JAR_EXT)) continue;
			if (useRepo && jf.getName().startsWith(appName)) continue;
			classLoader.addFile(jf.getAbsoluteFile());
			
		}
		
		return classLoader;
	}
	
	
	protected  static void requestAppMavenClassPath(String mavenService, String appName, String appVersion, StringBuilder bld)
	{
		String query=mavenService+"/classpath/"+appName+"-"+appVersion;
		printLogInfo(query);
		return ;
	}

	
	
	public static void printLogInfo(String s)
	{
		System.out.println("AppLauncher ->"+s);
	}
	
	protected static String resolveResourceConfigParam(String string,String string2) 
	{
		return null;
	}
	
	protected static File toAppLibFile(File appHome)
	{
		final File f=new File(appHome,LIB_PATH);
		if (!f.exists()) f.mkdir();
		return f;
	}
	
	protected static String toMavenArtifactPath(String base, String name, String version)
	{
		StringBuilder r=new StringBuilder(100);
		r.append(base).append('/').append(name.replace('.', '/'));
		r.append('/').append(version);
		r.append('/').append(name).append('-').append(version).append(JAR_EXT);
		return r.toString();
	}
}
