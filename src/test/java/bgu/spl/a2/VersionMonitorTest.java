package bgu.spl.a2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

public class VersionMonitorTest {
	
	private VersionMonitor versionMonitor;
	
	VersionMonitorTest(){
		VersionMonitor versionMonitor = new VersionMonitor();
	}

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
		fail("Not yet implemented"); // TODO
	}

}
