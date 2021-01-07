import java.util.*;

//430. CourseScheduleII
//        input -> 	int #ofCourses,
//        int[][] preReq, preReq[0] = [1, 0] means to take 1, take 0 first;
//
//        output -> 	int[] order

public class CourseScheduleII {
    public int[] findOrder(int numCourses, int[][] preReq) {
        // graph.get(0) = {1, 2} means 0 is a prerequisite of 1 and 2.
        List<List<Integer>> graph = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] pair : preReq) {
            int x = pair[0];
            int y = pair[1];
            graph.get(y).add(x);
        }
        return topologicalSort(numCourses, graph);
    }

    private int[] topologicalSort(int numCourses, List<List<Integer>> graph) {
        // corner cases
        if (numCourses == 0 || graph.size() == 0) {
            return new int[0];
        }

        int[] order = new int[numCourses];
        int[] incomingEdges = new int[numCourses];

        for (int x = 0; x < numCourses; x++) {
            for (int y : graph.get(x)) {
                incomingEdges[y]++;
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (incomingEdges[i] == 0) {
                queue.offer(i);
            }
        }

        int index = 0;
        while (!queue.isEmpty()) {
            Integer curr = queue.poll();
            order[index++] = curr;

            for (int next : graph.get(curr)) {
                if (--incomingEdges[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        return index == numCourses ? order : new int[0];
    }
}
