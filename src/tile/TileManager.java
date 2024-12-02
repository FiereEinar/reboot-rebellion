package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import entity.Vector2;
import main.GamePanel;
import main.Renderable;
import main.Utils;

public class TileManager implements Renderable {

	GamePanel gp;
	private Tile[] tiles;
	private int[][] map;

	public TileManager(GamePanel gp) {
		this.gp = gp;
		this.tiles = new Tile[20];
		this.map = new int[gp.worldCol][gp.worldRow];

		loadTiles();
		loadMap();
	}

	private void loadTiles() {
		Utils utils = new Utils();
		
		HashMap<String, String> tileData = new HashMap<>();
		
		loadTileData(tileData);
		
		for (int i = 1; i < 19; i++) {
			
			String index = "" + i;
			
			if (i < 10) {
				index = String.format("0%d", i);
			}
			
			String filename = index +  "_16tiles-Sheet.png";
			
			tiles[i - 1] = new Tile(utils.getAndScaleImage("/tiles/" + filename, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE));
			
			if (Boolean.parseBoolean(tileData.get(filename))) {
				tiles[i - 1].isSolid = true;
			}
		}
	}
	
	private void loadTileData(HashMap<String, String> tileData) {
		try {
			InputStream is = getClass().getResourceAsStream("/tiles/tiledata.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line = "";
			
			while ((line = br.readLine()) != null) {
				String data = br.readLine();
				
				tileData.put(line, data);
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadMap() {
		try {
			InputStream is = getClass().getResourceAsStream("/maps/map5.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int row = 0;

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
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, gp.fullScreenWidth, gp.fullScreenHeight);
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				BufferedImage image = tiles[map[i][j]].getSprite();

				int worldX = i * GamePanel.TILE_SIZE;
				int worldY = j * GamePanel.TILE_SIZE;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				if (gp.isInPlayerView(new Vector2(worldX, worldY))) {
					g2.drawImage(image, screenX, screenY, null);
				}
			}
		}
	}

	public int getMapTileNumber(int col, int row) {
		if (col < 0 || row < 0 || col >= gp.worldCol || row >= gp.worldRow)
			return 0;
		
		return map[col][row];
	}

	public Boolean isTileSolid(int tileIndex) {
		return tiles[tileIndex].isSolid;
	}

}
