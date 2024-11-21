package entity;

import java.awt.Rectangle;

import main.GamePanel;
import main.Utils;

public class Enemy_Robot_1 extends Entity {

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

}
