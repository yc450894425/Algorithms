import java.util.Comparator;
import java.util.PriorityQueue;
//133 Merge K Sorted Array
//        input -> int[][] array, r rows, c cols
//        output -> int[] array
//        iterative
//        rows pointers and a minHeap with r size
public class MergeKSortedArray {
    public int[] merge(int[][] matrix) {
        // corner cases

        PriorityQueue<Entry> minHeap = new PriorityQueue<>(matrix.length, new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2) {
                if (e1.value == e2.value) {
                    return 0;
                }
                return e1.value < e2.value ? -1 : 1;
            }
        });
        int length = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != 0) {
                length += matrix[i].length;
                minHeap.offer(new Entry(i, 0, matrix[i][0]));
            }
        }

        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            Entry curr = minHeap.poll();
            result[i] = curr.value;
            if (curr.col + 1 < matrix[curr.row].length) {
                curr.col += 1;
                curr.value = matrix[curr.row][curr.col];
                minHeap.offer(curr);
            }
        }
        return result;
    }
    static class Entry {
        int row;
        int col;
        int value;
        public Entry(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }
    }

}


