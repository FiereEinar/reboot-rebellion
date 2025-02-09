package sprite;

import java.awt.image.BufferedImage;

import entity.Entity;

public class SpriteManager {

	public Sprite left = new Sprite();
	public Sprite right = new Sprite();
	public Sprite up = new Sprite();
	public Sprite down = new Sprite();
	
	public Sprite dying = new Sprite();
	public Sprite idleRight = new Sprite();
	public Sprite idleLeft = new Sprite();
	
	public Sprite attackingLeft = new Sprite();
	public Sprite attackingRight = new Sprite();
	public Sprite attackingUp = new Sprite();
	public Sprite attackingDown = new Sprite();
	
	public Sprite attackingLeft2 = new Sprite();
	public Sprite attackingRight2 = new Sprite();
	public Sprite attackingUp2 = new Sprite();
	public Sprite attackingDown2 = new Sprite();
	
	public Sprite attackedLeft = new Sprite();
	public Sprite attackedRight = new Sprite();
	public Sprite attackedUp = new Sprite();
	public Sprite attackedDown = new Sprite();

	Entity entity;
	int spriteSize = Sprite.DEFAULT_SPRITE_SIZE;

	public SpriteManager(Entity entity) {
		this.entity = entity;
	}
	
	public SpriteManager(Entity entity, int spriteSize) {
		this.entity = entity;
		this.spriteSize = spriteSize;
		reInitializeSprites();
	}
	
	// REMINDERS: add a sprite reseter if you add another sprite, 
	// this is skill issue, poor code structure fr
	private void resetSpritesInterval() {
		up.resetCounters();
		down.resetCounters();
		left.resetCounters();
		right.resetCounters();
		
		dying.resetCounters();
		idleRight.resetCounters();
		idleLeft.resetCounters();
		
		attackingLeft.resetCounters();
		attackingRight.resetCounters();
		attackingUp.resetCounters();
		attackingDown.resetCounters();
		
		attackingLeft2.resetCounters();
		attackingRight2.resetCounters();
		attackingUp2.resetCounters();
		attackingDown2.resetCounters();
		
		attackedLeft.resetCounters();
		attackedRight.resetCounters();
		attackedUp.resetCounters();
		attackedDown.resetCounters();
	}
	
	private void reInitializeSprites() {
		up = new Sprite(spriteSize);
		down = new Sprite(spriteSize);
		left = new Sprite(spriteSize);
		right = new Sprite(spriteSize);
		
		dying = new Sprite(spriteSize);
		idleRight = new Sprite(spriteSize);
		idleLeft = new Sprite(spriteSize);
		
		attackingLeft = new Sprite(spriteSize);
		attackingRight = new Sprite(spriteSize);
		attackingUp = new Sprite(spriteSize);
		attackingDown = new Sprite(spriteSize);
		
		attackingLeft2 = new Sprite(spriteSize);
		attackingRight2 = new Sprite(spriteSize);
		attackingUp2 = new Sprite(spriteSize);
		attackingDown2 = new Sprite(spriteSize);
		
		attackedLeft = new Sprite(spriteSize);
		attackedRight = new Sprite(spriteSize);
		attackedUp = new Sprite(spriteSize);
		attackedDown = new Sprite(spriteSize);
	}
	
	private void assertIfShouldResetSprites() {
		if (entity.state.shouldResetSprites()) {
			resetSpritesInterval();
			entity.state.offShouldResetSprites();
		}
	}

	public BufferedImage getSprite() {
		assertIfShouldResetSprites();
		
		if (entity.state.dying.getState()) {
			if (dying.getSpritesSize() != 0) {
				return dying.getSprite();				
			}
			return down.getSprite();
		}

		
		if (entity.isPlayer) {
			if (!entity.gp.keys.isMoving() && idleLeft.getSpritesSize() != 0 && idleRight.getSpritesSize() != 0) {
				if (entity.getSpriteDirection().equalsIgnoreCase("left")) {
					if (entity.state.attacked.getState()) return attackedLeft.getSprite();
					if (entity.state.attacking.getState()) return attackingLeft.getSprite();
					return idleLeft.getSprite();
				} else {
					if (entity.state.attacked.getState()) return attackedRight.getSprite();
					if (entity.state.attacking.getState()) return attackingRight.getSprite();
					return idleRight.getSprite();
				}
			}

			return getPlayerSprite();
		} else {
			return getEntitySprite();
		}
	}
	
	public BufferedImage getAttack2Sprite() {
		assertIfShouldResetSprites();
		
		if (entity.getDirection().equals("up")) {
			return attackingUp2.getSprite();
		}
		if (entity.getDirection().equals("left")) {
			return attackingLeft2.getSprite();
		}
		if (entity.getDirection().equals("right")) {
			return attackingRight2.getSprite();
		}

		// default return sprite
		return attackingDown2.getSprite();
	}
	
	public BufferedImage getIdleSprite() {
		assertIfShouldResetSprites();
		
		if (entity.getSpriteDirection().equalsIgnoreCase("left")) {
			if (entity.state.attacked.getState()) return attackedLeft.getSprite();
			if (entity.state.attacking.getState()) return attackingLeft.getSprite();
			return idleLeft.getSprite();
		} else {
			if (entity.state.attacked.getState()) return attackedRight.getSprite();
			if (entity.state.attacking.getState()) return attackingRight.getSprite();
			return idleRight.getSprite();
		}
	}
	
	public BufferedImage safeGetSprite() {
		if (entity.isPlayer && !entity.gp.keys.isMoving() && idleLeft.getSpritesSize() != 0 && idleRight.getSpritesSize() != 0) {
			if (entity.getSpriteDirection().equalsIgnoreCase("left")) {
				if (entity.state.attacked.getState()) return attackedLeft.safeGetSprite();
				if (entity.state.attacking.getState()) return attackingLeft.safeGetSprite();
				return idleLeft.safeGetSprite();
			} else {
				if (entity.state.attacked.getState()) return attackedRight.safeGetSprite();
				if (entity.state.attacking.getState()) return attackingRight.safeGetSprite();
				return idleRight.safeGetSprite();
			}
		}
		
		if (entity.getSpriteDirection().equalsIgnoreCase("left")) {
			if (entity.state.attacked.getState()) return attackedLeft.safeGetSprite();
			if (entity.state.attacking.getState()) return attackingLeft.safeGetSprite();
			return left.safeGetSprite();
		} else {
			if (entity.state.attacked.getState()) return attackedRight.safeGetSprite();
			if (entity.state.attacking.getState()) return attackingRight.safeGetSprite();
			return right.safeGetSprite();
		}
	}
	
	private BufferedImage getPlayerSprite() {
		if (entity.getSpriteDirection().equalsIgnoreCase("left")) {
			if (entity.state.attacked.getState()) return attackedLeft.getSprite();
			if (entity.state.attacking.getState()) return attackingLeft.getSprite();
			return left.getSprite();
		} else {
			if (entity.state.attacked.getState()) return attackedRight.getSprite();
			if (entity.state.attacking.getState()) return attackingRight.getSprite();
			return right.getSprite();
		}
	}
	
	private BufferedImage getEntitySprite() {
		if (entity.getDirection().equals("up")) {
			if (entity.state.attacking.getState()) return attackingUp.getSprite();
			if (entity.state.attacked.getState()) return attackedUp.getSprite();
			return up.getSprite();
		}
		if (entity.getDirection().equals("left")) {
			if (entity.state.attacking.getState()) return attackingLeft.getSprite();
			if (entity.state.attacked.getState()) return attackedLeft.getSprite();
			return left.getSprite();
		}
		if (entity.getDirection().equals("right")) {
			if (entity.state.attacking.getState()) return attackingRight.getSprite();
			if (entity.state.attacked.getState()) return attackedRight.getSprite();
			return right.getSprite();
		}

		// default return sprite
		if (entity.state.attacking.getState()) return attackingDown.getSprite();
		if (entity.state.attacked.getState()) return attackedDown.getSprite();
		return down.getSprite();
	}

}
