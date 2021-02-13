import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// quickSelect
public class Solution {

    public static void main(String[] args) {
        Practice6QueueStackAndDeque.StackByCircularArray queue = new Practice6QueueStackAndDeque.StackByCircularArray(5);
        System.out.println(queue.peek());
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        System.out.println("size: " + queue.size());
        System.out.println(queue.offer(5));
        System.out.println(queue.offer(6));
        System.out.println(queue.poll());

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
