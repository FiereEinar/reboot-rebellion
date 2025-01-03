package main;

import java.util.LinkedList;

import gun.GunObject;

public class Inventory {
	
	private LinkedList<GunObject> arsenal = new LinkedList<>();
	private int keys = 0;
	private int selectedGun = 0;
	
	public static final int GUN_SLOT_1 = 0;
	public static final int GUN_SLOT_2 = 1;
	
	public Inventory() {
	}
	
	public int getKeys() {
		return keys;
	}

	public void setKeys(int keys) {
		this.keys = keys;
	}
	
	public GunObject getSelectedGun() {
		if (selectedGun >= arsenal.size()) return null;
		return arsenal.get(selectedGun);
	}
	
	public int arsenalSize() {
		return arsenal.size();
	}
	
	public void setSelectedGun(int index) {
		if (arsenal.size() == 1) arsenal.get(GUN_SLOT_1).reloading.reset();
		if (arsenal.size() == 2) arsenal.get(GUN_SLOT_2).reloading.reset();		
		this.selectedGun = index;
	}
	
	public int getSelectedGunIndex() {
		return selectedGun;
	}
	
	public GunObject getGunByIndex(int index) {
		if (index >= arsenal.size()) return null;
		return arsenal.get(index);
	}
	
	public LinkedList<GunObject> getArsenal() {
		return this.arsenal;
	}
	
	public Boolean isGunSelected(int gunIndex) {
		return selectedGun == gunIndex;
	}
	
	public void update() {
		for (GunObject g: arsenal) {
			g.update();
			if (g.reloading.isTriggered()) {
				g.handleReload();
			}
		}
	}

}
