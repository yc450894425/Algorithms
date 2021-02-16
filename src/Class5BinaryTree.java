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

    // iterative
    public static TreeNode search(TreeNode root, int key) {

        TreeNode cur = root;
        while (cur != null ||cur.key != key) {
            cur = cur.key > key ? cur.left : cur.right;
        }
        return cur;
    }

    // peek before going down
    public static TreeNode insertIterative1(TreeNode root, int key) {
        // corner cases
        if (root == null) {
            return new TreeNode(key);
        }
        TreeNode cur = root;
        while (cur.key != key) {
            if (key < cur.key) {
                if (cur.left == null) {
                    cur.left = new TreeNode(key);
                }
                cur = cur.left;
            } else {
                if (cur.right == null) {
                    cur.right = new TreeNode(key);
                }
                cur = cur.right;
            }
        }
        return root;
    }

//    use a prev pointer
//    DS: prev: records cur’s value in last iteration.
//            Initialization: TreeNode cur = root; TreeNode prev = null;
//    For each step:
//            if cur.key == key, return root;
//            if key < cur.key, prev = cur, cur = cur.left;
//            if key > cur.key, prev = cur, cur = cur.right;
//    Termination condition:
//        cur == null
//    Post-processing:
//            if key < prev.key, prev.left = new TreeNode(key);
//            if key > prev.key, prev.right = new TreeNode(key);
    public static TreeNode insertIterative2(TreeNode root, int key) {
        // corner cases
        if (root == null) {
            return new TreeNode(key);
        }

        TreeNode cur = root;
        TreeNode prev = null;

        while (cur != null) {
            prev = cur;
            if (cur.key == key) {
                return root;
            } else if (key < cur.key) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }

        if (key < prev.key) {
            prev.left = new TreeNode(key);
        } else {
            prev.right = new TreeNode(key);
        }
        return root;
    }

    // The semantic of the method is to insert the key into the input BST, then return the root of the tree.
    public static TreeNode insertRecursive1(TreeNode root, int key) {
        // base cases
        if (root == null) {
            return new TreeNode(key);
        }

        // recursive rules
        if (key < root.key) {
            root.left = insertRecursive1(root.left, key);
        } else if (key > root.key) {
            root.right = insertRecursive1(root.right, key);
        }
        return root;
    }

    // Tail recursion. Not good practice. Do NOT use this.
    public static TreeNode insertRecursive2(TreeNode root, int key) {
        // corner cases
        if (root == null) {
            return new TreeNode(key);
        }
        insertRecursive2Helper(root, key);
        return root;
    }
    public static void insertRecursive2Helper(TreeNode root, int key) {
        if (root.key == key) {
            return;
        } else if (key < root.key) {
            if (root.left == null) {
                root.left = new TreeNode(key);
            }
            insertRecursive2Helper(root.left, key);
        } else {
            if (root.right == null) {
                root.right = new TreeNode(key);
            }
            insertRecursive2Helper(root.right, key);
        }
    }

}
