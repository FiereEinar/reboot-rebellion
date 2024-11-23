package states;

public class StateManager {
	
	private final int DEFAULT_STATE_DURATION = 60;
	public State invincibility = new State(DEFAULT_STATE_DURATION);
	public State dying = new State(DEFAULT_STATE_DURATION);

	public StateManager() {
		dying.setDuration(120);
	}
	
	// updater function
	public void update() {
		invincibility.update();
		dying.update();
	}

}
