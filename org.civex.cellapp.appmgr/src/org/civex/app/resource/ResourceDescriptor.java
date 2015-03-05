package org.civex.app.resource;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

public class ResourceDescriptor implements Serializable,Comparable<ResourceDescriptor> 
{
		private static final long serialVersionUID = 1L;
		final public static byte EMPTY_BYTES[]={};		
		final protected String path;
		final protected long size;
		final protected long timestamp;
		
		
		public ResourceDescriptor(final String path)
		{
			this(path,-1,-1);
		}
		
		public ResourceDescriptor(final String path, final long len, final long timestamp)
		{
			this.path=path.replace('\\', '/');
			this.size=len;
			this.timestamp=timestamp;
		}
		
		public ResourceDescriptor(final File f)
		{
			this(f.getAbsolutePath(),f.length(),f.lastModified());
		}
		
		
		public ResourceDescriptor(final URL url) throws IOException
		{
			final URLConnection conn=url.openConnection();
			timestamp=conn.getLastModified();
			size=conn.getContentLength();
			path=url.getPath();
		}
		
		
		public String getName()
		{
			final String name=getFullName();
			final int index=name.lastIndexOf('.');
			return name.substring(0,index);
		}
		
		public String getExtension() 
		{
			final String name=getFullName();
			final int index=name.lastIndexOf('.');
			return index<0?null:name.substring(index+1);
		}
		
		public String getVersion() {return  null;}
		public String getPath()    {return path;}
		public long getSize()      {return size;}
		public long getTimestamp() {return timestamp;}
		public byte[] getResourceHash()    {return EMPTY_BYTES;}
		
		
		public String getFullName() 
		{	
			int x1=getPath().lastIndexOf('/');
			return getPath().substring(x1+1);
		}
		
		@Override
		public int compareTo(ResourceDescriptor other) 
		{
			if (this==other) return 0;
			return -1;
		}

		@Override
		public int hashCode() 
		{
			return getPath().hashCode();
		}

		@Override
		public boolean equals(Object obj) 
		{
			if (obj.hashCode()!=this.hashCode()) return false;
			if (!(obj instanceof ResourceDescriptor)) return false;
			final ResourceDescriptor other=(ResourceDescriptor)obj;
			return other.getPath().equals(getPath());
		}

		@Override
		public String toString() 
		{
			final StringBuilder bld=new StringBuilder(100);
			bld.append(getPath());
			bld.append(':').append(getSize()).append(':').append(getTimestamp());
			return bld.toString();
		}

		public String hashToString()
		{
			final StringBuilder bld=new StringBuilder(100);
			final byte buff[]=getResourceHash();
			for (int ii=0;ii<buff.length;ii++)
			{
				bld.append(':');
				bld.append('A');
				bld.append('B');
			}
			return bld.toString();
		}
		
		/**resource changed**/
		public boolean isResourceChanged(final File f)
		{
			return f.lastModified()>getTimestamp();
		}

		public ResourceDescriptor cloneWithNewName(String name, String ext)
		{
			return null;
		}

		public Object getLoginCredentials() {return null;}
		public Class<?> getResourceType()	{return null;}

		
		public static class MavenResourceDescriptor extends ResourceDescriptor
		{
			protected String version;
			
			
			public MavenResourceDescriptor(File f) 
			{
				super(f);
			}
			
			
			public String getVersion() {return version;}
			
			
			
		}
		
}
