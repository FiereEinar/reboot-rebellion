package object;

import java.awt.Rectangle;

import entity.Player;
import gun.GunObject;
import main.Utils;

public class OBJ_Ammo extends GameObject {

	public OBJ_Ammo(int x, int y) {
		this.worldX = x;
		this.worldY = y;
		
		this.type = GameObject.OBJ_AMMO;
		this.value = 50;
		
		this.name = "obj_ammo";
		
		this.setSolidArea(new Rectangle(0, 0, 32, 32));

		loadSprites();
	}
	
	private void loadSprites() {
		Utils utils = new Utils();
		
		this.sprite.addSprite(utils.getAndScaleImage("/objects/basic_ammo.png", 32, 32));
	}

	@Override
	public Boolean useEffect(Player entity) {
		GunObject gun = entity.inventory.getSelectedGun();
		if (gun == null) return false;
		
		gun.increaseReservedAmmo(gun.getMagSize() * 2);
		return true;
	}

}
