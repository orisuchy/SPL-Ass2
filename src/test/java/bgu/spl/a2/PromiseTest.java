package bgu.spl.a2;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PromiseTest {

	private Promise<Integer> promise;
	public final int[] counter = new int[1];
	

	@Before
	public void setUp() throws Exception {
		promise = new Promise<>();
		counter[0] = 0;
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
		
		Integer valueToResolve = new Integer(250);
		
		int numOfCallbacks = 10;
		for(int i=0; i<numOfCallbacks; i++){
			promise.subscribe(()->{
				int tmp = counter[0];
				counter[0] = tmp + 1;
			});
		}
		
		try {
			promise.resolve(valueToResolve);
			Assert.assertEquals(counter[0], numOfCallbacks);
		} 
		catch(Exception e) {
		}
		
		int jump = 2000;
		
		promise.subscribe(()->{
			int tmp = counter[0];
			counter[0] = tmp + jump;
		});
		
		Assert.assertEquals(counter[0], numOfCallbacks + jump);
	}
}
