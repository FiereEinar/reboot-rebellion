package main;

import java.awt.Rectangle;

import entity.Entity;
import object.GameObject;

public class CollisionDetector {

	GamePanel gp;

	public CollisionDetector(GamePanel gp) {
		this.gp = gp;
	}

	public Boolean checkWorldCollision(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.getSolidArea().x;
		int entityRightWorldX = entity.worldX + entity.getSolidArea().x + entity.getSolidArea().width;
		int entityTopWorldY = entity.worldY + entity.getSolidArea().y;
		int entityBottomWorldY = entity.worldY + entity.getSolidArea().y + entity.getSolidArea().height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		int tileNum1 = 0, tileNum2 = 0;

		switch (entity.getDirection()) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.tileSize;
			tileNum1 = gp.tm.getMapTileNumber(entityLeftCol, entityTopRow);
			tileNum2 = gp.tm.getMapTileNumber(entityRightCol, entityTopRow);
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.tileSize;
			tileNum1 = gp.tm.getMapTileNumber(entityLeftCol, entityBottomRow);
			tileNum2 = gp.tm.getMapTileNumber(entityRightCol, entityBottomRow);
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.tileSize;
			tileNum1 = gp.tm.getMapTileNumber(entityLeftCol, entityTopRow);
			tileNum2 = gp.tm.getMapTileNumber(entityLeftCol, entityBottomRow);
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.tileSize;
			tileNum1 = gp.tm.getMapTileNumber(entityRightCol, entityTopRow);
			tileNum2 = gp.tm.getMapTileNumber(entityRightCol, entityBottomRow);
			break;
		default:
			System.out.println("WARNING: Invalid value for direction");
			break;
		}

		if (gp.tm.isTileSolid(tileNum1) || gp.tm.isTileSolid(tileNum2)) {
			return true;
		}
		
		return false;
	}

	public GameObject checkEntityObjectCollision(Entity entity, Boolean isPlayer) {
		GameObject hitObject = null;
		Rectangle rec1 = entity.getSolidAreaRelativeToWorld();

		for (GameObject o : gp.om.getObjects()) {
			Rectangle rec2 = o.getSolidAreaRelativeToWorld();

			switch (entity.getDirection()) {
			case "up":
				rec1.y -= entity.getSpeed();
				break;
			case "down":
				rec1.y += entity.getSpeed();
				break;
			case "left":
				rec1.x -= entity.getSpeed();
				break;
			case "right":
				rec1.x += entity.getSpeed();
				break;
			}

			if (rec1.intersects(rec2))
				hitObject = o;
		}
		
		return hitObject;
	}
	
	public Entity checkEntityCollision(Entity entity) {
		Entity hitEntity = null;
		Rectangle rec1 = entity.getSolidAreaRelativeToWorld();

		for (Entity e : gp.em.getEnities()) {
			if (e == null || entity == e) continue;
			
			Rectangle rec2 = e.getSolidAreaRelativeToWorld();

			switch (entity.getDirection()) {
			case "up":
				rec1.y -= entity.getSpeed();
				break;
			case "down":
				rec1.y += entity.getSpeed();
				break;
			case "left":
				rec1.x -= entity.getSpeed();
				break;
			case "right":
				rec1.x += entity.getSpeed();
				break;
			}

			if (rec1.intersects(rec2))
				hitEntity = e;
		}
		
		return hitEntity;
	}
	
	public Boolean isCollidingWithPlayer(Entity entity) {
		Rectangle rec1 = entity.getSolidAreaRelativeToWorld();
		Rectangle rec2 = gp.player.getSolidAreaRelativeToWorld();
		
		switch (entity.getDirection()) {
		case "up":
			rec1.y -= entity.getSpeed();
			break;
		case "down":
			rec1.y += entity.getSpeed();
			break;
		case "left":
			rec1.x -= entity.getSpeed();
			break;
		case "right":
			rec1.x += entity.getSpeed();
			break;
		}
		
		if (rec1.intersects(rec2)) return true;
		return false;
	}
}
