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
        // Traverse all elements of array, O(nlogk)
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

//    Data Structure: FIFO queue
//    Algorithm: Breadth First Search
//    Initialize: queue = {root}
//    For each step:
//    record the current size (k) of the queue.
//    Expand and generate k times.
//            Terminate: queue is empty.
//            Dedup: do not need deduplication.
    public static List<List<Integer>> layerByLayer(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> curLevel = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                curLevel.add(cur.key);
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
            result.add(curLevel);
        }
        return result;
    }

//    Data Structure: FIFO queue
//    Algorithm: Breadth First Search
//    Initialize: enqueue one node, color this node with 0.
//    For each step:
//    Expand: dequeue the first element (x) of the queue. (拿出)
//    Generate: enqueue all neighbors of x into the queue, and color them with another color. (放入)
//            case 1: 	the neighbor is not visited.
//                color it with another color and enqueue.
//		    case 2.1:	neighbor is visited, same color with x.
//                return false;
//            case 2.2: neighbor visited, different color with x.
//                do nothing;
//    At last, return true;
//    Terminate: queue is empty.
//    Dedup: We use a HashMap to deduplicate. The key of HashMap is node, the value is its color.
    public static boolean isBipartite(List<GraphNode> graph) {
        HashMap<GraphNode, Integer> visited = new HashMap<>();
        for (GraphNode node : graph) {
            if (!isBipartite(node, visited)) {
                return false;
            }
        }
        return true;
    }
    private static boolean isBipartite(GraphNode node, HashMap<GraphNode, Integer> visited) {
        // base cases
        if (visited.containsKey(node)) {
            return true;
        }
        Queue<GraphNode> queue = new ArrayDeque<>();
        visited.put(node, 0);
        queue.offer(node);

        while (!queue.isEmpty()) {
            GraphNode cur = queue.poll();
            int color = visited.get(cur);
            int neiColor = color == 0 ? 1 : 0;
            for (GraphNode neigh : cur.neighbors) {
                if (!visited.containsKey(neigh)) {
                    visited.put(neigh, neiColor);
                    queue.offer(neigh);
                } else if (visited.get(neigh) != neiColor) {
                    return false;
                }
            }
        }
        return true;
    }

//    Data Structure: FIFO queue
//    Algorithm: Breadth First Search
//    Initialize: queue = {root}
//    For each step:
//    Maintaining a boolean “flag” represents whether we met a “bubble” before.
//            Expand: dequeue the first element (x) from queue.
//            Generate: enqueue its children into queue if necessary.
//	            case 1.1: 	flag true, x is not null;
//			                return false;
//	            case 1.2:   flag true, x is null;
//			                do nothing;
//	            case 2.1:   flag false, x is not null;
//                            enqueue x’s children;
//	            case 2.2:   flag false, x is null;
//                            flag = true;
//    Terminate: queue is empty
    public static boolean isCompletedSolution1(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean flag = false;
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (flag && cur != null) {
                return false;
            } else if (cur == null) {
                flag = true;
            } else {
                queue.offer(cur.left);
                queue.offer(cur.right);
            }
        }
        return true;
    }
    // This solution doesn't push "null" into queue.
    public static boolean isCompletedSolution2(TreeNode root) {
        // corner cases
        if (root == null) {
            return true;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        boolean flag = false;
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur.left == null) {
                flag = true;
            } else if (flag) {
                return false;
            } else {
                queue.offer(cur.left);
            }

            if (cur.right == null) {
                flag = true;
            } else if (flag) {
                return false;
            } else {
                queue.offer(cur.right);
            }
        }
        return true;
    }

    public static Set<DijGraphNode> DijkstraWithoutPathSolution1(DijGraphNode start) {
        Set<DijGraphNode> expanded = new HashSet<>();
        Set<DijGraphNode> generated = new HashSet<>();
        // corner cases
        if (start == null) {
            return expanded;
        }
        PriorityQueue<DijGraphNode> minHeap = new PriorityQueue<>(new Comparator<DijGraphNode>() {
            @Override
            public int compare(DijGraphNode o1, DijGraphNode o2) {
                if (o1.distance.equals(o2.distance)) {
                    return 0;
                }
                return o1.distance < o2.distance ? -1 : 1;
            }
        });
        start.distance = 0;
        minHeap.offer(start);
        generated.add(start);
        while (!minHeap.isEmpty()) {
            DijGraphNode cur = minHeap.poll();
            expanded.add(cur);
            for (DijGraphNode nei : cur.neighbors.keySet()) {
                // case 1: nei has been expanded, do nothing;
                // case 2: nei hasn't been expanded or generated, it's distance = cur.distance + cur.neighbors.get(nei), enqueue it (generate);
                // case 3: nei has been generated and its distance <= cur.distance + cur.neighbors.get(nei), do nothing;
                // case 4: nei has been generated and its distance > cur.distance + cur.neighbors.get(nei), replace its distance (re-generate);
                Integer neiDis = cur.distance + cur.neighbors.get(nei);
                 if (!expanded.contains(nei) && (!generated.contains(nei) || nei.distance > neiDis)) {
                     nei.distance = neiDis;
                     if (!generated.contains(nei)) {
                         generated.add(nei);
                         minHeap.offer(nei);
                     }
                }
            }
        }
        return expanded;
    }

    public static Set<DijGraphNode> DijkstraWithoutPathSolution2(DijGraphNode start) {
        Set<DijGraphNode> expanded = new HashSet<>();
        // corner cases
        if (start == null) {
            return expanded;
        }
        PriorityQueue<DijGraphNode> minHeap = new PriorityQueue<>(new Comparator<DijGraphNode>() {
            @Override
            public int compare(DijGraphNode o1, DijGraphNode o2) {
                if (o1.distance.equals(o2.distance)) {
                    return 0;
                }
                return o1.distance < o2.distance ? -1 : 1;
            }
        });
        start.distance = 0;
        minHeap.offer(start);
        while (!minHeap.isEmpty()) {
            DijGraphNode cur = minHeap.poll();
            expanded.add(cur);
            for (DijGraphNode nei : cur.neighbors.keySet()) {
                // case 1: nei has been expanded, do nothing;
                // case 2: nei hasn't been expanded or generated, it's distance = cur.distance + cur.neighbors.get(nei), enqueue it (generate);
                // case 3: nei has been generated and its distance <= cur.distance + cur.neighbors.get(nei), do nothing;
                // case 4: nei has been generated and its distance > cur.distance + cur.neighbors.get(nei), replace its distance (re-generate);
                Integer neiDis = cur.distance + cur.neighbors.get(nei);
                if (!expanded.contains(nei) && (nei.distance == null || nei.distance > neiDis)) {
                    nei.distance = neiDis;
                    minHeap.offer(nei); // not necessary when nei.distance != null, not wrong although.
                }
            }
        }
        return expanded;
    }

    public static Set<DijGraphNode> DijkstraWithPath(DijGraphNode start) {
        Set<DijGraphNode> expanded = new HashSet<>();
        // corner cases
        if (start == null) {
            return expanded;
        }
        PriorityQueue<DijGraphNode> minHeap = new PriorityQueue<>(new Comparator<DijGraphNode>() {
            @Override
            public int compare(DijGraphNode o1, DijGraphNode o2) {
                if (o1.distance.equals(o2.distance)) {
                    return 0;
                }
                return o1.distance < o2.distance ? -1 : 1;
            }
        });
        start.distance = 0;
        minHeap.offer(start);
        while (!minHeap.isEmpty()) {
            DijGraphNode cur = minHeap.poll();
            expanded.add(cur);
            for (DijGraphNode nei : cur.neighbors.keySet()) {
                // case 1: nei has been expanded, do nothing;
                // case 2: nei hasn't been expanded or generated, it's distance = cur.distance + cur.neighbors.get(nei), enqueue it (generate);
                // case 3: nei has been generated and its distance <= cur.distance + cur.neighbors.get(nei), do nothing;
                // case 4: nei has been generated and its distance > cur.distance + cur.neighbors.get(nei), replace its distance (re-generate);
                Integer neiDis = cur.distance + cur.neighbors.get(nei);
                if (!expanded.contains(nei) && (nei.distance == null || nei.distance > neiDis)) {
                    nei.distance = neiDis;
                    nei.prev = cur;
                    minHeap.offer(nei); // not necessary when nei.distance != null, not wrong although.
                }
            }
        }
        return expanded;
    }
//    Data Structure:
//        PriorityQueue(heap)
//        boolean[][] visited, for deduplication.
//    Algorithm: Best First Search
//    Initialize: enqueue matrix[0][0].
//    For each step:
//        Expand: dequeue the top element (x) from heap
//        Generate: enqueue x’s right and bottom adjacent elements into heap if they are not visited.
//    Terminate: For loop (k-1) times, then the top element is the k-th smallest.
//    Deduplicate: Everytime we enqueue an element(matrix[i][j]), set visited[i][j] = true;
    public static int kthSmallest(int[][] matrix, int k) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        PriorityQueue<Cell> minHeap = new PriorityQueue<>(k, new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                if (o1.value == o2.value) {
                    return 0;
                }
                return o1.value < o2.value ? -1 : 1;
            }
        });
        boolean[][] visited = new boolean[rows][cols];
        // Initialize
        minHeap.offer(new Cell(0, 0, matrix[0][0]));
        visited[0][0] = true;
        for (int i = 0; i < k - 1; i++) {
            Cell cur = minHeap.poll();
            if (cur.row + 1 < rows && !visited[cur.row + 1][cur.col]) {
                minHeap.offer(new Cell(cur.row + 1, cur.col, matrix[cur.row + 1][cur.col]));
                visited[cur.row + 1][cur.col] = true;
            }
            if (cur.col + 1 < cols && !visited[cur.row][cur.col + 1]) {
                minHeap.offer(new Cell(cur.row, cur.col + 1, matrix[cur.row][cur.col + 1]));
                visited[cur.row][cur.col + 1] = true;
            }
        }
        return minHeap.poll().value;
    }
    private static class Cell {
        int row;
        int col;
        int value;
        public Cell(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }
    }

}
