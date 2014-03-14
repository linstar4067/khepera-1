package behaviour;

import java.util.ArrayList;

<<<<<<< HEAD
public abstract class Behaviour implements Comparable<Behaviour>{
	
	public int priority;
	private int currentState = 0;
	private ArrayList<State> states;
	
	public Behaviour(int priority) {
		this.priority = priority;
		states = new ArrayList<State>();
	}
	
	public void addState(State state) {
		states.add(state);
	}	
	
	public void doWork() {
		int transition = states.get(currentState).shouldTransition(); 
		
		if (transition != 0) {
			//TODO: make this work
			return;
		}
		
		states.get(currentState).doWork();
	}

	@Override
	public int compareTo(Behaviour o) {
		return o.priority - priority;	
	}
	
	public abstract boolean shouldRun();
=======
public abstract class Behaviour implements Comparable<Behaviour> {

  public int priority;
  private int currentState = 0;
  private ArrayList<State> states;

  public Behaviour(int priority) {
    this.priority = priority;
    states = new ArrayList<State>();
  }

  public void addState(State state) {
    states.add(state);
  }

  public void doWork() {
    int transition = states.get(currentState).shouldTransition();

    if (transition != 0) {
      // TODO: make this work
    }

    states.get(currentState).doWork();
  }

  @Override
  public int compareTo(Behaviour o) {
    return o.priority - priority;
  }

  public abstract boolean shouldRun();
>>>>>>> 2e0886508187a70e4b56394f442ee68da1ab1be1
}
