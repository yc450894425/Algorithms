import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
//200 Max Water Trapped II
//        input -> int[][] matrix, not null, fit in memory
//        output -> int result
//        iterative way
//        Best First Search
//        min-heap to maintain all the border cells of the “closed area” and we always find the one cell with the lowest height, calculate the water in this cell, and put all its neighbors into min-heap.
//
//
//class Cell {
//    int x;
//    int y;
//    int height;
//    int water; // the water can be stored in this cell
//}
//
//    Data structure: min-heap
//        Initialize: put all border cells into minHeap
//        for each step:
//        find the lowest border cell
//        result += (its water - its height) > 0 ? (its water - its height):0;
//        get all its neighbors and update their water to Math.max(neigh.height, curr.water)
//        m * n
//        max size of minHeap is m*n.
//        tc: pop in and pop out every cell once, O(2 * m * n * log(nm)) -> O(mnlog(mn))
//        sc: minHeap, O(mn)

public class MaxTrappedII {
    public int maxTrapped(int[][] matrix) {
        int result = 0;
        // corner cases
        if (matrix.length < 3 || matrix[0].length < 3) {
            return result;
        }
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        PriorityQueue<Cell> minHeap = getBorder(matrix, visited);
        while (!minHeap.isEmpty()) {
            Cell curr = minHeap.poll();
            result += curr.water - curr.height;
            List<Cell> neighbors = getNeighbors(curr, matrix, visited);
            for (Cell nei : neighbors) {
                if (visited[nei.x][nei.y]) {
                    continue;
                }
                visited[nei.x][nei.y] = true;
                if (curr.water > nei.height) {
                    nei.water = curr.water;
                }
//                nei.water = Math.max(nei.height, curr.water);
                minHeap.offer(nei);
            }
        }
        return result;
    }

    private PriorityQueue<Cell> getBorder(int[][] matrix, boolean[][] visited) {
        PriorityQueue<Cell> minHeap = new PriorityQueue<>();
        for (int col = 0; col < matrix[0].length; col++) {
            visited[0][col] = true;
            minHeap.offer(new Cell(0, col, matrix[0][col]));
            visited[matrix.length - 1][col] = true;
            minHeap.offer(new Cell(matrix.length - 1, col, matrix[matrix.length - 1][col]));
        }

        for (int row = 1; row < matrix.length - 1; row++) {
            visited[row][0] = true;
            minHeap.offer(new Cell(row, 0, matrix[row][0]));
            visited[row][matrix[0].length - 1] = true;
            minHeap.offer(new Cell(row, matrix[0].length - 1, matrix[row][matrix[0].length - 1]));
        }
        return minHeap;
    }

    private List<Cell> getNeighbors(Cell curr, int[][] matrix, boolean[][] visited) {
        List<Cell> neighbors = new ArrayList<>();
        if (curr.x > 0) {
            neighbors.add(new Cell(curr.x - 1, curr.y, matrix[curr.x - 1][curr.y]));
//            visited[curr.x - 1][curr.y] = true;
        }
        if (curr.x < matrix.length - 1) {
            neighbors.add(new Cell(curr.x + 1, curr.y, matrix[curr.x + 1][curr.y]));
//            visited[curr.x + 1][curr.y] = true;
        }
        if (curr.y > 0) {
            neighbors.add(new Cell(curr.x, curr.y - 1, matrix[curr.x][curr.y - 1]));
//            visited[curr.x][curr.y - 1] = true;
        }
        if (curr.y < matrix[0].length - 1) {
            neighbors.add(new Cell(curr.x, curr.y + 1, matrix[curr.x][curr.y + 1]));
//            visited[curr.x][curr.y + 1] = true;
        }
        return neighbors;
    }

    static class Cell implements Comparable<Cell>{
        int x;
        int y;
        int height;
        int water; // the water can be stored in this cell
        public Cell(int x, int y, int height) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.water = height;
        }

        @Override
        public int compareTo(Cell another) {
            if (this.water == another.water) {
                return 0;
            }
            return this.water < another.water ? -1 : 1;
        }
    }

}


