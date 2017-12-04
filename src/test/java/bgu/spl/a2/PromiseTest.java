package bgu.spl.a2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PromiseTest {

//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
	
	private Promise<Integer> promise;
	
	
	PromiseTest(){
		promise = new Promise<>();
	}
	

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		promise = new Promise<>();
	}

	@Test
	public final void testGet() {
		try{
			promise.get();
			Assert.fail();	
		} 
		catch(IllegalStateException e) {
			promise.resolve(100);
			
			try {
				int i = promise.get();
				Assert.assertEquals(i,100);
			}
			catch(Exception ex) {
				Assert.fail();
			}
		}
		catch(Exception e) {
			Assert.fail();
		}
	}

	@Test
	public final void testIsResolved() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testResolve() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSubscribe() {
		fail("Not yet implemented"); // TODO
	}

}
