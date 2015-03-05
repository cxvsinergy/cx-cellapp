package org.civex.japp;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.FileSystemXmlApplicationContext;
/**
 * instances host 0...n
 * instances multihosts
 *
 */
public class SpringAppMain
{
   protected static final Logger log=Logger.getLogger(SpringAppMain.class);
   protected static final String VERSION="0.9.3";
  /**Entry method for the spring application **/	
   static 
   {
	 //TODO init log from spring.xml
	   BasicConfigurator.configure();
   }
   
  public static void main(String[] args) 
  {
	  final List<String> params=Arrays.asList(args);
	  final String appName  =getCmdLineParam(params,"-app","consoleApp");
	  final String appConfig=getCmdLineParam(params,"-config","./spring-app.xml");
	  final File file=new File(appConfig);
	  if (!file.exists() || !file.isFile())
	  {
		  printInfo();
		  return;
	  }
	  
	  // init application properties
	  final PropertyPlaceholderConfigurer propsCfg=new PropertyPlaceholderConfigurer();
	  final Properties cmdProps=toCommandLineProperties(params);
	  propsCfg.setProperties(cmdProps);
	  //swing gui suppoort
	  log.info("Application "+appName +" is started ...");
	  
	  try
	  {
	  final FileSystemXmlApplicationContext context=new FileSystemXmlApplicationContext();
	  context.addBeanFactoryPostProcessor(propsCfg);
	  context.setConfigLocation(appConfig);
	  context.refresh();
	  
	  
	  
	  final Runnable appBean=(Runnable)context.getBean(appName);
	  final SimpleSpringApp appBean2=context.getBeanFactory().createBean(SimpleSpringApp.class);
	  context.getBeanFactory().initializeBean(appBean2, appName);
	  //context.getBeanFactory().	
	  System.out.println(appBean2.getName()+"  "+appBean);
	     
	  appBean.run();
	  }
	  catch(Exception x)
	  {
		  log.error(x);
	  }
	  finally
	  {
		  log.info("Application "+appName +" is completed ...");
	  }
  }
  
  /**Prints application Info**/
  public static void printInfo()
  {
	  System.out.println("Spring Application Launcher "+VERSION);
	  System.out.println("spring modules:");
	  System.out.println(" usage  java "+SpringAppMain.class.getName()+" -app <appName> -config <springConfig>");
  }
  
   /**Gets command line parameter**/
  public static String getCmdLineParam(List<String> params, String name, String defaultValue)
	{
		int index=params.indexOf(name);
		if (index<0 || index==params.size()-1) return defaultValue;
		return params.get(index+1);
	}
  
  /**converts parameters to the map for the further usage with app bean**/
  public static Properties toCommandLineProperties(List<String> params)
  {
	  final Properties paramMap=new Properties();
	  String prevName=null;
	  for (int ii=0;ii<params.size();ii++)
	  {
		  final String v=params.get(ii);
		  // we have a named parameter
		  if (v.startsWith("-"))
		  {
			  if (prevName!=null) paramMap.put(prevName, Boolean.TRUE);
			  prevName=v.substring(1).trim();
			  continue;
		  }
		  //parameter w/o name, error, skip
		  if (prevName==null)
		  {
			  log.warn("Can't find name for the values "+v);
			  continue;
		  }
		  
		   paramMap.put(prevName, v);
		   prevName=null;
	  }
	  if (prevName!=null) paramMap.put(prevName, Boolean.TRUE);
	  return paramMap;
  }
}
