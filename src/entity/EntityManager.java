package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import enemy.ENM_Ranger_1;
import enemy.ENM_Skeleton;
import main.GamePanel;
import main.Renderable;

public class EntityManager implements Renderable {

	GamePanel gp;
	private ArrayList<Entity> entities = new ArrayList<Entity>();

	public EntityManager(GamePanel gp) {
		this.gp = gp;
		initNPCs();
		initEnemies();
	}
	
	private void initNPCs() {
		entities.add(new Enemy_Robot_1(gp));
	}
	
	private void initEnemies() {
		entities.add(new ENM_Ranger_1(gp, 600, 1000));
		entities.add(new ENM_Skeleton(gp, 200, 1200));
		entities.add(new ENM_Skeleton(gp, 400, 1400));
	}
	
	public ArrayList<Entity> getEnities() {
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
