package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;

import entity.Entity;
import entity.Vector2;

public class Debug implements Renderable {

	GamePanel gp;

	public Debug(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void update() {
		if (gp.keys.LOG_SWITCH) {
//			Point p = MouseInfo.getPointerInfo().getLocation();
//			System.out.println("Mouse Position: X = " + p.getX() + " | Y = " + p.getY());
//
//			System.out.println("Player World Position: X = " + gp.player.worldX + " | Y = " + gp.player.worldY);
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		if (gp.keys.LOG_SWITCH) {
			// player hitbox
			g2.setColor(Color.RED);
			g2.drawRect(gp.player.screenX + gp.player.getSolidArea().x, gp.player.screenY + gp.player.getSolidArea().y,
					gp.player.getSolidArea().width, gp.player.getSolidArea().height);

			// entities hitbox
			for (Entity e: gp.em.getEnities()) {
				Vector2 screen = e.getScreenLocation();
				g2.drawRect(screen.x + gp.player.getSolidArea().x, screen.y + gp.player.getSolidArea().y,
						gp.player.getSolidArea().width, gp.player.getSolidArea().height);
			}
		}
	}

}
