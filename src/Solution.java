import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// quickSelect
public class Solution {

    public static void main(String[] args) {
        Practice6QueueStackAndDeque.DequeByCircularArray queue = new Practice6QueueStackAndDeque.DequeByCircularArray(8);
        System.out.println(queue.peekFirst());
        queue.print();
        queue.offerFirst(4);
        queue.print();
        queue.offerFirst(3);
        queue.print();
        queue.offerFirst(2);
        queue.print();
        queue.offerFirst(1);
        queue.print();
        queue.offerLast(5);
        queue.print();
        queue.offerLast(6);
        queue.print();
        queue.offerLast(7);
        queue.print();
        queue.offerLast(8);
        queue.print();
        System.out.println(queue.offerFirst(9));
        System.out.println(queue.offerFirst(10));
        queue.pollLast();
        queue.print();
        queue.pollLast();
        queue.print();
        queue.pollLast();
        queue.print();
        queue.pollLast();
        queue.print();
        queue.pollLast();
        queue.print();
        queue.pollFirst();
        queue.print();
        queue.pollFirst();
        queue.print();
    }

    private static void printArray(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
//            System.out.print(i + 1);
//            System.out.print(": ");
            System.out.print(array[i]);
            System.out.print(',');
        }
        System.out.println(array[array.length - 1]);
    }

    private static void printMinHeap(MinHeap minHeap) {
        int size = minHeap.size();
        for (int i = 0; i < size; i++) {
            System.out.print(minHeap.poll());
            System.out.print(" ");
        }
    }

    private static void printLinkedList(ListNode head) {
        while (head != null) {
            System.out.print(head.value + " => ");
            head = head.next;
        }
        System.out.println("null");
    }

    private static void printMyLinkedList(Practice4LinkedList.MyLinkedList linkedList) {
        int size = linkedList.getSize();
        for (int i = 0; i < size; i++) {
            System.out.print(linkedList.get(i) + " => ");
        }
        System.out.println(linkedList.get(size));
    }
}
