package org.civex.app.resource;

import java.io.File;

import org.civex.app.resource.ResourceLink.FileResource;

public class MavenLibraryResource extends FileResource
{
	private String uri;
	private String localRepo;
	
	public MavenLibraryResource(final File res) 
	{
		super(res);
	}
	
	
	public String getVersion()
	{
		return null;
	}
	
	public void copy()
	{
		
	}
	
	public void checkRecentBuild(boolean update)
	{
		
	}
	
}
