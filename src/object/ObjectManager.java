package object;

import java.awt.Graphics2D;
import java.util.LinkedList;

import gun.GUN_Pistol_1;
import gun.GUN_Shotgun;
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
	}
	
	private void loadGuns() {
		guns.add(new GUN_Pistol_1(GamePanel.tileSize * 3, GamePanel.tileSize * 3));
		guns.add(new GUN_Shotgun(GamePanel.tileSize * 3, GamePanel.tileSize * 9));
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

	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics2D g2) {
		for (GameObject o: objects) {
			int screenX = o.worldX - gp.player.worldX + gp.player.screenX;
			int screenY = o.worldY - gp.player.worldY + gp.player.screenY;
			g2.drawImage(o.sprite.getSprite(), screenX, screenY, null);
		}
		
		for (GunObject g: guns) {
			int screenX = g.worldX - gp.player.worldX + gp.player.screenX;
			int screenY = g.worldY - gp.player.worldY + gp.player.screenY;
			g2.drawImage(g.sprite.getSprite(), screenX, screenY, null);
		}
	}

}
