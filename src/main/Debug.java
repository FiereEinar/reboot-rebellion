package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;

import entity.Entity;
import entity.Player;
import entity.Vector2;
import object.GameObject;
import pathfinder.Node;
import projectiles.Projectile;

public class Debug implements Renderable {

	GamePanel gp;

	public Debug(GamePanel gp) {
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
		if (gp.keys.LOG_SWITCH) {
			
			// player hitbox
			g2.setColor(Color.RED);
			g2.drawRect(gp.player.screenX + gp.player.getSolidArea().x, gp.player.screenY + gp.player.getSolidArea().y,
					gp.player.getSolidArea().width, gp.player.getSolidArea().height);
			
			// entities hitbox
			for (Entity e: gp.em.getEnities()) {
				Vector2 screen = e.getScreenLocation();
				g2.setColor(Color.RED);
				g2.drawRect(screen.x + e.getSolidArea().x, screen.y + e.getSolidArea().y,
						e.getSolidArea().width, e.getSolidArea().height);
			}
			
			for (Projectile p: gp.em.getProjectiles()) {	
				Vector2 screen = p.getScreenLocation();
				g2.setColor(Color.RED);
				g2.drawRect(screen.x + p.getSolidArea().x, screen.y + p.getSolidArea().y,
						p.getSolidArea().width, p.getSolidArea().height);
			}
			
			// objects hitbox
			for (GameObject o: gp.om.getObjects()) {
				Rectangle rec = o.getSolidAreaRelativeToWorld();
				rec.x = rec.x - gp.player.worldX + gp.player.screenX;
				rec.y = rec.y - gp.player.worldY + gp.player.screenY;
				
				g2.draw(rec);
			}

			// pathfinding guide
			g2.setColor(new Color(255, 0, 0, 70));
			
			for (int i = 0; i < gp.pathFinder.pathList.size(); i++) {
				Node curr = gp.pathFinder.pathList.get(i);
				Player player = gp.player;
				int tileSize = GamePanel.TILE_SIZE;
				
				int worldX = curr.position.x * tileSize;
				int worldY = curr.position.y * tileSize;
				int screenX = worldX - player.worldX + player.screenX;
				int screenY = worldY - player.worldY + player.screenY;
				
				g2.fillRect(screenX, screenY, tileSize, tileSize);
			}

		}
	}

}
