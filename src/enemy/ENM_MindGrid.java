package enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.Vector2;
import main.GamePanel;
import main.Utils;

public class ENM_MindGrid extends Entity {

	public ENM_MindGrid(GamePanel gp, int x, int y) {
		super(gp);
		
		this.worldX = x;
		this.worldY = y;
		
		this.setMaxHealth(4000);
		this.setHealth(getMaxHealth());

		this.setSpeed(0);
		this.setDirection("down");
		
		int tileSize = GamePanel.TILE_SIZE;

		this.setSolidArea(new Rectangle(tileSize * 2, tileSize * 3, tileSize * 4, tileSize * 4));
		
		this.type = ENTITY_TYPE.BOSS;
		
		loadSprites();
		updateSpritesInterval();
	}
	
	private void loadSprites() {
		Utils utils = new Utils();
		
		// Load the sprite sheet
	    BufferedImage sprite = utils.getSpriteSheet("/enemies/mindgrid.png");
	    
		this.sprite.down.addSprite(sprite);
		this.sprite.attackedDown.addSprite(sprite);
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void draw(Graphics2D g2) {
		Vector2 screen = getScreenLocation();
		if (gp.isInPlayerView(new Vector2(worldX, worldY)))
			g2.drawImage(this.sprite.getSprite(), screen.x, screen.y, null);
		
		drawHealthBar(g2);
	}

}
