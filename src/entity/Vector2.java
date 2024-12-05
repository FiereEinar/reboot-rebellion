package entity;

public class Vector2 {
	
	public int x;
	public int y;
	
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2() {
	}
	
	public Vector2 mul(int amount) {
		return new Vector2(x * amount, y * amount);
	}

}
