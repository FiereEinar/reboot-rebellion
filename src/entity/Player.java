package entity;

import java.awt.Graphics2D;

import main.GamePanel;
import main.KeyHandler;
import main.Renderable;

public class Player extends Entity implements Renderable {

	KeyHandler keys;
	GamePanel gp;
	
	public Player(GamePanel gp, KeyHandler keys) {
		this.gp = gp;
		this.keys = keys;
		
		this.x = gp.screenWidth / 2;
		this.y = gp.screenHeight / 2;
		this.setSpeed(5);
	}

	@Override
	public void update() {
		if (keys.UP) {	
			y -= getSpeed();
		}
		if (keys.DOWN) {
			y += getSpeed();
		}
		if (keys.LEFT) {
			x -= getSpeed();
		}
		if (keys.RIGHT) {
			x += getSpeed();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.fillOval(this.x, this.y, 20, 20);
	}

}
