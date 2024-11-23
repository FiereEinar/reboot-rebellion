package object;

import main.GamePanel;
import main.Utils;
import sprite.Sprite;

public class OBJ_Heart extends GameObject {
	
	public Sprite emptyHeart = new Sprite();
	public Sprite fullHeart = new Sprite();
	GamePanel gp;
	
	public OBJ_Heart(GamePanel gp) {
		this.gp = gp;
		this.worldX = 48 * 3;
		this.worldY = 48 * 3;
		this.name = "heart";
		loadSprites();
	}

	private void loadSprites() {
		Utils utils = new Utils();
		
		emptyHeart.addSprite(utils.getAndScaleImage("/heart/heart_empty.png", GamePanel.tileSize, GamePanel.tileSize));
		fullHeart.addSprite(utils.getAndScaleImage("/heart/heart_full.png", GamePanel.tileSize, GamePanel.tileSize));
	}

}
