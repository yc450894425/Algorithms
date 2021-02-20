import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphNode {
    public int key;
    public Map<GraphNode, Integer> neighbors;
    public GraphNode prev;
    public int distance;
    public GraphNode(int key) {
        this.key = key;
        this.neighbors = new HashMap<>();
    }
}
