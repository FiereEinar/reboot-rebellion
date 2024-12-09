package gun;

import main.Sound;

public class GUN_Sniper extends GunObject {

	public GUN_Sniper(int x, int y) {
		this.damage = 250;
		this.bulletSpeed = 55;
		this.bulletSpread = 1;
		this.bulletMultiplier = 1;
		this.fireRate = (float) 0.5;
		this.reloadTime = 180;
		
		this.worldX = x;
		this.worldY = y;
		
		this.reloading.setDuration(reloadTime);
		this.setReservedAmmo(50);
		this.setMagSize(5);
		this.setCurrentMag(this.getMagSize());
		
		this.name = "gun_sniper";
		this.sound = Sound.SNIPER_SHOT;
		loadSprites();
	}
	
	private void loadSprites() {
		this.sprite.addSprite(utils.getAndScaleImage("/guns/sniper.png", 74, 40));
	}

}
