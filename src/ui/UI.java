package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;
import main.Renderable;

public class UI implements Renderable {

	GamePanel gp;
	Font normalText;
	Graphics2D g2;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		this.normalText = new Font("Arial", Font.PLAIN, 40);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(normalText);
		g2.setColor(Color.WHITE);
		
		if (gp.gameState == gp.STATE_PLAY) {
			playScreenHandler();
		}
		
		if (gp.gameState == gp.STATE_PAUSE) {
			pausedScreenHandler();
		}
	}
	
	private void playScreenHandler() {
		
	}

	private void pausedScreenHandler() {
		String text = "PAUSED";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2;
		
		g2.drawString(text, x, y);
	}
	
	public int getXForCenteredText(String text) {
		int len = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return gp.screenWidth / 2 - len / 2;
	}

}
