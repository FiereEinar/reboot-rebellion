package enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.Vector2;
import gun.GUN_EnemyWeapon;
import main.GamePanel;
import main.Sound;
import main.Utils;
import projectiles.GunProjectile;
import sprite.SpriteManager;
import states.State;

public class ENM_Boss_1 extends ShootingEnemy {

	GUN_EnemyWeapon secondGun = new GUN_EnemyWeapon(); 
	State attackCooldown = new State(240);
	private int maxProjectileShotCount = 9;
	private int projectileShotCount = 0;
	private int secondAttackCount = 0;
	
	private final int FIRST_ATTACK = 1;
	private final int SECOND_ATTACK = 2;
	private int currentAttack = FIRST_ATTACK;
	
	private final int MAX_SECOND_ATTACK_COUNT = 3;
	
	private long lastFootstepTime = 0;
	private final long footstepInterval = 1100;
	
	public ENM_Boss_1(GamePanel gp, int x, int y) {
		super(gp);
		
		this.worldX = x;
		this.worldY = y;
		this.killPoints = 500;
		
		this.setMaxHealth(5000);
		this.setHealth(getMaxHealth());

		this.setSpeed(1);
		this.setDirection("down");
		
		int tileSize = GamePanel.TILE_SIZE;
		int halfTileSize = tileSize / 2;
	    
//		this.setSolidArea(new Rectangle(tileSize * 2, tileSize * 2, tileSize * 2, tileSize * 2));
		this.setSolidArea(new Rectangle(tileSize * 2 + halfTileSize, tileSize * 2 + halfTileSize, tileSize - 5, tileSize - 5));
		
		this.setAttackRange(new Rectangle(0, 0, Entity.DETECTION_RANGE_WIDTH, Entity.DETECTION_RANGE_HEIGHT));
		this.attackDetectionRange = new Rectangle(0, 0, Entity.DETECTION_RANGE_WIDTH * 2, Entity.DETECTION_RANGE_HEIGHT * 2);
//		this.setAttackRange(new Rectangle(-halfTile, -halfTile, attackRangeWidth, attackRangeHeight));
		this.state.attacking.setDuration(240);

		this.gun.fireRate = (float) 0.3;
		this.secondGun.fireRate = 5;
		
		this.type = ENTITY_TYPE.BOSS;
		
		this.sprite = new SpriteManager(this, 40);
		loadSprites();
		updateSpritesInterval();
	}
	
	private void loadSprites() {
		Utils utils = new Utils();
		
		// Load the sprite sheet
	    BufferedImage spritesheet = utils.getSpriteSheet("/enemies/BOSS-Sheet.png");

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
	    	this.sprite.attackingRight2.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    	this.sprite.attackingLeft2.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    	this.sprite.attackingDown2.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    	this.sprite.attackingUp2.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    }
	    
	    for (int i = 0; i < 19; i++) {
	    	this.sprite.attackingRight.addSprite(utils.cropSprite(spritesheet, i * width, 6 * height, width, height));
	    	this.sprite.attackingLeft.addSprite(utils.cropSprite(spritesheet, i * width, 6 * height, width, height));
	    	this.sprite.attackingDown.addSprite(utils.cropSprite(spritesheet, i * width, 6 * height, width, height));
	    	this.sprite.attackingUp.addSprite(utils.cropSprite(spritesheet, i * width, 6 * height, width, height));
	    }
	    
	    // Load attacked sprites
	    for (int i = 0; i < 2; i++) {
	    	this.sprite.attackedRight.addSprite(utils.cropSprite(spritesheet, i * width, 8 * height, width, height));
	        this.sprite.attackedLeft.addSprite(utils.cropSprite(spritesheet, i * width, 8 * height, width, height));
	        this.sprite.attackedDown.addSprite(utils.cropSprite(spritesheet, i * width, 8 * height, width, height));
	        this.sprite.attackedUp.addSprite(utils.cropSprite(spritesheet, i * width, 8 * height, width, height));
	    }

	    // Load dying sprites
	    for (int i = 0; i < 17; i++) {
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
	        gp.em.addBullets(new GunProjectile(gp, centerWorldX, centerWorldY, speedX, speedY, BULLET_DAMAGE));
	    }

	    gun.recordShot();
	    gp.sound.play(gun.sound);
	    return true;
	}
	
	private void startFirstAttack() {
		if (gun.canShoot() && !attackCooldown.getState() && (hasLineOfSight() || state.attacking.getState())) {
			currentAttack = FIRST_ATTACK;
			state.attacking.setState(true);
			movementDisabled = true;
			
			Boolean shouldAttack = state.attacking.getCounter() == state.attacking.getStateDuration() / 2;
			
			if (shouldAttack) {
				firstAttack();
				attackCooldown.setState(true);
				secondAttackCount = 0;
				gp.sound.play(Sound.ROBOT_ATTACK_2);
			}
		}
	}
	
	private Boolean secondAttack() {
		if (attackCooldown.getState() || !this.secondGun.canShoot()) return false;
		
		int tileSize = GamePanel.TILE_SIZE;
		int offset = 5;
		int turretY = worldY + tileSize - offset;
		
		Vector2 target = new Vector2(gp.player.worldX, gp.player.worldY);
		
		Vector2 firstTurretOrigin = new Vector2(worldX + tileSize + offset, turretY);
		
		Vector2 secondTurretOrigin = new Vector2(worldX + tileSize * 3 + tileSize / 2 + offset, turretY);
		
		shootProjectile(secondGun, target, firstTurretOrigin);
		shootProjectile(secondGun, target, secondTurretOrigin);
		
		gp.sound.play(gun.sound);
		this.secondGun.recordShot();
		
		return true;
	}
	
	private void startSecondAttack() {
		if (secondGun.canShoot() && !attackCooldown.getState() && (hasLineOfSight() || state.attacking.getState())) {
			currentAttack = SECOND_ATTACK;
			state.attacking.setState(true);
			
			Boolean shouldAttack = state.attacking.getCounter() > state.attacking.getStateDuration() / 4;
			
			if (shouldAttack) {
				secondAttack();
				projectileShotCount++;
				
				if (projectileShotCount == maxProjectileShotCount) {
					attackCooldown.setState(true);
					projectileShotCount = 0;
					secondAttackCount++;
				}
			}
		}
	}
	
	private void playFootsteps() {
		if (!movementDisabled) {
	        long currentTime = System.currentTimeMillis(); // Get the current time in milliseconds

	        // Check if the interval has passed
	        if (currentTime - lastFootstepTime >= footstepInterval) {
	        	gp.sound.play(Sound.BOSS_FOOTSTEPS);
	            lastFootstepTime = currentTime;
	        }
	    }
	}
	
	@Override
	public Rectangle getSolidAreaRelativeToWorld() {
		int tileSize = GamePanel.TILE_SIZE;
		Rectangle rec = new Rectangle(worldX + tileSize * 2, worldY + tileSize * 2, tileSize * 2, tileSize * 2);
		
		return rec;
	}
	
	@Override
	protected void attack() {
		moveToPlayer();
//		moveTowards(new Vector2(gp.player.worldX, gp.player.worldY));
		
		if (secondAttackCount < MAX_SECOND_ATTACK_COUNT) {
			startSecondAttack();
		} else {
			startFirstAttack();
		}
	}
	
	@Override
	public void update() {
		super.update();
		playFootsteps();
		attackCooldown.update();
	}
	
	@Override
	public void draw(Graphics2D g2) {
		if (isDead) return;

		Vector2 screen = getScreenLocation();
		
		BufferedImage image = sprite.getSprite();
		
		if (state.attacking.getState() && !state.dying.getState()) {
			if (currentAttack == SECOND_ATTACK) {
				image = sprite.getAttack2Sprite();
			}
		}
		
		if (gp.isInPlayerView(new Vector2(worldX, worldY))) g2.drawImage(image, screen.x, screen.y, null);
		drawHealthBar(g2);
	}

}
