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
}

