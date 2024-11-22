package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;
import main.Renderable;

public class Projectile extends BaseEntity implements Renderable {
	
	GamePanel gp;
	float speedX, speedY;
	int damage;
	Boolean isDead = false;

	public Projectile(GamePanel gp, int x, int y, float speedX, float speedY, int damage) {
		this.gp = gp;
		this.worldX = x;
		this.worldY = y;
		
		this.speedX = speedX;
		this.speedY = speedY;
		
		this.damage = damage;
		
		this.setSolidArea(new Rectangle(0, 0, 8, 8));
		
		loadSprites();
	}

	private void loadSprites() {
	}
	
	public Vector2 getScreenLocation() {
		Vector2 res = new Vector2();

		res.x = worldX - gp.player.worldX + gp.player.screenX;
		res.y = worldY - gp.player.worldY + gp.player.screenY;

		return res;
	}

	@Override
	public void update() {
		worldX += speedX;
		worldY += speedY;
		
		if (gp.cd.checkWorldCollision(this, speedX, speedY)) {
			isDead = true;
		}
		
		Entity hitEntity = gp.cd.checkEntityCollision(this);
		
		if (hitEntity != null) {
			hitEntity.recieveDamage(damage);
			isDead = true;
		}
		
        if (worldX < 0 || worldX > gp.worldWidth || worldY < 0 || worldY > gp.worldHeight) {
            isDead = true;
        }
	}

	@Override
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		g2.fillOval(screenX, screenY, getSolidArea().width, getSolidArea().height);
	}

}
