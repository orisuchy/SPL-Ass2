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
import java.util.HashMap;
import java.util.List;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.Promise;
import bgu.spl.a2.callback;
import bgu.spl.a2.sim.actions.SubmittableActionBoxFactory;
/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

	private static String JSONinput;
	private static ActorThreadPool actorThreadPool;
	private static Warehouse warehouse;
	private static SimulatorInput simInput;
	private static List<Promise<?>> phase1;
	private static List<Promise<?>> phase2;
	private static List<Promise<?>> phase3;
	
	/**
	* Begin the simulation Should not be called before attachActorThreadPool()
	*/
    public static void start(){
    	phase1 = simInput.getPromiseListPhase1();
    	phase2 = simInput.getPromiseListPhase2();	    	
    	phase3 = simInput.getPromiseListPhase3();   	
    	runPhase1();		
    }
	
    
    private static void runPhase1() {
    	subscribeToPromiseList(phase1, ()->{
    		runPhase2();
    		});
    	submitAllActions(simInput.getPhase1()); 	
    }
    
    
    private static void runPhase2() {
    	if(allPromisesAreResolved(phase1)) {
    		return;
    	}
    	subscribeToPromiseList(phase2, ()->{
    		runPhase3();
    		});
    	submitAllActions(simInput.getPhase2());
    }
    
    
    private static void runPhase3() {
    	if(allPromisesAreResolved(phase2)) {
    		return;
    	}
    	subscribeToPromiseList(phase3, ()->{
    		endPhase();
    		});
    	submitAllActions(simInput.getPhase3());
    }
    
    
    private static void endPhase() {
    	if(allPromisesAreResolved(phase3)) {
    		return;
    	}
  	
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
    
    
    
    private static void submitAllActions(SubmittableActionBoxFactory[] array) {
    	for(SubmittableActionBoxFactory factory : array) {
    		factory.getPromiseAndSubmit(actorThreadPool);
    	}
    }
    
    private static boolean allPromisesAreResolved(List<Promise<?>> promiseList) {
    	boolean ret = true;
    	for(Promise<?> promise : promiseList) {
    		if(!promise.isResolved()) {
    			ret = false;
    		}
    	}
    	return ret;
    }
    
    
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
	
	
	public static void attachWarehouse(Warehouse myWarehouse) {
		warehouse = myWarehouse;
	}
	
	/**
	* shut down the simulation
	* returns list of private states
	*/
	public static HashMap<String,PrivateState> end(){
		
		try {
			actorThreadPool.shutdown();
		} catch (InterruptedException e) {
			//do nothing
		}
		
		//TODO - will this work?
		return (HashMap<String,PrivateState>)actorThreadPool.getActors();	
	}
	
	
	public static int main(String [] args){
		JSONinput = args[0];
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
		
		return 0;
	}
	
	public static Warehouse getWarehouse() {
		return warehouse;
	}
}
