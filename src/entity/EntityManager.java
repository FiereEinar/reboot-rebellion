package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import enemy.ENM_Ranger_1;
import enemy.ENM_Skeleton;
import main.GamePanel;
import main.Renderable;

public class EntityManager implements Renderable {

	GamePanel gp;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Projectile> bullets = new ArrayList<Projectile>();

	public EntityManager(GamePanel gp) {
		this.gp = gp;
		initEnemies();
	}
	
	private void initEnemies() {
		int enemyCount = 20;
		
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
	
	public void addBullets(Projectile bullet) {
		bullets.add(bullet);
	}
	
	public ArrayList<Entity> getEnities() {
		return this.entities;
	}
	
	public ArrayList<Projectile> getProjectiles() {
		return this.bullets;
	}

	@Override
	public void update() {
		Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
        	Entity entity = iterator.next();
        	entity.update();
            if (entity.getHealth() <= 0) {
                iterator.remove();
            }
        }
        
		Iterator<Projectile> iterator1 = bullets.iterator();
        while (iterator1.hasNext()) {
        	Projectile bullet = iterator1.next();
            bullet.update();
            if (bullet.isDead) {
            	iterator1.remove();
            }
        }
	}

	@Override
	public void draw(Graphics2D g2) {
		for (Entity e: entities)  e.draw(g2);
		for (Projectile b: bullets) b.draw(g2);
	}

}
