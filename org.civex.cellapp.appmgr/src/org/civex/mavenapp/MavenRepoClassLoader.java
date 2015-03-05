package org.civex.mavenapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.net.URLClassLoader;

public class MavenRepoClassLoader extends URLClassLoader
{
	public MavenRepoClassLoader()
	{
		super(new URL[]{});
	}

	public void addURL(URL url) {  
        super.addURL(url);  
    }  
	
	public void addFile(File jf) throws IOException
	{
		
		final URL url=jf.getAbsoluteFile().toURI().toURL();
		addURL(url);
		System.out.println(url);
		
	}

	public void addUrlList(File classpathFile) throws IOException
	{
		 addUrlList(new FileReader(classpathFile));
	}
	
	public void addUrlList(Reader streamReader) throws IOException
	{
		final BufferedReader rdr=new BufferedReader(streamReader);
		String s=null;
		final String repoPath=System.getenv().get("M2_REPO");
		while((s=rdr.readLine())!=null)
		{
			if (s.trim().length()==0 || s.startsWith("#")) continue;	
			addFile(new File(toRepoPath(repoPath, s)).getAbsoluteFile());
		}
		rdr.close();
	}
	
	
	protected static String toRepoPath(String repoPath, String v)
	{
		if (repoPath==null) return v;
		return v.replace("%M2_REPO%", repoPath);
	}
}
