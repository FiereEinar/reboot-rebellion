package tile;

import java.awt.image.BufferedImage;

import sprite.Sprite;

public class Tile {
	
	private Sprite sprite = new Sprite();
	
	public Tile(BufferedImage image) {
		this.sprite.addSprite(image);
	}
	
	public BufferedImage getSprite() {
		return sprite.getSprite();
	}

}
