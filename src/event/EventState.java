package event;

import java.awt.Rectangle;

public class EventState extends Rectangle {

	private static final long serialVersionUID = 1L;

	public Boolean isTriggered = false;
	private int eventTriggerAreaSize = 5;

	public EventState() {
		this.width = this.height = eventTriggerAreaSize;
	}
	
	public void setCoordinate(int x, int y, int size) {
		this.x = x + size / 2 - eventTriggerAreaSize;
		this.y = y + size / 2 - eventTriggerAreaSize;
	}
	
	public void setEventTriggerAreaSize(int size) {
		this.eventTriggerAreaSize = size;
		this.width = this.height = size;
	}

}
