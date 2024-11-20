package ui;

import entity.Vector2;

public class ScreenText {
	
	public String text;
	public Vector2 pos = new Vector2();
	
	public ScreenText(String text) {
		this.text = text;
	}
	
	public ScreenText(String text, int x, int y) {
		this.text = text;
		this.pos.x = x;
		this.pos.y = y;
	}
	
	public void setPos(int x, int y) {
		this.pos.x = x;
		this.pos.y = y;
	}

}
