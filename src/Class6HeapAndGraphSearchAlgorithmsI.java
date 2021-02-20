import java.util.*;

public class Class6HeapAndGraphSearchAlgorithmsI {

    // Find teh smallest k elements in an unsorted array;
    // Assumptions: 1). array is not null 2). k >= 0 and k <= array.length;
    // Min Heap
    public static int[] kSmallestSolution1(int[] array, int k) {
        // corner cases
        if (array.length == 0 || k == 0) {
            return new int[0];
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        // put all elements into minHeap, O(nlogn)
        for (int n : array) {
            minHeap.offer(n);
        }
        // pop out k elements from minHeap, O(klogn)
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = minHeap.poll();
        }
        return result;
    }

    // Max Heap
    public static int[] kSmallestSolution2(int[] array, int k) {
        // corner cases
        if (array.length == 0 || k == 0) {
            return new int[0];
        }
        // Initialization
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1.equals(o2)) {
                    return 0;
                }
                return o1 > o2 ? -1 : 1;
            }
        });
        // Traverse all elements in array, O(nlogk)
        for (int i = 0; i < array.length; i++) {
            if (i < k) {
                maxHeap.offer(array[i]);
            } else if (array[i] < maxHeap.peek()) {
                // pop out before push in. It's ok in Java if pop out after push, but it's not good practice.
                maxHeap.poll();
                maxHeap.offer(array[i]);
            }
        }
        // Pop out k elements from minHeap, O(klogk)
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = maxHeap.poll();
        }
        return result;
    }

    // Quick Select
    public static int[] kSmallestSolution3(int[] array, int k) {
        // corner cases
        if (array.length == 0 || k == 0) {
            return new int[0];
        }
        quickSelect(array, 0, array.length - 1, k - 1);
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = array[i];
        }
        Arrays.sort(result);
        return result;
    }
    // The semantic of quickSelect is to find the (target + 1)th/index==target smallest element of the input array in area [left, right], and put the element to correct position where all elements at its right side >= it, and all elements at its left side < it.
    private static void quickSelect(int[] array, int left, int right, int target) {
        int mid = partition(array, left, right);
        // key point: left <= mid <= right, left <= target <= right;
        // is there any possibility that mid - 1 or mid + 1 is outofbound?
        // No. Because only if mid <= 0 or mid >= k - 1, they will be outofbound;
        // But since mid > target (target >= 0), mid cannot be 0 or negative;
        // Since mid < target (target <= k - 1), mid cannot be k - 1 or larger;
        // Thus, there is no possibility of outofbound.
        // Similarly, left cannot be smaller than right;
        // because mid - 1 cannot < left; mid + 1 cannot > right;
        if (mid > target) {
            // why “mid - 1” or "mid + 1"? Is it Ok if just “mid”?
            // NO!
            // 1. mid cannot be the target here;
            // 2. we must guarantee the search space decreases over time;
            // 	if use “mid” not “mid - 1” or "mid + 1", the search space might
            //	not decrease and dead loop occurs
            // 	when mid == right || mid == left;
            // 3. we must guarantee the target cannot be ruled out
            // accidentally;
            //	Since the mid cannot be the target, we can safely rule
            //	it out;
            quickSelect(array, left, mid - 1, target);
        } else if (mid < target) {
            quickSelect(array, mid + 1, right, target);
        }
    }
    // The semantic of partition is to pick up an element in array[left, right] randomly, put it to correct position, and return its index;
    private static int partition(int[] array, int left, int right) {
        int pivot = (int)(Math.random() * (right - left + 1)) + left;
        swap(array, pivot, right);
        // [left, i) => smaller
        // (j, right] => larger or equal
        // [i, j] => unexplored area
        int i = left;
        int j = right - 1;
        while (i <= j) {
            if (array[i] < array[right]) {
                i++;
            } else if (array[j] >= array[right]) {
                j--;
            } else {
                swap(array, i++, j--);
            }
        }
        swap(array, i, right);
        return i;
    }
    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static Set<GraphNode> DijkstraWithoutPathSolution1(GraphNode start) {
        Set<GraphNode> expanded = new HashSet<>();
        Set<GraphNode> generated = new HashSet<>();
        // corner cases
        if (start == null) {
            return expanded;
        }
        PriorityQueue<GraphNode> minHeap = new PriorityQueue<>(new Comparator<GraphNode>() {
            @Override
            public int compare(GraphNode o1, GraphNode o2) {
                if (o1.distance == o2.distance) {
                    return 0;
                }
                return o1.distance < o2.distance ? -1 : 1;
            }
        });
        minHeap.offer(start);
        generated.add(start);
        while (!minHeap.isEmpty()) {
            GraphNode cur = minHeap.poll();
            expanded.add(cur);
            for (GraphNode nei : cur.neighbors.keySet()) {
//              if nei has been expanded, do noting;
                if (expanded.contains(nei)) {
                    continue;
                } else if (!generated.contains(nei) || cur.distance + cur.neighbors.get(nei) < nei.distance) {
//              if nei has been generated and distance can be shortened, regenerate it;
//              if nei hasn't been generated, generate it;
                    nei.distance = cur.distance + cur.neighbors.get(nei);
                }
            }
        }
        return expanded;
    }

    public static Set<GraphNode> DijkstraWithoutPathSolution2(GraphNode start) {
        Set<GraphNode> expanded = new HashSet<>();
        // corner cases
        if (start == null) {
            return expanded;
        }
        PriorityQueue<GraphNode> minHeap = new PriorityQueue<>(new Comparator<GraphNode>() {
            @Override
            public int compare(GraphNode o1, GraphNode o2) {
                if (o1.distance == o2.distance) {
                    return 0;
                }
                return o1.distance < o2.distance ? -1 : 1;
            }
        });
    }

    public static Map<GraphNode, Integer> DijkstraWithPath(GraphNode start) {}
}
