package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	public Boolean UP;
	public Boolean DOWN;
	public Boolean LEFT;
	public Boolean RIGHT;
	public Boolean SHOOTING;

	public KeyHandler() {
		UP = false; 
		DOWN = false;
		LEFT = false;
		RIGHT = false;
		SHOOTING = false;
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
