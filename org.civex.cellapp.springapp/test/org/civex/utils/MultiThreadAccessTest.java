package org.civex.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

public class MultiThreadAccessTest extends TestCase 
{
	final int cpu=4; 
	final int itertaions=100;
	final int shortItertaions=1000*1000;
	final ExecutorService x=java.util.concurrent.Executors.newScheduledThreadPool(cpu);
	
	public class SafeInteger extends ScalableReference
	{
		 transient public int value=1;
		 transient public int counter=0;
	}
	
	public void testMS()
	{
		
		ScalableReference<Integer> s=new ScalableReference<Integer>();
		long t=System.currentTimeMillis();
		s.setReference(120, t);
		assertTrue(!s.hasExclusiveAccess(false, t));
		s.hasExclusiveAccess(true, t);
		assertTrue(s.hasExclusiveAccess(false, t));
		s.releaseExclusiveAccess(t);
		assertTrue(!s.hasExclusiveAccess(false, t));
	}
	
	public void testDirect()
	{
		final SafeInteger v=new SafeInteger();
		final UpdateRunnable r=new UpdateRunnable(v,itertaions,shortItertaions);
		TestStopWatch timer=new TestStopWatch();
		//for (int ii=0;ii<r.itno;ii++) r.performCalc();
		r.run();
		System.out.println("DIRECT="+timer.stop()+"  "+v.counter);
	}
	
	public void testMT2() throws Exception
	{
		final SafeInteger v=new SafeInteger();
		for (int ii=0;ii<cpu;ii++)
		{
			final UpdateRunnable r=new UpdateRunnable(v,itertaions, shortItertaions);
			x.execute(r);
		}
		TestStopWatch timer=new TestStopWatch();
		x.shutdown();
		x.awaitTermination(80000, TimeUnit.MILLISECONDS);
		System.out.println("MULTI "+timer.stop()+ "  "+v.counter);
		assertEquals(1,v.value);	
	}

	
	class UpdateRunnable extends IterativeRunnable<SafeInteger>
	{
		 final int m=(int)((Math.random()*100))+1;	
		 final int shortItertaions;
		 
		 public UpdateRunnable(SafeInteger v,long iterations, int shortIterations) 
		 {
			super(v,iterations);
			this.shortItertaions=shortIterations;
		}

		@Override
		protected boolean runIteration(long index, long timestamp) 
		{
			if (!obj.hasExclusiveAccess(true, timestamp))
	 		{
	 			Thread.yield();
	 			return false;
	 		}
		 	performCalc();
		 	if (obj.value!=1) System.out.println("FUCK");
		 	obj.releaseExclusiveAccess(timestamp);
		 	return true;
		}


		public void performCalc()
		 {
			 for (int ii=m;ii<(m+shortItertaions); ii++)
			 {
				 obj.value=obj.value*ii;
				 obj.value=obj.value/ii;
				 if (ii%1000==0) obj.counter++;
			 }			 
		 }
	}
	
}
