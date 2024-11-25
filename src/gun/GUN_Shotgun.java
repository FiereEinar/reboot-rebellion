package gun;

public class GUN_Shotgun extends GunObject {

	public GUN_Shotgun(int x, int y) {
		this.damage = 20;
		this.bulletSpeed = 45;
		this.bulletSpread = 35;
		this.bulletMultiplier = 5;
		this.fireRate = 1;
		
		this.worldX = x;
		this.worldY = y;
		
		this.name = "gun_shotgun";
		
		loadSprites();
	}
	
	private void loadSprites() {
		this.sprite.addSprite(utils.getAndScaleImage("/guns/shotgun.png", 66, 20));
	}

}
