package gun;

import object.GameObject;
import sprite.Sprite;

public class GunObject extends GameObject {

	public int damage;
	public int bulletSpeed;
	public int bulletSpread;
	public float fireRate;
	public Sprite shooting = new Sprite();
	private long lastShotTime;
	
	public Boolean canShoot() {
		long currentTime = System.currentTimeMillis();
		return currentTime - lastShotTime >= (1000 / fireRate);
	}
	
	public void recordShot() {
		lastShotTime = System.currentTimeMillis();
	}

}
