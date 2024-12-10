package event;

import java.awt.Rectangle;

import enemy.ENM_Boss_1;
import entity.Entity;
import entity.Entity.ENTITY_TYPE;
import entity.Vector2;
import main.GamePanel;
import main.GamePanel.LIGHTING;
import main.Objective.OBJECTIVE_TYPE;
import npc.NPC;
import npc.NPC_Scientist;
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
	EventState npcSave = new EventState();
	EventState mindGridOff = new EventState();
	
	public int npcSaved = 3;
	public final int maxNpcSaved = 3;
	
	private Boolean isFinalObjectiveShown = false;
	public int bossKilled = 0;
	public final int maxBossKilled = 1;	
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		setupEvents();
	}
	
	private void setupEvents() {
		int map1 = 0;
		int map2 = 1;
		int map3 = 2;

		map2Transition.setCoordinate(map1, 5, 0, GamePanel.TILE_SIZE);
		map3Transition.setCoordinate(map1, 93, 4, GamePanel.TILE_SIZE);
		powerdown.setCoordinate(map1, 93, 4, GamePanel.TILE_SIZE);
		shotgunPickup.setCoordinate(map1, 19, 92, GamePanel.TILE_SIZE);
		riflePickup.setCoordinate(map1, 70, 44, GamePanel.TILE_SIZE);
		npcSave.setCoordinate(map1, 49, 99, GamePanel.TILE_SIZE);
		
		map1Transition.setCoordinate(map2, 5, 0, GamePanel.TILE_SIZE);
		sniperPickup.setCoordinate(map2, 74, 48, GamePanel.TILE_SIZE);
		machineGunPickup.setCoordinate(map2, 14, 38, GamePanel.TILE_SIZE);
		powerSupplyRoomDoor.setCoordinate(map2, 96, 95, GamePanel.TILE_SIZE);
		keyPickup.setCoordinate(map2, 32, 81, GamePanel.TILE_SIZE);
		openPowerSupplyDoor.setCoordinate(map2, 96, 95, GamePanel.TILE_SIZE);
		powerRestore.setCoordinate(map2, 94, 86, GamePanel.TILE_SIZE);

		mindGridOff.setCoordinate(map3, 48, 47, GamePanel.TILE_SIZE);
	}
	
	public void restart() {
		map1Transition.restart();
		map2Transition.restart();
		map3Transition.restart();
		powerdown.restart();
		shotgunPickup.restart();
		riflePickup.restart();
		sniperPickup.restart();
		machineGunPickup.restart();
		powerSupplyRoomDoor.restart();
		keyPickup.restart();
		openPowerSupplyDoor.restart();
		powerRestore.restart();
		npcSave.restart();
		mindGridOff.restart();
		
		npcSaved = 0;
		isFinalObjectiveShown = false;
		bossKilled = 0;
	}
	
	public void checkEvent() {
		if (eventCanTrigger(powerdown)) {
			handlePowerdownEvent();
		} 
		
		if (eventCanTrigger(map1Transition)) {
			if (gp.lightingState == LIGHTING.LIGHT) {
				handleMap1Transition();
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
			if (!openPowerSupplyDoor.isTriggered) return;
			
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
		
		if (map1Transition.isTriggered) {
			if (eventCanTrigger(npcSave)) {
				handleNpcSaving();

				for (Entity e: gp.em.getEnities()) {
					if (e.type != ENTITY_TYPE.NPC) continue;
					
					if (eventShouldFire(npcSave, e)) {
						gp.player.addPoints(e.killPoints);
						e.isDead = true;
						npcSaved++;
					}
				}

				if (npcSaved == maxNpcSaved) {
					gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_12");
					gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_npc_1");
					gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_npc_2");
					gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_npc_3");
					npcSave.isTriggered = true;
					gp.objectives.add(new Objective("Go to the rooftop", OBJECTIVE_TYPE.MAIN, 0, 93 * GamePanel.TILE_SIZE, 4 * GamePanel.TILE_SIZE, "main_objective_13"));
				}
			}
		}
		
		if (bossKilled == maxBossKilled && map3Transition.isTriggered && !isFinalObjectiveShown) {
			isFinalObjectiveShown = true;
			gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_14");
			gp.em.setMaxEntityCount(5);
			gp.objectives.add(new Objective("Turn off the Mind Grid", OBJECTIVE_TYPE.MAIN, 2, 48 * GamePanel.TILE_SIZE, 47 * GamePanel.TILE_SIZE, "main_objective_15"));
		}
		
		if (eventCanTrigger(mindGridOff)) {
			if (bossKilled == 0) return;
			
			gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_15");
			mindGridOff.isTriggered = true;
			gp.gameState = GamePanel.STATE_ENDGAME_DIALOGUE;
		}
	}
	
	private void handleNpcSaving() {
		if (!powerRestore.isTriggered) return;

		int tileSize = GamePanel.TILE_SIZE;
		
		for (Entity e: gp.em.getEnities()) {
			if (e.type == ENTITY_TYPE.NPC) {
				NPC npc = (NPC) e;
				if (npc.isFollowingPlayer()) {
					e.searchPath(new Vector2(49 * tileSize, 99 * tileSize));
				}
			}
		}
	}
	
	private void handleMap1Transition() {
		gp.currentMap = 0;
		map1Transition.isTriggered = true;
		gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_11");
		gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_5");
		gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_6");
		gp.ui.showMessage("Oh no! It turns out there's actually workers", "that are stuck! We need to save them!");
		gp.em.addEntity(new NPC_Scientist(gp, 4210, 4332));
		gp.objectives.add(new Objective("Save worker 1", OBJECTIVE_TYPE.MAIN, 0, 4210, 4332, "main_objective_npc_1"));
		gp.em.addEntity(new NPC_Scientist(gp, 4210, 2387));
		gp.objectives.add(new Objective("Save worker 2", OBJECTIVE_TYPE.MAIN, 0, 4210, 2387, "main_objective_npc_2"));
		gp.em.addEntity(new NPC_Scientist(gp, 235, 4582));
		gp.objectives.add(new Objective("Save worker 3", OBJECTIVE_TYPE.MAIN, 0, 235, 4582, "main_objective_npc_3"));

		gp.objectives.add(new Objective("Lead the workers to the exit", OBJECTIVE_TYPE.MAIN, 0, 49 * GamePanel.TILE_SIZE, 99 * GamePanel.TILE_SIZE, "main_objective_12"));
	}
	
	private void handleMap2Transition() {
		gp.currentMap = 1;
		gp.player.worldX = 5 * GamePanel.TILE_SIZE;
		gp.player.worldY = 0;
		gp.em.setMaxEntityCount(5);
		gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_4");

		gp.objectives.add(new Objective("Pick up the sniper (optional)", OBJECTIVE_TYPE.MAIN, 1, 73 * GamePanel.TILE_SIZE, 48 * GamePanel.TILE_SIZE, "main_objective_5"));
		gp.objectives.add(new Objective("Pick up the machine gun (optional)", OBJECTIVE_TYPE.MAIN, 1, 14 * GamePanel.TILE_SIZE, 38 * GamePanel.TILE_SIZE, "main_objective_6"));
		gp.objectives.add(new Objective("Go to the power supply room", OBJECTIVE_TYPE.MAIN, 1, 96 * GamePanel.TILE_SIZE, 95 * GamePanel.TILE_SIZE, "main_objective_7"));
	}
	
	private void handleMap3Transition() {
		if (!npcSave.isTriggered) return;
		map3Transition.isTriggered = true;
		
		gp.objectives.removeIf(t -> t.getIdentifier() == "main_objective_13");
		gp.objectives.add(new Objective("Defeat the boss", OBJECTIVE_TYPE.MAIN, 1, 50 * GamePanel.TILE_SIZE, 50 * GamePanel.TILE_SIZE, "main_objective_14"));
		
		gp.currentMap = 2;
		gp.em.addEntity(new ENM_Boss_1(gp, 50 * GamePanel.TILE_SIZE, 45 * GamePanel.TILE_SIZE));
		
		gp.music.stop();
		gp.music.setFile(Sound.MUSIC_BOSS);
		gp.music.play();
		gp.music.loop();
	}
	
	private void handlePowerdownEvent() {
		gp.ui.showMessage("Oh no! the power went out! We can't", "go up. Go to the basement to fix it");
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
	
	private Boolean eventShouldFire(Rectangle rec2, Entity entity) {
		Rectangle rec1 = entity.getSolidAreaRelativeToWorld();
		
		return rec1.intersects(rec2);
	}

}
