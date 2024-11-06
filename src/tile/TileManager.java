package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Renderable;

public class TileManager implements Renderable {
	
	GamePanel gp;
	Tile[] tiles;
	int[][] map;
			
	public TileManager(GamePanel gp) {
		this.gp = gp;
		this.tiles = new Tile[10];
		this.map = new int[gp.worldCol][gp.worldRow];
		
		loadTiles();
		loadMap();
	}
	
	private void loadTiles() {
		try {
			tiles[0] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")));	
			tiles[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/water.png")));	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadMap() {
		try {
			InputStream is = getClass().getResourceAsStream("/maps/world01.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int row = 0;;
			
			while (row < gp.worldRow) {
				int col = 0;
				String line = br.readLine();
				String[] numbers = line.split(" ");
				
				while (col < gp.worldCol) {
					map[col][row] = Integer.parseInt(numbers[col]);
					col++;
				}
				
				row++;
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g2) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				BufferedImage image = tiles[map[i][j]].getSprite();
				
				int worldX = i * gp.tileSize;
				int worldY = j * gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				Boolean isInView = 
					worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
					&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
					&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
					&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY;
				
				if (isInView) {
					g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
				}
			}
		}
	}

}
