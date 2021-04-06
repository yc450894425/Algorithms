import ObjectOrientedDesign.ParkingLot.Car;
import ObjectOrientedDesign.ParkingLot.ParkingLot;
import ObjectOrientedDesign.ParkingLot.Truck;
import ObjectOrientedDesign.ParkingLot.Vehicle;

import java.util.*;

// quickSelect
public class Solution {
    private final static Practice14HashMapImplementation.CustomHashMap<Integer, String> map1 = new Practice14HashMapImplementation.CustomHashMap<>();
    private final static Practice23ConcurrencyII.SynchronizedHashMap<Integer, String> map2 = new Practice23ConcurrencyII.SynchronizedHashMap<>();
    private final static Integer testKey = 0b1111111111111111;
    private final static String testValue = "testValue";

    public static void main(String[] args) throws InterruptedException {
        map2.put(testKey, testValue);
        MyThread myThread = new MyThread();
        myThread.start();
        while (true) {
            if (!testValue.equals(map2.get(testKey))) {
                throw new RuntimeException("This HashMap is not thread safe.");
            }
        }
    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 0b1111111111111111; i++) {
                map2.put(i, "some value");
            }
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
