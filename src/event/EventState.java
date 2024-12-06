package event;

import java.awt.Rectangle;

public class EventState extends Rectangle {

	private static final long serialVersionUID = 1L;

	public Boolean isTriggered = false;
	private int eventTriggerAreaSize = 5;
	private int mapToTrigger;

	public EventState() {
		this.width = this.height = eventTriggerAreaSize;
	}
	
	public void setCoordinate(int map, int x, int y, int size) {
		this.x = x + size / 2 - eventTriggerAreaSize;
		this.y = y + size / 2 - eventTriggerAreaSize;
		this.mapToTrigger = map;
	}
	
	public void setEventTriggerAreaSize(int size) {
		this.eventTriggerAreaSize = size;
		this.width = this.height = size;
	}

	public Boolean inCorrectMap(int currentMap) {
		return mapToTrigger == currentMap;
	}

}
