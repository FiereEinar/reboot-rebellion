package sprite;

import java.awt.image.BufferedImage;

import entity.Entity;

public class SpriteManager {

	public Sprite left = new Sprite();
	public Sprite right = new Sprite();
	public Sprite up = new Sprite();
	public Sprite down = new Sprite();

	Entity entity;

	public SpriteManager(Entity entity) {
		this.entity = entity;
	}

	public BufferedImage getSprite() {
		if (entity.getDirection().equals("up")) {
			return up.getSprite();
		}
		if (entity.getDirection().equals("left")) {
			return left.getSprite();
		}
		if (entity.getDirection().equals("right")) {
			return right.getSprite();
		}
		// default return sprite
		return down.getSprite();
	}

}
