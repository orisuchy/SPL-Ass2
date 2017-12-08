package bgu.spl.a2;

class ActionMock<R> extends Action<R> {
	
	int _amount;
	boolean _print;
	
	ActionMock(int amount){
		this(amount, false);
	}
	
	ActionMock(int amount, boolean print){
		_amount = amount;
		_print = print;
	}
	
	protected void start() {
		if(_actorState instanceof PrivateStateMock) {
			((PrivateStateMock) _actorState).increaseBy(_amount, _print);
			((PrivateStateMock) _actorState).getCounter();
		}
		else {
			throw new IllegalArgumentException("Private state should be mock type");
		}
	}
	
}
