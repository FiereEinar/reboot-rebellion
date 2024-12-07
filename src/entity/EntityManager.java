package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import enemy.ENM_Bomber_1;
import enemy.ENM_Boss_1;
import enemy.ENM_Melee_1;
import enemy.ENM_Ranger_1;
import entity.Entity.ENTITY_TYPE;
import main.GamePanel;
import main.Renderable;
import npc.NPC_Scientist;
import projectiles.Projectile;

public class EntityManager implements Renderable {

	GamePanel gp;
	private ArrayList<ArrayList<Entity>> entities = new ArrayList<>();
	private ArrayList<ArrayList<Projectile>> bullets = new ArrayList<>();
	
	private final int DESPAWN_RANGE = 1200;
	private final int HALF_DESPAWN_RANGE = DESPAWN_RANGE / 2;
	private final int MAX_ENTITY_COUNT = 100;
	
	public EntityManager(GamePanel gp) {
		this.gp = gp;
		initLists();
		spawnNPCS();
	}
	
	private void initLists() {
		for (int i = 0; i < gp.MAX_MAPS; i++) {
			entities.add(new ArrayList<Entity>());
			bullets.add(new ArrayList<Projectile>());
		}
	}
	
	private void spawnNPCS() {
		int map = 1;
		
		int tileSize = GamePanel.TILE_SIZE;
		
		int x = 28 * tileSize;
		int y = 5 * tileSize;
		
		entities.get(map).add(new NPC_Scientist(gp, x, y));
	}
	
	public void addBullets(Projectile bullet) {
		getProjectiles().add(bullet);
	}
	
	public void addEntity(Entity entity) {
		getEnities().add(entity);
	}
	
	public ArrayList<Entity> getEnities() {
		return this.entities.get(gp.currentMap + 1);
	}
	
	public ArrayList<Projectile> getProjectiles() {
		return this.bullets.get(gp.currentMap + 1);
	}

	private void removeFarFromPlayerEnemies() {
		Vector2 player = new Vector2(
			gp.player.worldX,
			gp.player.worldY
		);
		
		for (Entity e: getEnities()) {
			double distance = Math.sqrt(Math.pow(e.worldX - player.x, 2) + Math.pow(e.worldY - player.y, 2));
			if (distance > DESPAWN_RANGE && e.type != ENTITY_TYPE.NPC) {
			    e.isDead = true;
			}
		}
	}
	
	private void spawnNewEntities() {
		if (getEnities().size() > MAX_ENTITY_COUNT) return;
	    
		int safeZoneRadius = HALF_DESPAWN_RANGE; // Distance close to the player where entities shouldn't spawn

	    Vector2 player = new Vector2(gp.player.worldX, gp.player.worldY);

	    Random random = new Random();

        int randX, randY;

        while (true) {
            // Generate random positions within the DESPAWN_RANGE
            randX = player.x - HALF_DESPAWN_RANGE + random.nextInt(HALF_DESPAWN_RANGE * 2);
            randY = player.y - HALF_DESPAWN_RANGE + random.nextInt(HALF_DESPAWN_RANGE * 2);

            // Check if coordinates are within map bounds
            if (randX < 0 || randX >= gp.worldWidth || randY < 0 || randY >= gp.worldHeight) {
                continue; // Retry if out of bounds
            }
            
            // Ensure the spawn point is within the despawn range but outside the safe zone
            double distanceToPlayer = Math.sqrt(Math.pow(randX - player.x, 2) + Math.pow(randY - player.y, 2));
            if (distanceToPlayer >= safeZoneRadius && distanceToPlayer <= HALF_DESPAWN_RANGE) {
                // Ensure the tile is not solid
                if (!gp.tm.isTileSolid(gp.tm.getMapTileNumber(randX, randY))) {
                	randX = randX - (randX % GamePanel.TILE_SIZE);
                	randY = randY - (randY % GamePanel.TILE_SIZE);
                    break; // Valid spawn point found
                }
            }
        }

        // Randomly determine the type of entity to spawn
        int rand = random.nextInt(3);
//        int boss = random.nextInt(101);
//
//        if (boss < 25) {
//        	getEnities().add(new ENM_Boss_1(gp, randX, randY));
//        }
        
        if (rand == 0) {
        	getEnities().add(new ENM_Ranger_1(gp, randX, randY));
        } else if (rand == 1) {
        	getEnities().add(new ENM_Melee_1(gp, randX, randY));
        } else {
        	getEnities().add(new ENM_Bomber_1(gp, randX, randY));
        }
	}


	@Override
	public void update() {
		removeFarFromPlayerEnemies();
		
		Iterator<Entity> iterator = getEnities().iterator();
        while (iterator.hasNext()) {
        	Entity entity = iterator.next();
        	if (entity.isDead) {
        		iterator.remove();
        	} 
        	entity.update();
        }
        
		Iterator<Projectile> iterator1 = getProjectiles().iterator();
        while (iterator1.hasNext()) {
        	Projectile bullet = iterator1.next();
            if (bullet.isDead) {
            	iterator1.remove();
            }
            bullet.update();
        }
        
        spawnNewEntities();
	}

	@Override
	public void draw(Graphics2D g2) {
		for (Entity e: new ArrayList<>(getEnities())) {
			if (gp.isInPlayerView(new Vector2(e.worldX, e.worldY))) e.draw(g2);
		}
		
		for (Projectile b: new ArrayList<>(getProjectiles())) {
			if (gp.isInPlayerView(new Vector2(b.worldX, b.worldY))) b.draw(g2);
		}
	}

}
