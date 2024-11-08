package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Enemy;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	private final int originalTileSize = 16;
	private final int scale = 3;

	public final int tileSize = originalTileSize * scale;
	public final int col = 20;
	public final int row = 12;

	public final int screenWidth = col * tileSize;
	public final int screenHeight = row * tileSize;

	public final int FPS = 60;

	public final int worldCol = 100;
	public final int worldRow = 100;
	public final int worldWidth = worldCol * tileSize;
	public final int worldHeight = worldRow * tileSize;

	private Thread thread = null;
	
	private KeyHandler keys = new KeyHandler();
	private KeyHandler2 keys2 = new KeyHandler2();
	
	public Player player = new Player(this, keys);
	public Enemy player2 = new Enemy(this, keys2);
	
	public TileManager tm = new TileManager(this);
	public CollisionDetector cd = new CollisionDetector(this);

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(keys);
		this.addKeyListener(keys2);
	}

	public void startGameThread() {
		thread = new Thread(this);
		thread.run();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (thread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 0) {
				update();
				repaint();
				delta--;
			}
		}
	}

	public void update() {
		player.update();
		player2.update();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		tm.draw(g2);
		player.draw(g2);
		player2.draw(g2);
		
		g2.dispose();
	}

}
