import java.util.ArrayList;
import java.util.List;

public class Class11RecursionII {
    // N*N 2D array, recursive solution
    public List<Integer> spiral(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        spiralPrint(0, matrix.length, matrix, result);
        return result;
    }
    /*  T T T R
    *   L X X R
    *   L X X R
    *   L B B B
    * */
    private void spiralPrint(int offset, int size, int[][] m, List<Integer> result) {
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
        spiralPrint(offset + 1, size - 2, m, result);
    }
    // N*N 2D array, iterative solution
    /*  T T T T
    *   L X X R
    *   L X X R
    *   B B B B
    * */
    public List<Integer> spiralI(int[][] matrix) {
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
    public List<Integer> spiralII(int[][] matrix) {}


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

//    public boolean match(String input, String pattern) {
//
//    }
//
//    public void numNodesLeft(TreeNodeLeft root) {
//
//    }
//    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode one, TreeNode two)

}
