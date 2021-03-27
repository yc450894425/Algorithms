import java.util.ArrayList;
import java.util.List;

public class Class11RecursionII {
//    public double power(int a, int b)
//    public List<Integer> spiral(int[][] matrix) {
//
//    }

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
