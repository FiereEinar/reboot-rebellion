package main;

import java.awt.Rectangle;

import entity.Vector2;
import object.OBJ_Gun;

public class EventHandler {
	
	GamePanel gp;
	private int eventTriggerAreaSize = 5;
	
	// Events
	EventState gunTrigger = new EventState();
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		setupEvents();
	}
	
	private void setupEvents() {
		OBJ_Gun gun = (OBJ_Gun)gp.om.getObjects().get(gp.om.GUN_INDEX);
		
		gunTrigger.x = gun.worldX + gp.tileSize / 2 - eventTriggerAreaSize;
		gunTrigger.y = gun.worldY + gp.tileSize / 2 - eventTriggerAreaSize;
	}
	
	public void checkEvent() {
		if (!gunTrigger.isTriggered && eventShouldFire(gunTrigger.x, gunTrigger.y)) {
			handleGunInteraction();
		}
	}
	
	private Boolean eventShouldFire(int x, int y) {
		Boolean shouldTrigger = false;
		
		Rectangle rec1 = gp.player.getSolidAreaRelativeToWorld();
		Rectangle rec2 = new Rectangle(x, y, eventTriggerAreaSize, eventTriggerAreaSize);
		
		if (rec1.intersects(rec2)) return true;
			
		return shouldTrigger;
	}
	
	private void handleGunInteraction() {
		gunTrigger.isTriggered = true;
		System.out.println("interacted with a gun");
	}
	
	class EventState extends Vector2 {
		
		public Boolean isTriggered = false;
		
		public EventState() {
		}
	}

}
