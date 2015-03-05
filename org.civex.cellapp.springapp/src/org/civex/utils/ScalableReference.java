package org.civex.utils;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ScalableReference<T> implements Serializable
{
	final transient private AtomicBoolean locked=new AtomicBoolean();
	/**indicated last time the object was used**/
	transient private long lastTimestamp;
	private T obj;
	
	
	public boolean setReference(T ref, long timestamp)
	{
		if (!hasExclusiveAccess(true, timestamp));
		this.obj=ref;
		releaseExclusiveAccess(timestamp);
		return true;
	}
	
	
	public T getObject()
	{
		return obj;
	}
	
	/**gets last access time to the object**/
	public long getLastUseTimestamp()
	{
		return lastTimestamp;
	}
	
	/**mark last access to the object**/
	private void touch(long time)
	{
		lastTimestamp=time;
	}
	/**indicated that the object is blocked**/
	public boolean hasExclusiveAccess(final boolean block, final long timestamp)
	{
			if (block) 
			{
				//true->false; false->true
				final boolean v=locked.compareAndSet(false, true);
				if (v) touch(timestamp);
				return v;						
			}
			return locked.get();
	}
	
	/** releases exclusive access**/
	public boolean releaseExclusiveAccess(long t)
	{	
		touch(t);
		return locked.compareAndSet(true, false);
	}


	
	public ScalableReference<T> cloneAll(long timestamp) 
	{
		final ScalableReference<T> newRef=new ScalableReference<T>();
		newRef.hasExclusiveAccess(true, timestamp);
		return newRef;
	}
	
	
}
