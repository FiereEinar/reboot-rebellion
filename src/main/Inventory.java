package main;

import java.util.LinkedList;

import gun.GunObject;

public class Inventory {
	
	private LinkedList<GunObject> arsenal = new LinkedList<>();
	private int selectedGun = 0;
	
	public static final int GUN_SLOT_1 = 0;
	public static final int GUN_SLOT_2 = 1;
	
	public Inventory() {
	}
	
	public GunObject getSelectedGun() {
		if (selectedGun >= arsenal.size()) return null;
		return arsenal.get(selectedGun);
	}
	
	public int arsenalSize() {
		return arsenal.size();
	}
	
	public void setSelectedGun(int index) {
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
