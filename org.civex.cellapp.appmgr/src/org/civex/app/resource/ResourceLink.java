package org.civex.app.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class ResourceLink<T> 
{
  private T   res;
  
  public ResourceLink(T res)
  {
	  this.res=res;
  }
 	
  public T getResource() {return res;}
  public long size() {return -1;}
  public long timeatamp() {return -1;}
  public abstract InputStream openInputStream() throws IOException;
  public abstract OutputStream openOpenStream() throws IOException;
  public boolean isCollecton() {return false;}	
  
  
  
  
	
public static class DesriptorResourceLink extends ResourceLink<ResourceDescriptor>
{

	public DesriptorResourceLink(ResourceDescriptor res) 
	{
		super(res);
	}

	@Override
	public long size() 
	{
		return getResource().getTimestamp();
	}

	@Override
	public long timeatamp() 
	{
		return getResource().getTimestamp();
	}

	@Override
	public InputStream openInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream openOpenStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
	
public static class FileResource extends ResourceLink<File>
{
	public FileResource(File res) 
	{
		super(res);
	}

	@Override
	public long size() 
	{
		return getResource().length();
	}

	@Override
	public InputStream openInputStream() throws IOException
	{
		return new FileInputStream(getResource());
	}

	@Override
	public long timeatamp() 
	{
		return getResource().lastModified();
	}

	@Override
	public OutputStream openOpenStream() throws IOException 
	{
		return new FileOutputStream(getResource());
	}
	
	public boolean isReadable()  {return true;}
	public boolean isWriteable() {return true;}
	public boolean appended() 	 {return true;}
	
}
}

