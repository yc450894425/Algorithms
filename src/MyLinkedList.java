public class MyLinkedList<E> {

    private MyListNode<E> head;
    private MyListNode<E> tail;
    private int size;

    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {
        size = 0;
    }

    public int getSize() {
        return size;
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return null.
     */
    public E get(int index) {
        if (index < 0 || head == null || tail == null) {
            return null;
        }

        MyListNode<E> cur = head;
        while (index != 0 && cur != null) {/** Termination condition: index == 0 || cur == null */
            cur = cur.next;
            index--;
        }
        return cur == null ? null : cur.value;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(E val) {
        MyListNode<E> newHead = new MyListNode<>(val);
        newHead.next = head;
        head = newHead;
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(E val) {
        MyListNode<E> newTail = new MyListNode<>(val);
        if (tail == null) {
            head = newTail;
            tail = newTail;
        } else {
            tail.next = newTail;
            tail = tail.next;
        }
        size++;
    }

    /**
     * Add a node of value val BEFORE the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, E val) {
        if (index < 0 || index > size) {
            return;
        } else if (index == 0) {
            addAtHead(val);
        } else if (index == size) {
            addAtTail(val);
        } else {
            MyListNode prev = head;
            // termination condition: index == 1
            while (index > 1) {
                prev = prev.next;
                index--;
            }
            MyListNode<E> newNode = new MyListNode<>(val);
            newNode.next = prev.next;
            prev.next = newNode;
            size++;
        }
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public E deleteAtIndex(int index) {
        MyListNode<E> deleted = null;
        if (head == null || index < 0 || index >= size) {
            return null;
        }

        if (index == 0) {
            deleted = head;
            head = head.next;
            size--;
            if (size == 0) {
                tail = null;
            }
            return deleted.value;
        }

        MyListNode<E> prev = head;
        while (index > 1) {
            prev = prev.next;
            index--;
        }
        deleted = prev.next;
        prev.next = prev.next.next;
        size--;
        if (prev.next == null) {
            tail = prev;
        }
        return deleted.value;
    }

    private class MyListNode<E> {
        public E value;
        public MyListNode<E> next;

        public MyListNode(E value) {
            this.value = value;
        }
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */

