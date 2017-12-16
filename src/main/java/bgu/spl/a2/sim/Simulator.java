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
import java.util.HashMap;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.actions.SubmittableActionBoxFactory;
/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

	public static String JSONinput;
	public static ActorThreadPool actorThreadPool;
	public static Warehouse warehouse;
	
	@SerializedName(value = "Phase 1", alternate = "phase1")
	private static SubmittableActionBoxFactory[] phase1;
	private boolean phase1Finished;
	
	@SerializedName(value = "Phase 2", alternate = "phase2")
	private static SubmittableActionBoxFactory[] phase2;
	private static boolean phase2Finished;
	
	@SerializedName(value = "Phase 3", alternate = "phase3")
	private static SubmittableActionBoxFactory[] phase3;
	private static boolean phase3Finished;
	
	/**
	* Begin the simulation Should not be called before attachActorThreadPool()
	*/
    public static void start(){
    	
		Gson gson = new Gson();
    	
		//TODO: replace method body with real implementation
		throw new UnsupportedOperationException("Not Implemented Yet.");
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
	
	public static Warehouse getWarehouse() {
		return warehouse;
	}
}
