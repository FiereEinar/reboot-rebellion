package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Gun extends GameObject {

	public OBJ_Gun(int x, int y) {
		this.worldX = x;
		this.worldY = y;
		this.name = "gun_1";
		loadSprites();
	}

	private void loadSprites() {
		try {
			this.sprite.addSprite(ImageIO.read(getClass().getResourceAsStream("/guns/gun_1.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
