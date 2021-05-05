public class Class20RecursionIII {

    public int maxPathSumAnyToAny(TreeNode root) {
        int[] max = new int[]{Integer.MIN_VALUE};
        maxPathSumHelper(root, max);
        return max[0];
    }
    private int maxPathSumHelper(TreeNode root, int[] max) {
        // base case
        if (root == null) {
            return 0;
        }
        // recursive rule
        int left = maxPathSumHelper(root.left, max);
        int right = maxPathSumHelper(root.right, max);
        left = Math.max(left, 0);
        right = Math.max(right, 0);
        max[0] = Math.max(root.key + left + right, max[0]);
        return root.key + Math.max(left, right);
    }

    /*  Assumptions: root != null
        We define the semantic of the function as returning the max sum of the path from the input TreeNode to a leaf node.
    * */
    public int maxPathSumLeafToRoot(TreeNode root) {
        // base case
        // We shouldn't return 0 when root == null, because the max sum might be negative.
        // if root is a leaf node
        if (root.left == null && root.right == null) {
            return root.key;
        }
        // recursive rule
        // if root.left == null, we just add right result on root.key
        if (root.left == null) {
            return maxPathSumLeafToRoot(root.right) + root.key;
        }
        // vice versa
        if (root.right == null) {
            return maxPathSumLeafToRoot(root.left) + root.key;
        }
        return root.key + Math.max(maxPathSumLeafToRoot(root.left), maxPathSumLeafToRoot(root.right));
    }

    public int maxPathSumLeafToLeaf(TreeNode root) {
        int[] max = new int[]{Integer.MIN_VALUE};
        maxPathSumLeafToLeafHelper(root, max);
        return max[0];
    }
    /*  We define the semantic of the helper function as returning the max sum of the path from the input TreeNode to a leaf node.
        What do we expect from lc and rc?
            the max sum of the path in the left/right subtree that starts from lc/rc.
        What do we want to do in the current layer?
            if lc && rc == null => return root.key
            if lc || rc == null => return (which not null).key + root.key
            else, update max[0] with (root.key + lc + rc) if necessary, and return root.key + max(lc, rc)
        What do we want to return to the previous layer/parent?
            the max sum of the path in the binary tree that starts from root.
    */
    private int maxPathSumLeafToLeafHelper(TreeNode root, int[] max) {
        // base case
        if (root == null) {
            return 0;
        }
        // recursive rule
        int left = maxPathSumLeafToLeafHelper(root.left, max);
        int right = maxPathSumLeafToLeafHelper(root.right, max);
        if (root.left != null && root.right != null) {
            // update max[0]
            max[0] = Math.max(max[0], root.key + left + right);
            return root.key + Math.max(left, right);
        }
        return root.key + (root.left == null ? right : left);
    }

//    public boolean pathSumToTarget(TreeNode root, int target) {
//
//    }
}
