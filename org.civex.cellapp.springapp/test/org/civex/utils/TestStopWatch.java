package org.civex.utils;

public class TestStopWatch 
{
	long prev;
	long t1;
	long t2=0;
	
	public long getCurrentTime()
	{
		return System.currentTimeMillis();
	}
	
	public TestStopWatch()
	{
		t1=getCurrentTime();
	}
	
	public void reset()
	{
		t1=getCurrentTime();
		t2=0;
		prev=0;
	}
	
	public TestStopWatch stop()
	{
		t2=getCurrentTime();
		return this;
	}

	@Override
	public String toString() 
	{
		return String.valueOf(getCurrent());
	}
	
	public long getCurrent()
	{
		return prev+(t2>0?t2-t1:System.currentTimeMillis()-t1);
	}
	
	public boolean pause()
	{
		if(t2>0) return false; 
		t1=0;
		prev+=System.currentTimeMillis()-t1;
		return true;
	}
	
	public boolean resume()
	{
		if(t2>0) return false;
		t1=System.currentTimeMillis();
		return true;
	}
	
	
}
