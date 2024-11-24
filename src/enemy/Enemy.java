package enemy;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import entity.Entity;
import entity.Player;
import entity.Vector2;
import main.GamePanel;

public class Enemy extends Entity {
	
	private Boolean lockToPlayer = true;
	
	public Enemy(GamePanel gp) {
		super(gp);
	}

	private void checkIfCollidingWithPlayer() {
		if (this.isPlayer)
			return;

		if (gp.cd.isCollidingWithPlayer(this)) {
			this.movementDisabled = true;
			if (!state.dying.getState() && !isDead) gp.player.recieveDamage(damage);
		}
	}
	
	private void drawHealthBar(Graphics2D g2) {
		Vector2 screen = getScreenLocation();

		double oneScale = (double) GamePanel.tileSize / maxHealth;
		double healthBarWidth = oneScale * health;

		g2.setColor(Color.GRAY);
		g2.fillRect(screen.x - 1, screen.y - 16, GamePanel.tileSize + 2, 12);

		g2.setColor(Color.RED);
		g2.fillRect(screen.x, screen.y - 15, (int) healthBarWidth, 10);
	}
	
	private void checkIfCloseToPlayer() {
		int width = Entity.DETECTION_RANGE_WIDTH;
		int height = Entity.DETECTION_RANGE_HEIGHT;
		int offsetW = width / 2;
		int offsetH = height / 2;
		
		attackDetectionRange.x = worldX - offsetW;
		attackDetectionRange.y = worldY - offsetH;
		
		gp.player.attackDetectionRange.x = gp.player.worldX - offsetW;
		gp.player.attackDetectionRange.y = gp.player.worldY - offsetH;
		
		if (attackDetectionRange.intersects(gp.player.attackDetectionRange)) {
			System.out.println("CLOSE");
			lockToPlayer = true;
		} else {
			lockToPlayer = false;
		}
	}
	
	private void moveToPlayer() {
		Player player = gp.player;
		
		if (this.worldX - getSpeed() > player.worldX) {
			setDirection("left");
		} else if (this.worldX + getSpeed() < player.worldX) {
			setDirection("right");
		}
		
		if (this.worldY - getSpeed() > player.worldY) {
			setDirection("up");
		} else if (this.worldY + getSpeed() < player.worldY) {
			setDirection("down");
		}
		
	}

	@Override
	protected void updateDirection() {
		if (state.dying.getState()) return;
		
		if (lockToPlayer) {
			moveToPlayer();
		} else {
			roamEntity();
		}
	}
	
	@Override
	public void update() {
		if (isDead) return;
		state.update();
		checkState();
		checkIfCloseToPlayer();
		this.movementDisabled = false;
		updateDirection();
		checkWorldCollision();
		checkEntitiesCollision();
		checkIfCollidingWithPlayer();
		updateCoordinates();
	}

	@Override
	public void draw(Graphics2D g2) {
		if (state.invincibility.getState()) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}

		Vector2 screen = getScreenLocation();

		g2.drawImage(this.sprite.getSprite(), screen.x, screen.y, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		drawHealthBar(g2);
	}
	
}
