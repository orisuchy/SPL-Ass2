package bgu.spl.a2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ActorThreadPoolTest {
	
	ActorThreadPool _pool; 

	@Before
	public void setUp() throws Exception {
		_pool = new ActorThreadPool(5);
	}

	@After
	public void tearDown() throws Exception {
		_pool.shutdown();
	}

	@Test
	public final void testSubmit() {
//		
//		int amount = 50;
//		
//		Action<Integer> actionMock = new ActionMock<>(amount, true);
//		PrivateStateMock stateMock = new PrivateStateMock();
//		_pool.submit(actionMock, "actor1", stateMock);
//		
//		_pool.start();
//		
//		try{
//			Thread.sleep(1000);
//		}
//		catch(InterruptedException e){}
//		
//		Assert.assertEquals(amount, stateMock.getCounter());
	}

	@Test
	public final void testShutdown() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testStart() {
		int amount = 50;
		int numOfActions = 4;
		int numOfActors = 3;
		
		PrivateStateMock[] actors = new PrivateStateMock[numOfActors];
		
		for(int j=0; j<numOfActions; j++) {
			actors[j] = new PrivateStateMock("actor" + j);
			for(int i=0; i<numOfActions; i++) {
				Action<Integer> actionMock = new ActionMock<>(amount, true);
				_pool.submit(actionMock, "actor" + j, actors[j]);
			}
		}
		
		_pool.start();
		try{
			Thread.sleep(5000);
		}
		catch(InterruptedException e){}
		
		for(int j=0; j<numOfActions; j++) {
			Assert.assertEquals(amount*4, actors[j].getCounter());
		}	
	}

}
