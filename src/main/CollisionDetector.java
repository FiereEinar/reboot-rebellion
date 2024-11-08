package main;

import entity.Entity;

public class CollisionDetector {

	GamePanel gp;

	public CollisionDetector(GamePanel gp) {
		this.gp = gp;
	}

	public void checkWorldCollision(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

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
			entity.movementDisabled = true;
		}
	}

}
