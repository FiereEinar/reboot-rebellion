package enemy;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.Utils;

public class ENM_Boss_1 extends ShootingEnemy {

	public ENM_Boss_1(GamePanel gp, int x, int y) {
		super(gp);
		
		this.worldX = x;
		this.worldY = y;
		
		this.setMaxHealth(1000);
		this.setHealth(getMaxHealth());

		this.setSpeed(1);
		this.setDirection("down");
		
		int tileSize = GamePanel.tileSize;
		int width = 325;
	    int height = 294;
	    
		this.setSolidArea(new Rectangle(tileSize * 2, tileSize * 2, tileSize * 2, tileSize * 2));
		int halfTile = tileSize / 2;
		int attackRangeWidth = width + tileSize;
		int attackRangeHeight= height + tileSize;
		this.setAttackRange(new Rectangle(-halfTile, -halfTile, attackRangeWidth, attackRangeHeight));
		this.state.attacking.setDuration(240);
		
		this.gun.bulletMultiplier = 9;
		this.gun.bulletSpread = 20;
		
		loadSprites();
		updateSpritesInterval();
	}
	
	private void loadSprites() {
		Utils utils = new Utils();
		
		// Load the sprite sheet
	    BufferedImage spritesheet = utils.getSpriteSheet("/enemies/BOSS.png");

	    int width = 325;
	    int height = 294;

	    // Load movement sprites
	    for (int i = 0; i < 4; i++) { 
	        this.sprite.right.addSprite(utils.cropSprite(spritesheet, i * width, height, width, height));
	        this.sprite.left.addSprite(utils.cropSprite(spritesheet, i * width, 2 * height, width, height));
	        this.sprite.down.addSprite(utils.cropSprite(spritesheet, i * width, 3 * height, width, height));
	        this.sprite.up.addSprite(utils.cropSprite(spritesheet, i * width, 4 * height, width, height));
	    }
	    
	    // Load attacking sprites
	    for (int i = 0; i < 33; i++) {
	    	this.sprite.attackingRight.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    	this.sprite.attackingLeft.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    	this.sprite.attackingDown.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    	this.sprite.attackingUp.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    }
	    
	    // Load attacked sprites
	    for (int i = 0; i < 33; i++) {
	    	this.sprite.attackedRight.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    	this.sprite.attackedLeft.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    	this.sprite.attackedDown.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    	this.sprite.attackedUp.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    }

	    // Load dying sprites
	    for (int i = 0; i < 15; i++) {
	        this.sprite.dying.addSprite(utils.cropSprite(spritesheet, i * width, 7 * height, width, height));
	    }
	}
	
	@Override
	protected void attack() {
		moveToPlayer();
		if (shootProjectile()) {
			state.attacking.setState(true);
			movementDisabled = true;
		}
	}

}
