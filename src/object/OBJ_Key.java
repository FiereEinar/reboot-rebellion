package object;

import java.awt.Rectangle;

import entity.Player;
import main.Utils;

public class OBJ_Key extends GameObject {

	public OBJ_Key(int x, int y) {
		this.worldX = x;
		this.worldY = y;
		
		this.type = GameObject.OBJ_KEY;
		this.value = 50;
		
		this.name = "obj_key";
		
		this.setSolidArea(new Rectangle(0, 0, 32, 32));

		loadSprites();
	}
	
	private void loadSprites() {
		Utils utils = new Utils();
		
		this.sprite.addSprite(utils.getAndScaleImage("/objects/Key.png", 16, 16));
	}

	@Override
	public Boolean useEffect(Player entity) {
//		GunObject gun = entity.inventory.getSelectedGun();
//		if (gun == null) return false;
//		
//		gun.increaseReservedAmmo(gun.getMagSize() * 2);
		return true;
	}

}
