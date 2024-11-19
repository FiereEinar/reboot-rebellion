package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	private GamePanel gp;

	public Boolean UP;
	public Boolean DOWN;
	public Boolean LEFT;
	public Boolean RIGHT;
	public Boolean SHOOTING;

	public KeyHandler(GamePanel gp) {
		UP = false; 
		DOWN = false;
		LEFT = false;
		RIGHT = false;
		SHOOTING = false;
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
		
		if (code == KeyEvent.VK_SPACE) {
			SHOOTING = true;
		}
		
		if (code == KeyEvent.VK_P) {
			if (gp.gameState == gp.STATE_PLAY) gp.gameState = gp.STATE_PAUSE;
			else if (gp.gameState == gp.STATE_PAUSE) gp.gameState = gp.STATE_PLAY;
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
		
		if (code == KeyEvent.VK_SPACE) {
			SHOOTING = false;
		}
	}

}
