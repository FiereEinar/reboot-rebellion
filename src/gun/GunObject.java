package gun;

import main.Utils;
import object.GameObject;
import sprite.Sprite;
import states.State;

public class GunObject extends GameObject {

	public int damage;
	public int bulletSpeed;
	public int bulletSpread;
	public int bulletMultiplier;
	public int reloadTime = 120;
	public float fireRate;
	private long lastShotTime;
	
	private int reservedAmmo;
	private int currentMag;
	private int magSize;

	public State reloading = new State(reloadTime);
	
	public Sprite shooting = new Sprite();
	Utils utils = new Utils();
	
	public Boolean canShoot() {
		long currentTime = System.currentTimeMillis();
		return currentTime - lastShotTime >= (1000 / fireRate) && hasAmmo() && !reloading.getState();
	}
	
	public Boolean hasAmmo() {
		return currentMag > 0;
	}
	
	public void recordShot() {
		reduceCurrentMag(1);
		lastShotTime = System.currentTimeMillis();
	}

	public int getReservedAmmo() {
		return reservedAmmo;
	}

	public void setReservedAmmo(int reservedAmmo) {
		this.reservedAmmo = reservedAmmo;
	}
	
	public void reduceReservedAmmo(int amount) {
		if (reservedAmmo <= 0) return;
		
		this.reservedAmmo -= amount;
	}

	public int getCurrentMag() {
		return currentMag;
	}

	public void reduceCurrentMag(int amount) {
		if (currentMag <= 0) return;
		
		this.currentMag -= amount;
	}
	
	public void setCurrentMag(int mag) {
		this.currentMag = mag;
	}

	public int getMagSize() {
		return magSize;
	}

	public void setMagSize(int magSize) {
		this.magSize = magSize;
	}
	
	public void handleReload() {
		int remainingAmmo = getCurrentMag();
		setCurrentMag(getMagSize());
		reduceReservedAmmo(getMagSize() - remainingAmmo);
		reloading.resetIsTriggered();
	}
	
	public void update() {
		reloading.update();
	}

}
