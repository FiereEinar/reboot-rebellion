package ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import gun.GunObject;
import main.GamePanel;
import main.Inventory;
import main.Renderable;

public class UI implements Renderable {

	GamePanel gp;
	Graphics2D g2;

	Font normalText;
	Font smallText;
	Font extraSmallText;
	Font normalBoldText;

	public int selectedMenuNum = 0;
	public int menuItems = 2;
	private String tooltipText = "";
	private String progressText = "";
	
	Asset healthbar = new Asset(64 * 3, 32 * 3);
	Asset buttons = new Asset(64 * 3, 32 * 3);
	Asset background = new Asset();
	
	public final int MENU_OPTION_START = 0;
	public final int MENU_OPTION_EXIT = 1;
	public static int START_BUTTON = 0;
	public static int EXIT_BUTTON = 1;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		String font = "Arial";
		
		this.normalText = new Font(font, Font.PLAIN, 40);
		this.smallText = new Font(font, Font.PLAIN, 20);
		this.extraSmallText = new Font(font, Font.PLAIN, 10);
		this.normalBoldText = new Font(font, Font.BOLD, 80);
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
		
		g2.setFont(normalText);
		
		BufferedImage startButton = buttons.image.getSpriteByIndex(UI.START_BUTTON);
		x = gp.screenWidth / 2 - startButton.getWidth() / 2;
		y = GamePanel.TILE_SIZE * 4;
		g2.drawImage(startButton, x, y, null);
		if (selectedMenuNum == MENU_OPTION_START) drawTextWithShadow(">", x - GamePanel.TILE_SIZE / 2, y + 64);
		
		BufferedImage exitButton = buttons.image.getSpriteByIndex(UI.EXIT_BUTTON);
		x = gp.screenWidth / 2 - exitButton.getWidth() / 2;
		y = GamePanel.TILE_SIZE * 5 + 16;
		g2.drawImage(exitButton, x, y, null);
		if (selectedMenuNum == MENU_OPTION_EXIT) drawTextWithShadow(">", x - GamePanel.TILE_SIZE / 2, y + 64);
	}
	
	private void dialogueScreenHandler() {
		
	}
	
	private void playScreenHandler() {
		drawPlayerTooltip();
		drawPlayerHealth();
		drawPlayerWeapons();
		drawWeaponState();
	}

	private void pausedScreenHandler() {
		String text = "PAUSED";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2;
		
		g2.drawString(text, x, y);
	}
	
	public void setTooltipText(String text) {
		tooltipText = text;
	}

	public void setProgressText(String progressText) {
		this.progressText = progressText;
	}

	
	private void drawPlayerWeapons() {
		g2.setFont(smallText);
		
		int rec1Width = GamePanel.TILE_SIZE * 2 + 20;
		int rec1Height = GamePanel.TILE_SIZE;
		int rec2X = gp.screenWidth - rec1Width - 20;
		int rec1X = rec2X - rec1Width;
		int rec1Y = 20;
		
		g2.setColor(Color.BLUE);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2.setColor(Color.BLACK);
		g2.fillRoundRect(rec1X, rec1Y, rec1Width * 2, rec1Height, 10, 10);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		g2.setColor(Color.BLUE);
		
		GunObject slot1Gun = gp.player.inventory.getGunByIndex(Inventory.GUN_SLOT_1);
		if (slot1Gun != null) {
			if (gp.player.inventory.getSelectedGunIndex() == Inventory.GUN_SLOT_2) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
			}
			
			BufferedImage image = slot1Gun.sprite.getSprite();
			
			int gunW = image.getWidth();
			int gunH = image.getHeight();
			
			int gunX = rec1X + rec1Width - gunW - 5;
			int gunY = rec1Y + rec1Height / 2 - gunH / 2;
			
			g2.drawImage(image, gunX, gunY, null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			g2.setColor(Color.WHITE);
			g2.setFont(extraSmallText);
			g2.drawString(slot1Gun.getCurrentMag() + "/" + slot1Gun.getReservedAmmo(), rec1X + 5, rec1Y + rec1Height - 5);
			g2.drawString("1", rec1X + 5, rec1Y + 10);
		}
		
		GunObject slot2Gun = gp.player.inventory.getGunByIndex(Inventory.GUN_SLOT_2);
		if (slot2Gun != null) {
			if (gp.player.inventory.getSelectedGunIndex() == Inventory.GUN_SLOT_1) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
			}

			BufferedImage image = slot2Gun.sprite.getSprite();
			
			int gunW = image.getWidth();
			int gunH = image.getHeight();
			
			int gunX = rec2X + rec1Width - gunW - 5;
			int gunY = rec1Y + rec1Height / 2 - gunH / 2;
			
			g2.drawImage(image, gunX, gunY, null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			g2.setColor(Color.WHITE);
			g2.setFont(extraSmallText);
			g2.drawString(slot2Gun.getCurrentMag() + "/" + slot2Gun.getReservedAmmo(), rec2X + 5, rec1Y + rec1Height - 5);
			g2.drawString("2", rec2X + 5, rec1Y + 10);
		}
	}
	
	private void drawWeaponState() {
		GunObject slot1Gun = gp.player.inventory.getGunByIndex(Inventory.GUN_SLOT_1);
		if (slot1Gun != null) {
			if (slot1Gun.reloading.getState()) {
				setProgressText("Reloading...");
				drawProgressTextBar(slot1Gun.reloading.getStateDuration(), slot1Gun.reloading.getCounter());
			}
		}
		
		GunObject slot2Gun = gp.player.inventory.getGunByIndex(Inventory.GUN_SLOT_2);
		if (slot2Gun != null) {
			if (slot2Gun.reloading.getState()) {
				setProgressText("Reloading...");
				drawProgressTextBar(slot2Gun.reloading.getStateDuration(), slot2Gun.reloading.getCounter());
			}
		}
	}
	
	private void drawPlayerTooltip() {
		g2.setFont(smallText);
		
		int x = gp.player.screenX + GamePanel.TILE_SIZE;
		int y = gp.player.screenY;
		
		g2.drawString(tooltipText, x, y);
	}
	
	private void drawProgressTextBar(int duration, int progress) {
		g2.setFont(extraSmallText);
		
		int tileSize = GamePanel.TILE_SIZE;
		
		int x = gp.player.screenX;
		int y = gp.player.screenY + tileSize + 10;
		
		g2.drawString(progressText, x, y);
		
		double oneScale = (double) tileSize / duration;
		double progressWidth = oneScale * progress;
		
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(x - 1, y + 4, tileSize + 2, 12);

		g2.setColor(Color.GRAY);
		g2.fillRect(x, y + 5, (int) progressWidth, 10);
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
		setTooltipText("");
		setProgressText("");
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
