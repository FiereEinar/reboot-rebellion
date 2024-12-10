package main;

import java.awt.Graphics2D;

public abstract interface Renderable {

	public abstract void update();
	
	public abstract void draw(Graphics2D g2);

}
