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

    public ListNode insertInSortedLinkedList(ListNode head, int value) {
        ListNode newNode = new ListNode(value);

        if (head == null || head.value >= value) {
            newNode.next = head;
            return newNode;
        }

        ListNode prev = head;
        while (prev.next != null && prev.next.value < value) {
            prev = prev.next;
        }

        newNode.next = prev.next;
        prev.next = newNode;
        return head;
    }

    public ListNode merge(ListNode one, ListNode two) {
        // corner cases
        if (one == null) {
            return two;
        } else if (two == null) {
            return one;
        }

        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;

        while (one != null && two != null) {
            if (one.value <= two.value) {
                cur.next = one;
                one = one.next;
            } else {
                cur.next = two;
                two = two.next;
            }
            cur = cur.next;
        }

        cur.next = one == null ? two : one;
        return dummy.next;
    }

    public ListNode reorder(ListNode head) {
        // corner cases
        if (head == null || head.next == null) {
            return head;
        }

        ListNode mid = middleNode(head);
        ListNode right = mid.next;
        mid.next = null;
        return merge(head, reverse(right));
    }

    public ListNode partition(ListNode head, int target) {
        // corner cases
        if (head == null || head.next == null) {
            return head;
        }

        ListNode largerDummy = new ListNode(0);
        ListNode larger = largerDummy;
        ListNode smallerDummy = new ListNode(0);
        ListNode smaller = smallerDummy;
        while (head != null) {
            if (head.value < target) {
                smaller.next = head;
                smaller = smaller.next;
            } else {
                larger.next = head;
                larger = larger.next;
            }
            head = head.next;
        }
        smaller.next = largerDummy.next;
        larger.next = null;
        return smallerDummy.next;
    }

    public ListNode mergeSort(ListNode head) {
        // base cases
        if (head == null || head.next == null) {
            return head;
        }

        // recursive rules
        ListNode mid = middleNode(head);
        ListNode right = mid.next;
        mid.next = null;
        return merge(mergeSort(head), mergeSort(right));
    }


}
