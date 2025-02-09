package main;

import java.awt.Rectangle;

import entity.BaseEntity;
import entity.Entity;
import gun.GunObject;
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

		int entityLeftCol = entityLeftWorldX / GamePanel.TILE_SIZE;
		int entityRightCol = entityRightWorldX / GamePanel.TILE_SIZE;
		int entityTopRow = entityTopWorldY / GamePanel.TILE_SIZE;
		int entityBottomRow = entityBottomWorldY / GamePanel.TILE_SIZE;

		int tileNum1 = 0, tileNum2 = 0;

		switch (entity.getDirection()) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.getSpeed()) / GamePanel.TILE_SIZE;
			tileNum1 = gp.tm.getMapTileNumber(entityLeftCol, entityTopRow);
			tileNum2 = gp.tm.getMapTileNumber(entityRightCol, entityTopRow);
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / GamePanel.TILE_SIZE;
			tileNum1 = gp.tm.getMapTileNumber(entityLeftCol, entityBottomRow);
			tileNum2 = gp.tm.getMapTileNumber(entityRightCol, entityBottomRow);
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / GamePanel.TILE_SIZE;
			tileNum1 = gp.tm.getMapTileNumber(entityLeftCol, entityTopRow);
			tileNum2 = gp.tm.getMapTileNumber(entityLeftCol, entityBottomRow);
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.getSpeed()) / GamePanel.TILE_SIZE;
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
	
	public Boolean checkWorldCollision(BaseEntity entity, float speedX, float speedY) {
		Rectangle rec = entity.getSolidAreaRelativeToWorld();

		float nextX = rec.x + speedX - GamePanel.TILE_SIZE / 2;
	    float nextY = rec.y + speedY - GamePanel.TILE_SIZE / 2;

	    int nextWorldCol = Math.round(nextX / GamePanel.TILE_SIZE);
	    int nextWorldRow = Math.round(nextY / GamePanel.TILE_SIZE);

	    if (nextWorldCol < 0 || nextWorldRow < 0 || nextWorldCol > gp.worldWidth || nextWorldRow > gp.worldHeight) {
	        return true; 
	    }

	    int nextTileIndex = gp.tm.getMapTileNumber(nextWorldCol, nextWorldRow);

	    return gp.tm.isTileSolid(nextTileIndex);
	}

	public GameObject checkEntityObjectCollision(Entity entity, Boolean isPlayer) {
		GameObject hitObject = null;
		Rectangle rec1 = entity.getSolidAreaRelativeToWorld();

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
		
		for (GameObject o : gp.om.getObjects()) {
			Rectangle rec2 = o.getSolidAreaRelativeToWorld();

			if (rec1.intersects(rec2))
				hitObject = o;
		}
		
		return hitObject;
	}
	
	public GunObject checkEntityGunsCollision(Entity entity) {
		GunObject hitObject = null;
		Rectangle rec1 = entity.getSolidAreaRelativeToWorld();
		
		for (GunObject o : gp.om.getGuns()) {
			Rectangle rec2 = o.getSolidAreaRelativeToWorld();

			if (rec1.intersects(rec2))
				hitObject = o;
		}
		
		return hitObject;
	}
	
	public Entity checkEntityCollision(Entity entity) {
		Entity hitEntity = null;
		Rectangle rec1 = entity.getSolidAreaRelativeToWorld();
		
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

		for (Entity e : gp.em.getEnities()) {
			if (e == null || entity == e) continue;
			
			Rectangle rec2 = e.getSolidAreaRelativeToWorld();

			if (rec1.intersects(rec2))
				hitEntity = e;
		}
		
		return hitEntity;
	}
	
	public Entity checkEntityCollision(BaseEntity entity) {
		Entity hitEntity = null;
		Rectangle rec1 = entity.getSolidAreaRelativeToWorld();

		for (Entity e : gp.em.getEnities()) {
			if (e == null || entity == e) continue;
			
			Rectangle rec2 = e.getSolidAreaRelativeToWorld();

			if (rec1.intersects(rec2))
				hitEntity = e;
		}
		
		return hitEntity;
	}
	
	public Boolean isCollidingWithPlayer(BaseEntity entity) {
		Rectangle rec1 = entity.getSolidAreaRelativeToWorld();
		Rectangle rec2 = gp.player.getSolidAreaRelativeToWorld();
		
		if (rec1.intersects(rec2)) return true;
		return false;
	}
}
