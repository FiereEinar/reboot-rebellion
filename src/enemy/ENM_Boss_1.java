package enemy;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Projectile;
import entity.Vector2;
import gun.GUN_EnemyWeapon;
import main.GamePanel;
import main.Utils;
import states.State;

public class ENM_Boss_1 extends ShootingEnemy {

	GUN_EnemyWeapon secondGun = new GUN_EnemyWeapon(); 
	State attackCooldown = new State(240);
	int maxProjectileShotCount = 9;
	int projectileShotCount = 0;
	
	
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

		this.gun.fireRate = (float) 0.3;
		this.secondGun.fireRate = 5;
		
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
//	    for (int i = 0; i < 33; i++) {
//	    	this.sprite.attackingRight.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
//	    	this.sprite.attackingLeft.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
//	    	this.sprite.attackingDown.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
//	    	this.sprite.attackingUp.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
//	    }
	    for (int i = 0; i < 19; i++) {
	    	this.sprite.attackingRight.addSprite(utils.cropSprite(spritesheet, i * width, 6 * height, width, height));
	    	this.sprite.attackingLeft.addSprite(utils.cropSprite(spritesheet, i * width, 6 * height, width, height));
	    	this.sprite.attackingDown.addSprite(utils.cropSprite(spritesheet, i * width, 6 * height, width, height));
	    	this.sprite.attackingUp.addSprite(utils.cropSprite(spritesheet, i * width, 6 * height, width, height));
	    }
	    
	    // Load attacked sprites
	    for (int i = 0; i < 4; i++) {
	    	this.sprite.attackedRight.addSprite(utils.cropSprite(spritesheet, i * width, height, width, height));
	        this.sprite.attackedLeft.addSprite(utils.cropSprite(spritesheet, i * width, 2 * height, width, height));
	        this.sprite.attackedDown.addSprite(utils.cropSprite(spritesheet, i * width, 3 * height, width, height));
	        this.sprite.attackedUp.addSprite(utils.cropSprite(spritesheet, i * width, 4 * height, width, height));
	    }

	    // Load dying sprites
	    for (int i = 0; i < 15; i++) {
	        this.sprite.dying.addSprite(utils.cropSprite(spritesheet, i * width, 7 * height, width, height));
	    }
	}
	
	private Boolean firstAttack() {
	    if (!gun.canShoot()) return false;

	    int BULLET_SPEED = gun.bulletSpeed;
	    int BULLET_DAMAGE = gun.damage;

	    int centerWorldX = worldX + getSolidArea().x + (getSolidArea().width / 2);
	    int centerWorldY = worldY + getSolidArea().y + (getSolidArea().height / 2);

	    // Fire projectiles at fixed angles (e.g., 10-degree intervals)
	    for (int degree = 0; degree < 360; degree += 30) { // Change 10 to your desired interval
	        // Convert degree to radians
	        double angle = Math.toRadians(degree);

	        // Calculate direction based on the angle
	        float directionX = (float) Math.cos(angle);
	        float directionY = (float) Math.sin(angle);

	        // Calculate bullet speed
	        float speedX = directionX * BULLET_SPEED;
	        float speedY = directionY * BULLET_SPEED;

	        // Spawn the bullet
	        gp.em.addBullets(new Projectile(gp, centerWorldX, centerWorldY, speedX, speedY, BULLET_DAMAGE));
	    }

	    gun.recordShot();
	    return true;
	}
	
	private Boolean secondAttack() {
		if (attackCooldown.getState() || !this.secondGun.canShoot()) return false;
		
		int tileSize = GamePanel.tileSize;
		
		Vector2 target = new Vector2(gp.player.worldX, gp.player.worldY);
		
		Vector2 secondTurretOrigin = new Vector2(worldX + tileSize * 3, worldY);
		
		shootProjectile(secondGun, target);
		shootProjectile(secondGun, target, secondTurretOrigin);
		
		this.secondGun.recordShot();
		
		return true;
	}
	
	@Override
	protected void attack() {
		moveToPlayer();
		if (firstAttack()) {
			state.attacking.setState(true);
			movementDisabled = true;
		}
		
//		if (secondAttack()) {
//			projectileShotCount++;
//			
//			if (projectileShotCount == maxProjectileShotCount) {
//				attackCooldown.setState(true);
//				projectileShotCount = 0;
//			} else {
//				state.attacking.setState(true);
//				movementDisabled = true;
//			}
//		}
	}
	
	@Override
	public void update() {
		super.update();
		attackCooldown.update();
	}

}
