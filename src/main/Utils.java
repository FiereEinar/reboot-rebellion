package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utils {

	public Utils() {
	}
	
	public BufferedImage scaleImage(BufferedImage image, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, image.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(image, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}
	
	public BufferedImage getAndScaleImage(String path, int width, int height) {
		BufferedImage scaledImage = null;
		
		try {
			scaledImage = ImageIO.read(getClass().getResourceAsStream(path));
			scaledImage = scaleImage(scaledImage, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return scaledImage;
	}
	
	public BufferedImage getSpriteSheet(String path) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage cropSprite(BufferedImage sheet, int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
    
    public BufferedImage cropSprite(BufferedImage sheet, int x, int y, int width, int height, int resizeW, int resizeH) {
        BufferedImage image = sheet.getSubimage(x, y, width, height);
        image = scaleImage(image, resizeW, resizeH);
        return image;
    }

}
