package org.civex.utils;

public class IterativeRunnable<T> implements Runnable
{
	enum RUNNABLE_STATE{UNKNOWN,WORKING,PAUSING,PAUSED, CANCELING, CANCELED, COMPLETED}
	
	final protected TestStopWatch timer=new TestStopWatch();
	protected long iterations=1;
	private java.util.concurrent.CountDownLatch latch;
	protected final T obj;
	/**delay between iterations**/
	protected int delay=0;
	protected RUNNABLE_STATE state=RUNNABLE_STATE.UNKNOWN;
	
	
	
	
	
	public IterativeRunnable(T obj,long iterations)
	{
		this.obj=obj;
		this.iterations=iterations;
	}
	
	
	public void setIterationDelay(int delay)
	{
		this.delay=delay;
	}
	
	public int getIterationDelay()
	{
			
		return delay;
	}
	
	@Override
	public void run() 
	{
		
		timer.reset();
		try
		{
			runSafe();
		}
		catch(Exception x)
		{	
			x.printStackTrace();		
		}
		finally
		{
			timer.stop();
			if (latch!=null) latch.countDown();
		}
		
	}
	
	
	protected void runSafe()
	{
		long counter=iterations;
		long index=0;
		do 
		    {
			if (runIteration(++index, timer.getCurrentTime())) counter--;	
	 	    }
		while(counter>0);
		
	}
	
	
	protected boolean runIteration(long index, long timestamp)
	{
		return true;
	}
	
	/**safe wrapper for sleep method**/
	public void pauseThread(long timeout)
	{
		try
		{
			Thread.sleep(timeout);	
		}
		catch(InterruptedException x)
		{
			throw new RuntimeException(x);
		}
		
	}
}
