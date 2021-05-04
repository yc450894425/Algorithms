import ObjectOrientedDesign.ParkingLot.Car;
import ObjectOrientedDesign.ParkingLot.ParkingLot;
import ObjectOrientedDesign.ParkingLot.Truck;
import ObjectOrientedDesign.ParkingLot.Vehicle;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Class19CrossTrainingII solution = new Class19CrossTrainingII();
        Class19CrossTrainingII.RandomListNode node1 = solution.new RandomListNode(10);
        Class19CrossTrainingII.RandomListNode node2 = solution.new RandomListNode(20);
        Class19CrossTrainingII.RandomListNode node3 = solution.new RandomListNode(30);
        Class19CrossTrainingII.RandomListNode node4 = solution.new RandomListNode(40);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node1.random = node3;
        node2.random = node4;
        node3.random = node1;
        node4.random = node2;
        printIntArray(solution.countArray(new int[]{4,1,3,2}));
    }

    private static void printIntArray(int[] array) {
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

    private static void printMatrix(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j]);
                if (j != m[0].length - 1) {
                    System.out.print(" ");
                } else {
                    System.out.println();
                }
            }
        }
    }

    private static <E> void printGenericArray(E[] array) {
        for (E e : array) {
            System.out.print(e + " ");
        }
        System.out.println();
    }
}
