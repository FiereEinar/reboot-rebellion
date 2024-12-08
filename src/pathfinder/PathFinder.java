package pathfinder;

import java.util.ArrayList;

import entity.Vector2;
import main.GamePanel;

public class PathFinder {
	
	GamePanel gp;
	Node[][] node;
	ArrayList<Node> openList = new ArrayList<Node>();
	public ArrayList<Node> pathList = new ArrayList<Node>();
	Node startNode, goalNode, currentNode;
	Boolean goalReached = false;
	int step = 0;
	
	public PathFinder(GamePanel gp) {
		this.gp = gp;
		instantiateNode();
	}
	
	private void instantiateNode() {
		int worldCol = gp.worldCol;
		int worldRow = gp.worldRow;

		node = new Node[worldCol][worldRow];
		
		int row = 0;
		int col = 0;
		
		while (col < worldCol && row < worldRow) {
			node[col][row] = new Node(new Vector2(col, row));
			
			col++;
			if (col == worldCol) {
				col = 0;
				row++;
			}
		}
	}
	
	private void resetNode() {
		int row = 0;
		int col = 0;
		
		while (col < gp.worldCol && row < gp.worldRow) {
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			
			col++;
			if (col == gp.worldCol) {
				col = 0;
				row++;
			}
		}
		
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}
	
	public void setNodes(Vector2 start, Vector2 goal) {
		resetNode();
		
		int worldCol = gp.worldCol;
		int worldRow = gp.worldRow;
		int row = 0;
		int col = 0;
		
		if (start.x > gp.worldCol - 1) start.x = gp.worldCol - 1;
		if (start.y > gp.worldRow - 1) start.y = gp.worldRow - 1;
		if (goal.x > gp.worldCol - 1) goal.x = gp.worldCol - 1;
		if (goal.y > gp.worldRow - 1) goal.y = gp.worldRow - 1;

		startNode = node[start.x][start.y];
		currentNode = startNode;
		goalNode = node[goal.x][goal.y];
		
		while (col < worldCol && row < worldRow) {
			int tileNum = gp.tm.getMapTileNumber(col, row);
			if (gp.tm.isTileSolid(tileNum)) {
				node[col][row].solid = true;
			}

			getCost(node[col][row]);
			
			col++;
			if (col == gp.worldCol) {
				col = 0;
				row++;
			}
		}
	}
	
	private void getCost(Node node) {
		Vector2 direction = new Vector2(
			Math.abs(node.position.x - startNode.position.x),
			Math.abs(node.position.y - startNode.position.y)
		);
		node.gCost = direction.x + direction.y;
		
		direction.x = Math.abs(node.position.x - goalNode.position.x);
		direction.y = Math.abs(node.position.y - goalNode.position.y);
		node.hCost = direction.x + direction.y;
		
		node.fCost = node.gCost + node.hCost;
	}
	
	public Boolean search() {
//	    System.out.println("SEARCHING PATH....");
	    
	    while (!goalReached && step < 500) {
//	        if (openList.isEmpty()) {
//	            System.out.println("OPEN LIST EMPTY, NO PATH FOUND.");
//	            break;
//	        }

//	        System.out.println("SEARCHING...");
	        Vector2 current = currentNode.position;

	        currentNode.checked = true;
	        openList.remove(currentNode);

	        // Open neighboring nodes
	        if (current.y - 1 >= 0) openNode(node[current.x][current.y - 1]); // Up
	        if (current.x - 1 >= 0) openNode(node[current.x - 1][current.y]); // Left
	        if (current.y + 1 < gp.worldRow) openNode(node[current.x][current.y + 1]); // Down
	        if (current.x + 1 < gp.worldCol) openNode(node[current.x + 1][current.y]); // Right

	        // Find the best node to continue
	        int bestNodeIndex = 0;
	        int bestNodefCost = Integer.MAX_VALUE;

	        for (int i = 0; i < openList.size(); i++) {
	            Node currNode = openList.get(i);

	            // Choose node with lowest fCost; if tied, choose lower gCost
	            if (currNode.fCost < bestNodefCost || 
	               (currNode.fCost == bestNodefCost && currNode.gCost < openList.get(bestNodeIndex).gCost)) {
	                bestNodeIndex = i;
	                bestNodefCost = currNode.fCost;
	            }
	        }

	        // Update current node only if openList is not empty
	        if (!openList.isEmpty()) {
	            currentNode = openList.get(bestNodeIndex);

	            if (currentNode == goalNode) {
	                goalReached = true;
	                trackThePath();
	            }
	        } else {
//	            System.out.println("NO MORE NODES TO EXPLORE, EXITING.");
	            break;
	        }

	        step++;
	    }

//	    System.out.println("EXITED SEARCH LOOP WITH RESULT: " + goalReached + " STEPS: " + step);

	    return goalReached;
	}

	
//	public Boolean search() {
//		System.out.println("SEARCHING PATH....");
//		while (!goalReached && step < 500) {
//			System.out.println("SEARCHING...");
//			Vector2 current = currentNode.position;
//			
//			currentNode.checked = true;
//			openList.remove(currentNode);
//			
//			if (current.y - 1 >= 0) openNode(node[current.x][current.y - 1]);
//			if (current.x - 1 >= 0) openNode(node[current.x - 1][current.y]);
//			if (current.y + 1 < gp.worldRow) openNode(node[current.x][current.y + 1]);
//			if (current.x + 1 < gp.worldCol) openNode(node[current.x + 1][current.y]);
//			
//			int bestNodeIndex = 0;
//			int bestNodefCost = 999;
//			
//			for (int i = 0; i < openList.size(); i++) {
//				Node currNode = openList.get(i);
//				
//				if (currNode.fCost < bestNodefCost) {
//					bestNodeIndex = i;
//					bestNodefCost = currNode.fCost;
//				} else if (currNode.fCost == bestNodefCost) {
//					if (currNode.gCost < openList.get(bestNodeIndex).gCost) {
//						bestNodeIndex = i;
//					}
//				}
//				
//				if (openList.size() == 0) break;
//				
//				currentNode = openList.get(bestNodeIndex);
//				
//				if (currentNode == goalNode) {
//					goalReached = true;
//					trackThePath();
//				}
//				
//				step++;
//			}
//
//		}
//		
//		System.out.println("EXITED SEARCH LOOP WITH RESULT: " + goalReached + " STEPS: " + step);
//		
//		return goalReached;
//	}
	
	private void openNode(Node node) {
		if (!node.open && !node.checked && !node.solid) {
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}
	
	private void trackThePath() {
		Node current = goalNode;
		
		while(current != startNode) {
			pathList.add(0, current);
			current = current.parent; 
		}
	}
	
}