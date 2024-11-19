package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;
import main.Renderable;
import main.Utils;

public class Enemy_Robot_1 extends Entity implements Renderable {
	
	public Enemy_Robot_1(GamePanel gp) {
		super(gp);
		
		this.worldX = 200;
		this.worldY = 800;

		this.setSpeed(2);
		this.setDirection("down");

		this.setSolidArea(new Rectangle(8, 16, 32, 32));

		loadSprites();
	}
	
	private void loadSprites() {
		Utils utils = new Utils();

		this.sprite.up.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_front_1.png", gp.tileSize, gp.tileSize));
		this.sprite.up.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_front_2.png", gp.tileSize, gp.tileSize));
		
		this.sprite.down.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_back_1.png", gp.tileSize, gp.tileSize));
		this.sprite.down.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_back_2.png", gp.tileSize, gp.tileSize));
		
		this.sprite.left.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_left_1.png", gp.tileSize, gp.tileSize));
		this.sprite.left.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_left_2.png", gp.tileSize, gp.tileSize));
		
		this.sprite.right.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_right_1.png", gp.tileSize, gp.tileSize));
		this.sprite.right.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_right_2.png", gp.tileSize, gp.tileSize));
	}

	@Override
	public void update() {
		actionLockCounter++;
		
		if (actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;
			
			if (i <= 25) {
				setDirection("up");
			}
			if (i > 25 && i <= 50) {
				setDirection("down");
			}
			if (i > 50 && i <= 75) {
				setDirection("left");
			}
			if (i > 75 && i <= 100) {
				setDirection("right");
			}

			actionLockCounter = 0;
		}
		
		if (this.getDirection().equalsIgnoreCase("up")) {
			this.worldY -= this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("down")) {
			this.worldY += this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("left")) {
			this.worldX -= this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("right")) {
			this.worldX += this.getSpeed();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		g2.drawImage(this.sprite.getSprite(), screenX, screenY, gp.tileSize, gp.tileSize, null);
	}

}
