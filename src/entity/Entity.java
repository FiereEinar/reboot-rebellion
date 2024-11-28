package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;
import main.Renderable;
import sprite.SpriteManager;
import states.StateManager;

public class Entity extends BaseEntity implements Renderable {

	public GamePanel gp;
	public static final int DETECTION_RANGE_WIDTH = 500;
	public static final int DETECTION_RANGE_HEIGHT = 350;
	private int speed;
	private String direction;
	private String spriteDirection = "left";
	public int damage = 1;
	
	public Boolean movementDisabled = false;
	public int actionLockCounter = 0;
	public Boolean isPlayer = false;
	public Boolean isDead = false;
	
	public SpriteManager sprite = new SpriteManager(this);
	public StateManager state = new StateManager();
	public Rectangle attackDetectionRange = new Rectangle(0, 0, DETECTION_RANGE_WIDTH, DETECTION_RANGE_HEIGHT);

	protected int maxHealth;
	protected int health;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	// used AFTER loading entity sprites so that the interval adjusts based in sprites len
	protected void updateSpritesInterval() {
		// for dying state
		int dyingSpriteLen = this.sprite.dying.getSpritesSize();
		int dyingStateDuration = this.state.dying.getStateDuration();
		if (dyingSpriteLen != 0) {
			this.sprite.dying.setInterval(dyingStateDuration / dyingSpriteLen);
		}
		
		// for attacking state
		// REMINDINERS: attackingSpriteLen is based on DOWN since all sprites have the same len
		// change this when the sprites differs in sizes
		int attackingSpriteLen = this.sprite.attackingDown.getSpritesSize();
		int attackingStateDuration = this.state.attacking.getStateDuration();
		if (attackingSpriteLen != 0) {
			int interval = attackingStateDuration / attackingSpriteLen;
			this.sprite.attackingDown.setInterval(interval);
			this.sprite.attackingUp.setInterval(interval);
			this.sprite.attackingLeft.setInterval(interval);
			this.sprite.attackingRight.setInterval(interval);
		}
		
		int attacking2SpriteLen = this.sprite.attackingDown2.getSpritesSize();
		int attacking2StateDuration = this.state.attacking.getStateDuration();
		if (attacking2SpriteLen != 0) {
			int interval = attacking2StateDuration / attacking2SpriteLen;
			this.sprite.attackingDown2.setInterval(interval);
			this.sprite.attackingUp2.setInterval(interval);
			this.sprite.attackingLeft2.setInterval(interval);
			this.sprite.attackingRight2.setInterval(interval);
		}
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

	public void recieveDamage(int damage) {
		if (!state.invincibility.getState()) {
			this.state.attacked.setState(true);
			this.health -= damage;
			if (this.health <= 0) state.dying.setState(true);
			if (isPlayer) state.invincibility.setState(true);
		}
	}

	protected void updateCoordinates() {
		if (this.movementDisabled || state.dying.getState() && !isPlayer)
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
	
	protected void checkState() {
		if (state.dying.isTriggered()) {
			isDead = true;
			return;
		}
		
		if (state.attacking.getState() && !isPlayer) {
			movementDisabled = true;
		}
	}

	public Vector2 getScreenLocation() {
		Vector2 res = new Vector2();

		res.x = worldX - gp.player.worldX + gp.player.screenX;
		res.y = worldY - gp.player.worldY + gp.player.screenY;

		return res;
	}

	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics2D g2) {
	}

	public String getSpriteDirection() {
		return spriteDirection;
	}

	public void setSpriteDirection(String spriteDirection) {
		this.spriteDirection = spriteDirection;
	}

}
