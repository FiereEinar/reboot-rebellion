package object;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import entity.Vector2;
import entity.EntityManager;
import gun.GUN_MachineGun;
import gun.GUN_Rifle;
import gun.GUN_Shotgun;
import gun.GUN_Sniper;
import gun.GunObject;
import main.GamePanel;
import main.Renderable;

public class ObjectManager implements Renderable {
	
	GamePanel gp;
	private ArrayList<LinkedList<GameObject>> objects = new ArrayList<>();
	private ArrayList<LinkedList<GunObject>> guns = new ArrayList<>();
	
	public ObjectManager(GamePanel gp) {
		this.gp = gp;
		initLists();
		loadObjects();
		loadGuns();
	}
	
	private void initLists() {
		for (int i = 0; i < gp.MAX_MAPS; i++) {
			objects.add(new LinkedList<GameObject>());
			guns.add(new LinkedList<GunObject>());
		}
	}
	
	public void restart() {
		for (LinkedList<GameObject> go: objects) {
			go.clear();
		}
		for (LinkedList<GunObject> g: guns) {
			g.clear();
		}
		loadObjects();
		loadGuns();
	}
	
	private void loadObjects() {
//		int map = 1;
//		objects.get(map).add(new OBJ_Heart(GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 3));
//		objects.get(map).add(new OBJ_Ammo(GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 6));
	}
	
	private void loadGuns() {
		guns.get(1).add(new GUN_Shotgun(912, 4416));
		guns.get(1).add(new GUN_Rifle(3360, 2112));
		guns.get(2).add(new GUN_MachineGun(14 * GamePanel.TILE_SIZE, 38 * GamePanel.TILE_SIZE));
		guns.get(2).add(new GUN_Sniper(73 * GamePanel.TILE_SIZE, 48 * GamePanel.TILE_SIZE));
	}
	
	public LinkedList<GameObject> getObjects() {
		return this.objects.get(gp.currentMap + 1);
	}
	
	public LinkedList<GunObject> getGuns() {
		return this.guns.get(gp.currentMap + 1);
	}
	
	public void removeObject(String name) {
		getObjects().removeIf(t -> t.name == name);
	}
	
	public void removeObject(GameObject object) {
		getObjects().removeIf(t -> t == object);
	}
	
	public void removeGun(String name) {
		getGuns().removeIf(t -> t.name == name);
	}
	
	public GameObject getObject(int index) {
		return getObjects().get(index);
	}
	
	public GunObject getGun(int index) {
		return getGuns().get(index);
	}
	
	public void addGun(GunObject gun) {
		getGuns().add(gun);
	}
	
	public void addObject(GameObject obj) {
		getObjects().add(obj);
	}
	
	public void addObject(int map, GameObject obj) {
		objects.get(map).add(obj);
	}
	
	@Override
	public void update() {
		// Get player position
	    Vector2 player = new Vector2(gp.player.worldX, gp.player.worldY);
	    
	    // Iterate and update objects
	    Iterator<GameObject> objectIterator = getObjects().iterator();
	    while (objectIterator.hasNext()) {
	        GameObject obj = objectIterator.next();
	        
	        // Mark objects as dead if far from the player
	        double distance = Math.sqrt(Math.pow(obj.worldX - player.x, 2) + Math.pow(obj.worldY - player.y, 2));
	        if (distance > EntityManager.DESPAWN_RANGE && obj.type != GameObject.OBJ_KEY) {
	            obj.isDead = true;
	        }
	        
	        // Remove dead objects
	        if (obj.isDead) {
	        	objectIterator.remove();
	        }
	    }
	}

	@Override
	public void draw(Graphics2D g2) {
		for (GameObject o: getObjects()) {
			Vector2 screen = new Vector2(
					o.worldX - gp.player.worldX + gp.player.screenX,
					o.worldY - gp.player.worldY + gp.player.screenY
			);
			if (gp.isInPlayerView(new Vector2(o.worldX, o.worldY))) g2.drawImage(o.sprite.getSprite(), screen.x, screen.y, null);
		}
		
		for (GunObject g: getGuns()) {
			Vector2 screen = new Vector2(
					g.worldX - gp.player.worldX + gp.player.screenX,
					g.worldY - gp.player.worldY + gp.player.screenY
			);
			if (gp.isInPlayerView(new Vector2(g.worldX, g.worldY))) g2.drawImage(g.sprite.getSprite(), screen.x, screen.y, null);
		}
	}

}
