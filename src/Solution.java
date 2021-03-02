import java.util.*;

// quickSelect
public class Solution {

    public static void main(String[] args) {
        System.out.println(Class8HashTableAndStringI.commonSolution3(new int[]{1, 6, 7, 9, 13}, new int[]{1,2,3,4,5,6,8,10,11,12,13,14,15}));
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

//    transfer input string to char array, and string t to a set.
//    two pointers:
//        slow: left (excluding itself) are processed letters that should be kept in result string;
//        fast: left (excluding itself) are processed;
//    Initialize:
//        slow = 0;
//        fast = 0;
//    For each step:
//        array[fast] is in set, fast++;
//        array[fast] is not in set, array[slow++] = array[fast++];
//    terminate: fast == input’s length
//    post-processing: convert char array[0, slow - 1] into string.
//    time:
//        O(n) in a, where n is input length;
//        O(n*m) in w, m is length of t;
//    space:
//        O(m), m is length of t;
    public static String remove(String input, String t) {
        // corner cases
        if (input.length() == 0 || t.length() == 0) {
            return input;
        }
        Set<Character> set = toSet(t);
        char[] array = input.toCharArray();
        int slow = 0;
        for (int fast = 0; fast < array.length; fast++) {
            if (!set.contains(array[fast])) {
                array[slow++] = array[fast];
            }
        }
        return new String(array, 0, slow);
    }
    private static Set<Character> toSet(String input) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < input.length(); i++) {
            set.add(input.charAt(i));
        }
        return set;
    }

}
