package object;

import java.awt.Graphics2D;
import java.util.LinkedList;

import entity.Vector2;
import gun.GUN_MachineGun;
import gun.GUN_Pistol_1;
import gun.GUN_Shotgun;
import gun.GUN_Sniper;
import gun.GunObject;
import main.GamePanel;
import main.Renderable;

public class ObjectManager implements Renderable {
	
	GamePanel gp;
	private LinkedList<GameObject> objects = new LinkedList<>();
	private LinkedList<GunObject> guns = new LinkedList<>();
	public final int GUN_INDEX = 0;
	
	public ObjectManager(GamePanel gp) {
		this.gp = gp;
		loadObjects();
		loadGuns();
	}
	
	private void loadObjects() {
		objects.add(new OBJ_Heart(GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 3));
		objects.add(new OBJ_Ammo(GamePanel.TILE_SIZE * 9, GamePanel.TILE_SIZE * 6));
	}
	
	private void loadGuns() {
		guns.add(new GUN_Pistol_1(GamePanel.TILE_SIZE * 3, GamePanel.TILE_SIZE * 3));
		guns.add(new GUN_Shotgun(GamePanel.TILE_SIZE * 3, GamePanel.TILE_SIZE * 6));
		guns.add(new GUN_MachineGun(GamePanel.TILE_SIZE * 6, GamePanel.TILE_SIZE * 3));
		guns.add(new GUN_Sniper(GamePanel.TILE_SIZE * 6, GamePanel.TILE_SIZE * 6));
	}
	
	public LinkedList<GameObject> getObjects() {
		return this.objects;
	}
	
	public LinkedList<GunObject> getGuns() {
		return this.guns;
	}
	
	public void removeObject(String name) {
		objects.removeIf(t -> t.name == name);
	}
	
	public void removeGun(String name) {
		guns.removeIf(t -> t.name == name);
	}
	
	public GameObject getObject(int index) {
		return objects.get(index);
	}
	
	public GunObject getGun(int index) {
		return guns.get(index);
	}
	
	public void addGun(GunObject gun) {
		guns.add(gun);
	}
	
	public void addObject(GameObject obj) {
		objects.add(obj);
	}

	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics2D g2) {
		for (GameObject o: objects) {
			Vector2 screen = new Vector2(
					o.worldX - gp.player.worldX + gp.player.screenX,
					o.worldY - gp.player.worldY + gp.player.screenY
			);
			if (gp.isInPlayerView(new Vector2(o.worldX, o.worldY))) g2.drawImage(o.sprite.getSprite(), screen.x, screen.y, null);
		}
		
		for (GunObject g: guns) {
			Vector2 screen = new Vector2(
					g.worldX - gp.player.worldX + gp.player.screenX,
					g.worldY - gp.player.worldY + gp.player.screenY
			);
			if (gp.isInPlayerView(new Vector2(g.worldX, g.worldY))) g2.drawImage(g.sprite.getSprite(), screen.x, screen.y, null);
		}
	}

}
