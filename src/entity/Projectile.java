package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.Renderable;
import main.Utils;
import sprite.Sprite;

public class Projectile extends BaseEntity implements Renderable {

	GamePanel gp;
	
	float speedX, speedY;
	int damage;
	
	Boolean isDead = false;
	Boolean fromPlayer = false;
	
	Sprite sprite = new Sprite();
	
	public static final int DESPAWN_RANGE = 600;
	public static final int SIZE = 16;

	public Projectile(GamePanel gp, int x, int y, float speedX, float speedY, int damage) {
		__init__(gp, x, y, speedX, speedY, damage);
	}
	
	public Projectile(GamePanel gp, int x, int y, float speedX, float speedY, int damage, Boolean fromPlayer) {
		__init__(gp, x, y, speedX, speedY, damage);
		this.fromPlayer = fromPlayer;
	}
	
	private void __init__(GamePanel gp, int x, int y, float speedX, float speedY, int damage) {
		this.gp = gp;
		this.worldX = x;
		this.worldY = y;

		this.speedX = speedX;
		this.speedY = speedY;

		this.damage = damage;

		this.setSolidArea(new Rectangle(0, 0, SIZE, SIZE));

		loadSprites();
	}

	private void loadSprites() {
		Utils utils = new Utils();
		
		BufferedImage spritesheet = utils.getSpriteSheet("/projectiles/boss_projectile.png");
		
		int width = 32;
		int height = 32;

		for (int i = 0; i < 2; i++) { 
			this.sprite.addSprite(utils.scaleImage(utils.cropSprite(spritesheet, i * width, 9 * height, width, height), getSolidArea().width, getSolidArea().height));
	    }
		
	}

	public Vector2 getScreenLocation() {
		Vector2 res = new Vector2();

		res.x = worldX - gp.player.worldX + gp.player.screenX;
		res.y = worldY - gp.player.worldY + gp.player.screenY;

		return res;
	}
	
	private void checkIfTooFarFromPlayer() {
		int range = DESPAWN_RANGE;

		if (gp.player.worldX - worldX > range || 
			gp.player.worldX + range < worldX || 
			gp.player.worldY - worldY > range || 
			gp.player.worldY + range < worldY
		) {
			isDead = true;
		}
	}
	
	private void checkCollisions() {
		if (gp.cd.checkWorldCollision(this, speedX, speedY)) {
			isDead = true;
		}
		
		if (fromPlayer) {
			Entity hitEntity = gp.cd.checkEntityCollision(this);
			
			if (hitEntity != null) {
				hitEntity.recieveDamage(damage);
				isDead = true;
			}
		} else {
			if (gp.cd.isCollidingWithPlayer(this)) {
				gp.player.recieveDamage(damage);
				isDead = true;
			}
		}
	}

	@Override
	public void update() {
		worldX += speedX;
		worldY += speedY;
		checkCollisions();
		checkIfTooFarFromPlayer();
	}

	@Override
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
//		g2.fillOval(screenX, screenY, getSolidArea().width, getSolidArea().height);
		g2.drawImage(this.sprite.getSprite(), screenX, screenY, null);
	}

}
