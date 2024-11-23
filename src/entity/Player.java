package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import inventory.Inventory;
import main.GamePanel;
import main.KeyHandler;
import main.Utils;
import object.GameObject;
import object.OBJ_Gun;

public class Player extends Entity {

	KeyHandler keys;
	Inventory inventory = new Inventory();

	public int screenX;
	public int screenY;

	public Player(GamePanel gp, KeyHandler keys) {
		super(gp);
		this.keys = keys;
		this.isPlayer = true;

		this.worldX = 100;
		this.worldY = 400;

		this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		this.setMaxHealth(6);
		this.setHealth(getMaxHealth());

		this.setSpeed(4);
		this.setDirection("down");

		this.setSolidArea(new Rectangle(10, 16, 28, 28));

		loadSprites();
	}

	private void loadSprites() {
		Utils utils = new Utils();

		this.sprite.up.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_1.png", gp.tileSize, gp.tileSize));
		this.sprite.up.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_2.png", gp.tileSize, gp.tileSize));
		this.sprite.up.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_3.png", gp.tileSize, gp.tileSize));
		this.sprite.up.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_4.png", gp.tileSize, gp.tileSize));

		this.sprite.down
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_1.png", gp.tileSize, gp.tileSize));
		this.sprite.down
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_2.png", gp.tileSize, gp.tileSize));
		this.sprite.down
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_3.png", gp.tileSize, gp.tileSize));
		this.sprite.down
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_4.png", gp.tileSize, gp.tileSize));

		this.sprite.left
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_1.png", gp.tileSize, gp.tileSize));
		this.sprite.left
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_2.png", gp.tileSize, gp.tileSize));
		this.sprite.left
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_3.png", gp.tileSize, gp.tileSize));
		this.sprite.left
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_right_4.png", gp.tileSize, gp.tileSize));

		this.sprite.right
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_1.png", gp.tileSize, gp.tileSize));
		this.sprite.right
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_2.png", gp.tileSize, gp.tileSize));
		this.sprite.right
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_3.png", gp.tileSize, gp.tileSize));
		this.sprite.right
				.addSprite(utils.getAndScaleImage("/skeleton/skeleton2_v2_left_4.png", gp.tileSize, gp.tileSize));
	}

	public void shootProjectile() {
		int BULLET_SPREAD = 5;
		int BULLET_SPEED = 20;
		
		float mouseX = gp.mouse.mouseX;
		float mouseY = gp.mouse.mouseY;

		float directionX = mouseX - screenX;
		float directionY = mouseY - screenY;

		float magnitude = (float) Math.sqrt(directionX * directionX + directionY * directionY);
		float normalizedX = directionX / magnitude;
		float normalizedY = directionY / magnitude;
		
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

		int centerWorldX = worldX + (gp.tileSize / 2);
		int centerWorldY = worldY + (gp.tileSize / 2);

		gp.em.addBullets(new Projectile(gp, centerWorldX, centerWorldY, speedX, speedY, damage));
	}

	@Override
	public void updateDirection() {
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

			this.inventory.addInventoryItem(hitObject);
			gp.om.removeObject(hitObject.name);
			System.out.println("Player Inventory: ");
			for (GameObject o : this.inventory.getItems()) {
				System.out.println(o.name);
			}
		}
	}

	private void checkEntitiesCollision() {
		Entity entity = gp.cd.checkEntityCollision(this);

		if (entity != null) {
			this.movementDisabled = true;
			recieveDamage(entity.damage);
		}
	}

	private void drawPlayerGun(Graphics2D g2) {
		Utils utils = new Utils();
		int size = 40;

		double angle = Math.atan2(gp.mouse.mouseY - screenY, gp.mouse.mouseX - screenX);

		OBJ_Gun gun = (OBJ_Gun) gp.om.getObject(gp.om.GUN_INDEX);
		BufferedImage image = gun.sprite.getSprite();
		image = utils.scaleImage(image, size, size);

		int width = image.getWidth();
		int height = image.getHeight();

		// Save the original transform
		Graphics2D gCopy = (Graphics2D) g2.create();

		// Translate to the player's position, rotate, then draw
		gCopy.translate(screenX + gp.tileSize / 2, screenY + gp.tileSize / 2 + 5);
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
	public void update() {
		this.movementDisabled = false;
		updateDirection();
		state.update();
		checkWorldCollision();
		checkEntitiesCollision();

		checkObjectCollisions();
		gp.eh.checkEvent();
		if (gp.keys.isMoving())
			updateCoordinates();
		if (gp.mouse.SHOOTING)
			shootProjectile();
	}

	@Override
	public void draw(Graphics2D g2) {
		if (state.isInvincible()) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}

		g2.drawImage(this.sprite.getSprite(), this.screenX, this.screenY, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		drawPlayerGun(g2);
	}

}
