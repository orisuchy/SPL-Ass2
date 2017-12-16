/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.Promise;
import bgu.spl.a2.callback;
import bgu.spl.a2.sim.actions.SubmittableActionBox;
import bgu.spl.a2.sim.actions.SubmittableActionBoxFactory;
/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

	public static String JSONinput;
	public static ActorThreadPool actorThreadPool;
	public static Warehouse warehouse;
	public static SimulatorInput simInput;
	public static List<Promise<?>> phase1;
	private static boolean phase1Finished;
	public static List<Promise<?>> phase2;
	private static boolean phase2Finished;
	public static List<Promise<?>> phase3;
	private static boolean phase3Finished;
	
	/**
	* Begin the simulation Should not be called before attachActorThreadPool()
	*/
    public static void start(){
    	
		Gson gson = new Gson();
		simInput = gson.fromJson(JSONinput, SimulatorInput.class);
		
		
		
		
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
    
    

    
	/**
	* attach an ActorThreadPool to the Simulator, this ActorThreadPool will be used to run the simulation
	* 
	* @param myActorThreadPool - the ActorThreadPool which will be used by the simulator
	*/
	public static void attachActorThreadPool(ActorThreadPool myActorThreadPool){
		actorThreadPool = myActorThreadPool;
	}
	
	
	/**
	* shut down the simulation
	* returns list of private states
	*/
	public static HashMap<String,PrivateState> end(){
		
		try {
			actorThreadPool.shutdown();
		} catch (InterruptedException e) {
		}
		
		//TODO - Fingers crossed this works
		return (HashMap<String,PrivateState>)actorThreadPool.getActors();
		
	}
	
	
	public static int main(String [] args){
		String JSONinput = args[0];
		start();
		
		//TODO - should I wait or something? I am assuming the shutdown causes a wait...
		
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
		return 0;
	}
	
	
	public static void setWarehouse(Warehouse myWarehouse) {
		warehouse = myWarehouse;
	}
	
	public static Warehouse getWarehouse() {
		return warehouse;
	}
}
