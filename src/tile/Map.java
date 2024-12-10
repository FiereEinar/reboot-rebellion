package tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.Entity.ENTITY_TYPE;
import gun.GunObject;
import main.GamePanel;
import main.Objective;
import main.Utils;

public class Map {

	GamePanel gp;
	BufferedImage worldMap[];
	public Boolean isVisible = true;
	int width = 200;
	int height = 200;
	
	public Map(GamePanel gp) {
		this.gp = gp;
		createWorldMap();
	}
	
	private void createWorldMap() {
		Utils utils = new Utils();
		worldMap = new BufferedImage[gp.MAX_MAPS];
		int tileSize = GamePanel.TILE_SIZE;
		
		int worldMapW = tileSize * gp.worldCol;
		int worldMapH = tileSize * gp.worldRow;
		
		for (int i = 0; i < gp.MAX_MAPS; i++) {
			worldMap[i] = new BufferedImage(worldMapW, worldMapH, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D) worldMap[i].getGraphics();
			
			int col = 0;
			int row = 0;
			
			while (col < gp.worldCol && row < gp.worldRow) {
				int tileNum = gp.tm.getMapTileNumber(i, col, row);
				int x = tileSize * col;
				int y = tileSize * row;
				g2.drawImage(gp.tm.getTileByIndex(tileNum).getSprite(), x, y, null);
				
				col++;
				if (col == gp.worldCol) {
					col = 0;
					row++;
				}
			}
			
			worldMap[i] = utils.scaleImage(worldMap[i], width, height);
		}
	}
	
	public void drawFullMapScreen(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int width = 500;
		int height = 500;
		int x = gp.screenWidth / 2 - width / 2;
		int y = gp.screenHeight / 2 - height / 2;
		g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);
		
		double scale = (double) (GamePanel.TILE_SIZE * gp.worldCol) / width;
		int playerX = (int) (x + gp.player.worldX / scale);
		int playerY = (int) (y + gp.player.worldY / scale);
		int playerSize = (int) (GamePanel.TILE_SIZE / scale);
		g2.drawImage(gp.player.sprite.safeGetSprite(), playerX, playerY, playerSize, playerSize, null);
	}
	
	public void drawMiniMap(Graphics2D g2) {
		if (isVisible) {
			int x = 20;
			int y = 20;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g2.drawImage(worldMap[gp.currentMap], x, y, null);
			
			// player
			double scale = (double) (GamePanel.TILE_SIZE * gp.worldCol) / width;
			int playerX = (int) (x + gp.player.worldX / scale);
			int playerY = (int) (y + gp.player.worldY / scale);
			int playerSize = (int) (GamePanel.TILE_SIZE / scale) * 2;
			g2.setColor(Color.BLUE);
			g2.fillRect(playerX, playerY, playerSize, playerSize);
			
			// guns in the map
			for (GunObject gun: gp.om.getGuns()) {
				int gunX = (int) (x + gun.worldX / scale);
				int gunY = (int) (y + gun.worldY / scale);
				int gunSize = (int) (GamePanel.TILE_SIZE / scale) * 2;
				g2.setColor(Color.YELLOW);
				g2.fillRect(gunX, gunY, gunSize, gunSize);
			}

			// objectives in the map
			for (int i = 0; i < gp.objectives.size(); i++) {
				Objective o = gp.objectives.get(i);

				if (o.getMapIndex() != gp.currentMap) continue;
				
				int objX = (int) (x + o.position.x / scale);
				int objY = (int) (y + o.position.y / scale);
				int objSize = (int) (GamePanel.TILE_SIZE / scale) * 2;
				g2.setColor(Color.MAGENTA);
				g2.fillRect(objX, objY, objSize, objSize);
				g2.setFont(gp.ui.veryExtraSmallText);
				String number = "" + (i + 1);
				int h = (int) g2.getFontMetrics().getStringBounds(number, g2).getHeight();
				g2.setColor(Color.BLACK);
				g2.fillOval(objX - 2, objY - h + 2, 10, 10);
				g2.setColor(Color.WHITE);
				g2.drawString(number, objX, objY);
			}
			
			// npcs in the map
			for (Entity e: gp.em.getEnities()) {
				if (e.type != ENTITY_TYPE.NPC) return;
				
				int npcX = (int) (x + e.worldX / scale);
				int npcY = (int) (y + e.worldY / scale);
				g2.setColor(Color.GREEN);
				g2.fillRect(npcX, npcY, playerSize, playerSize);
			}
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}

}
