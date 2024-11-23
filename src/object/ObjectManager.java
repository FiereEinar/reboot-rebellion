package object;

import java.awt.Graphics2D;
import java.util.ArrayList;

import main.GamePanel;
import main.Renderable;

public class ObjectManager implements Renderable {
	
	GamePanel gp;
	private ArrayList<GameObject> objects = new ArrayList<>();
	public final int GUN_INDEX = 0;
	
	public ObjectManager(GamePanel gp) {
		this.gp = gp;
		loadObjects();
	}
	
	private void loadObjects() {
		objects.add(new OBJ_Gun(gp.tileSize * 3, gp.tileSize * 3));
	}
	
	public ArrayList<GameObject> getObjects() {
		return this.objects;
	}
	
	public void removeObject(String name) {
		objects.removeIf(t -> t.name == name);
	}
	
	public GameObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics2D g2) {
		for (GameObject o: objects) {
			int screenX = o.worldX - gp.player.worldX + gp.player.screenX;
			int screenY = o.worldY - gp.player.worldY + gp.player.screenY;
			g2.drawImage(o.sprite.getSprite(), screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}

}
