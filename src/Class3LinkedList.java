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

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // corner cases
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }

        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        int value = 0;
        while (l1 != null || l2 != null || value != 0) {
            if (l1 != null) {
                value += l1.value;
                l1 = l1.next;
            }
            if (l2 != null) {
                value += l2.value;
                l2 = l2.next;
            }
            cur.next = new ListNode(value % 10);
            cur = cur.next;
            value = value / 10;
        }
        return dummy.next;
    }

    public boolean isPalindrome(ListNode head) {
        // corner cases
        if (head == null || head.next == null) {
            return true;
        }

        ListNode mid = middleNode(head);
        ListNode right = reverse(mid.next);

        while (right != null) {
            if (head.value != right.value) {
                return false;
            }
            head = head.next;
            right = right.next;
        }
        return true;
    }

    public ListNode removeElements(ListNode head, int val) {
        // corner cases
        if (head == null) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;

        while (cur.next != null) {
            if (cur.next.value == val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return dummy.next;
    }

}
