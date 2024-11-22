package states;

public class Invincibility {
	
	public Boolean isInvincible = false;
	private int time;
	private int counter = 0;

	public Invincibility(int duration) {
		this.time = duration;
	}
	
	public void setDuration(int duration) {
		this.time = duration;
	}
	
	public void update() {
		if (isInvincible) {
			counter++;
			
			if (counter == time) {
				counter = 0;
				isInvincible = false;
			}
		}
	}

}
