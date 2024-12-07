package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import enemy.ENM_Bomber_1;
import enemy.ENM_Boss_1;
import enemy.ENM_Melee_1;
import enemy.ENM_Ranger_1;
import main.GamePanel;
import main.Renderable;
import projectiles.Projectile;

public class EntityManager implements Renderable {

	GamePanel gp;
	private ArrayList<ArrayList<Entity>> entities = new ArrayList<>();
	private ArrayList<ArrayList<Projectile>> bullets = new ArrayList<>();

	public EntityManager(GamePanel gp) {
		this.gp = gp;
		initLists();
		initEnemies();
	}
	
	private void initLists() {
		for (int i = 0; i < gp.MAX_MAPS; i++) {
			entities.add(new ArrayList<Entity>());
			bullets.add(new ArrayList<Projectile>());
		}
	}
	
	private void initEnemies() {
		int enemyCount = 10;
		int map = 1;
		
		for (int i = 0; i < enemyCount; i++) {
			int rand = (int) Math.floor(Math.random() * 3);
			
			Random random = new Random();
			
			int randX;
			int randY;
			
			while(true) {
				randX = random.nextInt(gp.worldWidth);
				randY = random.nextInt(gp.worldHeight);
				
				if (!gp.tm.isTileSolid(gp.tm.getMapTileNumber(randX, randY))) {
					break;
				}
			}
			
			if (i % 20 == 0) {
				entities.get(map).add(new ENM_Boss_1(gp, randX, randY));
			}
			
			if (rand == 0) {
				entities.get(map).add(new ENM_Ranger_1(gp, randX, randY));
			} else if (rand == 1) {
				entities.get(map).add(new ENM_Melee_1(gp, randX, randY));
			} else {
				entities.get(map).add(new ENM_Bomber_1(gp, randX, randY));
			}
		}
		
		map = 2;
		
		for (int i = 0; i < enemyCount; i++) {
			int rand = (int) Math.floor(Math.random() * 3);
			
			Random random = new Random();
			int randX = random.nextInt(gp.worldWidth);
			int randY = random.nextInt(gp.worldHeight);
//			int randX = 1200 / 48;
//			int randY = 345 / 48;
			
			if (i % 20 == 0) {
				entities.get(map).add(new ENM_Boss_1(gp, randX, randY));
			}
			
			if (rand == 0) {
				entities.get(map).add(new ENM_Ranger_1(gp, randX, randY));
			} else if (rand == 1) {
				entities.get(map).add(new ENM_Melee_1(gp, randX, randY));
			} else {
				entities.get(map).add(new ENM_Bomber_1(gp, randX, randY));
			}
		}
	}
	
	public void addBullets(Projectile bullet) {
		int currentMap = gp.currentMap + 1;
		bullets.get(currentMap).add(bullet);
	}
	
	public void addEntity(Entity entity) {
		int currentMap = gp.currentMap + 1;
		entities.get(currentMap).add(entity);
	}
	
	public ArrayList<Entity> getEnities() {
		return this.entities.get(gp.currentMap + 1);
	}
	
	public ArrayList<Projectile> getProjectiles() {
		return this.bullets.get(gp.currentMap + 1);
	}

	@Override
	public void update() {
		int currentMap = gp.currentMap + 1;
		
		Iterator<Entity> iterator = entities.get(currentMap).iterator();
        while (iterator.hasNext()) {
        	Entity entity = iterator.next();
        	if (entity.isDead) {
        		iterator.remove();
        	} 
        	entity.update();
        }
        
		Iterator<Projectile> iterator1 = bullets.get(currentMap).iterator();
        while (iterator1.hasNext()) {
        	Projectile bullet = iterator1.next();
            if (bullet.isDead) {
            	iterator1.remove();
            }
            bullet.update();
        }
	}

	@Override
	public void draw(Graphics2D g2) {
		int currentMap = gp.currentMap + 1;
		
		for (Entity e: new ArrayList<>(entities.get(currentMap))) {
			if (gp.isInPlayerView(new Vector2(e.worldX, e.worldY))) e.draw(g2);
		}
		
		for (Projectile b: new ArrayList<>(bullets.get(currentMap))) {
			if (gp.isInPlayerView(new Vector2(b.worldX, b.worldY))) b.draw(g2);
		}
	}

}
