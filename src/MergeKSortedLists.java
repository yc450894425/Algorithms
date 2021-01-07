import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MergeKSortedLists {
    public ListNode merge(List<ListNode> list) {
        // corner cases
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(list.size(), new Comparator<ListNode>() {
            @Override
            public int compare(ListNode n1, ListNode n2) {
                if (n1.value == n2.value) {
                    return 0;
                }
                return n1.value < n2.value ? -1 : 1;
            }
        });
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int i = 0; i < list.size(); i++) {
            minHeap.offer(list.get(i));
        }
        while (!minHeap.isEmpty()) {
            curr.next = minHeap.poll();
            curr = curr.next;
            if (curr.next != null) {
                minHeap.offer(curr.next);
            }
        }
        return dummy.next;
    }
}
