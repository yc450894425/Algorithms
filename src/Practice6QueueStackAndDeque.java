import javax.management.BadAttributeValueExpException;

public class Practice6QueueStackAndDeque {
    // Implementing stack by linked list
    public static class StackByLinkedList {

        private ListNode head;
        private int size;

        public StackByLinkedList() {
            size = 0;
        }

        public void offer(int element) {
            ListNode node = new ListNode(element);
            node.next = head;
            head = node;
            size++;
        }
        public Integer peek() {
            return head == null ? null : head.value;
        }
        public Integer poll() {
            if (head == null) {
                return null;
            }
            ListNode prev = head;
            head = head.next;
            prev.next = null; // best practice
            size--;
            return prev.value;
        }
        public int size() {
            return size;
        }
        public boolean isEmpty() {
            return size == 0;
        }
    }

    // Implementing queue by linked list
    public static class QueueByLinkedList {

        // offer at tail, poll at head.
        private ListNode head;
        private ListNode tail;
        private int size;

        public void offer(int element) {
            ListNode node = new ListNode(element);
            if (tail == null) {
                tail = node;
                head = tail;
            } else {
                tail.next = node;
                tail = tail.next;
            }
            size++;
        }

        public Integer peek() {
            return head == null ? null : head.value;
        }
        public Integer poll() {
            if (head == null) {
                return null;
            }
            ListNode prev = head;
            head = head.next;
            if (head == null) { // you need to test!!!
                tail = null;
            }
            return prev.value;
        }
        public int size() {
            return size;
        }
        public boolean isEmpty() {
            return size == 0;
        }
    }

    // Implementing queue by circular array
    public static class QueueByCircularArray {

        // Elements are stored in the area [head, tail).
        private int head; // head points to the head element of the queue;
        private int tail; // tail points to the next available position;
        private Integer[] array;
        private int size;

        public QueueByCircularArray(int cap) {
            if (cap <= 0) {
                try {
                    throw new BadAttributeValueExpException("Capability should be larger than Zero.");
                } catch (BadAttributeValueExpException e) {
                    e.printStackTrace();
                }
            }
            array = new Integer[cap];
            head = 0;
            tail = 0;
            size = 0;
        }

        // user cannot push ‘null’ into the queue
        public boolean offer(int ele) {
            if (isFull()) {
                return false;
            }
            array[tail] = ele;
            tail = tail + 1 == array.length ? 0 : tail + 1;
            size++;
            return true;
        }

        public Integer poll() {
            if (isEmpty()) {
                return null;
            }
            Integer result = array[head];
            head = (head + 1) % array.length;
            size--;
            return result;
        }

        public Integer peek() {
            if (isEmpty()) {
                return null;
            }
            return array[head];
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }
        private boolean isFull() {
            return size == array.length;
        }
    }

    // Implementing stack by circular array
    public static class StackByCircularArray {

        private int head; // head points to the newest element;
        Integer[] array;

        public StackByCircularArray(int cap) {
            if (cap <= 0) {
                try {
                    throw new BadAttributeValueExpException("Capability should be larger than Zero.");
                } catch (BadAttributeValueExpException e) {
                    e.printStackTrace();
                }
            }
            array = new Integer[cap];
            head = -1;
        }

        public boolean offer(int ele) {
            if (head == array.length - 1) {
                return false;
            }
            array[++head] = ele;
            return true;
        }

        public Integer poll() {
            return head == -1 ? null : array[head--];
        }
        public Integer peek() {
            return head == -1 ? null : array[head];
        }
        public int size() {
            return head + 1;
        }
        public boolean isEmpty() {
            return head == -1;
        }
    }

    // 614. Design Circular Queue
    // https://app.laicode.io/app/problem/614
    public static class MyCircularQueue {

        /**
         * Your MyCircularQueue object will be instantiated and called as such:
         * MyCircularQueue obj = new MyCircularQueue(k);
         * boolean param_1 = obj.enQueue(value);
         * boolean param_2 = obj.deQueue();
         * int param_3 = obj.Front();
         * int param_4 = obj.Rear();
         * boolean param_5 = obj.isEmpty();
         * boolean param_6 = obj.isFull();
         */

        // Elements are stored in the area [head, tail).
        private int head; // head points to the head element of the queue;
        private int tail; // tail points to the next available position;
        private Integer[] array;
        private int size;

        /** Initialize your data structure here. Set the size of the queue to be k. */
        public MyCircularQueue(int k) {
            array = new Integer[k];
            head = 0;
            tail = 0;
            size = 0;
        }

        /** Insert an element into the circular queue. Return true if the operation is successful. */
        public boolean enQueue(int value) {
            if (isFull()) {
                return false;
            }
            array[tail] = value;
            tail = tail + 1 == array.length ? 0 : tail + 1;
            size++;
            return true;
        }

        /** Delete an element from the circular queue. Return true if the operation is successful. */
        public boolean deQueue() {
            if (isEmpty()) {
                return false;
            }
            Integer result = array[head];
            head = (head + 1) % array.length;
            size--;
            return true;
        }

        /** Get the front item from the queue. */
        public int Front() {
            if (isEmpty()) {
                return -1;
            }
            return array[head];
        }

        /** Get the last item from the queue. */
        public int Rear() {
            if (isEmpty()) {
                return -1;
            }
            return tail == 0 ? array[array.length - 1] : array[tail - 1];
        }

        /** Checks whether the circular queue is empty or not. */
        public boolean isEmpty() {
            return size == 0;
        }

        /** Checks whether the circular queue is full or not. */
        public boolean isFull() {
            return size == array.length;
        }
    }

    // Implementing Deque by linked list
    public static class DequeByLinkedList {

        private static class DoublyNode {
            public int value;
            public DoublyNode next;
            public DoublyNode prev;

            public DoublyNode(int value) {
                this.value = value;
            }
        }

        // Assuming head is first and tail is last.
        private DoublyNode head;
        private DoublyNode tail;
        private int size;

        public DequeByLinkedList() {
            size = 0;
        }

        public void offerFirst(int ele) {
            DoublyNode node = new DoublyNode(ele);
            if (isEmpty()) {
                head = node;
                tail = node;
            } else {
                node.next = head;
                head.prev = node;
                head = head.prev;
            }
            size++;
        }

        public Integer pollFirst() {
            if (isEmpty()) {
                return null;
            }
            DoublyNode result = head;
            head = head.next;
            if (head != null) {
                head.prev = null;
                result.next = null;
            } else {
                tail = head;
            }
            size--;
            return result.value;
        }

        public Integer peekFirst() {
            if (isEmpty()) {
                return null;
            }
            return head.value;
        }

        public void offerLast(int ele) {
            DoublyNode node = new DoublyNode(ele);
            if (isEmpty()) {
                head = node;
                tail = head;
            } else {
                tail.next = node;
                node.prev = tail;
                tail = tail.next;
            }
            size++;
        }

        public Integer pollLast() {
            if (isEmpty()) {
                return null;
            }
            DoublyNode result = tail;
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
                result.prev = null;
            } else {
                head = tail;
            }
            size--;
            return result.value;
        }

        public Integer peekLast() {
            if (isEmpty()) {
                return null;
            }
            return tail.value;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void print() {
            DoublyNode cur = head;
            System.out.print("First");
            while (cur != null) {
                System.out.print(" " + cur.value);
                cur = cur.next;
            }
            System.out.println(" Last");
        }
    }

}
