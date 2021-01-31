import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// quickSelect
public class Solution {

    public static void main(String[] args) {

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

    private static void printLinkedList(ListNode node) {
        while (node.next != null) {
            System.out.print(node.value);
            System.out.print(", ");
            node = node.next;
        }
        System.out.println(node.value);
    }
}
