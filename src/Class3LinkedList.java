public class Class3LinkedList {
    public ListNode reverse(ListNode head) {
        // base cases
        if (head.next == null) {
            return head;
        }
        // recursive rules
        ListNode newHead = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

//    C:	input => ListNode head
//    output => ListNode mid
//    A:	1 => 2 => 3, return 2;
//          1 => 2 => 3 => 4, return 2
//    R:
//      Highlevel: fast and slow pointers;
    public ListNode middleNode(ListNode head) {
        // corner cases
        if (head == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // Use fast and slow pointers to check if there are cycles.
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }


}
