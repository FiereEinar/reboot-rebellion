package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import inventory.Inventory;
import main.GamePanel;
import main.KeyHandler;
import main.Renderable;
import main.Utils;
import object.GameObject;

public class Player extends Entity implements Renderable {

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

		this.maxHealth = 6;
		this.health = maxHealth;

		this.setSpeed(4);
		this.setDirection("down");

		this.setSolidArea(new Rectangle(8, 16, 32, 32));

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

	@Override
	public void update() {
		this.movementDisabled = false;
		updateDirection();
		gp.cd.checkWorldCollision(this);
		checkObjectCollisions();
		checkEntitiesCollision();
		gp.eh.checkEvent();
		updateCoordinates();
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(this.sprite.getSprite(), this.screenX, this.screenY, null);
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

	@Override
	public void updateCoordinates() {
		if (this.movementDisabled)
			return;
		if (!keys.isMoving())
			return;

		if (this.getDirection().equalsIgnoreCase("up") || keys.UP) {
			this.worldY -= this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("down") || keys.DOWN) {
			this.worldY += this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("left") || keys.LEFT) {
			this.worldX -= this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("right") || keys.RIGHT) {
			this.worldX += this.getSpeed();
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
		Entity hitEntity = gp.cd.checkEntityCollision(this);

		if (hitEntity != null) {
			this.movementDisabled = true;
		}
	}

}
