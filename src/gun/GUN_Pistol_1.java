package gun;

import main.GamePanel;

public class GUN_Pistol_1 extends GunObject {

	public GUN_Pistol_1(int x, int y) {
		this.damage = 2;
		this.bulletSpeed = 35;
		this.bulletSpread = 5;
		this.bulletMultiplier = 1;
		this.fireRate = 5;
		
		this.worldX = x;
		this.worldY = y;
		
		this.name = "gun_pistol_1";
		
		loadSprites();
	}
	
	private void loadSprites() {
		this.sprite.addSprite(utils.getAndScaleImage("/guns/gun_1.png", GamePanel.tileSize, GamePanel.tileSize));
	}

}
