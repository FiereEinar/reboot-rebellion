package entity;

import java.awt.Rectangle;

import sprite.SpriteManager;

public class Entity {

	public int worldX;
	public int worldY;
	private int speed;
	private String direction;
	public SpriteManager sprite = new SpriteManager(this);
	public Rectangle solidArea;
	public Boolean movementDisabled = false;

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

}
