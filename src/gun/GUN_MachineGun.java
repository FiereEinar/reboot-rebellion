package gun;

public class GUN_MachineGun extends GunObject {

	public GUN_MachineGun(int x, int y) {
		this.damage = 3;
		this.bulletSpeed = 40;
		this.bulletSpread = 10;
		this.bulletMultiplier = 1;
		this.fireRate = 15;
		
		this.worldX = x;
		this.worldY = y;
		
		this.name = "machine_gun";
		
		loadSprites();
	}
	
	private void loadSprites() {
		this.sprite.addSprite(utils.getAndScaleImage("/guns/machine_gun.png", 85, 40));
	}
}
