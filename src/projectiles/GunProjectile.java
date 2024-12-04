package projectiles;

import main.GamePanel;
import main.Utils;

public class GunProjectile extends Projectile {

	public GunProjectile(GamePanel gp, int x, int y, float speedX, float speedY, int damage) {
		super(gp, x, y, speedX, speedY, damage);
	}
	
	public GunProjectile(GamePanel gp, int x, int y, float speedX, float speedY, int damage, Boolean fromPlayer) {
		super(gp, x, y, speedX, speedY, damage, fromPlayer);
	}

	@Override
	protected void loadSprites() {
		Utils utils = new Utils();
		
		for (int i = 0; i < 2; i++) { 
			this.sprite.addSprite(utils.getAndScaleImage("/projectiles/gun_shots.png", getSolidArea().width, getSolidArea().height));
	    }
	}

}
