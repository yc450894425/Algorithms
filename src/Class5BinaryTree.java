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

}
