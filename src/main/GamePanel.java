package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.EntityManager;
import entity.Player;
import object.ObjectManager;
import tile.TileManager;
import ui.UI;

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

	public KeyHandler keys = new KeyHandler(this);
	public Player player = new Player(this, keys);
	public TileManager tm = new TileManager(this);
	public CollisionDetector cd = new CollisionDetector(this);
	public ObjectManager om = new ObjectManager(this);
	public UI ui = new UI(this);
	public EntityManager em = new EntityManager(this);
	public EventHandler eh = new EventHandler(this);
	private Debug debug = new Debug(this);
	
	public int gameState;
	public final int STATE_MENU_SCREEN = 0;
	public final int STATE_PAUSE = 1;
	public final int STATE_PLAY = 2;
	public final int STATE_DIALOGUE = 3;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(keys);
		this.gameState = STATE_MENU_SCREEN;
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
		debug.update();
		
		if (gameState == STATE_PLAY) {
			om.update();
			em.update();
			player.update();
		}

		if (gameState == STATE_PAUSE) {

		}

		if (gameState == STATE_DIALOGUE) {

		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		switch (gameState) {
		case STATE_MENU_SCREEN:
			ui.draw(g2);
			break;
		default:
			tm.draw(g2);
			om.draw(g2);
			em.draw(g2);
			player.draw(g2);
			ui.draw(g2);
			break;
		}

		debug.draw(g2);

		g2.dispose();
	}

}
