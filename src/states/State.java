package states;

public class State {

	private Boolean isOn = false;
	private Boolean shouldResetSprites = false;
	private Boolean isTriggered = false;
	private int time;
	private int counter = 0;

	public State(int duration) {
		this.time = duration;
	}
	
	public void setDuration(int duration) {
		this.time = duration;
	}
	
	public void setState(Boolean val) {
		if (val == isOn) return;
		
		if (val) shouldResetSprites = true;
		this.isOn = val;
	}
	
	public void offShouldResetSprites() {
		shouldResetSprites = false;
	}
	
	public Boolean shouldResetSprites() {
		return shouldResetSprites;
	}
	
	public Boolean getState() {
		return this.isOn;
	}
	
	public Boolean isTriggered() {
		return this.isTriggered;
	}
	
	public int getStateDuration() {
		return this.time;
	}
	
	public int getCounter() {
		return this.counter;
	}
	
	public void resetIsTriggered() {
		this.isTriggered = false;
	}
	
	public void reset() {
		counter = 0;
		isOn = false;
	}
	
	public void update() {
		if (getState()) {
			counter++;
			
			if (counter == time) {
				counter = 0;
				isOn = false;
				isTriggered = true;
			}
		}
	}

}
