package entity;

import java.awt.Rectangle;

public class BaseEntity {

	public int worldX = 0;
	public int worldY = 0;
	private Rectangle solidArea;
	private Rectangle collisionArea;
	private Rectangle solidAreaRelativeToWorld = new Rectangle(0, 0, 48, 48);
	
	public void setSolidArea(Rectangle solidArea) {
		this.solidArea = collisionArea = solidArea;
	}
	
	public Rectangle getSolidArea() {
		return this.solidArea;
	}
	
	public Rectangle getSolidAreaRelativeToWorld() {
		this.solidAreaRelativeToWorld.x = this.worldX + this.solidArea.x;
		this.solidAreaRelativeToWorld.y = this.worldY + this.solidArea.y;
		this.solidAreaRelativeToWorld.width = solidArea.width;
		this.solidAreaRelativeToWorld.height = solidArea.height;
		
		return this.solidAreaRelativeToWorld;
	}

	public Rectangle getCollisionArea() {
		return collisionArea;
	}

	public void setCollisionArea(Rectangle collisionArea) {
		this.collisionArea = collisionArea;
	}

}
