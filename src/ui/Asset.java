package ui;

import main.Utils;
import sprite.Sprite;

public class Asset {
	
	Utils utils = new Utils();
	Sprite image = new Sprite();
	int width, height;
	
	public Asset(int w, int h) {
		this.width = w;
		this.height = h;
	}
	
	public void set(String filepath) {
		image.addSprite(utils.getAndScaleImage(filepath, width, height));
	}

}
