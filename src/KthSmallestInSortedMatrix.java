import java.util.Comparator;
import java.util.PriorityQueue;

public class KthSmallestInSortedMatrix {
    public int kthSmallest(int[][] matrix, int k) {
        // corner cases
        PriorityQueue<Cell> minHeap = new PriorityQueue<>(k, new Comparator<Cell>() {
            @Override
            public int compare(Cell c1, Cell c2) {
                if (c1.value == c2.value) {
                    return 0;
                }
                return c1.value < c2.value ? -1 : 1;
            }
        });
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        visited[0][0] = true;
        minHeap.offer(new Cell(0, 0, matrix[0][0]));
        for (int i = 0; i < k - 1; i++) {
            Cell curr = minHeap.poll();
            if (curr.x + 1 < matrix.length && !visited[curr.x + 1][curr.y]) {
                visited[curr.x + 1][curr.y] = true;
                minHeap.offer(new Cell(curr.x + 1, curr.y, matrix[curr.x + 1][curr.y]));
            }
            if (curr.y + 1 < matrix[0].length && !visited[curr.x][curr.y + 1]) {
                visited[curr.x][curr.y + 1] = true;
                minHeap.offer(new Cell(curr.x, curr.y + 1, matrix[curr.x][curr.y + 1]));
            }
        }
        return minHeap.poll().value;
    }

    class Cell {
        int x;
        int y;
        int value;
        public Cell(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }
}
