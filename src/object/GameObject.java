package object;

import java.awt.Rectangle;

import entity.BaseEntity;
import sprite.Sprite;

public class GameObject extends BaseEntity {
	
	public Sprite sprite = new Sprite();
	public String name;
	public Boolean isSolid = false;
	
	public GameObject() {
		this.setSolidArea(new Rectangle(0, 0, 48, 48));
	}

}
