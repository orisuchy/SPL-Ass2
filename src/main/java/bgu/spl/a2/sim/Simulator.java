/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.Gson;

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
		//TODO: replace method body with real implementation
		throw new UnsupportedOperationException("Not Implemented Yet.");
	}
	
	
	public static int main(String [] args){
		Scanner scanner = new Scanner(System.in);
		jsonInput = scanner.nextLine();
		scanner.close();
		
		
		
		//TODO: replace method body with real implementation
		throw new UnsupportedOperationException("Not Implemented Yet.");
	}
}
