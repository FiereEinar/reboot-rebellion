package enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import entity.Vector2;
import main.GamePanel;
import object.OBJ_Ammo;
import object.OBJ_Heart;

public class Enemy extends Entity {

	private Rectangle attackRange = new Rectangle(0, 0, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
	
	public Enemy(GamePanel gp) {
		super(gp);
	}

	public Rectangle getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(Rectangle attackRange) {
		this.attackRange = attackRange;
	}

	private void checkIfCollidingWithPlayer() {
		if (this.isPlayer)
			return;

		if (gp.cd.isCollidingWithPlayer(this)) {
			this.movementDisabled = true;
			if (!state.dying.getState() && !isDead)
				gp.player.recieveDamage(damage);
		}
	}

	protected void roamEntity() {
		if (state.dying.getState())
			return;

		actionLockCounter++;

		if (actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;

			if (i <= 25) {
				setDirection("up");
			}
			if (i > 25 && i <= 50) {
				setDirection("down");
			}
			if (i > 50 && i <= 75) {
				setDirection("left");
			}
			if (i > 75 && i <= 100) {
				setDirection("right");
			}

			actionLockCounter = 0;
		}
	}

	protected void drawHealthBar(Graphics2D g2) {
		Vector2 screen = getScreenLocation();

		double oneScale = (double) GamePanel.TILE_SIZE / maxHealth;
		double healthBarWidth = oneScale * health;

		g2.setColor(Color.GRAY);
		g2.fillRect(screen.x - 1, screen.y - 16, GamePanel.TILE_SIZE + 2, 12);

		g2.setColor(Color.RED);
		g2.fillRect(screen.x, screen.y - 15, (int) healthBarWidth, 10);
	}

	protected Boolean isCloseToPlayer() {
		int width = Entity.DETECTION_RANGE_WIDTH;
		int height = Entity.DETECTION_RANGE_HEIGHT;
		int offsetW = width / 2;
		int offsetH = height / 2;

		attackDetectionRange.x = worldX - offsetW;
		attackDetectionRange.y = worldY - offsetH;

		gp.player.attackDetectionRange.x = gp.player.worldX - offsetW;
		gp.player.attackDetectionRange.y = gp.player.worldY - offsetH;

		if (attackDetectionRange.intersects(gp.player.attackDetectionRange)) {
			return true;
		}

		return false;
	}

	private void checkIfCloseToPlayer() {
		if (isCloseToPlayer()) {
			lockToPlayer = true;
		} else {
			lockToPlayer = false;
		}
	}

	// override this when extending to have specific attacks
	protected void attack() {
	}

	protected void updateDirection() {
		if (state.dying.getState())
			return;

		if (lockToPlayer) {
			attack();
		} else {
			roamEntity();
		}
	}

	private void dropItem() {
		int i = new Random().nextInt(100) + 1;

		Rectangle rec = this.getSolidAreaRelativeToWorld();

		int x = rec.x + rec.width / 2;
		int y = rec.y + rec.height / 2;

		if (i <= 25) {
			gp.om.addObject(new OBJ_Heart(x, y));
		}
		if (i > 25 && i <= 50) {
			gp.om.addObject(new OBJ_Ammo(x, y));
		}
	}

	@Override
	public void update() {
		this.movementDisabled = false;
		state.update();
		checkState();
		if (isDead) {
			dropItem();
			return;
		}
		checkIfCloseToPlayer();
		checkIfCollidingWithPlayer();
		updateDirection();
		checkWorldCollision();
		updateCoordinates();
	}

	@Override
	public void draw(Graphics2D g2) {
		if (isDead)
			return;

//		if (state.invincibility.getState()) {
//			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
//		}

		Vector2 screen = getScreenLocation();
		if (gp.isInPlayerView(new Vector2(worldX, worldY)))
			g2.drawImage(this.sprite.getSprite(), screen.x, screen.y, null);
//		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		drawHealthBar(g2);
	}

}
