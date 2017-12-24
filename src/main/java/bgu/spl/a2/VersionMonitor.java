package bgu.spl.a2;

//TODO - delete when done and remove simOut!!
import bgu.spl.a2.sim.Simulator;

/**
 * Describes a monitor that supports the concept of versioning - its idea is
 * simple, the monitor has a version number which you can receive via the method
 * {@link #getVersion()} once you have a version number, you can call
 * {@link #await(int)} with this version number in order to wait until this
 * version number changes.
 *
 * you can also increment the version number by one using the {@link #inc()}
 * method.
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */
public class VersionMonitor {
	
	private int _version;
	
	
	public VersionMonitor() {
		_version = 0;
	}
	
	/**
	 * synchronized to ensure visibility and force cache flush
	 * @return the current version int value
	 */
    public synchronized int getVersion() {
    	return _version;
    }

	/**
	 * synchronized to ensure no overriding of values
	 * returned value from {@link #getVersion()} increases by 1 after calling
	 * this method
	 */
    public synchronized void inc() {
    	_version++;
    	this.notifyAll();
    	Simulator.simOut("VERSION INCREASED TO " + _version);
    }
    
	/**
	 * Causes the current thread to wait until the current version reaches a version value
	 * @param version
	 * 				- the target version that must be reached
	 */
    public synchronized void await(int version) throws InterruptedException {
    	//wait until _version >= version
    	while(getVersion()<version) {
    		this.wait();
    	}
    }
}
