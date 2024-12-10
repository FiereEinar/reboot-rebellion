package event;

import java.awt.Rectangle;

import enemy.ENM_Boss_1;
import enemy.ENM_MindGrid;
import main.GamePanel;
import main.GamePanel.LIGHTING;
import main.Objective.OBJECTIVE_TYPE;
import object.OBJ_Key;
import main.Objective;
import main.Sound;

public class EventHandler {
	
	GamePanel gp;
	// Events
	EventState map1Transition = new EventState();
	EventState map2Transition = new EventState();
	EventState map3Transition = new EventState();
	EventState powerdown = new EventState();
	EventState shotgunPickup = new EventState();
	EventState riflePickup = new EventState();
	EventState sniperPickup = new EventState();
	EventState machineGunPickup = new EventState();
	EventState powerSupplyRoomDoor = new EventState();
	EventState keyPickup = new EventState();
	EventState openPowerSupplyDoor = new EventState();
	EventState powerRestore = new EventState();
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		setupEvents();
	}
	
	private void setupEvents() {
		int map1 = 0;
		int map2 = 1;

		map1Transition.setCoordinate(map2, 5, 0, GamePanel.TILE_SIZE);
		map2Transition.setCoordinate(map1, 5, 0, GamePanel.TILE_SIZE);
		map3Transition.setCoordinate(map1, 93, 4, GamePanel.TILE_SIZE);
		powerdown.setCoordinate(map1, 93, 4, GamePanel.TILE_SIZE);
		shotgunPickup.setCoordinate(map1, 19, 92, GamePanel.TILE_SIZE);
		riflePickup.setCoordinate(map1, 70, 44, GamePanel.TILE_SIZE);
		
		sniperPickup.setCoordinate(map2, 74, 48, GamePanel.TILE_SIZE);
		machineGunPickup.setCoordinate(map2, 14, 38, GamePanel.TILE_SIZE);
		powerSupplyRoomDoor.setCoordinate(map2, 96, 95, GamePanel.TILE_SIZE);
		keyPickup.setCoordinate(map2, 32, 81, GamePanel.TILE_SIZE);
		openPowerSupplyDoor.setCoordinate(map2, 96, 95, GamePanel.TILE_SIZE);
		powerRestore.setCoordinate(map2, 94, 86, GamePanel.TILE_SIZE);
	}
	
	public void checkEvent() {
		if (eventCanTrigger(powerdown)) {
			handlePowerdownEvent();
		} 
		
		if (eventCanTrigger(map1Transition)) {
			if (gp.lightingState == LIGHTING.LIGHT) {
				gp.currentMap = 0;
				map1Transition.isTriggered = true;
				gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_11");
			}
		} 
		
		if (eventCanTrigger(map2Transition)) {
			if (powerdown.isTriggered && gp.lightingState == LIGHTING.DARK) {
				handleMap2Transition();
			}
		} 
		
		if (eventCanTrigger(map3Transition)) {
			if (powerdown.isTriggered && gp.lightingState == LIGHTING.LIGHT) {
				handleMap3Transition();
			}
		} 
		
		if (eventCanTrigger(powerRestore)) {
			gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_10");
			gp.lightingState = LIGHTING.LIGHT;
			powerRestore.isTriggered = true;
			gp.objectives.add(new Objective("Go back to the ground floor", OBJECTIVE_TYPE.MAIN, 1, 5 * GamePanel.TILE_SIZE, 0, "main_objective_11"));
		}
		
		if (eventCanTrigger(shotgunPickup)) {
			gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_1");
			shotgunPickup.isTriggered = true;
		}
		
		if (eventCanTrigger(riflePickup)) {
			gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_2");
			riflePickup.isTriggered = true;
		}
		
		if (eventCanTrigger(sniperPickup)) {
			gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_5");
			sniperPickup.isTriggered = true;
		}
		
		if (eventCanTrigger(machineGunPickup)) {
			gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_6");
			machineGunPickup.isTriggered = true;
		}
		
		if (eventCanTrigger(powerSupplyRoomDoor)) {
			gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_7");
			powerSupplyRoomDoor.isTriggered = true;
			gp.ui.showMessage("The door is locked. The key is ", "probably hidden in one of the rooms");
			gp.objectives.add(new Objective("Find the key in one of the rooms", OBJECTIVE_TYPE.MAIN, 5, 0, 0, "main_objective_8"));
			gp.om.addObject(new OBJ_Key(1488, 3888));
		}
		
		if (eventCanTrigger(keyPickup)) {
			gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_8");
			keyPickup.isTriggered = true;
			gp.objectives.add(new Objective("Open the door and restore the power", OBJECTIVE_TYPE.MAIN, 1, 96 * GamePanel.TILE_SIZE, 95 * GamePanel.TILE_SIZE, "main_objective_9"));
		}
		
		if (eventCanTrigger(openPowerSupplyDoor)) {
			if (!keyPickup.isTriggered) return;
			gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_9");
			gp.player.inventory.setKeys(gp.player.inventory.getKeys() - 1);
			openPowerSupplyDoor.isTriggered = true;
			gp.tm.getMap()[96][94] = 27;
			gp.tm.getMap()[97][94] = 27;
			gp.objectives.add(new Objective("Restore the power", OBJECTIVE_TYPE.MAIN, 1, 94 * GamePanel.TILE_SIZE, 86 * GamePanel.TILE_SIZE, "main_objective_10"));
		}
	}
	
	private void handleMap2Transition() {
		gp.currentMap = 1;
		gp.player.worldX = 5 * GamePanel.TILE_SIZE;
		gp.player.worldY = 0;
		gp.em.setMaxEntityCount(5);
		gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_4");

		gp.objectives.add(new Objective("Pick up the sniper(optional)", OBJECTIVE_TYPE.MAIN, 1, 73 * GamePanel.TILE_SIZE, 48 * GamePanel.TILE_SIZE, "main_objective_5"));
		gp.objectives.add(new Objective("Pick up the machine gun(optional)", OBJECTIVE_TYPE.MAIN, 1, 14 * GamePanel.TILE_SIZE, 38 * GamePanel.TILE_SIZE, "main_objective_6"));
		gp.objectives.add(new Objective("Go to the power supply room", OBJECTIVE_TYPE.MAIN, 1, 96 * GamePanel.TILE_SIZE, 95 * GamePanel.TILE_SIZE, "main_objective_7"));
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
		gp.em.setMaxEntityCount(5);
		
		gp.music.stop();
		gp.music.setFile(Sound.MUSIC_BOSS);
		gp.music.play();
		gp.music.loop();
	}
	
	private void handlePowerdownEvent() {
		gp.ui.showMessage("Oh no! the power went out!", "Go to the basement to fix it");
		gp.lightingState = LIGHTING.DARK;
		gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_3");
		gp.em.setMaxEntityCount(3);
		powerdown.isTriggered = true;
		gp.objectives.add(new Objective("Go to the basement", OBJECTIVE_TYPE.MAIN, 0, 5 * GamePanel.TILE_SIZE, 0, "main_objective_4"));
	}
	
	private Boolean eventCanTrigger(EventState event) {
		return !event.isTriggered && eventShouldFire(event) && event.inCorrectMap(gp.currentMap);
	}

	private Boolean eventShouldFire(Rectangle rec2) {
		Rectangle rec1 = gp.player.getSolidAreaRelativeToWorld();
		
		return rec1.intersects(rec2);
	}

}
