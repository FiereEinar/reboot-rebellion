package sprite;

import java.awt.image.BufferedImage;

public class Sprite {

	private BufferedImage[] sprites;
	private int spriteCounter = 0;
	private int fpsCounter = 0;
	private int fps = 12;
	private int spriteIndexCounter = 0;
	public static final int DEFAULT_SPRITE_SIZE = 15;
	
	public Sprite() {
		this.sprites = new BufferedImage[DEFAULT_SPRITE_SIZE];
	}
	
	public Sprite(int size) {
		this.sprites = new BufferedImage[size];
	}
	
	public void addSprite(BufferedImage sprite) {
		if (spriteIndexCounter >= sprites.length) {
			System.out.println("WARNING: sprites overflow");
			return;
		}
		sprites[spriteIndexCounter++] = sprite;
	}
	
	public void setInterval(int interval) {
		this.fps = interval;
	}
	
	public int getSpritesSize() {
		return spriteIndexCounter;
	}
	
	public BufferedImage getSpriteByIndex(int index) {
		return sprites[index];
	}
	
	public void resetCounters() {
		fpsCounter = 0;
		spriteCounter = 0;
	}
	
	public BufferedImage safeGetSprite() {
		if (spriteIndexCounter == 0) {
			System.out.println("You forgot to add some sprites you dimwit");
			return null;
		}
		
		return sprites[spriteCounter];
	}
	
	public BufferedImage getSprite() {
		if (spriteIndexCounter == 0) {
			System.out.println("You forgot to add some sprites you dimwit");
			return null;
		}
		
		BufferedImage sprite = sprites[spriteCounter];
		fpsCounter++;
		
		if (fpsCounter >= fps) {
			fpsCounter = 0;
			spriteCounter++;
		}

		if (spriteCounter >= spriteIndexCounter) {
			spriteCounter = 0;
		}
		
		return sprite;
	}

}
