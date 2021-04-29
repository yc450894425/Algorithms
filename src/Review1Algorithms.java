import java.util.HashMap;
import java.util.List;

public class Review1Algorithms {

    /*  DP + HashMap
        1. get prefix array m,
            m[i] represents the sum[0, i]. => O(n)
            m[i] - m[j] = sum[j + 1, i] = k, where 0 <= j <= i - 1
            m[j] = m[i] - k
            Every time we passed by index i, and got m[i], we should traverse all j in [0, i - 1]
            and check if there are js satisfy m[j] = m[i] - k.
                if yes, count + 1;
            To make it simpler, we can use a hashmap to store the value of m[j].
            The key is m[j], value is the occurrence, where 0 <= j <= length - 2

            Importantly, since j is in [0, length - 2], i is in [0, length - 1], and m[i] - m[j] = sum[j + 1, i],
            the max range of sum is [1, length - 1], not[0, length -1].
            So we need to put a (0, 1) into hashmap at first.
            It seems like we add a "0" at the left side of the input array.

            Time: O(n)
            Space: O(n)
    * */
    public int numOfSubarraySumK(int[] array, int k) {
        // corner case
        if (array == null || array.length == 0) {
            return 0;
        }
        int count = 0;
        HashMap<Integer, Integer> occur = new HashMap<Integer, Integer>();
        occur.put(0, 1);
        int pre = 0;
        for (int i = 0; i < array.length; i++) {
            pre = pre + array[i];
            if (occur.containsKey(pre - k)) {
                count += occur.get(pre - k);
            }
            occur.put(pre, occur.getOrDefault(pre, 0) + 1);
        }
        return count;
    }

    public Integer maxArea(int[][] m) {
//        int[][] m = toMatrix(a);
        int[] max = new int[]{0};
        boolean[][] visited = new boolean[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                maxAreaHelper(m, visited, i, j, max);
            }
        }
        return max[0];
    }
    /*
    High level idea: Breadth First Search
    I define the semantic of the helper function as calculating the maximum number of 1s that are adjacent to m[i][j].
    Data Structure:
        int[][] m:
            converted from List a
        boolean[][] visited:
            visited[i][j] = true represents m[i][j] has been visited before
        int[] max:
            only 1 element. max[0] represents the max number of 1s that are adjacent.

    */
    private int maxAreaHelper(int[][] m, boolean[][] visited, int i , int j, int[] max) {
        // base case
        if (outOfBound(i, j, m.length, m[0].length) || visited[i][j] || m[i][j] == 0) {
            return 0;
        }
        // recursive rule
        visited[i][j] = true;
        int down = maxAreaHelper(m, visited, i + 1, j, max);
        int up = maxAreaHelper(m, visited, i - 1, j, max);
        int left = maxAreaHelper(m, visited, i, j - 1, max);
        int right = maxAreaHelper(m, visited, i, j + 1, max);
        int  currMax = down + up + left + right + 1;
        max[0] = Math.max(max[0], currMax);
        return currMax;
    }
    private int[][] toMatrix(List<List<Integer>> list) {
        int rows = list.size();
        int cols = getColumns(list);
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Integer ele = list.get(i).get(j);
                if (ele == null || ele == 0) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = 1;
                }
            }
        }
        return matrix;
    }
    private int getColumns(List<List<Integer>> list) {
        int cols = 0;
        for (int i = 0; i < list.size(); i++) {
            cols = Math.max(cols, list.get(i).size());
        }
        return cols;
    }
    private boolean outOfBound(int i, int j, int rows, int cols) {
        return i < 0 || j < 0 || i >= rows || j >= cols;
    }

}
