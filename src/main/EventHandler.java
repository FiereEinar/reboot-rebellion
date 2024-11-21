package main;

import java.awt.Rectangle;

import event.EventState;
import object.OBJ_Gun;

public class EventHandler {
	
	GamePanel gp;
	
	// Events
	EventState gunTrigger = new EventState();
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		setupEvents();
	}
	
	private void setupEvents() {
		OBJ_Gun gun = (OBJ_Gun)gp.om.getObjects().get(gp.om.GUN_INDEX);
		
		gunTrigger.setCoordinate(gun.worldX, gun.worldY, gp.tileSize);
	}
	
	public void checkEvent() {
		if (!gunTrigger.isTriggered && eventShouldFire(gunTrigger)) {
			handleGunInteraction();
		}
	}
	
	private Boolean eventShouldFire(Rectangle rec2) {
		Boolean shouldTrigger = false;
		
		Rectangle rec1 = gp.player.getSolidAreaRelativeToWorld();
		
		if (rec1.intersects(rec2)) return true;
			
		return shouldTrigger;
	}
	
	private void handleGunInteraction() {
		gunTrigger.isTriggered = true;
		System.out.println("interacted with a gun");
	}

}
