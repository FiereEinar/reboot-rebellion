package tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Map {

	GamePanel gp;
	BufferedImage worldMap[];
	public Boolean isVisible = true;
	
	public Map(GamePanel gp) {
		this.gp = gp;
		createWorldMap();
	}
	
	private void createWorldMap() {
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
			int width = 200;
			int height = 200;
			int x = 20;
			int y = 20;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);
			
			double scale = (double) (GamePanel.TILE_SIZE * gp.worldCol) / width;
			int playerX = (int) (x + gp.player.worldX / scale);
			int playerY = (int) (y + gp.player.worldY / scale);
			int playerSize = (int) (GamePanel.TILE_SIZE / scale) * 2;
			g2.setColor(Color.BLUE);
			g2.fillRect(playerX, playerY, playerSize, playerSize);
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}

}
