import java.util.*;
//195. Place to put the chair I
//        input -> char[][] gym
//        output -> List<Integer> coordinate
//
//        Graph Search
//        Dijkstra algorithm
//
//        Run Dijkstra from every E node, and store results (cost) in matrices.
//        For each cell i,j in the gym, calculate the sum of cost, and update globalMin if necessary.

public class PutChair {

    private final static int[][] DIR = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    public List<Integer> putChair(char[][] gym) {
        // corner cases
        if (gym == null || gym.length == 0 || gym[0].length == 0) {
            return new ArrayList<>();
        }

        int[][] cost = new int[gym.length][gym[0].length];
        for (int i = 0; i < gym.length; i++) {
            for (int j = 0; j < gym[0].length; j++) {
                if (isEquip(gym, i, j)) {
                    dijkstra(gym, cost, i, j);
                }
            }
        }

        int globMin = Integer.MAX_VALUE;
        List<Integer> result = new ArrayList<>();
        result.add(-1);
        result.add(-1);
        for (int i = 0; i < gym.length; i++) {
            for (int j = 0; j < gym[0].length; j++) {
                if (!isEquip(gym, i, j) && !isObstacle(gym, i, j) && cost[i][j] < globMin) {
                    globMin = cost[i][j];
                    result.set(0, i);
                    result.set(1, j);
                }
            }
        }
        return result;
    }

    private void dijkstra(char[][] gym, int[][] cost, int i, int j) {
        boolean[][] visited = new boolean[cost.length][cost[0].length];
        Queue<Cell> queue = new ArrayDeque<>();
        queue.offer(new Cell(i, j, 0));
        visited[i][j] = true;

        while (!queue.isEmpty()) {
            Cell curr = queue.poll();
            cost[curr.i][curr.j] += curr.cost;

            for (int[] dir : DIR) {
                int nextI = curr.i + dir[0];
                int nextJ = curr.j + dir[1];
                if (!outOfBound(gym, nextI, nextJ) && !isObstacle(gym, nextI, nextJ) && !visited[nextI][nextJ]) {
                    queue.offer(new Cell(nextI, nextJ, curr.cost + 1));
                    visited[nextI][nextJ] = true;
                }
            }
        }
    }

    private boolean isEquip(char[][] m, int i, int j) {
        return m[i][j] == 'E';
    }

    private boolean outOfBound(char[][] m, int i, int j) {
        return i < 0 || i >= m.length || j < 0 || j >= m[0].length;
    }

    private boolean isObstacle(char[][] gym, int i, int j) {
        return gym[i][j] == 'O';
    }

    static class Cell {
        int i;
        int j;
        int cost;

        public Cell(int i, int j, int cost) {
            this.i = i;
            this.j = j;
            this.cost = cost;
        }
    }
}
