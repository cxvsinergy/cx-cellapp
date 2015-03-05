package org.civex.app.resource;

import junit.framework.TestCase;

public class ResourceDescriptorTest extends TestCase
{
			public void testResourceObject()
			{
				ResourceDescriptor r1=new ResourceDescriptor("a/b/c.txt");
				ResourceDescriptor r2=new ResourceDescriptor("a/b\\c.txt");
				assertEquals(r1,r2);
				assertEquals("txt",r2.getExtension());
				assertEquals("c.txt",r2.getFullName());
				assertEquals("c",r2.getName());
			}
			
			public void testMavenResource()
			{
				final MavenResourceDescriptor mv1=new MavenResourceDescriptor("org.civex.cellapp.cx-hostservice","1.0.0-SNAPSHOT");
				assertEquals(true,mv1.isSnapshot());				
				assertEquals("org.civex.cellapp",mv1.getArtifactGroup());
				assertEquals("cx-hostservice",mv1.getArtifactID());
				assertEquals("1.0.0-SNAPSHOT",mv1.getVersion());
				
			}
}
