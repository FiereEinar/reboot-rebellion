package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gun.GunObject;
import main.GamePanel;
import main.Inventory;
import main.KeyHandler;
import main.Utils;
import object.GameObject;

public class Player extends Entity {

	KeyHandler keys;
	public Inventory inventory = new Inventory();

	public int screenX;
	public int screenY;
	
	private Boolean canPickupWeapon = true;
	private int weaponPickupCooldown = 60;
	private int weaponPickupCooldownCounter = 0;

	public Player(GamePanel gp, KeyHandler keys) {
		super(gp);
		this.keys = keys;
		this.isPlayer = true;

		this.worldX = 100;
		this.worldY = 300;

		this.screenX = gp.screenWidth / 2 - (GamePanel.TILE_SIZE / 2);
		this.screenY = gp.screenHeight / 2 - (GamePanel.TILE_SIZE / 2);

		this.setMaxHealth(4);
		this.setHealth(getMaxHealth());

		this.setSpeed(5);
		this.setDirection("right");

		this.setSolidArea(new Rectangle(10, 16, 28, 28));

		loadSprites();
		updateSpritesInterval();
	}

	private void loadSprites() {
		Utils utils = new Utils();
		
		// Load the sprite sheet
	    BufferedImage spritesheet = utils.getSpriteSheet("/player/Character.png");

	    int width = 41;
	    int height = 41;
	    
	    // Load idle sprites
	    for (int i = 0; i < 2; i++) { 
	        this.sprite.idleRight.addSprite(utils.cropSprite(spritesheet, i * width, 0, width, height));
	        this.sprite.idleLeft.addSprite(utils.cropSprite(spritesheet, i * width, height, width, height));
	    }

	    // Load attacking sprites
	    for (int i = 0; i < 5; i++) { 
	        this.sprite.attackingRight.addSprite(utils.cropSprite(spritesheet, i * width, 2 * height, width, height));
	        this.sprite.attackingLeft.addSprite(utils.cropSprite(spritesheet, i * width, 3 * height, width, height));
	        this.sprite.attackingDown.addSprite(utils.cropSprite(spritesheet, i * width, 2 * height, width, height));
	        this.sprite.attackingUp.addSprite(utils.cropSprite(spritesheet, i * width, 3 * height, width, height));
	    }
	    
	    // Load movement sprites
	    for (int i = 0; i < 4; i++) {
	    	this.sprite.right.addSprite(utils.cropSprite(spritesheet, i * width, 2 * height, width, height));
	    	this.sprite.left.addSprite(utils.cropSprite(spritesheet, i * width, 3 * height, width, height));
	    	this.sprite.down.addSprite(utils.cropSprite(spritesheet, i * width, 2 * height, width, height));
	    	this.sprite.up.addSprite(utils.cropSprite(spritesheet, i * width, 3 * height, width, height));
	    }
	    
	    // Load attacked sprites
	    for (int i = 0; i < 2; i++) {
	    	this.sprite.attackedRight.addSprite(utils.cropSprite(spritesheet, i * width, 4 * height, width, height));
	    	this.sprite.attackedLeft.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    	this.sprite.attackedDown.addSprite(utils.cropSprite(spritesheet, i * width, 4 * height, width, height));
	    	this.sprite.attackedUp.addSprite(utils.cropSprite(spritesheet, i * width, 5 * height, width, height));
	    }

	    // Load dying sprites
	    for (int i = 0; i < 5; i++) { 
	        this.sprite.dying.addSprite(utils.cropSprite(spritesheet, i * width, 6 * height, width, height));
	    }
	}

	public void shootProjectile() {
		if (inventory.arsenalSize() == 0) return;
		if (inventory.getSelectedGunIndex() >= inventory.arsenalSize()) return;
		
		GunObject gun = inventory.getSelectedGun();
		
		if (!gun.canShoot()) return;
		
		int BULLET_SPREAD = gun.bulletSpread;
		int BULLET_SPEED = gun.bulletSpeed;
		int BULLET_MULTIPLIER = gun.bulletMultiplier;
		int BULLET_DAMAGE = gun.damage;
		
		float mouseX = gp.mouse.mouseX;
		float mouseY = gp.mouse.mouseY;

		float directionX = mouseX - screenX;
		float directionY = mouseY - screenY;

		float magnitude = (float) Math.sqrt(directionX * directionX + directionY * directionY);
		float normalizedX = directionX / magnitude;
		float normalizedY = directionY / magnitude;
		
		for (int i = 0; i < BULLET_MULTIPLIER; i++) {
			// Add random spread to the direction
		    float spreadAngle = (float) Math.toRadians(BULLET_SPREAD); // Adjust for more or less spread
		    float randomOffset = (float) (Math.random() * spreadAngle - spreadAngle / 2);
	
		    float cos = (float) Math.cos(randomOffset);
		    float sin = (float) Math.sin(randomOffset);
	
		    // Rotate the normalized vector by the spread angle
		    float spreadX = cos * normalizedX - sin * normalizedY;
		    float spreadY = sin * normalizedX + cos * normalizedY;
	
			float speedX = spreadX * BULLET_SPEED;
			float speedY = spreadY * BULLET_SPEED;
	
			int centerWorldX = worldX + (GamePanel.TILE_SIZE / 2);
			int centerWorldY = worldY + (GamePanel.TILE_SIZE / 2);

			gp.em.addBullets(new Projectile(gp, centerWorldX, centerWorldY, speedX, speedY, BULLET_DAMAGE, true));
		}

		gun.recordShot();
	}

	private void updateDirection() {
		float mouseX = gp.mouse.mouseX;
		float halfScreen = gp.screenWidth / 2;

		if (mouseX < halfScreen) {
			setSpriteDirection("left");
		} else {
			setSpriteDirection("right");
		}
		
		if (keys.UP) {
			this.setDirection("up");
		}
		if (keys.DOWN) {
			this.setDirection("down");
		}
		if (keys.LEFT) {
			this.setDirection("left");
		}
		if (keys.RIGHT) {
			this.setDirection("right");
		}
	}

	private void checkObjectCollisions() {
		GameObject hitObject = gp.cd.checkEntityObjectCollision(this, true);

		if (hitObject != null) {
			if (hitObject.isSolid) {
				this.movementDisabled = true;
			}
			
			// pick up object logic
		}
	}
	
	private void checkGunCollisions() {
		GunObject hitGun = gp.cd.checkEntityGunsCollision(this);
		
		if (hitGun != null) {
			if (inventory.arsenalSize() == 2) {
				gp.ui.setTooltipText("Press 'E' to pick up");
				
				if (keys.KEY_E && canPickupWeapon) {
					GunObject dropGun = inventory.getSelectedGun();
					dropGun.worldX = worldX;
					dropGun.worldY = worldY;

					inventory.getArsenal().set(inventory.getSelectedGunIndex(), hitGun);
					gp.om.addGun(dropGun);
					gp.om.removeGun(hitGun.name);
					
					canPickupWeapon = false;
				}
			} else {
				this.inventory.getArsenal().add(hitGun);
				gp.om.removeGun(hitGun.name);
			}
		}
	}
	
	private void updateWeaponPickupCooldown() {
		if (canPickupWeapon) return;
		
		weaponPickupCooldownCounter++;
		
		if (weaponPickupCooldownCounter >= weaponPickupCooldown) {
			weaponPickupCooldownCounter = 0;
			canPickupWeapon = true;
		}
	}

	private void drawPlayerGun(Graphics2D g2) {
		if (inventory.arsenalSize() == 0) return;
		
		if (inventory.getSelectedGunIndex() >= inventory.arsenalSize()) return;
		
		GunObject gun = inventory.getSelectedGun();

		double angle = Math.atan2(gp.mouse.mouseY - screenY, gp.mouse.mouseX - screenX);

		BufferedImage image = gun.sprite.getSprite();

		int width = image.getWidth();
		int height = image.getHeight();

		// Save the original transform
		Graphics2D gCopy = (Graphics2D) g2.create();

		// Translate to the player's position, rotate, then draw
		gCopy.translate(screenX + GamePanel.TILE_SIZE / 2, screenY + GamePanel.TILE_SIZE / 2 + 5);
		Boolean flip = Math.cos(angle) > 0;
		if (flip) {
	        // Flip the image horizontally
	        gCopy.scale(1, -1); // Invert the X-axis
	        angle *= -1;
	    }

		gCopy.rotate(angle + Math.PI);
		gCopy.drawImage(image, -width / 2, -height / 2, null);

		// Dispose of the copy to reset the transform
		gCopy.dispose();
	}

	@Override
	protected void checkEntitiesCollision() {
		Entity entity = gp.cd.checkEntityCollision(this);

		if (entity != null && !entity.isDead) {
			if (!entity.state.dying.getState()) recieveDamage(entity.damage);
		}
	}

	@Override
	public void update() {
		state.update();
		this.movementDisabled = false;
		updateDirection();
		checkWorldCollision();
		checkEntitiesCollision();
		updateWeaponPickupCooldown();
		
		checkObjectCollisions();
		checkGunCollisions();
		gp.eh.checkEvent();
		if (gp.keys.isMoving())
			updateCoordinates();
		if (gp.mouse.SHOOTING)
			shootProjectile();
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(this.sprite.getSprite(), this.screenX, this.screenY, null);
		drawPlayerGun(g2);
	}

}
