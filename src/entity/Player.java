package entity;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.Renderable;

public class Player extends Entity implements Renderable {

	KeyHandler keys;
	GamePanel gp;
	
	public int screenX;
	public int screenY;
	
	public Player(GamePanel gp, KeyHandler keys) {
		this.gp = gp;
		this.keys = keys;
		
		this.worldX = 50;
		this.worldY = 50;
		
		this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		this.setSpeed(5);
		this.setDirection("down");
		
		loadSprites();
	}
	
	private void loadSprites() {
		try {
			this.sprite.up.addSprite(ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_front_1.png")));
			this.sprite.up.addSprite(ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_front_2.png")));
			
			this.sprite.down.addSprite(ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_back_1.png")));
			this.sprite.down.addSprite(ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_back_2.png")));
			
			this.sprite.left.addSprite(ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_left_1.png")));
			this.sprite.left.addSprite(ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_left_2.png")));
			
			this.sprite.right.addSprite(ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_right_1.png")));
			this.sprite.right.addSprite(ImageIO.read(getClass().getResourceAsStream("/robot_3_ranger_movement/move_right_2.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		if (keys.UP) {	
			setDirection("up");
			worldY -= getSpeed();
		}
		if (keys.DOWN) {
			setDirection("down");
			worldY += getSpeed();
		}
		if (keys.LEFT) {
			setDirection("left");
			worldX -= getSpeed();
		}
		if (keys.RIGHT) {
			setDirection("right");
			worldX += getSpeed();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(this.sprite.getSprite(), this.screenX, this.screenY, gp.tileSize, gp.tileSize, null);
	}

}
