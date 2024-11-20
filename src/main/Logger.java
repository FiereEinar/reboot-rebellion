package main;

import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;

public class Logger implements Renderable {

	GamePanel gp;
	
	public Logger(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void update() {
		if (gp.keys.LOG_SWITCH) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			System.out.println("Mouse Position: X = " + p.getX() + " | Y = " + p.getY());
			
			System.out.println("Player World Position: X = " + gp.player.worldX + " | Y = " + gp.player.worldY);
		}
	}

	@Override
	public void draw(Graphics2D g2) {
	}

}
