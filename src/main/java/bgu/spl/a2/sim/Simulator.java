/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;

import com.google.gson.Gson;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.Promise;
import bgu.spl.a2.callback;
import bgu.spl.a2.sim.actions.SubmittableActionBoxFactory;
/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

	private static boolean DEBUG_MODE = false;
	
	private static String JSONinput;
	private static ActorThreadPool actorThreadPool;
	private static Warehouse warehouse;
	private static SimulatorInput simInput;
	private static List<Promise<?>> phase1;
	private static List<Promise<?>> phase2;
	private static List<Promise<?>> phase3;
	private static CountDownLatch countDownLatch1;
	private static CountDownLatch countDownLatch2;
	private static CountDownLatch countDownLatch3;
	
	/**
	* Begin the simulation Should not be called before attachActorThreadPool()
	*/
    public static void start(){
    	phase1 = simInput.getPromiseListPhase1();
    	phase2 = simInput.getPromiseListPhase2();	    	
    	phase3 = simInput.getPromiseListPhase3();  
    	countDownLatch1 = new CountDownLatch(phase1.size());
    	countDownLatch2 = new CountDownLatch(phase2.size());
    	countDownLatch3 = new CountDownLatch(phase3.size());
    	
    	actorThreadPool.start();
    	
    	runPhase1();

    	try{
    		countDownLatch1.await();
    	}
    	catch(InterruptedException e) {}
    	
    	runPhase2();
    	
    	try{
    		countDownLatch2.await();
    	}
    	catch(InterruptedException e) {}
    	
    	runPhase3();
    	
    	try{
    		countDownLatch3.await();
    	}
    	catch(InterruptedException e) {}
    	
    	endPhase();
    }
    
    /**
     * submit actions in phase1
     */
    private static void runPhase1() {	
    	subscribeToPromiseList(phase1, ()->{
    		countDownLatch1.countDown();
    		});
    	submitAllActions(simInput.getPhase1()); 
    }
    
    /**
     * submit actions in phase2 only if the actions in phase1 are resolved
     */
    private static void runPhase2() {   	
    	subscribeToPromiseList(phase2, ()->{
    		countDownLatch2.countDown();
    		});
    	submitAllActions(simInput.getPhase2());
    }
    
    /**
     * submit actions in phase3 only if the actions in phase2 are resolved
     */
    private static void runPhase3() {    	
    	subscribeToPromiseList(phase3, ()->{
    		countDownLatch3.countDown();
    		});
    	submitAllActions(simInput.getPhase3());
    }
    
    /**
     * shutdown the threadpool if phase3 is resolved and save result in file
     */
    private static void endPhase() {
		HashMap<String, PrivateState> SimulationResult;
		SimulationResult = end();
		try {
		FileOutputStream fout = new FileOutputStream("result.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(SimulationResult);
		oos.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}	
	}
    
    
    /**
     * Submits all actions in a given SubmittableActionBoxFactory[] array to the threadpool
     * @param SubmittableActionBoxFactory[] array
     */
    private static void submitAllActions(SubmittableActionBoxFactory[] array) {
    	for(SubmittableActionBoxFactory factory : array) {
    		factory.getPromiseAndSubmit(actorThreadPool);
    	}
    }
    
    /**
     * Verifies if all promises in a list are resolved
     * @param promiseList
     * @return TRUE if all are resolved, FALSE otherwise
     */
    private static boolean allPromisesAreResolved(List<Promise<?>> promiseList) {
    	boolean ret = true;
    	for(Promise<?> promise : promiseList) {
    		if(!promise.isResolved()) {
    			ret = false;
    		}
    	}
    	return ret;
    }
    
    
    /**
     * Subscribes a callback to all the promises in the the list
     * @param promiseList
     * @param callback
     */
    private static void subscribeToPromiseList(List<Promise<?>> promiseList, callback callback) {
    	for(Promise<?> promise : promiseList) {
    		promise.subscribe(callback);
    	}
    }
    
	/**
	* attach an ActorThreadPool to the Simulator, this ActorThreadPool will be used to run the simulation
	* 
	* @param myActorThreadPool - the ActorThreadPool which will be used by the simulator
	*/
	public static void attachActorThreadPool(ActorThreadPool myActorThreadPool){
		actorThreadPool = myActorThreadPool;
	}
	
	/**
	* attach a Warehouse to the Simulator, this Warehouse will be used to run the simulation
	* 
	* @param myWarehouse - the Warehouse which will be used by the simulator
	*/
	public static void attachWarehouse(Warehouse myWarehouse) {
		warehouse = myWarehouse;
	}
	
	/**
	* shut down the simulation
	* returns list of private states
	*/
	public static HashMap<String, PrivateState> end(){
		try {
			actorThreadPool.shutdown();
		} catch (InterruptedException e) {}
	
		HashMap<String, PrivateState> ret = new HashMap<>();
		Map<String,PrivateState> map = actorThreadPool.getActors();
		for(String actorId : map.keySet()) {
			ret.put(actorId, map.get(actorId));
		}	
		
		return ret;
	}
	
	/**
	 * reads from txt file to string
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private static String readFile(String path) throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded);
	}
	

	public static Warehouse getWarehouse() {
		return warehouse;
	}
	
	
	public static void main(String [] args){
		String path = args[0];
		
		
		for(int i=0; i<100000; i++) {
			System.out.println(i);
			
		try {
			JSONinput = readFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		
		//create object from JSON
		simInput = gson.fromJson(JSONinput, SimulatorInput.class);
		
		//create threadpool
		ActorThreadPool newPool = new ActorThreadPool(simInput.getThreads());
		attachActorThreadPool(newPool);
		
		//create warehouse
		Warehouse newWarehouse = new Warehouse(simInput.getComputers());
		attachWarehouse(newWarehouse);
	
		start();
		System.out.println("end");
		}
	}
}
