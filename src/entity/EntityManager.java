package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import enemy.ENM_Ranger_1;
import enemy.ENM_Skeleton;
import main.GamePanel;
import main.Renderable;

public class EntityManager implements Renderable {

	GamePanel gp;
	private ArrayList<Entity> entities = new ArrayList<Entity>();

	public EntityManager(GamePanel gp) {
		this.gp = gp;
		initEnemies();
	}
	
	private void initEnemies() {
		int enemyCount = 100;
		
		for (int i = 0; i < enemyCount; i++) {
			int rand = (int) Math.floor(Math.random() * 2);
			
			Random random = new Random();
			int randX = random.nextInt(gp.worldWidth);
			int randY = random.nextInt(gp.worldHeight);
			
			if (rand == 1) {
				entities.add(new ENM_Ranger_1(gp, randX, randY));
			} else {
				entities.add(new ENM_Skeleton(gp, randX, randY));
			}
		}
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
