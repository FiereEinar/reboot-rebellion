package enemy;

import entity.Projectile;
import entity.Vector2;
import gun.GUN_EnemyWeapon;
import gun.GunObject;
import main.GamePanel;

public class ShootingEnemy extends Enemy {
	
	GUN_EnemyWeapon gun = new GUN_EnemyWeapon(); 
	
	public ShootingEnemy(GamePanel gp) {
		super(gp);
	}
		
	protected void shootProjectile(GunObject gun, Vector2 target) {
		Vector2 start = new Vector2(
			worldX + (GamePanel.TILE_SIZE / 2),
			worldY + (GamePanel.TILE_SIZE / 2)
		);
		
		Vector2 direction = new Vector2(
			target.x - worldX,
			target.y - worldY
		);

		shoot(gun, start, direction);
	}
	
	protected void shootProjectile(GunObject gun, Vector2 target, Vector2 origin) {
		Vector2 start = new Vector2(
			origin.x + (GamePanel.TILE_SIZE / 2),
			origin.y + (GamePanel.TILE_SIZE / 2)
		);
		
		Vector2 direction = new Vector2(
			target.x - origin.x,
			target.y - origin.y
		);

		shoot(gun, start, direction);
	}
	
	private void shoot(GunObject gun, Vector2 start, Vector2 direction) {
		int BULLET_SPREAD = gun.bulletSpread;
		int BULLET_SPEED = gun.bulletSpeed;
		int BULLET_MULTIPLIER = gun.bulletMultiplier;
		int BULLET_DAMAGE = gun.damage;

		float magnitude = (float) Math.sqrt(direction.x * direction.x + direction.y * direction.y);
		float normalizedX = direction.x / magnitude;
		float normalizedY = direction.y / magnitude;

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

			gp.em.addBullets(new Projectile(gp, start.x, start.y, speedX, speedY, BULLET_DAMAGE));
		}

	}

}
