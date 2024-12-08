package event;

import java.awt.Rectangle;

import main.GamePanel;
import main.GamePanel.LIGHTING;

public class EventHandler {
	
	GamePanel gp;
	// Events
	EventState map2Transition = new EventState();
	EventState powerdown = new EventState();
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		setupEvents();
	}
	
	private void setupEvents() {
		int map1 = 0;
//		
//		map2Transition.setCoordinate(map1, 1500, 50, GamePanel.TILE_SIZE);
		powerdown.setCoordinate(map1, 93, 4, GamePanel.TILE_SIZE);
	}
	
	public void checkEvent() {
//		if (eventCanTrigger(map2Transition)) {
//			handleMap2TransitionTrigger();
//		}
		
		if (eventCanTrigger(powerdown)) {
			if (!powerdown.isTriggered) {
				handlePowerdownEvent();
			} else {
				
			}
		}
	}
	
	private void handlePowerdownEvent() {
//		gp.currentMap = 2;
		gp.lightingState = LIGHTING.DARK;
		gp.em.setMaxEntityCount(3);
	}
	
	private void handleMap2TransitionTrigger() {
		map2Transition.isTriggered = true;
		gp.currentMap = 1;
		gp.player.worldX = 100;
		gp.player.worldY = 300;
		gp.lightingState = LIGHTING.DARK;
	}
	
	private Boolean eventCanTrigger(EventState event) {
		return !event.isTriggered && eventShouldFire(event) && event.inCorrectMap(gp.currentMap);
	}

	private Boolean eventShouldFire(Rectangle rec2) {
		Rectangle rec1 = gp.player.getSolidAreaRelativeToWorld();
		
		if (rec1.intersects(rec2)) return true;
			
		return false;
	}

}
