package states;

public class StateManager {
	
	private final int DEFAULT_STATE_DURATION = 120;
	public State invincibility = new State(DEFAULT_STATE_DURATION);
	public State dying = new State(DEFAULT_STATE_DURATION);

	public StateManager() {
	}
	
	// updater function
	public void update() {
		invincibility.update();
		dying.update();
	}

}
