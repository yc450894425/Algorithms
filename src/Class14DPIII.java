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
            left[i][j] represents the number of consecutive 1s at left arm of m[i][j] that ends at m[i][j] and includes m[i][j];
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

    public int largestSquareSurroundedByOne(int[][] matrix) {
        return 0;
    }
}
