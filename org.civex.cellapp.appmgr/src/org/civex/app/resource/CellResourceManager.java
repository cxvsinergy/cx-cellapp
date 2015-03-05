package org.civex.app.resource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CellResourceManager 
{
	private String basePath="cells/0/code";
	
	
	public void loadResourceConfig(ResourceConfig cfg, File f) throws IOException
	{
		loadResourceConfig(cfg,new FileReader(f));
	}
	
	public void loadResourceConfig(ResourceConfig cfg, Reader rdr) throws IOException
	{
		
	}
}
