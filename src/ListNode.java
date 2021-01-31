public class ListNode {
    int value;
    ListNode next;

    public ListNode(int value) {
        this.value = value;
        next = null;
    }

    public static int length(ListNode head) {
        int len = 0;
        while (head != null) {
            len++;
            head = head.next;
        }
        return len;
    }

    public static ListNode get(ListNode head, int index) {
        // corner cases
        if (index < 0) {
            return null;
        }
        // termination condition: index == 0 or head == null
        while (index > 0 && head != null) {
            head = head.next;
            index--;
        }
        return head;
    }

    public static ListNode appendHead(ListNode head, int value) {
        ListNode newHead = new ListNode(value);
        newHead.next = head;
        return newHead;
    }

    public static ListNode appendTail(ListNode head, int value) {
        // corner cases
        if (head == null) {
            return new ListNode(value);
        }

        // reach tail
        ListNode cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = new ListNode(value);
        return head;
    }

    // assuming no duplicates, return head.
    public static ListNode remove(ListNode head, int value) {
        if (head == null) {
            return null;
        }
        if (head.value == value) {
            return head.next;
        }
        ListNode prev = head;
        while (prev.next != null && prev.next.value != value) {
            prev = prev.next;
        }
        if (prev.next != null) {
            prev.next = prev.next.next;
        }
        return head;
    }

}
