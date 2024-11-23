package enemy;

import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;
import main.Utils;

public class ENM_Ranger_1 extends Entity {

	public ENM_Ranger_1(GamePanel gp, int x, int y) {
		super(gp);
		
		this.worldX = x;
		this.worldY = y;
		
		this.setMaxHealth(100);
		this.setHealth(getMaxHealth());

		this.setSpeed(2);
		this.setDirection("down");

		this.setSolidArea(new Rectangle(8, 16, 32, 32));
		
		loadSprites();
		updateSpritesInterval();
	}
	
	private void loadSprites() {
		Utils utils = new Utils();

		this.sprite.up.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_front_1.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.up.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_front_2.png", GamePanel.tileSize, GamePanel.tileSize));
		
		this.sprite.down.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_back_1.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.down.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_back_2.png", GamePanel.tileSize, GamePanel.tileSize));
		
		this.sprite.left.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_left_1.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.left.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_left_2.png", GamePanel.tileSize, GamePanel.tileSize));
		
		this.sprite.right.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_right_1.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.right.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_movement/move_right_2.png", GamePanel.tileSize, GamePanel.tileSize));
		
		this.sprite.dying.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_dying/Dead 1.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.dying.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_dying/Dead 2.png", GamePanel.tileSize, GamePanel.tileSize));
		this.sprite.dying.addSprite(
				utils.getAndScaleImage("/robot_3_ranger_dying/Dead 3.png", GamePanel.tileSize, GamePanel.tileSize));
	}

}
