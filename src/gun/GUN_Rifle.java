package gun;

public class GUN_Rifle extends GunObject {

	public GUN_Rifle(int x, int y) {
		this.damage = 12;
		this.bulletSpeed = 40;
		this.bulletSpread = 6;
		this.bulletMultiplier = 1;
		this.fireRate = 12;
		this.reloadTime = 100;
		
		this.worldX = x;
		this.worldY = y;
		
		this.reloading.setDuration(reloadTime);
		this.setReservedAmmo(200);
		this.setMagSize(50);
		this.setCurrentMag(this.getMagSize());
		
		this.name = "rifle";
		
		loadSprites();
	}
	
	private void loadSprites() {
		this.sprite.addSprite(utils.getAndScaleImage("/guns/rifle.png", 78, 25));
	}

}
