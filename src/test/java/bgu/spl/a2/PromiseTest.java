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
	private final int[] counter = new int[1];
	
	PromiseTest(){
		promise = new Promise<>();
		counter[0] = 0;
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
			Integer valueToResolve = new Integer(100);
			promise.resolve(valueToResolve);
			
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
		Integer valueToResolve = new Integer(150);
		
		boolean isResolvedBool = promise.isResolved();
		Assert.assertEquals(isResolvedBool, false);	
		
		promise.resolve(valueToResolve);
		isResolvedBool = promise.isResolved();
		Assert.assertEquals(isResolvedBool, true);	
	}

	@Test
	public final void testResolve() {
		
		Integer valueToResolve = new Integer(200);
		
		int numOfCallbacks = 5;
		for(int i=0; i<numOfCallbacks; i++){
			promise.subscribe(()->{
				int tmp = counter[0];
				counter[0] = tmp + 1;
			});
		}
		
		try {
			promise.resolve(valueToResolve);
			Assert.assertEquals(counter[0], numOfCallbacks);
			
			int getResolve = promise.get();
			Assert.assertEquals(getResolve, 200);
		} 
		catch(Exception e) {
			Assert.fail();
		}
		
		try {
			promise.resolve(valueToResolve);
		} 
		catch(IllegalStateException e) {
			
		}
		catch(Exception e) {
			Assert.fail();
		}
		
		
	}

	@Test
	public final void testSubscribe() {
		fail("Not yet implemented"); // TODO
	}
}
