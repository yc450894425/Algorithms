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

        private int head; // next of head points to the head element of the queue;
        private int tail; // tail points to the next available position;
        private Integer[] array;

        public QueueByCircularArray(int cap) {
            if (cap <= 0) {
                try {
                    throw new BadAttributeValueExpException("Capibility should be larger than Zero.");
                } catch (BadAttributeValueExpException e) {
                    e.printStackTrace();
                }
            }
            array = new Integer[cap + 1];
            head = 0;
            tail = 1;
        }

        // user cannot push ‘null’ into the queue
        public boolean offer(int ele) {
            if (isFull()) {
                return false;
            }
            array[tail] = ele;
            tail = tail + 1 == array.length ? 0 : tail + 1;
            return true;
        }

        public Integer poll() {
            if (isEmpty()) {
                return null;
            }
            Integer result = array[head + 1];
            head = (head + 1) % array.length;
            return result;
        }

        public Integer peek() {
            if (isEmpty()) {
                return null;
            }
            return array[head + 1];
        }

        public int size() {
            if (tail > head) {
                return tail - head - 1;
            } else {
                return array.length - (head - tail + 1);
            }
        }

        public boolean isEmpty() {
            return (head + 1) % array.length == tail;
        }
        private boolean isFull() {
            return head == tail;
        }
    }

    // Implementing stack by circular array
    public static class StackByCircularArray {

        private int head; // head points to the newest element;
        Integer[] array;

        public StackByCircularArray(int cap) {
            if (cap <= 0) {
                try {
                    throw new BadAttributeValueExpException("Capibility should be larger than Zero.");
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

}
