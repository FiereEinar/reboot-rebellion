package npc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.Vector2;
import main.GamePanel;

public abstract class NPC extends Entity {
	
	private Boolean followPlayer = false;

	public NPC(GamePanel gp) {
		super(gp);
		type = ENTITY_TYPE.NPC;
	}
	
	private void checkIfCollidingWithPlayer() {
		if (gp.cd.isCollidingWithPlayer(this)) {
			followPlayer = true;
		}
	}
	
	@Override
	protected Color getHealthbarColor() {
		return Color.BLUE;
	}
	
	@Override
	protected void updateCoordinates() {
		if (!followPlayer) return;
		moveToPlayer();
		super.updateCoordinates();
	}
	
	@Override
	public void update() {
		this.movementDisabled = false;
		state.update();
		checkState();
		checkIfCollidingWithPlayer();
		checkWorldCollision();
		updateCoordinates();
	}

	@Override
	public void draw(Graphics2D g2) {
		if (isDead) return;

		drawHealthBar(g2);
		Vector2 screen = getScreenLocation();
		BufferedImage image = sprite.getSprite();
		
		if (!followPlayer || movementDisabled) {
			image = sprite.getIdleSprite();
		}
		
		if (gp.isInPlayerView(new Vector2(worldX, worldY)))
			g2.drawImage(image, screen.x, screen.y, null);
	}

}
