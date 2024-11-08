package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler2 extends KeyHandlerTemplate implements KeyListener {

	public KeyHandler2() {
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
		
		if (code == KeyEvent.VK_I) {
			UP = true;
		}
		
		if (code == KeyEvent.VK_K) {
			DOWN = true;
		}
		
		if (code == KeyEvent.VK_J) {
			LEFT = true;
		}
		
		if (code == KeyEvent.VK_L) {
			RIGHT = true;
		}
		
		if (code == KeyEvent.VK_SPACE) {
			SHOOTING = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_I) {
			UP = false;
		}
		
		if (code == KeyEvent.VK_K) {
			DOWN = false;
		}
		
		if (code == KeyEvent.VK_J) {
			LEFT = false;
		}
		
		if (code == KeyEvent.VK_L) {
			RIGHT = false;
		}
		
		if (code == KeyEvent.VK_SPACE) {
			SHOOTING = false;
		}
	}

}
