package event;

import java.awt.Rectangle;

import enemy.ENM_Boss_1;
import enemy.ENM_MindGrid;
import main.GamePanel;
import main.GamePanel.LIGHTING;

public class EventHandler {
	
	GamePanel gp;
	// Events
	EventState map2Transition = new EventState();
	EventState powerdown = new EventState();
	EventState powerRestore = new EventState();
	EventState shotgunPickup = new EventState();
	EventState riflePickup = new EventState();
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		setupEvents();
	}
	
	private void setupEvents() {
		int map1 = 0;

		map2Transition.setCoordinate(map1, 93, 4, GamePanel.TILE_SIZE);
		powerdown.setCoordinate(map1, 93, 4, GamePanel.TILE_SIZE);
		powerRestore.setCoordinate(map1, 88, 1, GamePanel.TILE_SIZE);
		shotgunPickup.setCoordinate(map1, 19, 92, GamePanel.TILE_SIZE);
		riflePickup.setCoordinate(map1, 70, 44, GamePanel.TILE_SIZE);
	}
	
	public void checkEvent() {
		if (eventCanTrigger(powerdown)) {
			handlePowerdownEvent();
			powerdown.isTriggered = true;
		} 
		
		if (eventCanTrigger(map2Transition)) {
			if (powerdown.isTriggered && gp.lightingState == LIGHTING.LIGHT) {
				handleMap3Transition();
				gp.em.setMaxEntityCount(5);
			}
		} 
		
		if (eventCanTrigger(powerRestore)) {
			gp.lightingState = LIGHTING.LIGHT;
		}
		
		if (eventCanTrigger(shotgunPickup)) {
			gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_1");
			shotgunPickup.isTriggered = true;
		}
		
		if (eventCanTrigger(riflePickup)) {
			gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_2");
			riflePickup.isTriggered = true;
		}
	}
	
	private void handleMap3Transition() {
		gp.currentMap = 2;
		gp.em.addEntity(new ENM_Boss_1(gp, 50 * GamePanel.TILE_SIZE, 45 * GamePanel.TILE_SIZE));
		
		ENM_MindGrid mindGrid = new ENM_MindGrid(gp, 50 * GamePanel.TILE_SIZE, 50 * GamePanel.TILE_SIZE);
		
		int spriteWidth = mindGrid.sprite.down.safeGetSprite().getWidth();
		int spriteHeight = mindGrid.sprite.down.safeGetSprite().getHeight();
		int x = gp.worldWidth / 2 - spriteWidth / 2;
		int y = gp.worldHeight / 2 - spriteHeight + GamePanel.TILE_SIZE * 2;
		
		mindGrid.worldX = x;
		mindGrid.worldY = y;
		
		gp.em.addEntity(mindGrid);
	}
	
	private void handlePowerdownEvent() {
		gp.lightingState = LIGHTING.DARK;
		gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_3");
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
		
		return rec1.intersects(rec2);
	}

}
