public class Class14DPIII {
    /*	m[i] represents the length of the longest subarray that ends at a[i] and contains only 1.
        base case:
            m[0] = a[0]
        induction rule:
            m[i] = 0, if a[i] = 0;
            m[i] = m[i - 1] + 1, if a[i] = 1;
        return:
            globMax
        time: O(n), where n is the length of a
        space: O(n) => O(1)
    */
    public int longest(int[] array) {
        if (array.length == 0) {
            return 0;
        }

        int[] m = new int[array.length];
        m[0] = array[0];
        int globMax = m[0];
        for (int i = 1; i < array.length; i++) {
            m[i] = array[i] == 1 ? m[i - 1] + 1 : 0;
            globMax = Math.max(globMax, m[i]);
        }
        return globMax;
    }
    public int longestII(int[] array) {
        int curr = 0;
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            curr = array[i] == 1 ? curr + 1 : 0;
            result = Math.max(curr, result);
        }
        return result;
    }

    /*  Data structure:
            We need four matrices for pre-processing.
            left[i][j] represents the length of the longest left arm of m[i][j] which contains only 1s (includes m[i][j]);
            right[i][j] ...right arm
            up[i][j]
            down[i][j]

            largest[i][j] represents the largest cross at m[i][j].
            largest[i][j] = min(left[i][j], right.., up.., down..);
        return:
            globMax
        Time:
            O(2*N*M) for pre-processing (traverse twice) where N = m.length, M = m[0].length;
            O(N*M) for finding globMax
            total O(N*M)
        Space:
            O(N*M)
    * */
    public int largest(int[][] matrix) {
        // corner case
        int m = matrix.length;
        if (m == 0) {
            return 0;
        }
        int n = matrix[0].length;
        if (n == 0) {
            return 0;
        }
        int[][] topLeft = getTopLeftArms(matrix, m, n);
        int[][] bottomRight = getBottomRightArms(matrix, m, n);
        return mergeToFirstAndGetLargest(topLeft, bottomRight, m, n);
    }
    private int[][] getTopLeftArms(int[][] matrix, int m, int n) {
        int[][] top = new int[m][n];
        int[][] left = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                top[i][j] = matrix[i][j] == 0 ? 0 : (i == 0 ? matrix[i][j] : top[i - 1][j] + 1);
                left[i][j] = matrix[i][j] == 0 ? 0 : (j == 0 ? matrix[i][j] : left[i][j - 1] + 1);
            }
        }
        mergeToFirstAndGetLargest(top, left, m, n);
        return top;
    }
    private int[][] getBottomRightArms(int[][] matrix, int m, int n) {
        int[][] bottom = new int[m][n];
        int[][] right = new int[m][n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                bottom[i][j] = matrix[i][j] == 0 ? 0 : (i == m - 1 ? matrix[i][j] : bottom[i + 1][j] + 1);
                right[i][j] = matrix[i][j] == 0 ? 0 : (j == n - 1 ? matrix[i][j] : right[i][j + 1] + 1);
            }
        }
        mergeToFirstAndGetLargest(bottom, right, m, n);
        return bottom;
    }
    private int mergeToFirstAndGetLargest(int[][] a, int[][] b, int m, int n) {
        int globMax = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = Math.min(a[i][j], b[i][j]);
                globMax = Math.max(a[i][j], globMax);
            }
        }
        return globMax;
    }

    /*  Very similar to largest
        Difference:
            How to traverse the matrix to fill out topL&bottomL/topR&bottomR?
    * */
    public int largestX(int[][] matrix) {
        int m = matrix.length;
        if (m == 0) {
            return 0;
        }
        int n = matrix[0].length;
        if (n == 0) {
            return 0;
        }
        int[][] left = getTopBottomLeftArms(matrix, m, n);
        int[][] right = getTopBottomRightArms(matrix, m, n);
        return mergeToFirstAndGetLargest(left, right, m, n);
    }
    private int[][] getTopBottomLeftArms(int[][] matrix, int m, int n) {
        int[][] topL = new int[m][n];
        int[][] bottomL = new int[m][n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                topL[i][j] = matrix[i][j] == 0 ? 0 : (i == 0 || j == 0 ? 1 : topL[i - 1][j - 1] + 1);
                bottomL[i][j] = matrix[i][j] == 0 ? 0 : (i == m - 1 || j == 0 ? 1 : bottomL[i + 1][j - 1] + 1);
            }
        }
        mergeToFirstAndGetLargest(topL, bottomL, m, n);
        return topL;
    }
    private int[][] getTopBottomRightArms(int[][] matrix, int m, int n) {
        int[][] topR = new int[m][n];
        int[][] bottomR = new int[m][n];
        for (int j = n - 1; j >= 0; j--) {
            for (int i = 0; i < m; i++) {
                topR[i][j] = matrix[i][j] == 0 ? 0 : (i == 0 || j == n - 1 ? 1 : topR[i - 1][j + 1] + 1);
                bottomR[i][j] = matrix[i][j] == 0 ? 0 : (i == m - 1 || j == n - 1 ? 1 : bottomR[i + 1][j + 1] + 1);
            }
        }
        mergeToFirstAndGetLargest(topR, bottomR, m, n);
        return topR;
    }

    /*  Data structure:
            We need two matrices for pre-processing.
            top[i][j] represents the length of the longest top arm of matrix[i][j] which contains only 1s (including matrix[i][j]).
            left[i][j] represents the length of the longest left arm of matrix[i][j] which contains only 1s (including matrix[i][j]).

        We also need a globMax to record the result.
        Then we traverse every position.
        For each position (i, j):
            - if it's 0, we just pass it.
            - we choose min(top(i, j), left(i, j)) as current side length;
                e.g. the current side length is l.
                Then we check top[i][j - (l - 1)] and left[i - (l - 1)][j]:
                    - if both of them are >= l, lucky! the size of the maximum square whose bottom right corner is (i, j) is l.
                    - if not, l--, check again, till l == 1.
                 Then we update globMax if necessary.
        return:
            globMax
        time:
            O(2*m*n) for pre-processing.
            O(m*n) * O(max(m, n)) for traversing every position.
            total O(n^3) if m is close to n
        space:
            O(2*m*n) => O(n^2)
    * */
    public int largestSquareSurroundedByOne(int[][] matrix) {
        int m = matrix.length;
        if (m == 0) {
            return 0;
        }
        int n =matrix[0].length;
        if (n == 0) {
            return 0;
        }
        int[][] left = new int[m][n];
        int[][] top = new int[m][n];
        int globMax = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    continue;
                }
                left[i][j] = j == 0 ? 1 : left[i][j - 1] + 1;
                top[i][j] = i == 0 ? 1 : top[i - 1][j] + 1;
                for (int currL = Math.min(top[i][j], left[i][j]); currL > 0; currL--) {
                    if (top[i][j - currL + 1] >= currL && left[i - currL + 1][j] >= currL) {
                        globMax = Math.max(globMax, currL);
                        break;
                    }
                }
            }
        }
        return globMax;
    }
}
