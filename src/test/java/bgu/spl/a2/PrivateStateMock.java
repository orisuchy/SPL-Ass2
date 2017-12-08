package bgu.spl.a2;

public class PrivateStateMock extends PrivateState {
	
	private int _counter;
	
	PrivateStateMock(){
		_counter = 0;
	}
	
	public synchronized int getCounter() {
		System.out.println("current count is" + _counter);
		return _counter;
	}
	
	public void increaseBy(int amount) {
		increaseBy(amount,false);
	}
	
	public void increaseBy(int amount, boolean printOut) {
		for(int i=0; i<amount; i++) {
			_counter++;
			if(printOut) {
				System.out.println(_counter);
			}
		}
	}	
}
