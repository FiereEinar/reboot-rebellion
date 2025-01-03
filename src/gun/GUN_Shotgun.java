package gun;

import main.Sound;

public class GUN_Shotgun extends GunObject {

	public GUN_Shotgun(int x, int y) {
		this.damage = 20;
		this.bulletSpeed = 45;
		this.bulletSpread = 45;
		this.bulletMultiplier = 5;
		this.fireRate = 1;
		
		this.worldX = x;
		this.worldY = y;
		
		this.reloading.setDuration(reloadTime);
		this.setReservedAmmo(100);
		this.setMagSize(10);
		this.setCurrentMag(this.getMagSize());
		
		this.name = "gun_shotgun";
		this.sound = Sound.SHOTGUN_SHOT;
		loadSprites();
	}
	
	private void loadSprites() {
		this.sprite.addSprite(utils.getAndScaleImage("/guns/shotgun.png", 66, 20));
	}

}
