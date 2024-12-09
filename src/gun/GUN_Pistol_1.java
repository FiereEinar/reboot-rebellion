package gun;

import main.Sound;

public class GUN_Pistol_1 extends GunObject {

	public GUN_Pistol_1(int x, int y) {
		this.damage = 10;
		this.bulletSpeed = 35;
		this.bulletSpread = 5;
		this.bulletMultiplier = 1;
		this.fireRate = 5;
		this.reloadTime = 60;
		
		this.worldX = x;
		this.worldY = y;
		
		this.reloading.setDuration(reloadTime);
		this.setReservedAmmo(30);
		this.setMagSize(20);
		this.setCurrentMag(this.getMagSize());
		
		this.name = "gun_pistol_1";
		this.sound = Sound.PISTOL_SHOT;
		loadSprites();
	}
	
	private void loadSprites() {
		this.sprite.addSprite(utils.getAndScaleImage("/guns/pistol2.png", 43, 24));
	}

}
