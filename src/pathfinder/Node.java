package pathfinder;
import entity.Vector2;

public class Node {

	public Node parent;
    public Vector2 position;
    public int gCost;
    public int hCost;
    public int fCost;
    Boolean solid;
    Boolean open;
    Boolean checked;

    public Node(Vector2 position) {
        this.position = position;
    }
}
