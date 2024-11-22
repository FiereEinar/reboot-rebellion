package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;
import main.Renderable;
import main.Utils;

public class TileManager implements Renderable {

	GamePanel gp;
	private Tile[] tiles;
	private int[][] map;

	public TileManager(GamePanel gp) {
		this.gp = gp;
		this.tiles = new Tile[10];
		this.map = new int[gp.worldCol][gp.worldRow];

		loadTiles();
		loadMap();
	}

	private void loadTiles() {
		Utils utils = new Utils();
		
		tiles[0] = new Tile(utils.getAndScaleImage("/tiles/grass.png", gp.tileSize, gp.tileSize));
		tiles[1] = new Tile(utils.getAndScaleImage("/tiles/water.png", gp.tileSize, gp.tileSize));
		tiles[1].isSolid = true;
		tiles[2] = new Tile(utils.getAndScaleImage("/tiles/floor.png", gp.tileSize, gp.tileSize));
	}

	private void loadMap() {
		try {
			InputStream is = getClass().getResourceAsStream("/maps/world01.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int row = 0;
			;

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

				Boolean isInView = worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
						&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
						&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
						&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY;

				if (isInView) {
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
