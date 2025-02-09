package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;
import main.Renderable;
import main.Sound;
import sprite.SpriteManager;
import states.StateManager;

public abstract class Entity extends BaseEntity implements Renderable {

	public GamePanel gp;

	public static final int DETECTION_RANGE_WIDTH = 500;
	public static final int DETECTION_RANGE_HEIGHT = 350;
	
	public int actionLockCounter = 0;
	public int killPoints = 0;
	public int damage = 1;
	public int hitSound;
	private int speed;

	private String direction;
	private String spriteDirection = "left";
	
	public Boolean movementDisabled = false;
	public Boolean isPlayer = false;
	public Boolean isDead = false;
	protected Boolean lockToPlayer = true;

	public StateManager state = new StateManager();
	public SpriteManager sprite = new SpriteManager(this);
	public Rectangle attackDetectionRange = new Rectangle(0, 0, DETECTION_RANGE_WIDTH, DETECTION_RANGE_HEIGHT);

	protected int maxHealth;
	protected int health;
	
	public enum ENTITY_TYPE {
		NPC,
		BOSS,
		ENEMY
	}
	
	public ENTITY_TYPE type;

	public Entity(GamePanel gp) {
		this.gp = gp;
		hitSound = Sound.NONE;
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
	
	public void reduceHealth(int amount) {
		this.health -= amount;
	}
	
	public void increaseHealth(int amount) {
		this.health += amount;
	}
	
	public void setMaxHealth(int health) {
		this.maxHealth = health;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}

	public void recieveDamage(int damage) {
		if (!state.invincibility.getState()) {
			gp.sound.play(hitSound);
			if (isPlayer && gp.keys.NOCLIP) return;
			this.state.attacked.setState(true);
			if (this.health > 0) this.health -= damage;
			if (this.health <= 0 && !state.dying.isTriggered()) state.dying.setState(true);
			if (isPlayer || type == ENTITY_TYPE.NPC) state.invincibility.setState(true);
		}
	}

	protected void updateCoordinates() {
		if (this.movementDisabled || state.dying.getState())
			return;
		
		Rectangle solidArea = getSolidArea();
		
		Boolean cantGoUp = ((this.worldY + solidArea.y) - this.getSpeed()) < 0;
		Boolean cantGoDown = (this.worldY + solidArea.height + solidArea.y + this.getSpeed()) > gp.worldHeight;
		Boolean cantGoLeft = ((this.worldX + solidArea.x) - this.getSpeed()) < 0;
		Boolean cantGoRight = (this.worldX + solidArea.width + solidArea.x + this.getSpeed()) > gp.worldWidth;

		if (this.getDirection().equalsIgnoreCase("up")) {
			if (cantGoUp)
				return;
			this.worldY -= this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("down")) {
			if (cantGoDown)
				return;
			this.worldY += this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("left")) {
			if (cantGoLeft)
				return;
			this.worldX -= this.getSpeed();
		}
		if (this.getDirection().equalsIgnoreCase("right")) {
			if (cantGoRight)
				return;
			this.worldX += this.getSpeed();
		}
	}
	
	protected void moveToPlayer() {
	    if (movementDisabled) return;

	    Vector2 playerVector = new Vector2();
	    playerVector.x = (gp.player.worldX + gp.player.getSolidArea().x) / GamePanel.TILE_SIZE;
	    playerVector.y = (gp.player.worldY + gp.player.getSolidArea().y) / GamePanel.TILE_SIZE;
	    
	    searchPath(playerVector);
	}
	
	protected void moveTowards(Vector2 targetWorldPos) {
		if (movementDisabled) return;
	    Boolean isFartherFromX = Math.abs(this.worldX - targetWorldPos.x) > Math.abs(this.worldY - targetWorldPos.y);

	    if (isFartherFromX) {
	        if (this.worldX - getSpeed() > targetWorldPos.x) {
	            setDirection("left");
	        } else if (this.worldX + getSpeed() < targetWorldPos.x) {
	            setDirection("right");
	        }
	    } else {
	        if (this.worldY - getSpeed() > targetWorldPos.y) {
	            setDirection("up");
	        } else if (this.worldY + getSpeed() < targetWorldPos.y) {
	            setDirection("down");
	        }
	    }
	}
	
	public void searchPath(Vector2 goal) {
		int tileSize = GamePanel.TILE_SIZE;
		
		Vector2 start = new Vector2();
		start.x = (worldX + getSolidArea().x + getSolidArea().width) / tileSize;
		start.y = (worldY + getSolidArea().y + getSolidArea().height) / tileSize;
		
		gp.pathFinder.setNodes(start, goal);
		
		if (gp.pathFinder.search()) {
			Vector2 nextPos = gp.pathFinder.pathList.get(0).position;
			
			int nextX = nextPos.x * tileSize;
			int nextY = nextPos.y * tileSize;
			
			int enLeftX = worldX + getSolidArea().x;
			int enRightX = worldX + getSolidArea().x + getSolidArea().width;
			int enTopY = worldY + getSolidArea().y;
			int enBottomY = worldY + getSolidArea().y + getSolidArea().height;
			
			if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + tileSize) {
				setDirection("up");
			}
			else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + tileSize) {
				setDirection("down");
			}
			else if (enTopY >= nextY && enBottomY < nextY + tileSize) {
				if (enLeftX > nextX) setDirection("left");
				if (enLeftX < nextX) setDirection("right");
			}
			else if (enTopY > nextY && enLeftX > nextX) {
				setDirection("up");
				checkWorldCollision();
				if (movementDisabled) setDirection("left");
			}
			else if (enTopY > nextY && enLeftX < nextX) {
				setDirection("up");
				checkWorldCollision();
				if (movementDisabled) setDirection("right");
			}
			else if (enTopY < nextY && enLeftX > nextX) {
				setDirection("down");
				checkWorldCollision();
				if (movementDisabled) setDirection("left");
			}
			else if (enTopY < nextY && enLeftX < nextX) {
				setDirection("down");
				checkWorldCollision();
				if (movementDisabled) setDirection("right");
			}
			
			Vector2 next = gp.pathFinder.pathList.get(0).position;
			if (next.x == goal.x && next.y == goal.y && type == ENTITY_TYPE.NPC) {
				movementDisabled = true;
			} else {
				movementDisabled = false;
			}
		} else {
//			moveTowards(goal.mul(tileSize));
			roamEntity();
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

	
	protected Color getHealthbarColor() {
		return Color.RED;
	}

	protected void drawHealthBar(Graphics2D g2) {
		Vector2 screen = getScreenLocation();
		
		int healthBarWidth = sprite.down.safeGetSprite().getWidth();

		double oneScale = (double) healthBarWidth / maxHealth;
		double currentHealthBarWidth = oneScale * health;

		g2.setColor(Color.GRAY);
		g2.fillRect(screen.x - 1, screen.y - 16, healthBarWidth + 2, 12);

		g2.setColor(getHealthbarColor());
		g2.fillRect(screen.x, screen.y - 15, (int) currentHealthBarWidth, 10);
	}

	protected void checkWorldCollision() {
		if (gp.cd.checkWorldCollision(this)) {
			this.movementDisabled = true;
		}
	}

	protected void checkEntitiesCollision() {
		Entity entity = gp.cd.checkEntityCollision(this);

		if (entity != null && !isDead) {
			if (entity.type == ENTITY_TYPE.NPC) {
				this.movementDisabled = true;
				entity.recieveDamage(damage);
			}
		}
	}	
	
	protected void checkState() {
		if (state.dying.isTriggered()) {
			isDead = true;
			return;
		}
		
		if (state.attacking.getState() && !isPlayer) {
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
