class MyLinkedList {
    ListNode head;
    ListNode tail;

    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (index < 0) {
            return -1;
        }
        ListNode cur = head;
        while (index > 0 && cur != null) {
            cur = cur.next;
            index--;
        }
        return cur == null ? -1 : cur.val;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        ListNode newHead = new ListNode(val);
        newHead.next = head;
        if (head == null) {
            tail = newHead;
        }
        head = newHead;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        if (head == null) {
            head = new ListNode(val);
            tail = head;
        } else {
            tail.next = new ListNode(val);
            tail = tail.next;
        }
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (index < 0) {
            return;
        }
        ListNode cur = head;
        while (index > 1 && cur != null) {
            cur = cur.next;
            index--;
        }
        if (cur != null) {
            ListNode newNode = new ListNode(val);
            newNode.next = cur.next;
            cur.next = newNode;
        }
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (index < 0) {
            return;
        }
        ListNode cur = head;
        while (index > 1 && cur != null) {
            cur = cur.next;
            index--;
        }
        if (cur != null && cur.next != null) {
            cur.next = cur.next.next;
        }
    }

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
