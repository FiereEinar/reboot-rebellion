package object;

import java.awt.Rectangle;

import entity.BaseEntity;
import entity.Player;
import sprite.Sprite;

public abstract class GameObject extends BaseEntity {
	
	public Sprite sprite = new Sprite();
	public String name;
	public Boolean isSolid = false;
	public Boolean isDead = false;
	public int value;
	
	public int type;
	public static final int OBJ_HEART = 1;
	public static final int OBJ_AMMO = 2;
	public static final int OBJ_KEY = 3;
	
	public GameObject() {
		this.setSolidArea(new Rectangle(0, 0, 48, 48));
	}
	
	public abstract Boolean useEffect(Player entity);

}
