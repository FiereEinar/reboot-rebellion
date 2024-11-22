package entity;

import java.awt.Graphics2D;
import java.util.Random;

import main.GamePanel;
import main.Renderable;
import sprite.SpriteManager;
import states.StateManager;

public class Entity extends BaseEntity implements Renderable {

	public GamePanel gp;
	private int speed;
	private String direction;
	public SpriteManager sprite = new SpriteManager(this);
	public Boolean movementDisabled = false;
	public int actionLockCounter = 0;
	public Boolean isPlayer = false;
	public int damage = 1;
	public StateManager state = new StateManager();
	
	public int maxHealth;
	public int health;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public void updateDirection() {
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
	
	protected void updateCoordinates() {
		if (this.movementDisabled)
			return;

		if (this.getDirection().equalsIgnoreCase("up")) {
			if ((this.worldY - this.getSpeed()) < 0) return;
			this.worldY -= this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("down")) {
			if ((this.worldY + this.getSpeed()) > gp.worldHeight) return;
			this.worldY += this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("left")) {
			if ((this.worldX - this.getSpeed()) < 0) return;
			this.worldX -= this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("right")) {
			if ((this.worldX + this.getSpeed()) > gp.worldWidth) return;
			this.worldX += this.getSpeed();
		}
	}
	
	public void recieveDamage(int damage) {
		if (!state.isInvincible()) {
			this.health -= damage;
			state.setIsInvincible(true);
		}
	}
	
	private void checkIfCollidingWithPlayer() {
		if (this.isPlayer) return;

		if (gp.cd.isCollidingWithPlayer(this)) {
			this.movementDisabled = true;
			gp.player.recieveDamage(damage);
		}
	}
	
	protected void checkWorldCollision() {
		if (gp.cd.checkWorldCollision(this)) {
			this.movementDisabled = true;
		}
	}
	
	private void checkEntitiesCollision() {
		Entity entity = gp.cd.checkEntityCollision(this);
		
		if (entity != null) {
			this.movementDisabled = true;
		}
	}
	
//	protected void updateInvincibilityFrame() {
//		if (isInvincible) {
//			invincibilityCounter++;
//			
//			if (invincibilityCounter == invincibleTime) {
//				invincibilityCounter = 0;
//				isInvincible = false;
//			}
//		}
//	}
	
	public Vector2 getScreenLocation() {
		Vector2 res = new Vector2();
		
		res.x = worldX - gp.player.worldX + gp.player.screenX;
		res.y = worldY - gp.player.worldY + gp.player.screenY;
		
		return res;
	}
	
	@Override
	public void update() {
		this.movementDisabled = false;
		updateDirection();
		state.update();
//		updateInvincibilityFrame();
		checkWorldCollision();
		checkEntitiesCollision();
		checkIfCollidingWithPlayer();
		updateCoordinates();
	}

	@Override
	public void draw(Graphics2D g2) {
		if (this.health <= 0) return;
		
		Vector2 screen = getScreenLocation();
		
		g2.drawImage(this.sprite.getSprite(), screen.x, screen.y, gp.tileSize, gp.tileSize, null);
	}

}
