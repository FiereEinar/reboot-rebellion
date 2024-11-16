package main;

public abstract class KeyHandlerTemplate {

	public Boolean UP;
	public Boolean DOWN;
	public Boolean LEFT;
	public Boolean RIGHT;
	public Boolean SHOOTING;
	
	public abstract Boolean isMoving();
}
