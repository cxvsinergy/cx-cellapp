package org.civex.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppUtils 
{
	public static final String[] EMPTY_STRING_ARR={};
	public static final Object[] EMPTY_ARR={};
	

	protected static List<String> makeParams(String[] args)
	{
		final List<String> params=new ArrayList<String>(20);
		for (final String arg:args) params.add(arg);
		return params;
	}
	
	public static boolean hasParam(List<String> params, String name)
	{
		final int index=params.indexOf("-"+name);
		return index>=0;
	}

	
	public static double resolveCommandParam(List<String> params, String name, double defaultValue)
	{
		String v=resolveCmdLineParam(params,name,null);
		return v==null?defaultValue:Double.parseDouble(v);
	}
	
	public static int resolveCommandParam(List<String> params, String name, int defaultValue)
	{
		final String v=resolveCmdLineParam(params,name,null);
		return v==null?defaultValue:Integer.parseInt(v);
	}
	
	
	public static String resolveCmdLineParam(List<String> params, String name, String defaultValue)
	{
		final int index=params.indexOf("-"+name);
		if (index<0 || index==params.size()-1) return defaultValue;
		return params.get(index+1);
	}


	public static File findFile(String baseDir, String prefix, String suffix)
	{
		final File fbase=new File(baseDir);
		if (!fbase.isDirectory()) return null;
		final File files[]=fbase.listFiles();
		for (final File f:files)
		{
			final String fname=f.getName();
			if (fname.startsWith(prefix) && fname.endsWith(suffix)) return f;
		}
		 return  null;
	}
	
	
	public static void safeSleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch(InterruptedException x)
		{
			x.printStackTrace();
		}
	}
	
	
	public static  Map<String, ?> list2map(List<String> args) 
	{
		final Map<String,Object> m=new HashMap<String,Object>();
		String prev=null;
		for (int ii=0;ii<args.size();ii++)
		{
			String v=args.get(ii);
			if (v.startsWith("-"))
			{
				prev=v.substring(1);
				m.put(v, Boolean.TRUE);
				continue;
			}
			if (prev==null) continue;
			m.put(prev,v); // adds the last value
		}
		return m;
	}
	
	
	public static String removeFromStringRight(String s, String fragment)
	{
		int index=s.lastIndexOf(fragment);
		if (index<0) return s;
		return s.substring(0,index);
	}


	public static String resolveMapParam(Map<String,?> params, String name, String defaultValue)
	{
		final Object v=params.get(name);
		return v!=null?v.toString():defaultValue;
	}
	
	public static  int resolveMapParam(Map<String,?> params, String name, int defaultValue)
	{
		final String v=resolveMapParam(params,name,null);
		return v==null?defaultValue:Integer.parseInt(v);
	}
	
	public static int parseNumber(String v)
	{	
		return Integer.parseInt(v);
	}

	
	public static void readToStringList(InputStream stream, List<String> r) throws IOException
	{
		final BufferedReader rdr=new BufferedReader(new InputStreamReader(stream));
		String s=null;
		while((s=rdr.readLine())!=null)
		{
			if (s.length()==0) continue;
			r.add(s);
		}
	}
}
