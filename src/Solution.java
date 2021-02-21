import java.util.*;

// quickSelect
public class Solution {

    public static void main(String[] args) {
        DijGraphNode n1 = new DijGraphNode(1);
        DijGraphNode n2 = new DijGraphNode(2);
        DijGraphNode n3 = new DijGraphNode(3);
        DijGraphNode n4 = new DijGraphNode(4);
        DijGraphNode n5 = new DijGraphNode(5);
        DijGraphNode n6 = new DijGraphNode(6);
        n1.neighbors.put(n2, 3);
        n1.neighbors.put(n5, 1);
        n2.neighbors.put(n1, 3);
        n2.neighbors.put(n5, 4);
        n3.neighbors.put(n2, 1);
        n3.neighbors.put(n4, 1);
        n4.neighbors.put(n3, 1);
        n4.neighbors.put(n5, 10);
        n4.neighbors.put(n6, 2);
        n5.neighbors.put(n1, 1);
        n5.neighbors.put(n2, 4);
        n5.neighbors.put(n4, 10);
        n6.neighbors.put(n4, 2);
        Set<DijGraphNode> set = Class6HeapAndGraphSearchAlgorithmsI.DijkstraWithPath(n4);
        for (DijGraphNode node : set) {
            printDijGraphNode(node);
        }
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
