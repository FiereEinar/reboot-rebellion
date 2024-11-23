package object;

import main.GamePanel;
import main.Utils;

public class OBJ_HealthBar extends GameObject {

	GamePanel gp;
	
	public OBJ_HealthBar(GamePanel gp) {
		this.gp = gp;
		this.worldX = 48 * 3;
		this.worldY = 48 * 3;
		this.name = "healthbar";
		loadSprites();
	}

	private void loadSprites() {
		Utils utils = new Utils();
		
		int width = 64 * 3;
		int height = 32 * 3;
		
		sprite.addSprite(utils.getAndScaleImage("/healthbar/Health_Bar1.png", width, height));
		sprite.addSprite(utils.getAndScaleImage("/healthbar/Health_Bar2.png", width, height));
		sprite.addSprite(utils.getAndScaleImage("/healthbar/Health_Bar3.png", width, height));
		sprite.addSprite(utils.getAndScaleImage("/healthbar/Health_Bar4.png", width, height));
		sprite.addSprite(utils.getAndScaleImage("/healthbar/Health_Bar5.png", width, height));
		sprite.addSprite(utils.getAndScaleImage("/healthbar/Health_Bar6.png", width, height));
		sprite.addSprite(utils.getAndScaleImage("/healthbar/Health_Bar7.png", width, height));
	}

}
