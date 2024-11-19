package entity;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Renderable;

public class Enemy extends Entity implements Renderable {

	GamePanel gp;
	
	public Enemy(GamePanel gp) {
		this.gp = gp;

		this.worldX = 50;
		this.worldY = 50;
		
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
	}

	@Override
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		g2.drawImage(this.sprite.getSprite(), screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
	
	

}
