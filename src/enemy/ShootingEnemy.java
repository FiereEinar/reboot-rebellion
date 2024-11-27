package enemy;

import entity.Projectile;
import gun.GUN_EnemyWeapon;
import gun.GunObject;
import main.GamePanel;

public class ShootingEnemy extends Enemy {
	
	GUN_EnemyWeapon gun = new GUN_EnemyWeapon(); 
	
	public ShootingEnemy(GamePanel gp) {
		super(gp);
	}
		
	protected Boolean shootProjectile(GunObject gun) {
		if (!gun.canShoot()) return false;
		
		int BULLET_SPREAD = gun.bulletSpread;
		int BULLET_SPEED = gun.bulletSpeed;
		int BULLET_MULTIPLIER = gun.bulletMultiplier;
		int BULLET_DAMAGE = gun.damage;

		float directionX = gp.player.worldX - worldX;
		float directionY = gp.player.worldY - worldY;

		float magnitude = (float) Math.sqrt(directionX * directionX + directionY * directionY);
		float normalizedX = directionX / magnitude;
		float normalizedY = directionY / magnitude;
		
		int centerWorldX = worldX + (GamePanel.tileSize / 2);
		int centerWorldY = worldY + (GamePanel.tileSize / 2);

		for (int i = 0; i < BULLET_MULTIPLIER; i++) {
			// Add random spread to the direction
		    float spreadAngle = (float) Math.toRadians(BULLET_SPREAD); // Adjust for more or less spread
		    float randomOffset = (float) (Math.random() * spreadAngle - spreadAngle / 2);
	
		    float cos = (float) Math.cos(randomOffset);
		    float sin = (float) Math.sin(randomOffset);
	
		    // Rotate the normalized vector by the spread angle
		    float spreadX = cos * normalizedX - sin * normalizedY;
		    float spreadY = sin * normalizedX + cos * normalizedY;
	
			float speedX = spreadX * BULLET_SPEED;
			float speedY = spreadY * BULLET_SPEED;

			gp.em.addBullets(new Projectile(gp, centerWorldX, centerWorldY, speedX, speedY, BULLET_DAMAGE));
		}

		gun.recordShot();
		return true;
	}

}
