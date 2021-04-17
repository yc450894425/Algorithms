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

    public int largestSquareOfMatches(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] right = new int[m + 1][n + 1];
        int[][] down = new int[m + 1][n + 1];
        int result = 0;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                right[i][j] = hasRight(matrix[i][j]) ? right[i][j + 1] + 1 : 0;
                down[i][j] = hasDown(matrix[i][j]) ? down[i + 1][j] + 1 : 0;
                if (hasRight(matrix[i][j]) && hasDown(matrix[i][j])) {
                    for (int l = Math.min(right[i][j], down[i][j]); l > 0; l--) {
                        if (down[i][j + l] >= l && right[i + l][j] >= l) {
                            result = Math.max(result, l);
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }
    private boolean hasRight(int n) {
        return n == 1 || n == 3;
    }
    private boolean hasDown(int n) {
        return n == 2 || n == 3;
    }

    /*  Pre-processing:
            preSum[i][j] represents sum of {(0,0)...(i,j)}.
            Pre-pre-processing:
                preRowSum[i][j] represents sum of {(i,0)...(i,j)}.
                base case:
                    preRowSum[i][0] = matrix[i][0];
                induction rule:
                    preRS[i][j] = preRS[i][j - 1] + matrix[i][j];
            base case:
                preSum[0][j] = preRowSum[0][j];
            induction rule:
                preSum[i][j] = preSum[i - 1][j] + preRowSum[i][j];
            time: O(m*n)

        Finding largest sub-matrix:
            globMax = 0;
            sum[(i,j), (k,l)] = preSum[k][l] - preSum[i - 1][j] - preSum[i][j - 1] + preSum[i - 1][j - 1];
            for for for for (i, j, k, l), calculate sum and update globMax if necessary;

        Return:
            globMax

        Time:
            O(mn) + O((mn)^2) => O((mn)^2)
        Space:
            O(mn) for preSum;
    * */
    public int largestSubMatrixSum(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        // pre-processing
        int[][] sum = new int[m + 1][n + 1];
        int rowSum = matrix[0][0];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                rowSum = j == 1 ? matrix[i - 1][j - 1] : rowSum + matrix[i - 1][j - 1];
                sum[i][j] = i == 1 ? rowSum : (sum[i - 1][j] + rowSum);
            }
        }
        // finding the largest sub-matrix
        int globMax = Integer.MIN_VALUE;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = i; k <= m; k++) {
                    for (int l = j; l <= n; l++) {
                        int currSum = sum[k][l] - sum[i - 1][l] - sum[k][j - 1] + sum[i - 1][j - 1];
                        globMax = Math.max(globMax, currSum);
                    }
                }
            }
        }
        return globMax;
    }
    /*  preSum[i][j] = sum{(0,j),(1,j), ..., (i,j)}
        base case:
            preSum[0][j] = matrix[0][j]
        induction rule:
            preSum[i][j] = preSum[i - 1][j] + matrix[i][j];
    * */
    public int largestSubMatrixSumII(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int globMax = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            int[] curRow = new int[n];
            for (int j = i; j < m; j++) {
                add(curRow, matrix[j]);
                globMax = Math.max(globMax, getLargest(curRow));
            }
        }
        return globMax;
    }
    private void add(int[] base, int[] addend) {
        for (int i = 0; i < base.length; i++) {
            base[i] += addend[i];
        }
    }
    private int getLargest(int[] array) {
        int largest = array[0];
        int curr = array[0];
        for (int i = 1; i < array.length; i++) {
            curr = Math.max(curr + array[i], array[i]);
            largest = Math.max(largest, curr);
        }
        return largest;
    }
}
