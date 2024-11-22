package states;

public class StateManager {
	
	private Invincibility inv = new Invincibility(60);

	public StateManager() {
	}
	
	public void setInvincibilityDuration(int duration) {
		inv.setDuration(duration);
	}
	
	public Boolean isInvincible() {
		return inv.isInvincible;
	}
	
	public void setIsInvincible(Boolean s) {
		inv.isInvincible = s;
	}
	
	public void update() {
		inv.update();
	}

}
