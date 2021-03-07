import java.util.*;

// quickSelect
public class Solution {

    public static void main(String[] args) {
        System.out.println(Class9StringII.decompress("a1c0b2c4"));
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

    private static void printDijGraphNode(DijGraphNode node) {
        System.out.print("Node " + node.key + ": shortest distance is " + node.distance + ", Path: ");
        while (node != null) {
            System.out.print(node.key + " <= ");
            node = node.prev;
        }
        System.out.println("null");
    }
}
