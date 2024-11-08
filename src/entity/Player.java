package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandlerTemplate;
import main.Renderable;

public class Player extends Entity implements Renderable {

	KeyHandlerTemplate keys;
	GamePanel gp;

	public int screenX;
	public int screenY;

	public Player(GamePanel gp, KeyHandlerTemplate keys) {
		this.gp = gp;
		this.keys = keys;

		this.worldX = 50;
		this.worldY = 50;

		this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		this.setSpeed(5);
		this.setDirection("down");

		this.solidArea = new Rectangle(8, 16, 32, 32);

		loadSprites();
	}

	private void loadSprites() {
		try {
			this.sprite.up.addSprite(
					ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_front_1.png")));
			this.sprite.up.addSprite(
					ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_front_2.png")));

			this.sprite.down.addSprite(
					ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_back_1.png")));
			this.sprite.down.addSprite(
					ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_back_2.png")));

			this.sprite.left.addSprite(
					ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_left_1.png")));
			this.sprite.left.addSprite(
					ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_left_2.png")));

			this.sprite.right.addSprite(
					ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_right_1.png")));
			this.sprite.right.addSprite(
					ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_right_2.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		updateDirection();
		this.movementDisabled = false;
		gp.cd.checkWorldCollision(this);
		updateCoordinates();
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(this.sprite.getSprite(), this.screenX, this.screenY, gp.tileSize, gp.tileSize, null);
	}

	private void updateDirection() {
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

	private void updateCoordinates() {
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

}
