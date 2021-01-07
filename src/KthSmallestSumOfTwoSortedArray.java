import java.util.Comparator;
import java.util.PriorityQueue;

public class KthSmallestSumOfTwoSortedArray {
    public int kthSmallest(int[] a, int[] b, int k) {
        // corner cases
        PriorityQueue<Cell> minHeap = new PriorityQueue<>(k, new Comparator<Cell>() {
            @Override
            public int compare(Cell c1, Cell c2) {
                if (c1.key == c2.key) {
                    return 0;
                }
                return c1.key < c2.key ? -1 : 1;
            }
        });
        boolean[][] visited = new boolean[a.length][b.length];
        minHeap.offer(new Cell(0, 0, a[0] + b[0]));
        visited[0][0] = true;
        for (int m = 0; m < k - 1; m++) {
            Cell cur = minHeap.poll();
            int row = cur.i;
            int col = cur.j;
            if (row + 1 < a.length && !visited[row + 1][col]) {
                minHeap.offer(new Cell(row + 1, col, a[row + 1] + b[col]));
                visited[row + 1][col] = true;
            }
            if (col + 1 < b.length && !visited[row][col + 1]) {
                minHeap.offer(new Cell(row, col + 1, a[row] + b[col + 1]));
                visited[row][col + 1] = true;
            }
        }
        return minHeap.poll().key;
    }

    class Cell {
        int i;
        int j;
        int key;
        public Cell(int i, int j, int key) {
            this.i = i;
            this.j = j;
            this.key = key;
        }
    }

}
