import ObjectOrientedDesign.ParkingLot.Car;
import ObjectOrientedDesign.ParkingLot.ParkingLot;
import ObjectOrientedDesign.ParkingLot.Truck;
import ObjectOrientedDesign.ParkingLot.Vehicle;

import java.util.*;

// quickSelect
public class Solution {

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(4, 10, 0.8f);
        List<Vehicle> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            final Vehicle v = i < 15 ? new Truck() : new Car();
            boolean hasSpot = lot.hasSpot(v);
            System.out.println("i = " + i + ", size = " + v.getSize() + ", hasSpot = " + hasSpot);
            System.out.println("Parking: " + (lot.park(v) ? "success" : "fail"));
            list.add(v);
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(lot.leave(list.get(i)));
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
