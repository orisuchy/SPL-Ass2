/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;


/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

	
	public static ActorThreadPool actorThreadPool;
	public static Warehouse warehouse;
	
	private static String jsonInput;
	private static Action[] phase1;
	private static Action[] phase2;
	private static Action[] phase3;
	
	/**
	* Begin the simulation Should not be called before attachActorThreadPool()
	*/
    public static void start(){
		//TODO: replace method body with real implementation
		throw new UnsupportedOperationException("Not Implemented Yet.");
    }
    
    
	
	/**
	* create the phase1,phase2,phase3 arrays from the jsonInput field.
	*/
    private static void generatePhaseArraysFromJSON() {
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
		Scanner scanner = new Scanner(System.in);
		jsonInput = scanner.nextLine();
		scanner.close();
		
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
