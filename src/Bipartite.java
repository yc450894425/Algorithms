import java.util.*;
//56. Bipartite
//        If it’s hard to prove the graph is bipartite, we can prove it’s not bipartite easily. We mark a node as group1, then mark all its neighbors as group2. And we use breadth first search to do this operation for all nodes. If we find a node whose neighbor(s) has the same mark with it, we can say this graph is not bipartite.

public class Bipartite {
    public boolean isBipartite(List<GraphNode> graph) {
        if (graph == null) {
            return false;
        }
        Map<GraphNode, Integer> visited = new HashMap<>();
        for (GraphNode node : graph) {
            if (!BFS(node, visited)) {
                return false;
            }
        }
        return true;
    }

    private boolean BFS(GraphNode node, Map<GraphNode, Integer> visited) {
        if (visited.containsKey(node)) {
            return true;
        }
        Queue<GraphNode> queue = new ArrayDeque<>();
        visited.put(node, 0);
        queue.offer(node);
        while (!queue.isEmpty()) {
            GraphNode curr = queue.poll();
            int currGroup = visited.get(curr);
            int neiGroup = currGroup == 1 ? 0 : 1;
            for (GraphNode nei : curr.neighbors) {
                if (visited.get(nei) == null) {
                    visited.put(nei, neiGroup);
                    queue.offer(nei);
                } if (visited.get(nei) == currGroup) {
                    return false;
                }
            }
        }
        return true;
    }
}
