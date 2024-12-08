package sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite {

	private ArrayList<BufferedImage> sprites;
	private int spriteCounter = 0;
	private int fpsCounter = 0;
	private int fps = 12;
	
	public Sprite() {
		this.sprites = new ArrayList<>();
	}
	
	public void addSprite(BufferedImage sprite) {
		sprites.add(sprite);
	}
	
	public void setInterval(int interval) {
		this.fps = interval;
	}
	
	public int getSpritesSize() {
		return sprites.size();
	}
	
	public BufferedImage getSpriteByIndex(int index) {
		return sprites.get(index);
	}
	
	public void resetCounters() {
		fpsCounter = 0;
		spriteCounter = 0;
	}
	
	public BufferedImage safeGetSprite() {
		return sprites.get(spriteCounter);
	}
	
	public BufferedImage getSprite() {
		if (sprites.size() == 0) {
			System.out.println("You forgot to add some sprites you dimwit");
			return null;
		}
		
		BufferedImage sprite = sprites.get(spriteCounter);
		fpsCounter++;
		
		if (fpsCounter >= fps) {
			fpsCounter = 0;
			spriteCounter++;
		}

		if (spriteCounter >= sprites.size()) {
			spriteCounter = 0;
		}
		
		return sprite;
	}

}
