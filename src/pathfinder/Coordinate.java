package pathfinder;

import java.util.ArrayList;

import entity.Vector2;

public class Coordinate {

	public Vector2 coord = new Vector2();
	public ArrayList<Vector2> previousPaths;

	public Coordinate(Vector2 coord) {
		this.coord.x = coord.x;
		this.coord.y = coord.y;
		this.previousPaths = new ArrayList<Vector2>();
	}

	public Coordinate(Vector2 coord, ArrayList<Vector2> previousPaths) {
		this.coord.x = coord.x;
		this.coord.y = coord.y;
		this.previousPaths = new ArrayList<>(previousPaths);
	}

}
