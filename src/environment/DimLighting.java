package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class DimLighting {

	GamePanel gp;
	BufferedImage darknessFilter;
	
	public DimLighting(GamePanel gp, int size) {
		
		this.gp = gp;
		this.darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();
		
		Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));
		
		int centerX = gp.player.screenX + (GamePanel.TILE_SIZE) / 2;
		int centerY = gp.player.screenY + (GamePanel.TILE_SIZE) / 2;
		
		double x = centerX - (size / 2);
		double y = centerY - (size / 2);
		
		Shape circleShape = new Ellipse2D.Double(x, y, size, size);
		
		Area lightArea = new Area(circleShape);
		
		screenArea.subtract(lightArea);
		
		Color[] color = new Color[5];
		float[] fraction = new float[5];
		
		color[0] = new Color(0, 0, 0, 0f);
		color[1] = new Color(0, 0, 0, 0.14f);
		color[2] = new Color(0, 0, 0, 0.26f);
		color[3] = new Color(0, 0, 0, 0.38f);
		color[4] = new Color(0, 0, 0, 0.50f);
		
		fraction[0] = 0f;
		fraction[1] = 0.25f;
		fraction[2] = 0.5f;
		fraction[3] = 0.75f;
		fraction[4] = 1f;
		
		RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (size / 2), fraction, color);
		
		g2.setPaint(gPaint);
		
		g2.fill(lightArea);
		g2.fill(screenArea);
		
		g2.dispose();
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(darknessFilter, 0, 0, null);
	}

}
