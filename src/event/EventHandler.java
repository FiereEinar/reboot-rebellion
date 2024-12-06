package event;

import java.awt.Rectangle;

import main.GamePanel;

public class EventHandler {
	
	GamePanel gp;
	
	// Events
	EventState map2Transition = new EventState();
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		setupEvents();
	}
	
	private void setupEvents() {
		map2Transition.setCoordinate(1500, 50, GamePanel.TILE_SIZE);
	}
	
	public void checkEvent() {
		if (!map2Transition.isTriggered && eventShouldFire(map2Transition)) {
			handleMap2TransitionTrigger();
		}
	}
	
	private void handleMap2TransitionTrigger() {
		System.out.println("Map transition triggered");
		map2Transition.isTriggered = true;
		gp.currentMap = 1;
		gp.player.worldX = 100;
		gp.player.worldY = 300;
	}

	private Boolean eventShouldFire(Rectangle rec2) {
		Rectangle rec1 = gp.player.getSolidAreaRelativeToWorld();
		
		if (rec1.intersects(rec2)) return true;
			
		return false;
	}

}
