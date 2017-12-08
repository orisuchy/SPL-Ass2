package bgu.spl.a2;

public class PrivateStateMock extends PrivateState {
	
	private int _counter;
	private  String _actorName;
	
	PrivateStateMock(String actorName){
		_counter = 0;
		_actorName = actorName;
	}
	
	public synchronized int getCounter() {
		return getCounter(false);
	}
	
	public synchronized int getCounter(boolean printOut) {
		if(printOut) System.out.println(_actorName + ": current count is" + _counter);
		return _counter;
	}
	
	public void increaseBy(int amount) {
		increaseBy(amount,false);
	}
	
	public void increaseBy(int amount, boolean printOut) {
		for(int i=0; i<amount; i++) {
			_counter++;
			if(printOut) {
				System.out.println(_actorName +": " +_counter);
			}
		}
	}	
}
