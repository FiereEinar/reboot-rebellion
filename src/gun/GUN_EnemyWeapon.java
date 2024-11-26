package gun;

public class GUN_EnemyWeapon extends GunObject {

	public GUN_EnemyWeapon() {
		this.damage = 1;
		this.bulletSpeed = 10;
		this.bulletSpread = 1;
		this.bulletMultiplier = 1;
		this.fireRate = (float) 0.5;
		
		this.worldX = 0;
		this.worldY = 0;
		
		this.name = "enemy_weapon";
		
		loadSprites();
	}
	
	private void loadSprites() {
		// maybe just remove this?
		this.sprite.addSprite(utils.getAndScaleImage("/guns/pistol.png", 40, 24));
	}

}
