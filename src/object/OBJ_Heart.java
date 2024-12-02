package object;

import java.awt.Rectangle;

import entity.Player;
import main.Utils;

public class OBJ_Heart extends GameObject {

	public OBJ_Heart(int x, int y) {
		this.worldX = x;
		this.worldY = y;
		
		this.type = GameObject.OBJ_HEART;
		this.value = 1;
		
		this.name = "obj_heart";
		
		this.setSolidArea(new Rectangle(0, 0, 32, 32));
		
		loadSprites();
	}
	
	private void loadSprites() {
		Utils utils = new Utils();
		
		this.sprite.addSprite(utils.getAndScaleImage("/objects/heart_full.png", 32, 32));
	}
	
	@Override
	public Boolean useEffect(Player entity) {
		if (entity.getHealth() >= entity.getMaxHealth()) return false;
		entity.increaseHealth(value);
		return true;
	}

}
