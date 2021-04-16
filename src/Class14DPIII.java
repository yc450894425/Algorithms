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
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int[][] topLeft = getTopLeftArms(matrix);
        int[][] bottomRight = getBottomRightArms(matrix);
        return getLargest(topLeft, bottomRight);
    }
    private int[][] getTopLeftArms(int[][] m) {
        int[][] topLeft = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                int left = m[i][j] == 0 ? 0 : (j == 0 ? m[i][j] : m[i][j - 1] + 1);
                int top = m[i][j] == 0 ? 0 : (i == 0 ? m[i][j] : m[i - 1][j] + 1);
                topLeft[i][j] = Math.min(left, top);
            }
        }
        return topLeft;
    }
    private int[][] getBottomRightArms(int[][] m) {
        int[][] bottomRight = new int[m.length][m[0].length];
        for (int i = m.length - 1; i >= 0; i--) {
            for (int j = m[0].length - 1; j >= 0; j--) {
                int right = m[i][j] == 0 ? 0 : (j == m[0].length - 1 ? m[i][j] : m[i][j + 1] + 1);
                int bottom = m[i][j] == 0 ? 0 : (i == m.length - 1 ? m[i][j] : m[i + 1][j] + 1);
                bottomRight[i][j] = Math.min(right, bottom);
            }
        }
        return bottomRight;
    }
    private int getLargest(int[][] topLeft, int[][] bottomRight) {
        int globMax = 0;
        for (int i = 0; i < topLeft.length; i++) {
            for (int j = 0; j < topLeft[0].length; j++) {
                int currMax = Math.min(topLeft[i][j], bottomRight[i][j]);
                globMax = Math.max(globMax, currMax);
            }
        }
        return globMax;
    }
    /*  11101
        10111
        11111
        10110
        00110
    * */
}
