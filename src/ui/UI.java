package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.Renderable;

public class UI implements Renderable {

	GamePanel gp;
	Graphics2D g2;

	Font normalText;
	Font normalBoldText;

	public int selectedMenuNum = 0;
	public int menuItems = 2;
	
	Asset healthbar = new Asset(64 * 3, 32 * 3);
	Asset buttons = new Asset(64 * 3, 32 * 3);
	Asset background = new Asset();
	
	public final int MENU_OPTION_START = 0;
	public final int MENU_OPTION_EXIT = 1;
	public static int START_BUTTON = 0;
	public static int EXIT_BUTTON = 1;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		this.normalText = new Font("Arial", Font.PLAIN, 40);
		this.normalBoldText = new Font("Arial", Font.BOLD, 80);
		loadAssets();
	}
	
	private void loadAssets() {
		this.buttons.set("/ui/Start_Button.png");
		this.buttons.set("/ui/Exit_Button.png");
		
		this.healthbar.set("/healthbar/Health_Bar1.png");
		this.healthbar.set("/healthbar/Health_Bar2.png");
		this.healthbar.set("/healthbar/Health_Bar3.png");
		this.healthbar.set("/healthbar/Health_Bar4.png");
		this.healthbar.set("/healthbar/Health_Bar5.png");
		this.healthbar.set("/healthbar/Health_Bar6.png");
		this.healthbar.set("/healthbar/Health_Bar7.png");
		
		this.background.load("/ui/menu_background.png");
	}
	
	private void menuScreenHandler() {
		g2.setFont(normalBoldText);
		
		g2.setColor(new Color(51, 153, 255));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.drawImage(background.image.getSprite(), 0, 0, gp.screenWidth, gp.screenHeight, null);
		
		int x, y = 0;
		
//		ScreenText screenTitle = new ScreenText("REBOOT REBELLION");
//		screenTitle.setPos(getXForCenteredText(screenTitle.text), GamePanel.tileSize * 3);
//		drawTextWithShadow(screenTitle.text, screenTitle.pos.x, screenTitle.pos.y);
		
		g2.setFont(normalText);
		
		BufferedImage startButton = buttons.image.getSpriteByIndex(UI.START_BUTTON);
		x = gp.screenWidth / 2 - startButton.getWidth() / 2;
		y = GamePanel.tileSize * 4;
		g2.drawImage(startButton, x, y, null);
		if (selectedMenuNum == MENU_OPTION_START) drawTextWithShadow(">", x - GamePanel.tileSize / 2, y + 64);
		
		BufferedImage exitButton = buttons.image.getSpriteByIndex(UI.EXIT_BUTTON);
		x = gp.screenWidth / 2 - exitButton.getWidth() / 2;
		y = GamePanel.tileSize * 5 + 16;
		g2.drawImage(exitButton, x, y, null);
		if (selectedMenuNum == MENU_OPTION_EXIT) drawTextWithShadow(">", x - GamePanel.tileSize / 2, y + 64);
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
		
		g2.setColor(new Color(0, 0, 0, 0));

		switch (playerCurrentHealth) {
		case 6: 
			g2.drawImage(healthbar.image.getSpriteByIndex(0), 18, 0, null);
			break;
		case 5: 
			g2.drawImage(healthbar.image.getSpriteByIndex(1), 18, 0, null);
			break;
		case 4: 
			g2.drawImage(healthbar.image.getSpriteByIndex(2), 18, 0, null);
			break;
		case 3: 
			g2.drawImage(healthbar.image.getSpriteByIndex(3), 18, 0, null);
			break;
		case 2: 
			g2.drawImage(healthbar.image.getSpriteByIndex(4), 18, 0, null);
			break;
		case 1: 
			g2.drawImage(healthbar.image.getSpriteByIndex(5), 18, 0, null);
			break;
		default: 
			g2.drawImage(healthbar.image.getSpriteByIndex(6), 18, 0, null);
			break;
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
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(normalText);
		g2.setColor(Color.WHITE);
		
		if (gp.gameState == GamePanel.STATE_MENU_SCREEN) {
			menuScreenHandler();
		}
		
		if (gp.gameState == GamePanel.STATE_PLAY) {
			playScreenHandler();
		}
		
		if (gp.gameState == GamePanel.STATE_PAUSE) {
			pausedScreenHandler();
		}
		
		if (gp.gameState == GamePanel.STATE_DIALOGUE) {
			dialogueScreenHandler();
		}
	}

}
