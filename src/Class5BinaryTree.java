import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Class5BinaryTree {
    public static List<Integer> preOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preOrderTraversal(root, result);
        return result;
    }

    private static void preOrderTraversal(TreeNode root, List<Integer> result) {
        // base cases
        if (root == null) {
            return;
        }

        // recursive rules
        result.add(root.key);
        preOrderTraversal(root.left, result);
        preOrderTraversal(root.right, result);
    }

    public static List<Integer> inOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    private static void inOrderTraversal(TreeNode root, List<Integer> result) {
        // base cases
        if (root == null) {
            return;
        }

        // recursive rules
        inOrderTraversal(root.left, result);
        result.add(root.key);
        inOrderTraversal(root.right, result);
    }

    public static List<Integer> postOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postOrderTraversal(root, result);
        return result;
    }

    private static void postOrderTraversal(TreeNode root, List<Integer> result) {
        // base cases
        if (root == null) {
            return;
        }

        // recursive rules
        postOrderTraversal(root.left, result);
        postOrderTraversal(root.right, result);
        result.add(root.key);
    }

    public static int findHeight(TreeNode root) {
        // base cases
        if (root == null) {
            return 0;
        }
        // 1. get information from children
        int left = findHeight(root.left);
        int right = findHeight(root.right);
        // 2. merge information
        // 3. return information to parent
        return Math.max(left, right) + 1;
    }

    public static boolean isBalanced(TreeNode root) {

        return findHeightAndBalanced(root) != -1;
    }
    // The semantic of the method is to return the height of the tree. Specifically, return -1 if the tree is not balanced.
    private static int findHeightAndBalanced(TreeNode root) {
        // base cases
        if (root == null) {
            return 0;
        }

        // recursive rules
        int left = findHeightAndBalanced(root.left);
        int right = findHeightAndBalanced(root.right);
        if (left == -1 || right == -1 || Math.abs(right - left) > 1) {
            return -1;
        }
        return Math.max(left, right) + 1;
    }

    public static boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetric(root.left, root.right);
    }

    private static boolean isSymmetric(TreeNode one, TreeNode two) {
        // base cases
        if (one == null && two == null) {
            return true;
        } else if (one == null || two == null || one.key != two.key) {
            return false;
        }

        // recursive rules
        return isSymmetric(one.left, two.right) && isSymmetric(one.right, two.left);
    }

    /**Worst case: original input trees are balanced.
    Time complexity: O(1 + 4 + 4^2 + 4^3 + ... + 4^(log2n)) = O(4^(log2n)) = O(2^(2log2n)) = O(2^(log2(n^2))) = O(n^2)
    What if the input trees are not balanced?
    Then time complexity will be far smaller than O(n^2).
    Assuming two linked lists, time complexity is O(n).**/
    public static boolean isTweakedIdentical(TreeNode one, TreeNode two) {
        // corner cases
        if (one == null && two == null) {
            return true;
        }
        if (one == null || two == null || one.key != two.key) {
            return false;
        }

        // recursive rules
        return isTweakedIdentical(one.left, two.left) && isTweakedIdentical(one.right, two.right) || isTweakedIdentical(one.left, two.right) && isTweakedIdentical(one.right, two.left);
    }

    public static boolean isBST(TreeNode root) {
        return isBST(root, Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    private static boolean isBST(TreeNode root, int max, int min) {
        // base cases
        if (root == null) {
            return true;
        }

        // recursive rules
        if (root.key >= max || root.key <= min) {
            return false;
        }
        return isBST(root.left, root.key, min) && isBST(root.right, max, root.key);
    }


}
