package bgu.spl.a2;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class VersionMonitorTest {
	
	public VersionMonitor versionMonitor;
	public final boolean[] boolArray = new boolean[1];
	

	@Before
	public void setUp() throws Exception {
		versionMonitor = new VersionMonitor();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetVersion() {
		int current_version = versionMonitor.getVersion();
		Assert.assertEquals(current_version , 0);
		
		versionMonitor.inc();
		current_version = versionMonitor.getVersion();
		Assert.assertEquals(current_version , 1);
	}

	@Test
	public final void testInc() {
		int final_version = 20;
		for (int i=0; i<final_version; i++) {
			versionMonitor.inc();
		}
		int current_version = versionMonitor.getVersion();
		Assert.assertEquals(current_version , final_version);
	}

	@Test
	public final void testAwait() {
		boolArray[0] = false;
		int requiredVersion = 5;
		
			Thread t1 = new Thread( ()->{
				try {
					versionMonitor.await(requiredVersion);
				}
				catch(InterruptedException e) {
					//ignore
				}
				boolArray[0] = true;
			});	
			
			t1.start();
				
		Assert.assertEquals(boolArray[0], false);
		
		for(int i=0; i<requiredVersion-1; i++) {
			versionMonitor.inc();
			Assert.assertEquals(boolArray[0], false);
		}
		
		versionMonitor.inc();
		try{
			t1.join();
		}
		catch(InterruptedException e) {
			//ignore
		}
		Assert.assertEquals(boolArray[0], true);
		
			Thread t2 = new Thread( ()->{
				try {
					versionMonitor.await(requiredVersion);
				}
				catch(InterruptedException e) {
					//ignore
				}
				boolArray[0] = false;
			});	
	
			t2.start();
		try{
			t2.join();
		}
		catch(InterruptedException e) {
			//ignore
		}
		Assert.assertEquals(boolArray[0], false);
	}

}
