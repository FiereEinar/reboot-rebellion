package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import entity.EntityManager;
import pathfinder.PathFinder;
import entity.Player;
import entity.Vector2;
import event.EventHandler;
import object.ObjectManager;
import tile.TileManager;
import ui.UI;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	private static final int originalTileSize = 16;
	private static final int scale = 3;
	public static final int TILE_SIZE = originalTileSize * scale;
	
	public final int col = 20;
	public final int row = 12;

	public int screenWidth = col * TILE_SIZE;
	public int screenHeight = row * TILE_SIZE;

	public final int FPS = 60;

	public final int worldCol = 50;
	public final int worldRow = 50;
	public final int worldWidth = worldCol * TILE_SIZE;
	public final int worldHeight = worldRow * TILE_SIZE;
	
	public int fullScreenWidth = screenWidth;
	public int fullScreenHeight = screenHeight;

	private Thread thread = null;
	private BufferedImage imageScreen;
	private Graphics2D g2;

	public KeyHandler keys = new KeyHandler(this);
	public MouseHandler mouse = new MouseHandler(this);
	public Player player;
	public TileManager tm = new TileManager(this);
	public CollisionDetector cd = new CollisionDetector(this);
	public ObjectManager om = new ObjectManager(this);
	public UI ui = new UI(this);
	public EntityManager em = new EntityManager(this);
	public EventHandler eh = new EventHandler(this);
	private Debug debug = new Debug(this);
	public PathFinder pathFinder = new PathFinder(this);
	
	public int gameState;
	public static final int STATE_MENU_SCREEN = 0;
	public static final int STATE_PAUSE = 1;
	public static final int STATE_PLAY = 2;
	public static final int STATE_DIALOGUE = 3;

	public GamePanel() {
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(keys);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.setupGame();
		this.setPreferredSize(new Dimension(fullScreenWidth, fullScreenHeight));
	}
	
	private void setupGame() {
		gameState = STATE_MENU_SCREEN;
		imageScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) imageScreen.getGraphics();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        fullScreenWidth = screenSize.width;
        fullScreenHeight = screenSize.height;
        
        player = new Player(this, keys);
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
				drawToImageScreen();
				drawToScreen();
				delta--;
			}
		}
	}

	private void update() {
		debug.update();
		ui.update();
		
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
	
	private void drawToImageScreen() {
		switch (gameState) {
		case STATE_MENU_SCREEN:
			break;
		default:
			tm.draw(g2);
			om.draw(g2);
			em.draw(g2);
			player.draw(g2);
			break;
		}

		debug.draw(g2);
		ui.draw(g2);
	}
	
	private void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(imageScreen, 0, 0, fullScreenWidth, fullScreenHeight, null);
	}
	
	public Boolean isInPlayerView(Vector2 position) {
		int offset = TILE_SIZE;
		
		Boolean isInView = position.x + offset > player.worldX - player.screenX
			&& position.x - offset < player.worldX + player.screenX
			&& position.y + offset > player.worldY - player.screenY
			&& position.y - offset < player.worldY + player.screenY;
				
		return isInView;
	}
	
}
