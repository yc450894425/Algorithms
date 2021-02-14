import javax.management.BadAttributeValueExpException;

public class Practice6QueueStackAndDeque {

    private static final int DEFAULT_CAPACITY = 8;
    private static final double EXPAND_COEFFICIENT = 1.5;

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

    // Implementing stack by circular array
    public static class StackByCircularArray {

        private int head; // head points to the newest element;
        private Integer[] array;

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

    // Implementing Deque by circular array
    public static class DequeByCircularArray {
        // Area (head, tail) stores elements;
        private int head; // head points to the next available position at the head side;
        private int tail; // tail points to the next available position at the tail side;
        private int size;
        private Integer[] array;

        public DequeByCircularArray(int cap) {
            array = new Integer[cap];
            size = 0;
            head = 0;
            tail = 1;
        }

        public DequeByCircularArray() {
            this(DEFAULT_CAPACITY);
        }

        public boolean offerFirst(int ele) {
            if (isFull()) {
                resize();
            }
            array[head] = ele;
            head = head == 0 ? array.length - 1 : head - 1;
            size++;
            return true;
        }

        public Integer pollFirst() {
            if (isEmpty()) {
                return null;
            }
            head = (head + 1) % array.length;
            size--;
            return array[head];
        }

        public Integer peekFirst() {
            if (isEmpty()) {
                return null;
            }
            return array[(head + 1) % array.length];
        }

        public boolean offerLast(int ele) {
            if (isFull()) {
                resize();
            }
            array[tail] = ele;
            tail = (tail + 1) % array.length;
            size++;
            return true;
        }

        public Integer pollLast() {
            if (isEmpty()) {
                return null;
            }
            tail = tail == 0 ? array.length - 1 : tail - 1;
            size--;
            return array[tail];
        }

        public Integer peekLast() {
            if (isEmpty()) {
                return null;
            }
            return array[tail == 0 ? array.length - 1 : tail - 1];
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

        public void print() {
            int index = (head + 1) % array.length;
            System.out.print("head");
            for (int i = 0; i < size; i++) {
                System.out.print(" " + array[index]);
                index = (index + 1) % array.length;
            }
            System.out.println(" tail");
        }

        private void resize() {
            Integer[] oldArray = array;
            array = new Integer[(int) (oldArray.length * EXPAND_COEFFICIENT)];
            int oldindex = (head + 1) % oldArray.length;
            for (int i = 1; i <= size; i++) {
                array[i] = oldArray[oldindex];
                oldindex = (oldindex + 1) % oldArray.length;
            }
            head = 0;
            tail = (size + 1) % array.length;
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

        /**
         * Initialize your data structure here. Set the size of the queue to be k.
         */
        public MyCircularQueue(int k) {
            array = new Integer[k];
            head = 0;
            tail = 0;
            size = 0;
        }

        /**
         * Insert an element into the circular queue. Return true if the operation is successful.
         */
        public boolean enQueue(int value) {
            if (isFull()) {
                return false;
            }
            array[tail] = value;
            tail = tail + 1 == array.length ? 0 : tail + 1;
            size++;
            return true;
        }

        /**
         * Delete an element from the circular queue. Return true if the operation is successful.
         */
        public boolean deQueue() {
            if (isEmpty()) {
                return false;
            }
            Integer result = array[head];
            head = (head + 1) % array.length;
            size--;
            return true;
        }

        /**
         * Get the front item from the queue.
         */
        public int Front() {
            if (isEmpty()) {
                return -1;
            }
            return array[head];
        }

        /**
         * Get the last item from the queue.
         */
        public int Rear() {
            if (isEmpty()) {
                return -1;
            }
            return tail == 0 ? array[array.length - 1] : array[tail - 1];
        }

        /**
         * Checks whether the circular queue is empty or not.
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * Checks whether the circular queue is full or not.
         */
        public boolean isFull() {
            return size == array.length;
        }
    }

    // 613. Design Circular Deque
    // https://app.laicode.io/app/problem/613
    public static class MyCircularDeque {

        /**
         * Your MyCircularDeque object will be instantiated and called as such:
         * MyCircularDeque obj = new MyCircularDeque(k);
         * boolean param_1 = obj.insertFront(value);
         * boolean param_2 = obj.insertLast(value);
         * boolean param_3 = obj.deleteFront();
         * boolean param_4 = obj.deleteLast();
         * int param_5 = obj.getFront();
         * int param_6 = obj.getRear();
         * boolean param_7 = obj.isEmpty();
         * boolean param_8 = obj.isFull();
         */

        // (head, tail) is elements storage area;
        private int head; // head points to the next available position at the front end;
        private int tail; // tail points to the next available position at the last end;
        private int size;
        private Integer[] array;

        /**
         * Initialize your data structure here. Set the size of the deque to be k.
         */
        public MyCircularDeque(int k) {
            array = new Integer[k];
            head = 0;
            tail = 1;
            size = 0;
        }

        /**
         * Adds an item at the front of Deque. Return true if the operation is successful.
         */
        public boolean insertFront(int value) {
            if (isFull()) {
                return false;
            }
            array[head] = value;
            head = head == 0 ? array.length - 1 : head - 1;
            size++;
            return true;
        }

        /**
         * Adds an item at the rear of Deque. Return true if the operation is successful.
         */
        public boolean insertLast(int value) {
            if (isFull()) {
                return false;
            }
            array[tail] = value;
            tail = (tail + 1) % array.length;
            size++;
            return true;
        }

        /**
         * Deletes an item from the front of Deque. Return true if the operation is successful.
         */
        public boolean deleteFront() {
            if (isEmpty()) {
                return false;
            }
            head = (head + 1) % array.length;
            size--;
            return true;
        }

        /**
         * Deletes an item from the rear of Deque. Return true if the operation is successful.
         */
        public boolean deleteLast() {
            if (isEmpty()) {
                return false;
            }
            tail = tail == 0 ? array.length - 1 : tail - 1;
            size--;
            return true;
        }

        /**
         * Get the front item from the deque.
         */
        public int getFront() {
            if (isEmpty()) {
                return -1;
            }
            return array[(head + 1) % array.length];
        }

        /**
         * Get the last item from the deque.
         */
        public int getRear() {
            if (isEmpty()) {
                return -1;
            }
            return array[tail == 0 ? array.length - 1 : tail - 1];
        }

        /**
         * Checks whether the circular deque is empty or not.
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * Checks whether the circular deque is full or not.
         */
        public boolean isFull() {
            return size == array.length;
        }
    }
}
