package main;

import java.util.LinkedList;

import gun.GunObject;

public class Inventory {
	
	public LinkedList<GunObject> arsenal = new LinkedList<>();
	public int selectedGun = 0;
	
	public Inventory() {
	}
	
	public GunObject getSelectedGun() {
		return arsenal.get(selectedGun);
	}

}
