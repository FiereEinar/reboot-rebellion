package ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import gun.GunObject;
import main.GamePanel;
import main.Inventory;
import main.Objective;
import main.Renderable;
import states.State;

public class UI implements Renderable {

	GamePanel gp;
	Graphics2D g2;

	public Font normalText;
	public Font smallText;
	public Font extraSmallText;
	public Font veryExtraSmallText;
	public Font normalBoldText;

	public int selectedMenuNum = 0;
	public int menuItems = 2;
	private final int MESSAGE_DURATION = 300;
	private String tooltipText = "";
	private String progressText = "";
	private String message1 = "";
	private String message2 = "";
	
	Asset healthbar = new Asset(64 * 3, 32 * 3);
	Asset buttons = new Asset(64 * 3, 32 * 3);
	Asset background = new Asset();
	Asset key = new Asset(64, 64);
	
	State showMessage = new State(MESSAGE_DURATION);
	
	public final int MENU_OPTION_START = 0;
	public final int MENU_OPTION_EXIT = 1;
	public static int START_BUTTON = 0;
	public static int EXIT_BUTTON = 1;
	
	public Boolean startButtonHovered = false;
	public Boolean exitButtonHovered = false;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		String font = "Arial";
		
		this.normalText = new Font(font, Font.PLAIN, 40);
		this.smallText = new Font(font, Font.PLAIN, 20);
		this.extraSmallText = new Font(font, Font.PLAIN, 15);
		this.veryExtraSmallText = new Font(font, Font.PLAIN, 10);
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
		
		this.key.load("/objects/Key.png");
	}
	
	public void setTooltipText(String text) {
		tooltipText = text;
	}

	public void setProgressText(String progressText) {
		this.progressText = progressText;
	}

	private void drawControls() {
		g2.setFont(extraSmallText);
		g2.setColor(Color.WHITE);
		int x = 20;
		int y = 290;
		
		String movement = "WASD: Movement";
		int height = (int)g2.getFontMetrics().getStringBounds(movement, g2).getHeight();
		
		g2.drawString(movement, x, y);
		g2.drawString("R: Reload", x, y + height);
		g2.drawString("P: Pause", x, y + height * 2);
		g2.drawString("L: Debug Mode", x, y + height * 3);
		g2.drawString("Left Click: Shoot", x, y + height * 4);
		g2.drawString("1/2: Switch Weapon", x, y + height * 5);
	}
	
	private void drawPlayerWeapons() {
		g2.setFont(smallText);
		
		int rec1Width = GamePanel.TILE_SIZE * 2 + 20;
		int rec1Height = GamePanel.TILE_SIZE;
		int rec2X = gp.screenWidth - rec1Width - 20;
		int rec1X = rec2X - rec1Width;
		int rec1Y = 20;
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2.setColor(Color.BLACK);
		g2.fillRoundRect(rec1X, rec1Y, rec1Width * 2, rec1Height, 10, 10);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
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
			g2.setFont(veryExtraSmallText);
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
			g2.setFont(veryExtraSmallText);
			g2.drawString("2", rec2X + 5, rec1Y + 10);
		}
	}
	
	private void drawWeaponState() {
		GunObject slot1Gun = gp.player.inventory.getGunByIndex(Inventory.GUN_SLOT_1);
		if (slot1Gun != null) {
			if (slot1Gun.reloading.getState() && gp.player.inventory.isGunSelected(Inventory.GUN_SLOT_1)) {
				setProgressText("Reloading...");
				drawProgressTextBar(slot1Gun.reloading.getStateDuration(), slot1Gun.reloading.getCounter());
			}
		}
		
		GunObject slot2Gun = gp.player.inventory.getGunByIndex(Inventory.GUN_SLOT_2);
		if (slot2Gun != null) {
			if (slot2Gun.reloading.getState() && gp.player.inventory.isGunSelected(Inventory.GUN_SLOT_2)) {
				setProgressText("Reloading...");
				drawProgressTextBar(slot2Gun.reloading.getStateDuration(), slot2Gun.reloading.getCounter());
			}
		}
	}
	
	private void drawPlayerTooltip() {
		g2.setFont(extraSmallText);
		
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
		
		int x = 15;
		int y = 200;

		switch (playerCurrentHealth) {
		case 6: 
			g2.drawImage(healthbar.image.getSpriteByIndex(0), x, y, null);
			break;
		case 5: 
			g2.drawImage(healthbar.image.getSpriteByIndex(1), x, y, null);
			break;
		case 4: 
			g2.drawImage(healthbar.image.getSpriteByIndex(2), x, y, null);
			break;
		case 3: 
			g2.drawImage(healthbar.image.getSpriteByIndex(3), x, y, null);
			break;
		case 2: 
			g2.drawImage(healthbar.image.getSpriteByIndex(4), x, y, null);
			break;
		case 1: 
			g2.drawImage(healthbar.image.getSpriteByIndex(5), x, y, null);
			break;
		default: 
			g2.drawImage(healthbar.image.getSpriteByIndex(6), x, y, null);
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
	
	private void menuScreenHandler() {
	    g2.setFont(normalBoldText);

	    // Draw background
	    g2.setColor(new Color(51, 153, 255));
	    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
	    g2.drawImage(background.image.getSprite(), 0, 0, gp.screenWidth, gp.screenHeight, null);

	    // Mouse position
	    double mouseX = gp.mouse.mouseX;
	    double mouseY = gp.mouse.mouseY;

	    // Button positions and sizes
	    BufferedImage startButton = buttons.image.getSpriteByIndex(UI.START_BUTTON);
	    int startX = gp.screenWidth / 2 - startButton.getWidth() / 2;
	    int startFX = gp.fullScreenWidth / 2 - startButton.getWidth() / 2;
	    int startY = (gp.screenHeight / 2 - startButton.getHeight() / 2) - GamePanel.TILE_SIZE;
	    int startFY = (gp.fullScreenHeight / 2 - startButton.getHeight() / 2) - GamePanel.TILE_SIZE;
	    int startWidth = startButton.getWidth();
	    int startHeight = startButton.getHeight();

	    BufferedImage exitButton = buttons.image.getSpriteByIndex(UI.EXIT_BUTTON);
	    int exitX = gp.screenWidth / 2 - exitButton.getWidth() / 2;
	    int exitFX = gp.fullScreenWidth / 2 - exitButton.getWidth() / 2;
	    int exitY = (gp.screenHeight / 2 - exitButton.getHeight() / 2) + GamePanel.TILE_SIZE / 2;
	    int exitFY = (gp.fullScreenHeight / 2 - exitButton.getHeight() / 2) + GamePanel.TILE_SIZE / 2;
	    int exitWidth = exitButton.getWidth();
	    int exitHeight = exitButton.getHeight();

	    // Check if mouse is hovering over the Start button
	    startButtonHovered = mouseX >= startFX && mouseX <= startFX + startWidth
	            && mouseY >= startFY && mouseY <= startFY + startHeight;

	    // Check if mouse is hovering over the Exit button
        exitButtonHovered = mouseX >= exitFX && mouseX <= exitFX + exitWidth
	            && mouseY >= exitFY && mouseY <= exitFY + exitHeight;

	    // Draw Start button with hover effect
	    if (startButtonHovered) {
	        g2.drawImage(startButton, startX + 2, startY + 2, startWidth - 4, startHeight - 4, null); // Shrink effect
	    } else {
	        g2.drawImage(startButton, startX, startY, null);
	    }

	    // Draw Exit button with hover effect
	    if (exitButtonHovered) {
	        g2.drawImage(exitButton, exitX + 2, exitY + 2, exitWidth - 4, exitHeight - 4, null); // Shrink effect
	    } else {
	        g2.drawImage(exitButton, exitX, exitY, null);
	    }
	}
	
	private void drawObjectives() {
		int tileSize = GamePanel.TILE_SIZE;
		int margin = 20;
		int width = tileSize * 5;
		int height = tileSize * 3;
		int x = gp.screenWidth - width - margin;
		int y = tileSize * 2;
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2.setColor(Color.BLACK);
		g2.fillRoundRect(x, y, width, height, 10, 10);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		String header = "Objectives";
		int headerH = (int) g2.getFontMetrics().getStringBounds(header, g2).getHeight();
		
		int objX = x + 5;
		int objY = y + headerH + 5;
		
		g2.setColor(Color.WHITE);
		g2.drawString(header, objX, objY);
		
		for (int i = 0; i < gp.objectives.size(); i++) {
			Objective obj = gp.objectives.get(i);
			int textH = (int) g2.getFontMetrics().getStringBounds(obj.getObjective(), g2).getHeight();
			int gap = 3;
			
			g2.drawString((i + 1) + ". " + obj.getObjective(), objX, objY + (textH * (i + 1)) + gap);
		}
	}
	
	private void showMessage() {
		if (!showMessage.getState()) {
			message1 = "";
			message2 = "";
			return;
		}
		
		int tileSize = GamePanel.TILE_SIZE;
		int margin = 20;
		int width = tileSize * 6;
		int height = tileSize + 20;
		int x = gp.screenWidth - width - margin;
		int y = tileSize * 6;
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2.setColor(Color.BLACK);
		g2.fillRoundRect(x, y, width, height, 10, 10);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); 
		
		g2.setColor(Color.WHITE);
		g2.setFont(extraSmallText);

		int height1 = (int) g2.getFontMetrics().getLineMetrics(message1, g2).getHeight();
		int height2 = (int) g2.getFontMetrics().getLineMetrics(message2, g2).getHeight();
		
		g2.drawString(message1, x + 5, y + height1 + 5);
		g2.drawString(message2, x + 5, y + height1 +  height2 + 10);
	}
	
	public void showMessage(String message1, String message2) {
		this.message1 = message1;
		this.message2 = message2;
		showMessage.setState(true);
	}
	
	private void drawPlayerKeys() {
		Inventory inv = gp.player.inventory;
		if (inv.getKeys() == 0) return;
		
		int x = 230;
		int y = 20;
		
		int spriteWidth = key.image.getSprite().getWidth();
		int spriteHeight = key.image.getSprite().getHeight();
		
		g2.drawImage(key.image.getSprite(), x, y, null);
		g2.setFont(extraSmallText);
		g2.drawString("" + inv.getKeys(), x + spriteWidth + 2, y + spriteHeight - 4);
	}

	/*
	 * HANDLERS
	 */
	private void dialogueScreenHandler() {
		
	}
	
	private void playScreenHandler() {
		drawPlayerTooltip();
		drawPlayerHealth();
		drawPlayerWeapons();
		drawWeaponState();
		drawControls();
		drawObjectives();
		showMessage();
		drawPlayerKeys();
	}

	private void pausedScreenHandler() {
		String text = "PAUSED";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2;
		
		g2.drawString(text, x, y);
	}

	@Override
	public void update() {
		setTooltipText("");
		setProgressText("");
		showMessage.update();
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
			playScreenHandler();
		}
		
		if (gp.gameState == GamePanel.STATE_DIALOGUE) {
			dialogueScreenHandler();
		}
	}

}
