import java.util.*;

public class DeepCopyUndirectedGraph {
    public List<GraphNode> BFS(List<GraphNode> graph) {
        List<GraphNode> result = new ArrayList<>();
        Queue<GraphNode> queue = new ArrayDeque<>();
        Map<GraphNode, GraphNode> copied = new HashMap<>();

        // copy nodes
        for (GraphNode node : graph) {
            copied.put(node, new GraphNode(node.key));
            queue.offer(node);
            result.add(copied.get(node));
        }

// copy neighbors(edges)
        while (!queue.isEmpty()) {
            GraphNode old = queue.poll(); // expand
            for (GraphNode nei : old.neighbors) { // generate
                if (copied.get(nei) == null) {
                    copied.put(nei, new GraphNode(nei.key));
                    queue.offer(nei);
                }
                copied.get(old).neighbors.add(copied.get(nei));
            }
        }
        return result;
    }

    public List<GraphNode> DFS(List<GraphNode> graph) {
        List<GraphNode> result = new ArrayList<>();
        Map<GraphNode, GraphNode> map = new HashMap<>();
        for (GraphNode node : graph) {
            result.add(copyDFS(node, map));
        }
        return result;
    }

    // the semantic of copyDFS is to copy the seed node, and all its neighbors, then return the copy.
    private GraphNode copyDFS(GraphNode seed, Map<GraphNode, GraphNode> map) {
        if (map.containsKey(seed)) {
            return map.get(seed);
        }
        GraphNode copy = new GraphNode(seed.key);
        map.put(seed, copy);
        for (GraphNode nei : seed.neighbors) {
            copy.neighbors.add(copyDFS(nei, map));
        }
        return copy;
    }
}
