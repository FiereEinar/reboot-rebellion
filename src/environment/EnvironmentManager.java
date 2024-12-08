package environment;

import java.awt.Graphics2D;

import main.GamePanel;
import main.GamePanel.LIGHTING;

public class EnvironmentManager {

	GamePanel gp;
	Lighting lighting;
	DimLighting dimLighting;
	
	public EnvironmentManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setup() {
		lighting = new Lighting(gp, 350);
		dimLighting = new DimLighting(gp, 550);
	}
	
	public void draw(Graphics2D g2) {
		if (gp.lightingState == LIGHTING.DARK) {
			lighting.draw(g2);
		} else {
			dimLighting.draw(g2);
		}
	}

}
