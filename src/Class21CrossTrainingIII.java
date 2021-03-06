import java.util.*;

public class Class21CrossTrainingIII {
    // Assumptions: no duplicate
    /*  sort + two pointers
    * */
//    public List<Integer> commonI(int[] a, int[] b) {}
    /*  set + sort + traversal
    * */
//    public List<Integer> commonI(int[] a, int[] b) {}

    // Assumptions: duplicate numbers
    /*  sort + two pointers
        if a[i] == b[j], i++, j++
    * */
//    public List<Integer> commonWithDupI(int[] a, int[] b) {}

    /*  two TreeMaps
    * */
    public List<Integer> commonWithDupII(int[] a, int[] b) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> mapA = new TreeMap<>();
        for (int n : a) {
            mapA.put(n, mapA.getOrDefault(n, 0) + 1);
        }
        Map<Integer, Integer> mapB = new TreeMap<>();
        for (int n : b) {
            mapB.put(n, mapB.getOrDefault(n, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : mapA.entrySet()) {
            int num = entry.getKey();
            int freqA = entry.getValue();
            Integer freqB = mapB.get(num);
            if (freqB != null) {
                for (int i = 0; i < Math.min(freqA, freqB); i++) {
                    result.add(num);
                }
            }
        }
        return result;
    }

    /*
        Data structure:
            stack, which stores indices whose heights will be used to calculate area.
            The heights of these indices are ascending.
            Every time before we push a new index into the stack, we pop out all indices whose height is > height of new index.
        How to define a rectangle[i]:
            e.g. array[i] = 2
            left border = i;
            right border = i + 1;
            height = array[i] = 2;
            area = height * (right - left);
        For each index i:
            for every possible rectangle whose right border is i (whose height is > array[i]):
                pop out those indices whose height > array[i]. (because their possible farthest right border is i)
                calculate their areas and update globMax if necessary.
            put i into stack
        Terminate:
            i == length + 1, NOT length, because we define the right border is i + 1 for rectangle[i].
        Time:
            O(n), because every index will be inserted into and popped out the stack only once.
        Space:
            O(n) for stack
    * */
    public int largestRectangle(int[] array) {
        Deque<Integer> stack = new ArrayDeque<>();
        int globMax = 0;
        for (int i = 0; i <= array.length; i++) {
            int currHeight = i == array.length ? 0 : array[i];
            while (!stack.isEmpty() && array[stack.peekFirst()] > currHeight) {
                int height = array[stack.pollFirst()];
                int left = stack.isEmpty() ? 0 : stack.peekFirst() + 1;
                int right = i;
                globMax = Math.max(globMax, height * (right -  left));
            }
            stack.offerFirst(i);
        }
        return globMax;
    }
    /*  Brute force
        For each index i:
            => find its right border
            <= find its left border
            calculate its area, and update globMax if necessary
        Time:
            O(n^2)
    * */
    public int largestRectangleII(int[] array) {
        int globMax = 0;
        for (int i = 0; i < array.length; i++) {
            int left = getLeftBorder(array, i);
            int right = getRightBorder(array, i);
            int height = array[i];
            globMax = Math.max(globMax, height * (right - left));
        }
        return globMax;
    }
    private int getLeftBorder(int[] array, int index) {
        int left = index;
        while (left > 0 && array[left - 1] >= array[index]) {
            left--;
        }
        return left;
    }
    private int getRightBorder(int[] array, int index) {
        int right = index + 1;
        while (right < array.length && array[right] >= array[index]) {
            right++;
        }
        return right;
    }

    /*  Brute force
        For each index i:
            => find the highest right border
            <= find the highest left border
            water = min(leftMax, rightMax) - array[i] >= 0 ? ~ : 0
        Time: O(n^2)
    * */
//    public int maxTrappedI(int[] array) {}
    /*  Optimized
            leftMax[] => O(n)
            rightMax[] => O(n)
            water[i] = min(leftMax[i], rightMax[i]) - array[i] >= 0 ? ~ : 0 => O(n)
        Time: O(n)
    * */
//    public int maxTrappedII(int[] array) {}
    /*  Optimized
            int i = 0;
            int j = len - 1
            int leftMax = array[i]
            int rightMax = array[j]
        For each step:
            update leftMax with array[i] and update rightMax with array[j]
            if leftMax < rightMax, water[i] is determined
            else, water[j] is determined
        Terminate:
            i > j
    * */
    public int maxTrappedIII(int[] array) {
        // corner case
        if (array == null || array.length <= 2) {
            return 0;
        }
        int i = 0;
        int j = array.length - 1;
        int leftMax = array[i];
        int rightMax = array[j];
        int water = 0;
        while (i <= j) {
            leftMax = Math.max(leftMax, array[i]);
            rightMax = Math.max(rightMax, array[j]);
            int curr = 0;
            if (leftMax < rightMax) {
                curr = Math.max(curr, leftMax - array[i++]);
            } else {
                curr = Math.max(curr, rightMax - array[j--]);
            }
            water += curr;
        }
        return water;
    }

    /*
     *   Data structure:
     *       A minHeap, perimeter, sorted by Cell.level
     *   Initialize:
     *       put four edges of matrix into perimeter
     *   For each step:
     *       get the shortest board,
     *       sum += shortest.level - shortest.height
     *       for each unvisited neighbor of shortest:
     *           set the nei.level as max(nei.height, shortest.level)
     *           put the nei into perimeter
     *           mark the nei as "visited"
     *   Terminate:
     *       perimeter is empty
     * */
    public int maxTrapped3D(int[][] matrix) {
        PriorityQueue<Cell> perimeter = new PriorityQueue<>(new Comparator<Cell>() {
            @Override
            public int compare(Cell c1, Cell c2) {
                if (c1.level == c2.level) {
                    return 0;
                }
                return c1.level < c2.level ? -1 : 1;
            }
        });
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        periInitialization(perimeter, matrix, visited);
        int sum = 0;
        while (!perimeter.isEmpty()) {
            Cell shortest = perimeter.poll();
            sum += shortest.level - shortest.height;
            for (int[] dir : DIRS) {
                int x = shortest.x + dir[0];
                int y = shortest.y + dir[1];
                if (x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length && !visited[x][y]) {
                    Cell nei = new Cell(x, y, matrix[x][y], Math.max(matrix[x][y], shortest.level));
                    perimeter.offer(nei);
                    visited[x][y] = true;
                }
            }
        }
        return sum;
    }
    private void periInitialization(PriorityQueue<Cell> perimeter, int[][] m, boolean[][] v) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (i == 0 || j == 0 || i == m.length - 1 || j == m[0].length - 1) {
                    perimeter.offer(new Cell(i, j, m[i][j], m[i][j]));
                    v[i][j] = true;
                }
            }
        }
    }
    private final int[][] DIRS = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    class Cell {
        int x;
        int y;
        int height;
        int level;
        public Cell(int x, int y, int h, int l) {
            this.x = x;
            this.y = y;
            height = h;
            level = l;
        }
    }

    /*
    * Data structure:
    *   Map<slope, number of points> map
    *   globMax
    * For each point i:
    *       map = new HashMap<>();
    *       int perpen = 0;
    *       int same = 0;
    *       int currMax = 0;
    *       for each point j (j >= i):
    *           calculate the slope between i and j
    *               case 1. same position
    *                   same++;
    *               case 2. slope is infinite (i.x == j.x)
    *                   perpen++, update currMax
    *               case 3. otherwise
    *                   map.slope + 1, update currMax
    *       globMax = max(globMax, currMax + same);
    * */
    public int mostPointsOnALineI(Point[] points) {
        int globMax = 0;
        for (int i = 0; i < points.length; i++) {
            Point a = points[i];
            Map<Double, Integer> map = new HashMap<>();
            int perp = 0;
            int currMax = 0;
            int same = 0;
            for (int j = i; j < points.length; j++) {
                Point b = points[j];
                if (a.x == b.x && a.y == b.y) {
                    same++;
                } else if (a.x == b.x) {
                    perp++;
                    currMax = Math.max(currMax, perp);
                } else {
                    double slope = (b.y - a.y) * 1.0 / (b.x - a.x);
                    Integer num = map.getOrDefault(slope, 0);
                    map.put(slope, ++num);
                    currMax = Math.max(currMax, num);
                }
            }
            globMax = Math.max(globMax, currMax + same);
        }
        return globMax;
    }

    /*  Assumptions:
            matrix is not null;
            non of the arrays in matrix is null either;
            length of array may differ;
            length of array may be 0;
        Key point: minHeap
        Data structure:
            class Entry {
                int row;
                int col;
                int value;
                // constructor
            }
            PriorityQueue<Entry> minHeap = new PriorityQueue<>(new Comparator<Entry>() {
                @Override
                public int compare(Entry e1, Entry e2) {
                    if (e1.value == e2.value) {
                        return 0;
                    }
                    return e1.value < e2.value ? -1 : 1;
                }
            });
            int[] result = new int[len];
        Initialize:
            put first element of every array into minHeap
        For each step:
            pop out an element (curr) from heap,
            add curr.value into result
            add the next element of curr into heap if valid
        Terminate:
            minHeap.isEmpty()
        Time:
            m rows, n cols,
            O(m * n * logm) => O(mnlogm)
        Space:
            O(m) for heap
    * */
    class Entry {
        int row;
        int col;
        int value;

        public Entry(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }

    }
    public int[] merge(int[][] matrix) {
        PriorityQueue<Entry> minHeap = new PriorityQueue<>(new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2) {
                if (e1.value == e2.value) {
                    return 0;
                }
                return e1.value < e2.value ? -1 : 1;
            }
        });
        int len = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != 0) {
                minHeap.offer(new Entry(i, 0, matrix[i][0]));
                len += matrix[i].length;
            }
        }
        int[] result = new int[len];
        int index = 0;
        while (!minHeap.isEmpty()) {
            Entry curr = minHeap.poll();
            result[index++] = curr.value;
            if (curr.col + 1 < matrix[curr.row].length) {
                curr.col++;
                curr.value = matrix[curr.row][curr.col];
                minHeap.offer(curr);
            }
        }
        return result;
    }

    /*  Very similar with the previous problem.
    * */
    public ListNode merge(List<ListNode> list) {
        // corner case
        if (list == null || list.size() == 0) {
            return null;
        }
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode n1, ListNode n2) {
                if (n1.value == n2.value) {
                    return 0;
                }
                return n1.value < n2.value ? -1 : 1;
            }
        });
        for (ListNode node : list) {
            if (node != null) {
                minHeap.offer(node);
            }
        }
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (!minHeap.isEmpty()) {
            curr.next = minHeap.poll();
            if (curr.next.next != null) {
                minHeap.offer(curr.next.next);
            }
            curr = curr.next;
        }
        return dummy.next;
    }

    /*  Three pointers
    * */
    public List<Integer> commonInThreeSortedArrays(int[] a, int[] b, int[] c) {
        List<Integer> result = new ArrayList<>();
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < a.length && j < b.length && k < c.length) {
            if (a[i] == b[j] && b[j] == c[k]) {
                result.add(a[i]);
                i++;
                j++;
                k++;
            } else if (a[i] <= b[j] && a[i] <= c[k]) {
                i++;
            } else if (b[j] <= a[i] && b[j] <= c[k]) {
                j++;
            } else {
                k++;
            }
        }
        return result;
    }

    /*
        iterative way.
            1 + 2 => 12 + 3 => 13 + 4 => 14 ... + n => 1n
        Time: O((k - 1) * 2n) = O(kn), where k is the number of arrays, n is the average length of arrays.
    */
    public List<Integer> commonElementsInKSortedArrays(List<List<Integer>> input) {
        if (input.size() == 0) {
            return new ArrayList<>();
        }
        List<Integer> common = input.get(0);
        for (int i = 1; i < input.size(); i++) {
            common = commonInTwo(common, input.get(i));
        }
        return common;
    }
    private List<Integer> commonInTwo(List<Integer> a, List<Integer> b) {
        List<Integer> common = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < a.size() && j < b.size()) {
            int compare = a.get(i).compareTo(b.get(j));
            if (compare == 0) {
                common.add(a.get(i));
                i++;
                j++;
            } else if (compare < 0) {
                i++;
            } else {
                j++;
            }
        }
        return common;
    }
}

