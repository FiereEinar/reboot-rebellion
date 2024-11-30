package states;

public class StateManager {
	
	protected final int DEFAULT_STATE_DURATION = 60;
	public State invincibility = new State(DEFAULT_STATE_DURATION);
	public State dying = new State(DEFAULT_STATE_DURATION);
	public State attacking = new State(DEFAULT_STATE_DURATION);
	public State attacked = new State(DEFAULT_STATE_DURATION);

	public StateManager() {
		dying.setDuration(120);
	}
	
	// REMINDERS: add state here if you want to add another state
	// this is skill issue, poor code structure fr
	public Boolean shouldResetSprites() {
		if (
			invincibility.shouldResetSprites() ||
			dying.shouldResetSprites() ||
			attacking.shouldResetSprites() || 
			attacked.shouldResetSprites()
		) return true;
		
		return false;
	}
	
	public void offShouldResetSprites() {
		invincibility.offShouldResetSprites();
		dying.offShouldResetSprites();
		attacking.offShouldResetSprites();
		attacked.offShouldResetSprites();
	}
	
	// updater function
	public void update() {
		invincibility.update();
		dying.update();
		attacking.update();
		attacked.update();
	}

}
