import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// quickSelect
public class Solution {

    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>(5);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        printMyArrayList(list);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        printMyArrayList(list);
        list.remove();
        list.remove();
        list.remove();
        list.remove();
        list.remove();
        printMyArrayList(list);
        list.remove(2);
        printMyArrayList(list);
        list.add(2, 3);
        printMyArrayList(list);
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

    private static void printMyLinkedList(MyLinkedList linkedList) {
        int size = linkedList.getSize();
        for (int i = 0; i < size; i++) {
            System.out.print(linkedList.get(i) + " => ");
        }
        System.out.println(linkedList.get(size));
    }

    private static void printMyArrayList(MyArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            System.out.print(arrayList.get(i) + " ");
        }
        System.out.println();
    }
}
