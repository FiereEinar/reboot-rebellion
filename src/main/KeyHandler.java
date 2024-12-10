package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gun.GunObject;

public class KeyHandler implements KeyListener {
	
	private GamePanel gp;

	public Boolean UP = false;
	public Boolean DOWN = false;
	public Boolean LEFT = false;
	public Boolean RIGHT = false;
	public Boolean LOG_SWITCH = false;
	public Boolean KEY_E = false;
	public Boolean NOCLIP = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	public Boolean isMoving() {
		return UP || DOWN || LEFT || RIGHT;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			UP = true;
		}
		
		if (code == KeyEvent.VK_S) {
			DOWN = true;
		}
		
		if (code == KeyEvent.VK_A) {
			LEFT = true;
		}
		
		if (code == KeyEvent.VK_D) {
			RIGHT = true;
		}
		
		if (code == KeyEvent.VK_L) {
			LOG_SWITCH = !LOG_SWITCH;
		}
		
		if (code == KeyEvent.VK_BACK_SLASH) {
			NOCLIP = !NOCLIP;
		}
		
		if (code == KeyEvent.VK_E) {
			KEY_E = true;
		}
		
		if (code == KeyEvent.VK_R) {
			GunObject selectedGun = gp.player.inventory.getSelectedGun();
			
			if (selectedGun.getCurrentMag() == selectedGun.getMagSize()) return;
			if (selectedGun.reloading.getState()) return;
			if (selectedGun.getReservedAmmo() <= 0) return;
			
			selectedGun.reloading.setState(true);
			gp.sound.play(Sound.GUN_RELOAD);
		}
		
		if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
			gp.ui.selectedMenuNum--;
			if (gp.ui.selectedMenuNum < 0) gp.ui.selectedMenuNum = gp.ui.menuItems - 1;
		}
		
		if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
			gp.ui.selectedMenuNum++;
			if (gp.ui.selectedMenuNum == gp.ui.menuItems) gp.ui.selectedMenuNum = 0;
		}
		
		if (code == KeyEvent.VK_P) {
			if (gp.gameState == GamePanel.STATE_PLAY) gp.gameState = GamePanel.STATE_PAUSE;
			else if (gp.gameState == GamePanel.STATE_PAUSE) gp.gameState = GamePanel.STATE_PLAY;
		}
		
		if (code == KeyEvent.VK_ENTER) {
//			if (gp.ui.selectedMenuNum == gp.ui.MENU_OPTION_START) {
//				gp.gameState = GamePanel.STATE_PLAY;
//			}
//			
//			if (gp.ui.selectedMenuNum == gp.ui.MENU_OPTION_EXIT && gp.gameState == GamePanel.STATE_MENU_SCREEN) {
//				System.exit(0);
//			}
		}
		
		if (code == KeyEvent.VK_1) {
			gp.player.inventory.setSelectedGun(Inventory.GUN_SLOT_1);
			gp.sound.play(Sound.GUN_CLOCK);
		}
		
		if (code == KeyEvent.VK_2) {
			gp.player.inventory.setSelectedGun(Inventory.GUN_SLOT_2);
			gp.sound.play(Sound.GUN_CLOCK);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			UP = false;
		}
		
		if (code == KeyEvent.VK_S) {
			DOWN = false;
		}
		
		if (code == KeyEvent.VK_A) {
			LEFT = false;
		}
		
		if (code == KeyEvent.VK_D) {
			RIGHT = false;
		}
		
		if (code == KeyEvent.VK_E) {
			KEY_E = false;
		}
	}

}
