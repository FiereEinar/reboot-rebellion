package enemy;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.Sound;
import main.Utils;

public class ENM_Bomber_1 extends Enemy {

	public ENM_Bomber_1(GamePanel gp, int x, int y) {
		super(gp);
		
		this.worldX = x;
		this.worldY = y;
		this.killPoints = 80;
		
		this.setMaxHealth(50);
		this.setHealth(getMaxHealth());

		this.setSpeed(4);
		this.setDirection("down");

//		this.setSolidArea(new Rectangle(6, 0, 48, 48));
		this.setSolidArea(new Rectangle(16, 16, 32, 32));
		int halfTile = GamePanel.TILE_SIZE / 2;
		int attackRangeWidth = GamePanel.TILE_SIZE * 2;
		int attackRangeHeight= GamePanel.TILE_SIZE * 2;
		this.setAttackRange(new Rectangle(-halfTile, -halfTile, attackRangeWidth, attackRangeHeight));
		this.state.attacking.setDuration(120);
		
		loadSprites();
		updateSpritesInterval();
	}
	
	private void loadSprites() {
		Utils utils = new Utils();
		
		// Load the sprite sheet
	    BufferedImage spritesheet = utils.getSpriteSheet("/enemies/Robot_2_bomber.png");

	    int width = 62;
	    int height = 62;

	    // Load movement sprites
	    for (int i = 0; i < 2; i++) { 
	        this.sprite.right.addSprite(utils.cropSprite(spritesheet, i * width, 0, width, height));
	        this.sprite.left.addSprite(utils.cropSprite(spritesheet, i * width, height, width, height));
	        this.sprite.down.addSprite(utils.cropSprite(spritesheet, i * width, 2 * height, width, height));
	        this.sprite.up.addSprite(utils.cropSprite(spritesheet, i * width, 3 * height, width, height));
	    }
	    
	    // Load attacked sprites
	    for (int i = 0; i < 3; i++) {
	    	this.sprite.attackedRight.addSprite(utils.cropSprite(spritesheet, i * width, 4 * height, width, height));
	    	this.sprite.attackedLeft.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    	this.sprite.attackedDown.addSprite(utils.cropSprite(spritesheet, i * width, 6 * height, width, height));
	    	this.sprite.attackedUp.addSprite(utils.cropSprite(spritesheet, i * width, 7 * height, width, height));
	    }
	    
	    // Load attacking sprites
	    for (int i = 0; i < 7; i++) {
	    	this.sprite.attackingRight.addSprite(utils.cropSprite(spritesheet, i * width, 8 * height, width, height));
	    	this.sprite.attackingLeft.addSprite(utils.cropSprite(spritesheet, i * width, 8 * height, width, height));
	    	this.sprite.attackingDown.addSprite(utils.cropSprite(spritesheet, i * width, 8 * height, width, height));
	    	this.sprite.attackingUp.addSprite(utils.cropSprite(spritesheet, i * width, 8 * height, width, height));
	    }

	    // Load dying sprites
	    for (int i = 0; i < 3; i++) { 
	        this.sprite.dying.addSprite(utils.cropSprite(spritesheet, i * width, 9 * height, width, height));
	    }
	}
	
	private void checkIfCanAttack() {
		Rectangle rec1 = gp.player.getSolidAreaRelativeToWorld();
		Rectangle range = this.getAttackRange();
		Rectangle rec2 = new Rectangle(this.worldX + range.x, this.worldY + range.y, range.width, range.height);
		
		Boolean inRangeWhileExploding = state.attacking.getCounter() == state.attacking.getStateDuration() / 2;

		if (rec1.intersects(rec2)) {
			state.attacking.setState(true);
			
			
			if (inRangeWhileExploding) {
				gp.player.recieveDamage(damage);
			}
		}
		
		if (inRangeWhileExploding) {
			gp.sound.play(Sound.ROBOT_EXPLOSION);
		}
		
		if (this.state.attacking.isTriggered()) {
			this.isDead = true;
		}
	}

	@Override
	protected void attack() {
		moveToPlayer();
		checkIfCanAttack();
	}

}
