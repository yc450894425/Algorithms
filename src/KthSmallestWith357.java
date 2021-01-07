import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class KthSmallestWith357 {
    public long kth(int k) {
        // corner cases
        PriorityQueue<Long> minHeap = new PriorityQueue<>();
        Set<Long> visited = new HashSet<>();
        minHeap.offer(3 * 5 * 7L);
        visited.add(3 * 5 * 7L);
        for (int i = 0; i < k - 1; i++) {
            Long curr = minHeap.poll();
            if (visited.add(curr * 3)) {
                minHeap.offer(curr * 3);
            }
            if (visited.add(curr * 5)) {
                minHeap.offer(curr * 5);
            }
            if (visited.add(curr * 7)) {
                minHeap.offer(curr * 7);
            }
        }
        return minHeap.poll();
    }
}
