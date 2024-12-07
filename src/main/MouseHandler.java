package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

	GamePanel gp;
	public Boolean SHOOTING = false;
	public float mouseX = 0;
	public float mouseY = 0;
	
	public MouseHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
        mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
        mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
        mouseY = e.getY();
        
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (gp.gameState == GamePanel.STATE_MENU_SCREEN) {
				if (gp.ui.startButtonHovered) {
					gp.gameState = GamePanel.STATE_PLAY;
				}
				if (gp.ui.exitButtonHovered) {
					System.exit(0);
				}
			} else {
				SHOOTING = true;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			SHOOTING = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
