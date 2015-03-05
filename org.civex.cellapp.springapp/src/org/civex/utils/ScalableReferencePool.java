package org.civex.utils;

import java.util.ArrayList;
import java.util.List;

public class ScalableReferencePool<T extends Cloneable> 
{
	final private List<ScalableReference<T>> refs= new ArrayList<ScalableReference<T>>();
	private int maxAvailable;
	
	public  int getMaxAvailable()
	{
		return maxAvailable;
	}
	
	public ScalableReference<T> getAnyExclusive()
	{
		long t=System.currentTimeMillis();
		for (int ii=0;ii<refs.size();ii++)
		{
			final ScalableReference<T> ref=refs.get(ii);
			if (ref.hasExclusiveAccess(true, t)) return ref;
		}
		// too many objects, can only wait
		if (refs.size()>=this.getMaxAvailable()) return null;
		final ScalableReference<T> ref=createNew(t);
		// should be synchronized;
		refs.add(ref);
		// returns blocked reference but w/o actual object
		return ref;
	}
	//
	protected ScalableReference<T> createNew(long timestamp)
	{
		return refs.get(0).cloneAll(System.currentTimeMillis());
	}
}
