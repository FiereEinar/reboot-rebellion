package event;

import java.awt.Rectangle;

import main.GamePanel;

public class EventState extends Rectangle {

	private static final long serialVersionUID = 1L;

	public Boolean isTriggered = false;
	private int eventTriggerAreaSize = GamePanel.TILE_SIZE;
	private int mapToTrigger;

	public EventState() {
		this.width = this.height = eventTriggerAreaSize;
	}
	
	public void restart() {
		isTriggered = false;
	}
	
	public void setCoordinate(int map, int col, int row, int size) {
		int worldX = col * GamePanel.TILE_SIZE;
		int worldY = row * GamePanel.TILE_SIZE;
		
		this.x = worldX + size / 2 - eventTriggerAreaSize;
		this.y = worldY + size / 2 - eventTriggerAreaSize;
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
