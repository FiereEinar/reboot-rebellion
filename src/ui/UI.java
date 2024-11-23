package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;
import main.Renderable;
import object.OBJ_Heart;

public class UI implements Renderable {

	GamePanel gp;
	Font normalText;
	Font normalBoldText;
	Graphics2D g2;
	public int selectedMenuNum = 0;
	public int menuItems = 2;
	public final int MENU_OPTION_START = 0;
	public final int MENU_OPTION_EXIT = 1;
	OBJ_Heart heart;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		this.heart = new OBJ_Heart(gp);
		this.normalText = new Font("Arial", Font.PLAIN, 40);
		this.normalBoldText = new Font("Arial", Font.BOLD, 80);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(normalText);
		g2.setColor(Color.WHITE);
		
		if (gp.gameState == gp.STATE_MENU_SCREEN) {
			menuScreenHandler();
		}
		
		if (gp.gameState == gp.STATE_PLAY) {
			playScreenHandler();
		}
		
		if (gp.gameState == gp.STATE_PAUSE) {
			pausedScreenHandler();
		}

		if (gp.gameState == gp.STATE_DIALOGUE) {
			dialogueScreenHandler();
		}
	}
	
	private void menuScreenHandler() {
		g2.setFont(normalBoldText);
		
		g2.setColor(new Color(51, 153, 255));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		ScreenText screenTitle = new ScreenText("REBOOT REBELLION");
		screenTitle.setPos(getXForCenteredText(screenTitle.text), GamePanel.tileSize * 3);
		drawTextWithShadow(screenTitle.text, screenTitle.pos.x, screenTitle.pos.y);
		
		g2.setFont(normalText);
		
		ScreenText start = new ScreenText("START");
		start.setPos(getXForCenteredText(start.text), GamePanel.tileSize * 5);
		drawTextWithShadow(start.text, start.pos.x, start.pos.y);
		if (selectedMenuNum == MENU_OPTION_START) drawTextWithShadow(">", start.pos.x - GamePanel.tileSize, start.pos.y);
		
		ScreenText exit = new ScreenText("EXIT");
		exit.setPos(getXForCenteredText(exit.text), GamePanel.tileSize * 6);
		drawTextWithShadow(exit.text, exit.pos.x, exit.pos.y);
		if (selectedMenuNum == MENU_OPTION_EXIT) drawTextWithShadow(">", exit.pos.x - GamePanel.tileSize, exit.pos.y);
	}
	
	private void dialogueScreenHandler() {
		
	}
	
	private void playScreenHandler() {
		drawPlayerHealth();
	}

	private void pausedScreenHandler() {
		String text = "PAUSED";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2;
		
		g2.drawString(text, x, y);
	}
	
	private void drawPlayerHealth() {
		int playerCurrentHealth = gp.player.getHealth();
		int playerMaxHealth = gp.player.getMaxHealth();
		int healthBarWidth = GamePanel.tileSize;
		
		g2.setColor(new Color(0, 0, 0, 0));

		for (int i = 0; i < playerMaxHealth; i++) {
			if (i >= playerCurrentHealth) {
				g2.drawImage(heart.emptyHeart.getSprite(), 16 + i * healthBarWidth, 16, null);
			} else {
				g2.drawImage(heart.fullHeart.getSprite(), 16 + i * healthBarWidth, 16, null);
			}
		}
	}
	
	public int getXForCenteredText(String text) {
		int len = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return gp.screenWidth / 2 - len / 2;
	}
	
	public void drawTextWithShadow(String text, int x, int y) {
		g2.setColor(Color.BLACK);
		g2.drawString(text, x + 3, y + 3);
		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y);
	}

}
