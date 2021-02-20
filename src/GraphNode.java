import java.util.ArrayList;
import java.util.List;

public class GraphNode {
    int key;
    List<GraphNode> neighbors;
    public GraphNode(int key) {
        this.key = key;
        neighbors = new ArrayList<>();
    }
}
