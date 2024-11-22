package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import inventory.Inventory;
import main.GamePanel;
import main.KeyHandler;
import main.Utils;
import object.GameObject;

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
		float mouseX = gp.mouse.mouseX;
		float mouseY = gp.mouse.mouseY;

		float directionX = mouseX - screenX;
        float directionY = mouseY - screenY;

        float magnitude = (float) Math.sqrt(directionX * directionX + directionY * directionY);
        float normalizedX = directionX / magnitude;
        float normalizedY = directionY / magnitude;

        float speed = 16;
        float speedX = normalizedX * speed;
        float speedY = normalizedY * speed;
        
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

	@Override
	public void update() {
		this.movementDisabled = false;
		updateDirection();
		state.update();
		checkWorldCollision();
		checkEntitiesCollision();

		checkObjectCollisions();
		gp.eh.checkEvent();
		if (gp.keys.isMoving()) updateCoordinates();
		if (gp.mouse.SHOOTING) shootProjectile();
	}

	@Override
	public void draw(Graphics2D g2) {
		if (state.isInvincible()) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}

		g2.drawImage(this.sprite.getSprite(), this.screenX, this.screenY, null);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

}
