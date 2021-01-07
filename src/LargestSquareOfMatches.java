//638 Largest square of matches
//        input -> int[][], a matrix of points. Every point has 3 possible values:
//        0: there is no match to its right or bottom
//        1: there is one match to its right
//        2: there is one match to its bottom
//        3: there is one match to its right, and one match to its bottom
//
//        1 0 0					 _
//        0 3 2			->		   _
//        0 1 0					  |_|
//
//        output -> int length of the largest square
//        Dynamic Programming
//        1. pre-processing
//        data structure:
//        right/bottom[i][j] represents the longest right/bottom arm length at matrix[i][j].
//        Initialize:
//        int[][] right = new int[matrix.length + 1][matrix[0].length + 1];
//        int[][] bottom = new int[matrix.length + 1][matrix[0].length + 1];
//        for matrix[i][j]:
//        if (has right match), right[i][j] = right[i][j + 1] + 1;
//        otherwise, 0;
//        if (has bottom match), bottom[i][j] = right[i + 1][j] + 1;
//        otherwise, 0;
//        tc: O(n^2)
//        2. find largest square
//        for every position in matrix as the up left corner of the square:
//        len = 0;
//        maxLen = min(right[i][j], bottom[i][j])
//        for (maxLen to 1)
//        if the length of bottom match of up right corner >= len &&
//        the length of right match of bottom left corner >= len
//        update len and break;
//
//        update globalMax if necessary;

public class LargestSquareOfMatches {
    public int largestSquareOfMatches(int[][] matrix) {
        // corner cases
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] right = new int[m + 1][n + 1];
        int[][] bottom = new int[m + 1][n + 1];
        int result = 0;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (hasRight(matrix[i][j])) {
                    right[i][j] = right[i][j + 1] + 1;
                }
                if (hasBottom(matrix[i][j])) {
                    bottom[i][j] = bottom[i + 1][j] + 1;
                }
            }
        }

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                int len = 0;
                int maxLen = Math.min(bottom[i][j], right[i][j]);
                for (int k = maxLen; k > 0; k--) {
                    if (bottom[i][j + k] >= k && right[i + k][j] >= k) {
                        len = k;
                        break;
                    }
                }
                result = Math.max(result, len);
            }
        }
        return result;
    }

    private boolean hasRight(int value) {
        return value == 1 || value == 3;
    }

    private boolean hasBottom(int value) {
        return value == 2 || value == 3;
    }
}

