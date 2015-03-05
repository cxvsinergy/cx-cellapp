package org.civex.app.resource;

public class MavenResourceDescriptor extends ResourceDescriptor
{
	final private String artifactFullName;
	
	public MavenResourceDescriptor(String path) 
	{
		super(path+".jar");
		artifactFullName=path;
	}

	public MavenResourceDescriptor(String artifact, String ver) 
	{
		this(artifact+'-'+ver);
	}

	
	public boolean isSnapshot()
	{
		final String name=getName();
		return name.endsWith("-SNAPSHOT"); 
	}
	
	
	@Override
	public String getVersion() 
	{
		 String artifactID=getArtifactID() ;
		 int x1=artifactFullName.indexOf(artifactID);
		 return artifactFullName.substring(x1+1+artifactID.length());
	}

	public String getArtifactID()    
	{
		final int x1=artifactFullName.indexOf("-SNAPSHOT"); 
		final int x2=artifactFullName.lastIndexOf('-',x1>0?x1-1:artifactFullName.length());
		final int x3=artifactFullName.lastIndexOf('.',x2>0?x2:artifactFullName.length());
		return artifactFullName.substring(x3+1,x2);
	}

	public String getArtifactGroup() 
	{
		 final String artifactID=getArtifactID() ;
		 int x1=artifactFullName.lastIndexOf(artifactID);
		 return artifactFullName.substring(0,x1-1);
	}
	
	
	public String toRepoPath(String base)
	{
		return base;
	}
}
