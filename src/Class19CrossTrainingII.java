import java.util.*;

public class Class19CrossTrainingII {
    class RandomListNode {
        public int value;
        public RandomListNode next;
        public RandomListNode random;
        public RandomListNode(int value) {
            this.value = value;
        }
    }
    /*  Single pointer.
        Key point: store the relationship between old and new nodes in a map.
        For each step:
            if current node is not in the map, we create a copy of its and put (curr, copy) into map.
            Then we create/get the copies of its next and random, then connect copy with the nextCopy and randomCopy.
            node = node.next;
        Terminate:
            node == null
        return map.get(head)
        Time: O(n) in average, where n is length of the linked list.
              O(n^2) in worst case due to hash collision.
        Space: O(n) for map
    * */
    public RandomListNode copyI(RandomListNode head) {
        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode curr = head;
        while (curr != null) {
            if (!map.containsKey(curr)) {
                map.put(curr, new RandomListNode(curr.value));
            }
            RandomListNode copy = map.get(curr);
            if (curr.next != null) {
                if (!map.containsKey(curr.next)) {
                    map.put(curr.next, new RandomListNode(curr.next.value));
                }
                copy.next = map.get(curr.next);
            }
            if (curr.random != null) {
                if (!map.containsKey(curr.random)) {
                    map.put(curr.random, new RandomListNode(curr.random.value));
                }
                copy.random = map.get(curr.random);
            }
            curr = curr.next;
        }
        return map.get(head);
    }
    /*  Double pointers.
        head is faster than copy by 1 step.
                 1 => 2 => 3 => 4 => null
                      h
        dummy => 1 => 2
                 c   c.next
    * */
    public RandomListNode copyII(RandomListNode head) {
        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode dummy = new RandomListNode(0);
        RandomListNode copy = dummy;
        while (head != null) {
            if (!map.containsKey(head)) {
                map.put(head, new RandomListNode(head.value));
            }
            copy.next = map.get(head);
            if (head.random != null) {
                if (!map.containsKey(head.random)) {
                    map.put(head.random, new RandomListNode(head.random.value));
                }
                copy.next.random = map.get(head.random);
            }
            head = head.next;
            copy = copy.next;
        }
        return dummy.next;
    }
    /*  Three pass solution without using map.
        1. make a copy for cur node, and insert the copy to the middle of cur and cur.next;
            1 => 2 => 3 => 4 => null
            1 => 1' => 2 => 2' => 3 => 3' => 4 => 4' => null
        2. link the random pointer for the copied node.
            1(3) => 1'(3') => 2(4) => 2'(4') => 3(1) => 3'(1') => 4(2) => 4'(2') => null
        3. extract the copied nodes
            dummy => 1'(3') => 2'(4') => 3'(1') => 4'(2') => null
    * */
    public RandomListNode copyIII(RandomListNode head) {
        RandomListNode cur = head;
        while (cur != null) {
            RandomListNode copy = new RandomListNode(cur.value);
            copy.next = cur.next;
            cur.next = copy;
            cur = cur.next.next;
        }
        cur = head;
        while (cur != null) {
            if (cur.random != null) {
                cur.next.random = cur.random.next;
            }
            cur = cur.next.next;
        }
        RandomListNode dummy = new RandomListNode(0);
        cur = head;
        RandomListNode prev = dummy;
        while (cur != null) {
            prev.next = cur.next;
            cur.next = cur.next.next;
            prev = prev.next;
            cur = cur.next;
        }
        return dummy.next;
    }

    /*  Iterative solution.
        Assumption: undirected graph; there could be cycles.
        Key point: store the relationship between old and new nodes in a map.
        For each cur node in graph:
            if it's not in the map, put(cur, copy) into map.
            for each neighbor of its neighbors:
                if neighbor is not in the map, put(neighbor, neiCopy) into map.
                add neiCopy into copy's neighbors.
            add copy into List<GraphNode> copies.
    * */
    public List<GraphNode> copyGraphI(List<GraphNode> graph) {
        List<GraphNode> copies = new ArrayList<>();
        Map<GraphNode, GraphNode> map = new HashMap<>();
        for (GraphNode cur : graph) {
            if (!map.containsKey(cur)) {
                map.put(cur, new GraphNode(cur.key));
            }
            GraphNode copy = map.get(cur);
            for (GraphNode nei : cur.neighbors) {
                if (!map.containsKey(nei)) {
                    map.put(nei, new GraphNode(nei.key));
                }
                copy.neighbors.add(map.get(nei));
            }
            copies.add(copy);
        }
        return copies;
    }
    /*  DFS solution.
    * */
    public List<GraphNode> copyGraphII(List<GraphNode> graph) {
        Map<GraphNode, GraphNode> map = new HashMap<>();
        for (GraphNode node : graph) {
            if (!map.containsKey(node)) {
                map.put(node, new GraphNode(node.key));
                DFS(node, map);
            }
        }
        return new ArrayList<>(map.values());
    }
    // I define the semantic of the DFS function as copying all nodes (including all fields) connected with the seed (directly and indirectly) into map.
    private void DFS(GraphNode seed, Map<GraphNode, GraphNode> map) {
        GraphNode copy = map.get(seed);
        for (GraphNode nei : seed.neighbors) {
            if (!map.containsKey(nei)) {
                map.put(nei, new GraphNode(nei.key));
                DFS(nei, map);
            }
            copy.neighbors.add(map.get(nei));
        }
    }

    /*  Assumptions: Binary Search Tree; root not null; no duplicates.
        case 1. target == root.key, return root.key
        case 2. target > root.key, check if root is currently closest, then go to right subtree.
        case 3. target < root.key, check if root is currently closest, then go to left subtree.
    * */
    public int closestI(TreeNode root, int target) {
        int minDiff = Integer.MAX_VALUE;
        TreeNode closest = null;
        while (root != null) {
            if (Math.abs(root.key - target) < minDiff) {
                minDiff = Math.abs(root.key - target);
                closest = root;
            }
            if (root.key > target) {
                root = root.left;
            } else if (root.key < target){
                root = root.right;
            } else {
                return root.key;
            }
        }
        return closest.key;
    }
    // Better solution. No NPE risk.
    public int closestII(TreeNode root, int target) {
        int result = root.key;
        while (root != null) {
            if (Math.abs(root.key - target) < Math.abs(result - target)) {
                result = root.key;
            }
            if (root.key > target) {
                root = root.left;
            } else if (root.key < target){
                root = root.right;
            } else {
                return root.key;
            }
        }
        return result;
    }

    /*
        case 1. root.key == target, then result is in the left subtree.
        case 2. root.key < target, then result is root, or in its right subtree.
        case 3. root.key > target, then result is in the left subtree.
    * */
    public int largestSmaller(TreeNode root, int target) {
        int result = Integer.MIN_VALUE;
        while (root != null) {
            if (root.key >= target) {
                root = root.left;
            } else {
                result = root.key;
                root = root.right;
            }
        }
        return result;
    }

    /*  Use a set to store visited numbers.
        Set<Integer> visited
        for number in array:
            if target - number is visited
                return true
            visited.add(number)
    * */
    public boolean twoSumI(int[] array, int target) {
        Set<Integer> visited = new HashSet<>();
        for (int num : array) {
            if (visited.contains(target - num)) {
                return true;
            }
            visited.add(num);
        }
        return false;
    }
    /*  Sort + two pointers.
        Initialize: i = 0, j = len - 1;
        For each step:
            if array[i] + array[j] > target,
                j--;
            else if array[i] + array[j] < target,
                i++;
            else, return true
    * */
    public boolean twoSumII(int[] array, int target) {
        Arrays.sort(array);
        int i = 0;
        int j = array.length - 1;
        while (i < j) {
            if (array[i] + array[j] > target) {
                j--;
            } else if (array[i] + array[j] < target) {
                i++;
            } else {
                return true;
            }
        }
        return false;
    }
    /*  Sort + binary search
        for number in array:
            binary search(array, target - number)
            if found, return true.
    * */
    public boolean twoSumIII(int[] array, int target) {
        Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            int result = binarySearch(array, target - array[i]);
            if (result != -1 && result != i) {
                return true;
            }
        }
        return false;
    }
    private int binarySearch(int[] array, int target) {
        // corner cases
        if (array == null || array.length == 0) {
            return -1;
        }
        int l = 0;
        int r = array.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }
    /*  Data structure:
            map<number, list of indices where number occurs>
        For each num in array:
            if map.containsKey(target - num):
                for every index in indices list:
                    add(num, array[index]) into result;
            else:
                put/update num into map
    * */
    public List<List<Integer>> twoSumAllPairs(int[] array, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer, List<Integer>> occurs = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            List<Integer> indices = occurs.get(target - array[i]);
            if (indices != null) {
                for (int index : indices) {
                    result.add(Arrays.asList(i, index));
                }
            }
            if (!occurs.containsKey(array[i])) {
                occurs.put(array[i], new ArrayList<>());
            }
            occurs.get(array[i]).add(i);
        }
        return result;
    }
    /*  Order of the values in each pair doesn't matter.
        Sort + two pointers.
        How to deduplicate?
            ignore all consecutive duplicate values when we move i/j.
            here we choose i to deduplicate.
        Initialize: i = 0, j = len - 1;
        For each step:
            if array[i] is not the first element in the consecutive duplicate values:
                i++, continue;
            if array[i] + array[j] = target:
                add(i, j) into result;
            else if array[i] + array[j] < target:
                i++;
            else if array[i] + array[j] > target:
                j--;
        Terminate:  i == j
    * */
    public List<List<Integer>> twoSumAllDistinctPairsI(int[] array, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(array);
        int i = 0;
        int j = array.length - 1;
        while (i < j) {
            if (i > 0 && array[i - 1] == array[i]) {
                i++;
                continue;
            }
            if (array[i] + array[j] < target) {
                i++;
            } else if (array[i] + array[j] > target) {
                j--;
            } else {
                result.add(Arrays.asList(array[i++], array[j--]));
            }
        }
        return result;
    }
    /*  Order of the values in each pair doesn't matter.
        Key point:
            - How to deduplicate?
                - For each valid pair, we only add it into result ONCE.
            - How to guarantee adding each pair only once?
                - We only add it at the FIRST time we met it.
        Data structure:
            We use a map to store numbers and their occurrences.

        e.g. target = 4, map = {(3,1), (2,1), ...}
        For each num in array:
            case 1. map.containsKey(target - num) && !map.containsKey(num)          => num = 1
                add(target - num, num) into result
            case 2. num * 2 = target && map.containsKey(num) && map.get(num) == 1   => num = 2
                add(num, num)
            otherwise, just put/update num into map
    * */
    public List<List<Integer>> twoSumAllDistinctPairsII(int[] array, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer, Integer> counter = new HashMap<>();
        for (int num : array) {
            int count = counter.getOrDefault(num, 0);
            if (counter.containsKey(target - num) && count == 0) {
                result.add(Arrays.asList(num, target - num));
            } else if (num * 2 == target && count == 1) {
                result.add(Arrays.asList(num, num));
            }
            counter.put(num, count + 1);
        }
        return result;
    }

    /*  No duplicate triples should be returned; Order doesn't matter.
        Sort + three pointers.
        For each index of array from [0, len - 3]:
            if array[index] is not the first element of the consecutive duplicate values:
                index++, continue;
            int curr = array[index];
            Use two pointers.
            i = index + 1, j = len - 1;
            For each step:
                if array[i] is not the first element of the consecutive duplicate values in [index + 1, j - 1]:
                    i++, continue;
                if array[i] + array[j] < target - curr:
                    i++;
                else if array[i] + array[j] > target - curr:
                    j--;
                else:
                    add(index, i, j) into result.
            Terminate: i == j
        Terminate: index == len - 3
    * */
    public List<List<Integer>> threeSum(int[] array, int target) {
        Arrays.sort(array);
        List<List<Integer>> result = new ArrayList<>();
        for (int index = 0; index <= array.length - 3; index++) {
            if (index > 0 && array[index - 1] == array[index]) {
                continue;
            }
            int curr = array[index];
            int i = index + 1;
            int j = array.length - 1;
            while (i < j) {
                if (i > index + 1 && array[i - 1] == array[i]) {
                    i++;
                    continue;
                }
                if (array[i] + array[j] < target - curr) {
                    i++;
                } else if (array[i] + array[j] > target - curr) {
                    j--;
                } else {
                    result.add(Arrays.asList(curr, array[i++], array[j--]));
                }
            }
        }
        return result;
    }

    /*  Key point:
            4 sum = 2 sum + 2 sum
            i j k l
            How to guarantee that one element won't be used more than once?
                class Pair {
                    int x;
                    int y;
                }
                We maintain a map<2sum, list of all index pairs whose sum is the key>.
        For each iteration (i, j):
            sum = array[i] + array[j];
            // check
            if map.containsKey(target - sum):
                for each pair in the list:
                    if there is one pair neither of whose x/y == i/j:
                        return true;
            // add
            if !map.containsKey(sum), put (sum, new list) into map
            map.get(sum).add(new Pair(i, j))
    * */
    class Pair {
        int x;
        int y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public boolean fourSumI(int[] array, int target) {
        Map<Integer, List<Pair>> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                int sum = array[i] + array[j];
                List<Pair> pairs = map.get(target - sum);
                if (pairs != null) {
                    for (Pair pair : pairs) {
                        if (pair.x != i && pair.y != i && pair.y != j) { // pair.x must <= i, and i < j. So pair.x must < j, then pair.x must != j;
                            return true;
                        }
                    }
                }
                if (!map.containsKey(sum)) {
                    map.put(sum, new ArrayList<>());
                }
                map.get(sum).add(new Pair(i, j));
            }
        }
        return false;
    }
    /*  Key point:
            We don't need to store all pairs for each key in the map.
            Since 1. pair.x < pair.y, pair.x <= i, i < j, 2. we just need to prove existence, not all possible answers:
                we just need to store one pair which has the smallest y value.
            In other words, if we can find one pair that pair.x < pair.y < i < j, we can return true anyway.
            To make it simpler, since pair.x < pair.y and i < j are always true,
            we just need to check if pair.y < i.
    * */
    public boolean fourSumII(int[] array, int target) {
        Map<Integer, Pair> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                int sum = array[i] + array[j];
                Pair pair = map.get(target - sum);
                if (pair != null && pair.y < i) {
                    return true;
                }
                Pair currPair = map.get(sum);
                if (currPair == null || j < currPair.y) {
                    map.put(sum, new Pair(i, j));
                }
            }
        }
        return false;
    }


    /*  Key point: Merge Sort.
        When we merge two subarray together:
            xxxi..., yyyj...
            xxxyyy i/j
            if (array[i] <= array[j]), how many elements smaller than array[i] are at the right side of i?
                [0, j - 1], j elements.
        We need an array "indices" to store the index of each element after merge sort.
        Besides, we need an array "before" to record the index of each element before merge sort to help sort.
        Of course, we need an array "count" to store the final result.
    * */
    public int[] countArray(int[] array) {
        int[] indices = getIndex(array);
        int[] before = new int[array.length];
        int[] count = new int[array.length];
        mergeSort(array, 0, array.length - 1, indices, before, count);
        return count;
    }
    private void mergeSort(int[] array, int left, int right, int[] indices, int[] before, int[] count) {
        // base case
        if (left >= right) {
            return;
        }
        // recursive rule
        int mid = left + (right - left) / 2;
        mergeSort(array, left, mid, indices, before, count);
        mergeSort(array, mid + 1, right, indices, before, count);
        merge(array, left, mid, right, indices, before, count);
    }
    private void merge(int[] array, int left, int mid, int right, int[] indices, int[] before, int[] count) {
        // copy indices into array "before" before merging
        copy(indices, before, left, right);
        // i is the pointer in array "before"
        // left half: [left, mid]
        int i = left;
        // j is the pointer in array "before"
        // right half: [mid + 1, right]
        int j = mid + 1;
        // cur is the pointer in array "indices"
        int cur = left;
        while (i <= mid) {
            if (j > right || array[before[i]] <= array[before[j]]) {
                count[before[i]] += j - mid - 1;
                indices[cur++] = before[i++];
            } else {
                indices[cur++] = before[j++];
            }
        }
    }
    private void copy(int[] from, int[] to, int left, int right) {
        for (int i = left; i <= right; i++) {
            to[i] = from[i];
        }
    }
    private int[] getIndex(int[] array) {
        int[] indices = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            indices[i] = i;
        }
        return indices;
    }
}
