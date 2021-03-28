import java.util.ArrayList;
import java.util.List;

public class Class11RecursionII {

    // N*N 2D array, recursive solution
    public List<Integer> spiralI(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        spiralIPrint(0, matrix.length, matrix, result);
        return result;
    }
    /*  T T T R
     *  L X X R
     *  L X X R
     *  L B B B
     * */
    private void spiralIPrint(int offset, int size, int[][] m, List<Integer> result) {
        // base cases
        if (size <= 1) {
            if (size == 1) {
                result.add(m[offset][offset]);
            }
            return;
        }
        // recursive rules
        // top
        for (int i = 0; i < size - 1; i++) {
            result.add(m[offset][offset + i]);
        }
        // right
        for (int i = 0; i < size - 1; i++) {
            result.add(m[offset + i][offset + size - 1]);
        }
        // bottom
        for (int i = size - 1; i > 0; i--) {
            result.add(m[offset + size - 1][offset + i]);
        }
        // left
        for (int i = size - 1; i > 0; i--) {
            result.add(m[offset + i][offset]);
        }
        // inner spiral
        spiralIPrint(offset + 1, size - 2, m, result);
    }
    // N*N 2D array, iterative solution
    /*  T T T T
     *   L X X R
     *   L X X R
     *   B B B B
     * */
    public List<Integer> spiralII(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int start = 0;
        int end = matrix.length - 1;
        // base case is when there is only 1 or 0 element in the submatrix;
        // AKA start >= end
        while (start < end) {
            // top
            for (int i = start; i <= end; i++) {
                result.add(matrix[start][i]);
            }
            // right
            for (int i = start + 1; i <= end - 1; i++) {
                result.add(matrix[i][end]);
            }
            // bottom
            for (int i = end; i >= start; i--) {
                result.add(matrix[end][i]);
            }
            // left
            for (int i = end - 1; i >= start + 1; i--) {
                result.add(matrix[i][start]);
            }
            // update start and end
            start++;
            end--;
        }
        // post-processing
        if (start == end) {
            result.add(matrix[start][end]);
        }
        return result;
    }
    // M*N 2D array, recursive solution
    /*
     * */
    public List<Integer> spiralIII(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        spiralIIIPrint(matrix, 0, matrix.length, matrix[0].length, result);
        return result;
    }
    private void spiralIIIPrint(int[][] matrix, int offset, int rows, int cols, List<Integer> result) {
        // base cases
        if (rows <= 1 || cols <= 1) {
            if (rows == 1) {
                for (int i = 0; i < cols; i++) {
                    result.add(matrix[offset][offset + i]);
                }
            } else if (cols == 1) {
                for (int i = 0; i < rows; i++) {
                    result.add(matrix[offset + i][offset]);
                }
            }
            return;
        }
        // recursive rules
        // top
        for (int i = 0; i < cols - 1; i++) {
            result.add(matrix[offset][offset + i]);
        }
        // right
        for (int i = 0; i < rows - 1; i++) {
            result.add(matrix[offset + i][offset + cols - 1]);
        }
        // bottom
        for (int i = cols - 1; i > 0; i--) {
            result.add(matrix[offset + rows  - 1][offset + i]);
        }
        // left
        for (int i = rows - 1; i > 0; i--) {
            result.add(matrix[offset + i][offset]);
        }
        spiralIIIPrint(matrix, offset + 1, rows - 2, cols - 2, result);
    }
    public List<Integer> spiralIIII(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int up = 0;
        int down = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;
        while (up < down && left < right) {
            // top
            for (int i = left; i <= right; i++) {
                result.add(matrix[up][i]);
            }
            // right
            for (int i = up + 1; i <= down - 1; i++) {
                result.add(matrix[i][right]);
            }
            // bottom
            for (int i = right; i >= left; i--) {
                result.add(matrix[down][i]);
            }
            // left
            for (int i = down - 1; i >= up + 1; i--) {
                result.add(matrix[i][left]);
            }
            up++;
            left++;
            down--;
            right--;
        }
        if (up == down) {
            for (int i = left; i <= right; i++) {
                result.add(matrix[up][i]);
            }
        } else if (left == right) {
            for (int i = up; i <= down; i++) {
                result.add(matrix[i][right]);
            }
        }
        return result;
    }

    //    result.get(0) = 3, means the position of the first queue is [0, 3].
//    time: O(n^n * n), optimized to O(n! * n)
//    space: O(1 * n) = O(n)
    public List<List<Integer>> nqueens(int n) {
        List<List<Integer>> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        nqueensHelper(n, new ArrayList<>(), result);
        return result;
    }
    private void nqueensHelper(int n, List<Integer> cur, List<List<Integer>> result) {
        // base cases
        if (cur.size() == n) {
            result.add(new ArrayList<>(cur));
            return;
        }
        // recursive rules
        for (int i = 0; i < n; i++) {
            if (isValid(i, cur)) {
                cur.add(i);
                nqueensHelper(n, cur, result);
                cur.remove(cur.size() - 1);
            }
        }
    }
    private boolean isValid(int col, List<Integer> cur) {
        int row = cur.size();
        for (int i = 0; i < cur.size(); i++) {
            Integer curCol = cur.get(i);
            if (curCol == col || Math.abs(col - curCol) == row - i) {
                return false;
            }
        }
        return true;
    }
    // To be completed
    public List<List<Integer>> nqueensI(int n) {
        List<List<Integer>> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        return result;
    }

    // recursive way
    //    I define the subproblem as how to correctly reverse the linked list from head.next.next to the last node.
//    I define the semantic of “reverseInPairs” is reversing the linked list correctly and returning the new head.
    public ListNode reverseInPairs(ListNode head) {
        // base cases
        if (head == null || head.next == null) {
            return head;
        }
        // recursive rules
        ListNode newHead = head.next;
        head.next = reverseInPairs(head.next.next);
        newHead.next = head;
        return newHead;
    }
    // iterative way
    /*  dummy => 2 => 1 => 3 => 4 => 5 => null
     *                cur       next
     *                   ↓
     *   dummy => 2 => 1 => 4 => 3 => 5 => null
     *                          cur
     *   data structures:
     *       dummy head
     *       ListNode cur = dummy;
     *
     *   for each step:
     *       ListNode next = cur.next.next;
     *       cur.next.next = next.next;
     *       next.next = cur.next;
     *       cur.next = next;
     *       cur = cur.next.next;
     *
     *   terminate: cur.next == null || cur.next.next == null
     *
     *   return dummy.next;
     * */
    public ListNode reverseInPairsI(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;
        while (cur.next != null && cur.next.next != null) {
            ListNode next = cur.next.next;
            cur.next.next = next.next;
            next.next = cur.next;
            cur.next = next;
            cur = cur.next.next;
        }
        return dummy.next;
    }

    // recursive way
    public boolean matchI(String input, String pattern) {
        return helper(input, pattern, 0, 0);
    }
    private boolean helper(String s, String p, int si, int pi) {
        // base cases
        if (si == s.length() && pi == p.length()) {
            return true;
        }
        if (si == s.length() || pi == p.length()) {
            return false;
        }

        // if p[pi] is a letter
        if (p.charAt(pi) >= 'A' && p.charAt(pi) <= 'z') {
            if (p.charAt(pi) == s.charAt(si)) {
                return helper(s, p, si + 1, pi + 1);
            }
            return false;
        }
        // if p[pi] is a digit
        int num = p.charAt(pi++) - '0';
        while (pi < p.length() && p.charAt(pi) >= '0' && p.charAt(pi) <= '9') {
            num = num * 10 + (p.charAt(pi++) - '0');
        }
        if (si + num - 1 < s.length()) {
            return helper(s, p, si + num, pi);
        }
        return false;
    }
    //  iterative way
    /*  Data structures:
            two pointers, si, and pi, represents the next char should be compared in s and p;
            [si, s.length - 1) unexplored area of s
            [pi, p.length - 1) unexplored area of p
        Initialize: si = 0, pi = 0;
    *   For each step:
            case 1: p[pi] is a letter, compare p[pi] and s[si]
                case 1.1: match, pi++, si++;
                case 1.2: not match, return false;
            case 2: p[pi] is a digit, then form the number by keeping moving pi forward:
                int num = 0;
                while (pi < p.length && p[pi] is a digit) {
                    num = num * 10 + (p[pi] - '0');
                }
                si += num;
        Terminate: pi == p.length || si == s.length
        return pi == p.length && si == s.length
    * */
    public boolean matchII(String s, String p) {
        int si = 0;
        int pi = 0;
        while (pi < p.length() && si < s.length()) {
            // case 1, p[pi] is a letter
            if (p.charAt(pi) >= 'A' && p.charAt(pi) <= 'z') {
                if (p.charAt(pi) != s.charAt(si)) {
                    return false;
                }
                si++;
                pi++;
            }
            // case 2, p[pi] is a digit
            int num = 0;
            while (pi < p.length() && p.charAt(pi) >= '0' && p.charAt(pi) <= '9') {
                num = num * 10 + (p.charAt(pi++) - '0');
            }
            si += num;
        }
        return pi == p.length() && si == s.length();
    }
//
//    public void numNodesLeft(TreeNodeLeft root) {
//
//    }
//    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode one, TreeNode two)

}
