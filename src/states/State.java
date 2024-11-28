package states;

public class State {

	private Boolean isOn = false;
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
		this.isOn = val;
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
	
	public void update() {
		if (isOn) {
			counter++;
			
			if (counter == time) {
				counter = 0;
				isOn = false;
				isTriggered = true;
			}
		}
	}

}
