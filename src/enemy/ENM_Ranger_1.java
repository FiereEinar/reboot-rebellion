package enemy;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.Utils;

public class ENM_Ranger_1 extends ShootingEnemy {

	public ENM_Ranger_1(GamePanel gp, int x, int y) {
		super(gp);
		
		this.worldX = x;
		this.worldY = y;
		
		this.setMaxHealth(100);
		this.setHealth(getMaxHealth());

		this.setSpeed(2);
		this.setDirection("down");

		this.setSolidArea(new Rectangle(8, 16, 32, 32));
		
		loadSprites();
		updateSpritesInterval();
	}
	
	private void loadSprites() {
		Utils utils = new Utils();
		
		// Load the sprite sheet
	    BufferedImage spritesheet = utils.getSpriteSheet("/robot_ranger/Robot_3_All.png");

	    int width = 51;
	    int height = 51;

	    // Load attacking sprites
	    for (int i = 0; i < 5; i++) { 
	        this.sprite.attackingRight.addSprite(utils.cropSprite(spritesheet, i * width, 0, width, height));
	        this.sprite.attackingLeft.addSprite(utils.cropSprite(spritesheet, i * width, height, width, height));
	        this.sprite.attackingDown.addSprite(utils.cropSprite(spritesheet, i * width, 2 * height, width, height));
	        this.sprite.attackingUp.addSprite(utils.cropSprite(spritesheet, i * width, 3 * height, width, height));
	    }
	    
	    // Load movement sprites
	    for (int i = 0; i < 2; i++) {
	    	this.sprite.right.addSprite(utils.cropSprite(spritesheet, i * width, 4 * height, width, height));
	    	this.sprite.left.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    	this.sprite.down.addSprite(utils.cropSprite(spritesheet, i * width, 6 * height, width, height));
	    	this.sprite.up.addSprite(utils.cropSprite(spritesheet, i * width, 7 * height, width, height));
	    }
	    
	    // Load attacked sprites
	    for (int i = 0; i < 3; i++) {
	    	this.sprite.attackedRight.addSprite(utils.cropSprite(spritesheet, i * width, 8 * height, width, height));
	    	this.sprite.attackedLeft.addSprite(utils.cropSprite(spritesheet, i * width, 9 * height, width, height));
	    	this.sprite.attackedDown.addSprite(utils.cropSprite(spritesheet, i * width, 10 * height, width, height));
	    	this.sprite.attackedUp.addSprite(utils.cropSprite(spritesheet, i * width, 11 * height, width, height));
	    }

	    // Load dying sprites
	    for (int i = 0; i < 3; i++) { 
	        this.sprite.dying.addSprite(utils.cropSprite(spritesheet, i * width, 12 * height, width, height));
	    }
	}
	
	@Override
	protected void attack() {
		moveToPlayer();
		if (shootProjectile()) state.attacking.setState(true);
	}

}
