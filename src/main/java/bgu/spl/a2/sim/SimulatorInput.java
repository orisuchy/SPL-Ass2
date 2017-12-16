package bgu.spl.a2.sim;

import com.google.gson.annotations.SerializedName;

import bgu.spl.a2.sim.actions.SubmittableActionBoxFactory;

class SimulatorInput {
	
	@SerializedName(value = "threads", alternate = "Threads")
	private int threads;

	@SerializedName(value = "Computers", alternate = "computers")
	private Computer[] Computers;
	
	@SerializedName(value = "Phase 1", alternate = "phase1")
	private SubmittableActionBoxFactory[] phase1;
	
	@SerializedName(value = "Phase 2", alternate = "phase2")
	private SubmittableActionBoxFactory[] phase2;
	
	@SerializedName(value = "Phase 3", alternate = "phase3")
	private SubmittableActionBoxFactory[] phase3;

	
	/**
	 * Constructor
	 * @param threads
	 * @param computers
	 * @param phase1
	 * @param phase2
	 * @param phase3
	 */
	public SimulatorInput(int threads, Computer[] computers, SubmittableActionBoxFactory[] phase1,
			SubmittableActionBoxFactory[] phase2, SubmittableActionBoxFactory[] phase3) {
		this.threads = threads;
		Computers = computers;
		this.phase1 = phase1;
		this.phase2 = phase2;
		this.phase3 = phase3;
	}


	public int getThreads() {
		return threads;
	}


	public void setThreads(int threads) {
		this.threads = threads;
	}


	public Computer[] getComputers() {
		return Computers;
	}


	public void setComputers(Computer[] computers) {
		Computers = computers;
	}


	public SubmittableActionBoxFactory[] getPhase1() {
		return phase1;
	}


	public void setPhase1(SubmittableActionBoxFactory[] phase1) {
		this.phase1 = phase1;
	}


	public SubmittableActionBoxFactory[] getPhase2() {
		return phase2;
	}


	public void setPhase2(SubmittableActionBoxFactory[] phase2) {
		this.phase2 = phase2;
	}


	public SubmittableActionBoxFactory[] getPhase3() {
		return phase3;
	}


	public void setPhase3(SubmittableActionBoxFactory[] phase3) {
		this.phase3 = phase3;
	}
}
