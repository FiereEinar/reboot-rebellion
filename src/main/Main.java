package main;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		
		GamePanel gp = new GamePanel();
		window.add(gp);
		
		window.pack();
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage(Main.class.getResource("/guns/crosshair.png"));
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, new Point(16, 16), "Custom Cursor");
		window.setCursor(customCursor);
		
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		gp.startGameThread();
	}
}
