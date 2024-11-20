package entity;

import java.awt.Graphics2D;

import main.GamePanel;
import main.Renderable;

public class EntityManager implements Renderable {

	GamePanel gp;
	private Entity[] entities = new Entity[10];

	public EntityManager(GamePanel gp) {
		this.gp = gp;
		initNPCs();
	}
	
	private void initNPCs() {
		entities[0] = new Enemy_Robot_1(gp);
	}
	
	public Entity[] getEnities() {
		return this.entities;
	}

	@Override
	public void update() {
		for (Entity e: entities) {
			if (e != null) e.update();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		for (Entity e: entities) {
			if (e != null) e.draw(g2);
		}
	}

}
