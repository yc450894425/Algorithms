import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DijGraphNode {
    public int key;
    public Map<DijGraphNode, Integer> neighbors;
    public DijGraphNode prev;
    public Integer distance;
    public DijGraphNode(int key) {
        this.key = key;
        neighbors = new HashMap<>();
    }
}
