package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class GunOBJ extends GameObject {

	public GunOBJ() {
		this.worldX = 48 * 3;
		this.worldY = 48 * 3;
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
