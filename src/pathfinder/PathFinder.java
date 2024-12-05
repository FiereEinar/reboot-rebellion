package pathfinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import entity.Vector2;
import main.GamePanel;

public class PathFinder {
	
	private GamePanel gp;

	public PathFinder(GamePanel gp) {
		this.gp = gp;
	}
	
	private ArrayList<Vector2> getSorroundingTiles(Vector2 coord) {
		ArrayList<Vector2> sorroundingTiles = new ArrayList<>();
		
		int[][] map = gp.tm.getMap();
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 || j == 0) {
					Vector2 path = new Vector2(coord.x + i, coord.y + j);
					
					Boolean isOutOfBounds = path.x < 0 || path.x >= map[0].length || path.y < 0 || path.y >= map.length;
					Boolean isPathSolid = gp.tm.isTileSolid(gp.tm.getMapTileNumber(path.x, path.y));
					
					if (!isOutOfBounds && !isPathSolid) {
						sorroundingTiles.add(path);
					}
				}
			}
		}
		
		return sorroundingTiles;
	}
	
	public ArrayList<Vector2> pathFind(Vector2 origin, Vector2 target) {
		HashSet<String> takenPaths = new HashSet<>();
		Queue<Coordinate> queue = new LinkedList<>();
		queue.add(new Coordinate(origin));
		
		while (!queue.isEmpty()) {
			Coordinate current = queue.poll();
			
			if (current.coord.x == target.x && current.coord.y == target.y) return current.previousPaths;
			
			takenPaths.add(current.coord.x + "-" + current.coord.y);
			
			for (Vector2 coord: getSorroundingTiles(current.coord)) {
				if (!takenPaths.contains(coord.x + "-" + coord.y)) {
					Coordinate toAdd = new Coordinate(coord, current.previousPaths);
					toAdd.previousPaths.add(current.coord);
					queue.add(toAdd);
				}
			}
		}
		
		return new ArrayList<>();
	}

}
