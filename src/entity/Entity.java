package entity;

import java.awt.Graphics2D;

import main.GamePanel;
import main.Renderable;
import sprite.SpriteManager;

public class Entity extends BaseEntity implements Renderable {

	public GamePanel gp;
	private int speed;
	private String direction;
	public SpriteManager sprite = new SpriteManager(this);
	public Boolean movementDisabled = false;
	public int actionLockCounter = 0;
	
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

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g2) {
	}

}
