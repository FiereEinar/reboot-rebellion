package inventory;

import java.util.ArrayList;

import object.GameObject;

public class Inventory {
	
	private ArrayList<GameObject> items = new ArrayList<>();

	public Inventory() {
	}
	
	public ArrayList<GameObject> getItems() {
		return this.items;
	}
	
	public void addInventoryItem(GameObject object) {
		items.add(object);
	}

}
