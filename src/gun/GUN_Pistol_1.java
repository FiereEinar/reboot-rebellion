package gun;

import main.GamePanel;
import main.Utils;

public class GUN_Pistol_1 extends GunObject {

	public GUN_Pistol_1(int x, int y) {
		this.damage = 2;
		this.bulletSpeed = 20;
		this.bulletSpread = 5;
		
		this.worldX = x;
		this.worldY = y;
		
		this.name = "gun_pistol_1";
		
		loadSprites();
	}
	
	private void loadSprites() {
		Utils utils = new Utils();
		
		this.sprite.addSprite(utils.getAndScaleImage("/guns/gun_1.png", GamePanel.tileSize, GamePanel.tileSize));
	}

}
