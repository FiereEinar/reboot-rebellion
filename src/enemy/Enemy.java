package enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import entity.Vector2;
import main.GamePanel;
import main.Sound;
import object.OBJ_Ammo;
import object.OBJ_Heart;

public class Enemy extends Entity {

	private Rectangle attackRange = new Rectangle(0, 0, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
	
	public Enemy(GamePanel gp) {
		super(gp);
		type = ENTITY_TYPE.ENEMY;
		hitSound = Sound.ROBOT_HIT;
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

	protected Boolean isCloseToPlayer() {
		int width = attackDetectionRange.width;
		int height = attackDetectionRange.height;
		int offsetW = width / 2;
		int offsetH = height / 2;
		
		Rectangle rec1 = new Rectangle();
		Rectangle rec2 = new Rectangle();

		rec1.x = worldX - offsetW;
		rec1.y = worldY - offsetH;
		rec1.width = width;
		rec1.height = height;

		rec2.x = gp.player.worldX - gp.player.attackDetectionRange.width / 2;
		rec2.y = gp.player.worldY - gp.player.attackDetectionRange.height / 2;
		rec2.width = gp.player.attackDetectionRange.width;
		rec2.height = gp.player.attackDetectionRange.height;

		return rec1.intersects(rec2);
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
		if (state.dying.getState() || isDead)
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

		if (i <= 35) {
			gp.om.addObject(new OBJ_Heart(x, y));
		}
		if (i > 35 && i <= 100) {
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
		checkEntitiesCollision();
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
