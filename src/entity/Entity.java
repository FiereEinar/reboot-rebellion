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
	public Boolean isDead = false;
	public int damage = 1;
	public StateManager state = new StateManager();

	protected int maxHealth;
	protected int health;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	protected void updateSpritesInterval() {
		int dyingSpriteLen = this.sprite.dying.getSpritesSize();
		int dyingStateDuration = this.state.dying.getStateDuration();
		this.sprite.dying.setInterval(dyingStateDuration / dyingSpriteLen);
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
	
	public int getHealth() {
		return this.health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setMaxHealth(int health) {
		this.maxHealth = health;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}

	public void updateDirection() {
		if (state.dying.getState()) return;
		
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
		if (this.movementDisabled || state.dying.getState())
			return;

		if (this.getDirection().equalsIgnoreCase("up")) {
			if ((this.worldY - this.getSpeed()) < 0)
				return;
			this.worldY -= this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("down")) {
			if ((this.worldY + this.getSpeed()) > gp.worldHeight)
				return;
			this.worldY += this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("left")) {
			if ((this.worldX - this.getSpeed()) < 0)
				return;
			this.worldX -= this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("right")) {
			if ((this.worldX + this.getSpeed()) > gp.worldWidth)
				return;
			this.worldX += this.getSpeed();
		}
	}

	public void recieveDamage(int damage) {
		if (!state.invincibility.getState()) {
			this.health -= damage;
			if (this.health <= 0) state.dying.setState(true);
			if (isPlayer) state.invincibility.setState(true);
		}
	}

	protected void checkWorldCollision() {
		if (gp.cd.checkWorldCollision(this)) {
			this.movementDisabled = true;
		}
	}

	protected void checkEntitiesCollision() {
		Entity entity = gp.cd.checkEntityCollision(this);

		if (entity != null) {
			this.movementDisabled = true;
		}
	}


	public Vector2 getScreenLocation() {
		Vector2 res = new Vector2();

		res.x = worldX - gp.player.worldX + gp.player.screenX;
		res.y = worldY - gp.player.worldY + gp.player.screenY;

		return res;
	}
	
	
	protected void checkState() {
		if (state.dying.isTriggered()) {
			isDead = true;
			return;
		}
		
		if (state.dying.getState()) {
			state.invincibility.setState(!state.invincibility.getState());
			return;
		}
	}

	@Override
	public void update() {
//		state.update();
//		checkState();
//		this.movementDisabled = false;
//		updateDirection();
//		checkWorldCollision();
//		checkEntitiesCollision();
//		checkIfCollidingWithPlayer();
//		updateCoordinates();
	}

	@Override
	public void draw(Graphics2D g2) {
//		if (state.invincibility.getState()) {
//			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
//		}
//
//		Vector2 screen = getScreenLocation();
//
//		g2.drawImage(this.sprite.getSprite(), screen.x, screen.y, null);
//		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//		
//		drawHealthBar(g2);
	}

}
