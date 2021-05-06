import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Class20RecursionIII {

    public int maxPathSumAnyToAny(TreeNode root) {
        int[] max = new int[]{Integer.MIN_VALUE};
        maxPathSumAnyToAnyHelper(root, max);
        return max[0];
    }
    private int maxPathSumAnyToAnyHelper(TreeNode root, int[] max) {
        // base case
        if (root == null) {
            return 0;
        }
        // recursive rule
        int left = maxPathSumAnyToAnyHelper(root.left, max);
        int right = maxPathSumAnyToAnyHelper(root.right, max);
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
        // If the input node is null, the max sum is 0 of course.
        // This is unnecessary because we assume root != null and this could be confusing, so I commented the base case.
        // But you should know the base case is root == null, not that root is a leaf. Actually the latter is a part of recursive rules.
//        if (root == null) {
//            return 0;
//        }
        // recursive rule
        // We shouldn't really return 0 when root == null, because the max sum might be negative.
        // if root is a leaf node
        if (root.left == null && root.right == null) {
            return root.key;
        }
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
    /*  Key point: transfer preSum from top to bottom.
    * */
    public int maxPathSumLeafToRootII(TreeNode root) {
        int[] max = new int[]{Integer.MIN_VALUE};
        maxPathSumLeafToRootIIHelper(root, 0, max);
        return max[0];
    }
    /*  The semantic of preSum is the sum from root to the current node.
        Transfer sum up to down.
    * */
    private void maxPathSumLeafToRootIIHelper(TreeNode root, int preSum, int[] max) {
        // base case
        if (root == null) {
            return;
        }
        // recursive rule
        preSum += root.key;
        // if it's a leaf
        if (root.left == null && root.right == null) {
            max[0] = Math.max(preSum, max[0]);
            return;
        }
        maxPathSumLeafToRootIIHelper(root.left, preSum, max);
        maxPathSumLeafToRootIIHelper(root.right, preSum, max);
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

    /*  Key point: How to record the all possible sums of visited nodes.
            We use a set which contains all path prefix sum from root to current node.

            target: 17
                5 => 11 => 6
          set   5    16   22

            Update curr prefix sum immediately once we arrived at a new node.
          if set.contains(curr prefix sum - target), we can return true;
    * */
    public boolean pathSumToTarget(TreeNode root, int target) {
        Set<Integer> set = new HashSet<>();
        set.add(0);
        return pathSumToTargetHelper(root, 0, set, target);
    }
    private boolean pathSumToTargetHelper(TreeNode root, int preSum, Set<Integer> set, int target) {
        // base case
        if (root == null) {
            return false;
        }
        // recursive rule
        preSum += root.key;
        if (set.contains(preSum -  target)) {
            return true;
        }
        boolean notExisted = set.add(preSum);
        boolean left = pathSumToTargetHelper(root.left, preSum, set, target);
        boolean right = pathSumToTargetHelper(root.right, preSum, set, target);
        if (left || right) {
            return true;
        }
        if (notExisted) {
            set.remove(preSum);
        }
        return false;
    }

    public int maxPathSumOnStraightPath(TreeNode root) {
        int[] max = new int[] {Integer.MIN_VALUE};
//        maxPathSumOnStraightPathHelperI(root, 0, max);
        maxPathSumOnStraightPathHelperII(root, max);
        return max[0];
    }
    /*  The semantic of the preSum is the max sum ending at current node.
        Transfer sum up to down.
    * */
    private void maxPathSumOnStraightPathHelperI(TreeNode root, int preSum, int[] max) {
        // base case
        if (root == null) {
            return;
        }
        // recursive rule
        preSum = preSum > 0 ? preSum + root.key : root.key;
        max[0] = Math.max(preSum, max[0]);
        maxPathSumOnStraightPathHelperI(root.left, preSum, max);
        maxPathSumOnStraightPathHelperI(root.right, preSum, max);
    }
    /*  The semantic of the helper function is returning the max sum starting from the current node.
        Transfer sum down to up.
    * */
    private int maxPathSumOnStraightPathHelperII(TreeNode root, int[] max) {
        // base case
        if (root == null) {
            return 0;
        }
        // recursive rule
        int left = Math.max(maxPathSumOnStraightPathHelperII(root.left, max), 0);
        int right = Math.max(maxPathSumOnStraightPathHelperII(root.right, max), 0);
        int curr = root.key + Math.max(left, right);
        max[0] = Math.max(max[0], curr);
        return curr;
    }

    public TreeNode flatten(TreeNode root) {
        TreeNode[] prev = new TreeNode[1];
        postOrderFlattenHelper(root, prev);
        return root;
    }
    /*  We define the semantic of the helper function as
            flattening the tree whose root is the input node to a linked list,
            connecting the input node after the TreeNode prev,
            and update prev to the last node of the linked list.

                                    1
                                /       \
                               2         5
                            /     \       \
                          3       4        6

                 1  =>  2   =>  3               =>  4   =>  5   =>  6
                     prev[0]   cur

            For each recursion:
                if prev[0] == null, prev[0] = root
                else prev[0].right = root, prev[0] = root

                record root.left and root.right
                root.left = null;
                Why should we also record root.right?
                    because root.right will be changed in the next recursion (as prev[0].right).

                helper(root.left, prev);
                helper(root.right, prev);
    * */
    private void preOrderFlattenHelper(TreeNode root, TreeNode[] prev) {
        // base case
        if (root == null) {
            return;
        }
        // recursive rule
        TreeNode left = root.left;
        TreeNode right = root.right;
        if (prev[0] != null) {
            prev[0].right = root;
        }
        prev[0] = root;
        root.left = null;
        preOrderFlattenHelper(left, prev);
        preOrderFlattenHelper(right, prev);
    }
    /*  We define the semantic of the helper function as
            flattening the tree whose root is the input node to a linked list,
            connecting the input node before the TreeNode next,
            and update next to the first node of the linked list.

                                    1
                                /       \
                               2         5
                            /     \       \
                          3       4        6

                 1  =>          2   =>  3   =>  4   =>  5   =>  6
                                c     next[0]
     */
    private void postOrderFlattenHelper(TreeNode root, TreeNode[] next) {
        // base case
        if (root == null) {
            return;
        }
        // recursive rule
        postOrderFlattenHelper(root.right, next);
        postOrderFlattenHelper(root.left, next);
        root.right = next[0];
        root.left = null;
        next[0] = root;
    }

    /*  preOrder:   10 5 2 7 15 12 17
                   pL/r            pR
        inOrder:    2 5 7 10 12 15 17
                   iL     r        iR
        Map<node in inOrder, its index in inOrder>
    * */
    public TreeNode reconstruct(int[] inOrder, int[] preOrder) {
        Map<Integer, Integer> map = toMap(inOrder);
        return reconstructHelper(preOrder, 0, preOrder.length - 1, 0, inOrder.length - 1, map);
    }
    /*  The semantic of the helper function is reconstructing a binary tree from inOrder[inL, inR] and preOrder[preL, preR],
        and returning the root of the tree.
    * */
    private TreeNode reconstructHelper(int[] preOrder, int preL, int preR, int inL, int inR, Map<Integer, Integer> map) {
        // base case
        if (preL > preR) {
            return null;
        }
        // recursive rule
        TreeNode root = new TreeNode(preOrder[preL]);
        int rootIdx = map.get(root.key);
        int leftSize = rootIdx - inL;
        TreeNode leftChild = reconstructHelper(preOrder, preL + 1, preL + leftSize, inL, rootIdx - 1, map);
        TreeNode rightChild = reconstructHelper(preOrder, preL + leftSize + 1, preR, rootIdx + 1, inR, map);
        root.left = leftChild;
        root.right = rightChild;
        return root;
    }
    private Map<Integer, Integer> toMap(int[] array) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            map.put(array[i], i);
        }
        return map;
    }

    /*  AKA reconstruct a binary tree from post-order array and in-order array.
        Key point: if we reverse the postOrder array, it will be a [roo][right subtree][left subtree] "right first" pre-order array.

        post:   1 4 3 11 8 5
                           l
    * */
    public TreeNode reconstructBST(int[] postOrder) {
        int[] lastIdx = new int[]{postOrder.length - 1};
        return reconstructBSTHelper(postOrder, lastIdx, Integer.MIN_VALUE);
    }
    /*  Any key of any node under root should be larger than min.
    * */
    private TreeNode reconstructBSTHelper(int[] post, int[] lastIdx, int min) {
        // base case
        if (lastIdx[0] < 0 || post[lastIdx[0]] <= min) {
            return null;
        }
        // recursive rule
        TreeNode root = new TreeNode(post[lastIdx[0]--]);
        root.right = reconstructBSTHelper(post, lastIdx, root.key);
        root.left = reconstructBSTHelper(post, lastIdx, min);
        return root;
    }
}
