package projectiles;

import java.awt.image.BufferedImage;

import main.GamePanel;
import main.Utils;

public class BossProjectile extends Projectile {

	public BossProjectile(GamePanel gp, int x, int y, float speedX, float speedY, int damage) {
		super(gp, x, y, speedX, speedY, damage);
	}
	
	public BossProjectile(GamePanel gp, int x, int y, float speedX, float speedY, int damage, Boolean fromPlayer) {
		super(gp, x, y, speedX, speedY, damage, fromPlayer);
	}

	@Override
	protected void loadSprites() {
		Utils utils = new Utils();
		
		BufferedImage spritesheet = utils.getSpriteSheet("/projectiles/boss_projectile.png");
		
		int width = 32;
		int height = 32;

		for (int i = 0; i < 2; i++) { 
			this.sprite.addSprite(utils.scaleImage(utils.cropSprite(spritesheet, i * width, 9 * height, width, height), getSolidArea().width, getSolidArea().height));
	    }
	}

}
