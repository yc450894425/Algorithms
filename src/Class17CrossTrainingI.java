import java.util.*;

public class Class17CrossTrainingI {

    /*
        slow: all elements on the left side of slow (including slow itself) are elements we want to keep in the result.
        fast: the current index being processed.
    * */
    public int[] dedupI(int[] array) {
        if (array.length <= 1) {
            return array;
        }
        int slow = 0;
        for (int fast = 1; fast < array.length; fast++) {
            if (array[fast] != array[slow]) {
                array[++slow] = array[fast];
            }
        }
        return Arrays.copyOf(array, slow + 1);
    }

    /*
        slow: all elements on the left side of slow (including slow itself) are elements we want to keep in the result.
        fast: the current index being processed.
    * */
    public int[] dedupII(int[] array) {
        if (array.length <= 2) {
            return array;
        }
        int slow = 1;
        for (int fast = 2; fast < array.length; fast++) {
            if (array[fast] != array[slow - 1]) {
                array[++slow] = array[fast];
            }
        }
        return Arrays.copyOf(array, slow + 1);
    }

    /*
    slow: all elements on the left side of slow (excluding slow itself) are elements we want to keep in the result.
    fast: the current index being processed.
* */
    public int[] dedupIII(int[] array) {
        if (array.length <= 1) {
            return array;
        }
        int slow = 0;
        int fast = 0;
        while (fast < array.length) {
            int start = fast;
            while (fast < array.length && array[fast] == array[start]) {
                fast++;
            }
            if (fast - start == 1) {
                array[slow++] = array[start];
            }
        }
        return Arrays.copyOf(array, slow);
    }

    /*  High level idea:
            Push elements into a stack, if new element is same with the top element in stack, pop the top ele out and ignore all same elements.
        Data structure:
            Use a pointer "slow" to simulate the stack.
            [0, slow) are elements in stack (excluding slow)
            fast: all elements at left side of fast (excluding fast) are processed elements.
        Initialize: slow = 0, fast = 0;
        For each step:
            if stack is empty or array[fast] != top(slow - 1), push array[fast] into stack
            if array[fast] == top, move fast forward until array[fast] != top, then pop top
        Terminate: fast == length
        Return: array[0, slow) (excluding slow)
        Time: O(n) where n is the length of input array
        Space: O(1) for pointers
    * */
    public int[] dedupIV(int[] array) {
        // corner case
        if (array == null || array.length <= 1) {
            return array;
        }
        int slow = 0;
        int fast = 0;
        while (fast < array.length) {
            if (slow == 0 || array[fast] != array[slow - 1]) {
                array[slow++] = array[fast++];
            } else {
                while (fast < array.length && array[fast] == array[slow - 1]) {
                    fast++;
                }
                slow--;
            }
        }
        return Arrays.copyOf(array, slow);
    }

    /*
    High level idea: copy non-zero elements to left side, then fill out the array with 0s.
    slow: all elements on the left side of slow (excluding slow itself) are elements we want to keep in the result.
    fast: the current index being processed.
* */
    public int[] moveZero(int[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }
        int slow = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                array[slow++] = array[i];
            }
        }
        while (slow < array.length) {
            array[slow++] = 0;
        }
        return array;
    }

    /*  Not Good Practice.
     *   High level idea: rotate the outer border recursively.
     *   A X X X B
     *   X X X X X
     *   X X X X X
     *   X X X X X
     *   D X X X C
     *   A[offset][offset]
     *   B[offset][offset + size - 1]
     *   C[offset + size - 1][offset + size - 1]
     *   D[offset + size - 1][offset]
     *
     *   Base case:
     *       size == 1 or 0
     *       do nothing, return
     *   How to rotate/Recursive rule:
     *       tmp = D
     *       D = C
     *       C = B
     *       B = A
     *       A = D
     *
     * */
    public void rotateI(int[][] matrix) {
        if (matrix == null) {
            return;
        }
        int size = matrix.length;
        rotateHelper(matrix, 0, size);
    }

    private void rotateHelper(int[][] matrix, int offset, int size) {
        // base case
        if (size == 1 || size == 0) {
            return;
        }
        // recursive rule
        for (int i = 0; i < size - 1; i++) {
            int tmp = matrix[offset + size - 1 - i][offset];
            matrix[offset + size - 1 - i][offset] = matrix[offset + size - 1][offset + size - 1 - i];
            matrix[offset + size - 1][offset + size - 1 - i] = matrix[offset + i][offset + size - 1];
            matrix[offset + i][offset + size - 1] = matrix[offset][offset + i];
            matrix[offset][offset + i] = tmp;
        }
        rotateHelper(matrix, offset + 1, size - 2);
    }

    /*   Better Practice
     *   High level idea: rotate the outer border iteratively.
     *   U U U U R
     *   L X X X R
     *   L X X X R
     *   L X X X R
     *   L D D D D
     *
     * */
    public void rotateII(int[][] matrix) {
        if (matrix == null) {
            return;
        }
        int offset = 0;
        int size = matrix.length;
        while (size > 1) {
            for (int i = 0; i < size - 1; i++) {
                int tmp = matrix[offset + size - 1 - i][offset];
                matrix[offset + size - 1 - i][offset] = matrix[offset + size - 1][offset + size - 1 - i];
                matrix[offset + size - 1][offset + size - 1 - i] = matrix[offset + i][offset + size - 1];
                matrix[offset + i][offset + size - 1] = matrix[offset][offset + i];
                matrix[offset][offset + i] = tmp;
            }
            offset++;
            size -= 2;
        }
    }

    /*  Data structure:
            Deque
                   First [  1 4 11 ] Last
        Define the level of root is 0;
        For odd level:
            Expand from First, print it, add left to Last, then add right to Last.
        For even level:
            Expand from Last, print it, add right to First, then add left to First.
    * */
    public List<Integer> zigZag(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.offerFirst(root);
        boolean isEven = true;
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode curr = null;
                if (isEven) {
                    curr = deque.pollLast();
                    if (curr.right != null) {
                        deque.offerFirst(curr.right);
                    }
                    if (curr.left != null) {
                        deque.offerFirst(curr.left);
                    }
                } else {
                    curr = deque.pollFirst();
                    if (curr.left != null) {
                        deque.offerLast(curr.left);
                    }
                    if (curr.right != null) {
                        deque.offerLast(curr.right);
                    }
                }
                result.add(curr.key);
            }
            isEven = isEven ? false : true;
        }
        return result;
    }

    /*  Assumptions: No parent pointer; guaranteed to be in the binary tree.
        I define the semantic of the method is returning the LCA of {one, two} that is/are under root.
        case 1. Neither of one or two are under root, return null;
        case 2. One of the two nodes is under root, return the node;
        case 3. Both of them are under root,
            case 3.1. one is two's ancestor, return one
            case 3.2. two is one's ancestor, return two
            case 3.3. otherwise, return the lowest node with one and two in its different subtree.
    * */
    public TreeNode lowestCommonAncestorI(TreeNode root, TreeNode one, TreeNode two) {
        // base case
        if (root == null || root == one || root == two) {
            return root;
        }
        // recursive rule
        TreeNode left = lowestCommonAncestorI(root.left, one, two);
        TreeNode right = lowestCommonAncestorI(root.right, one, two);
        if (left != null && right != null) {
            return root;
        }
        return left == null ? right : left;
    }

    /*  Assumption: Node has parent pointer; not guaranteed to be in the binary tree.
        High level idea:
            Move both two nodes up through parent pointers until they collide.
            1. get depth of two nodes
                1.1 if they are not under the same root, return null
            2. move the deeper node up until it matches the depth of another node
            3. move both nodes up until they collide

        Time: O(height) for get depth, O(height) for get LCA => O(height) => O(n)
        Space: O(1)
    * */
    public TreeNodeP lowestCommonAncestorII(TreeNodeP one, TreeNodeP two) {
        int d1 = getDepth(one);
        int d2 = getDepth(two);
        if (d1 >= d2) {
            return mergeNodes(two, one, d1 - d2);
        } else {
            return mergeNodes(one, two, d2 - d1);
        }
    }
    private TreeNodeP mergeNodes(TreeNodeP shallower, TreeNodeP deeper, int diff) {
        while (diff > 0) {
            deeper = deeper.parent;
            diff--;
        }
        while (shallower != deeper) {
            shallower = shallower.parent;
            deeper = deeper.parent;
        }
        return deeper;
    }
    private int getDepth(TreeNodeP node) {
        int depth = 0;
        while (node != null) {
            node = node.parent;
            depth++;
        }
        return depth;
    }

    /*  Assumption: Binary Search Tree; No parent pointer; guaranteed to be in the BST.
     * */
    public TreeNode lowestCommonAncestorIII(TreeNode root, int p, int q) {
        int max = Math.max(p, q);
        int min = Math.min(p, q);
        while (root != null) {
            if (root.key > max) {
                root = root.left;
            } else if (root.key < min) {
                root = root.right;
            } else {
                return root;
            }
        }
        return null;
    }

    /*  Assumptions: LCA of K nodes; No parent; Guaranteed to be in the binary tree.
        I define the semantic of the method is returning the LCA of {nodes} that is/are under root.
        case 1. None of them are under root, return null;
        case 2. One of the them is under root, return the node;
        case 3. Two or more of them are under root, return LCA of these nodes;
    * */
    public TreeNode lowestCommonAncestorIV(TreeNode root, List<TreeNode> nodes) {
        Set<TreeNode> set = new HashSet<>(nodes);
        return lowestCommonAncestorIV(root, set);
    }
    private TreeNode lowestCommonAncestorIV(TreeNode root, Set<TreeNode> set) {
        // base case
        if (root == null || set.contains(root)) {
            return root;
        }
        // recursive rule
        TreeNode l = lowestCommonAncestorIV(root.left, set);
        TreeNode r = lowestCommonAncestorIV(root.right, set);
        if (l != null && r != null) {
            return root;
        }
        return l == null ? r : l;
    }

    /*  Assumptions: K-nary tree; no parent pointer; guaranteed to be in the tree.
        I define the semantic of the method is returning the LCA of {a, b} that is/are under root.
        case 1. Neither of them are under root, return null;
        case 2. One of the them is under root, return the node;
        case 3. Both of them are under root,
            case 3.1. one is two's ancestor, return one
            case 3.2. two is one's ancestor, return two
            case 3.3. otherwise, return the lowest node with one and two in its different subtree.
    * */
    public KnaryTreeNode lowestCommonAncestorV(KnaryTreeNode root, KnaryTreeNode a, KnaryTreeNode b) {
        // base case
        if (root == null || root == a || root == b) {
            return root;
        }
        KnaryTreeNode found = null;
        for (KnaryTreeNode child : root.children) {
            KnaryTreeNode node = lowestCommonAncestorV(child, a, b);
            if (node == null) {
                continue;
            }
            if (found == null) {
                // find one of them
                found = node;
            } else {
                // find both of them
                return root;
            }
        }
        return found;
    }

    /*  Assumptions: K-nary tree; M nodes; no parent pointer; guaranteed to be in the tree.
        I define the semantic of the method is returning the LCA of {nodes} that is/are under root.
        case 1. Neither of them are under root, return null;
        case 2. One of the them is under root, return the node;
        case 3. Two or more of them are under root, return their LCA;
    * */
    public KnaryTreeNode lowestCommonAncestorVI(KnaryTreeNode root, List<KnaryTreeNode> nodes) {
        Set<KnaryTreeNode> set = new HashSet<>(nodes);
        return lowestCommonAncestorVI(root, set);
    }
    private KnaryTreeNode lowestCommonAncestorVI(KnaryTreeNode root, Set<KnaryTreeNode> set) {
        // base case
        if (root == null || set.contains(root)) {
            return root;
        }
        // recursive rule
        KnaryTreeNode found = null;
        for (KnaryTreeNode child : root.children) {
            KnaryTreeNode node = lowestCommonAncestorVI(child, set);
            if (node == null) {
                continue;
            }
            if (found == null) {
                found = node;
            } else {
                return root;
            }
        }
        return found;
    }

    /*  Compare each pair of elements, move larger number to left half, smaller number to right half.
        Then traverse left half to get the largest, traverse right half to get the smallest.
        case 1. if length is odd:
                0   12
                [0, n / 2 - 1] left
                [n / 2, len - 1] right
        case 2. if length if even:
                01  23
                [0, n / 2 - 1] left
                [n / 2, len - 1] right
    * */
    public int[] largestAndSmallest(int[] array) {
        int n = array.length;
        if (n == 1) {
            return new int[]{array[0], array[0]};
        }
        for (int i = 0; i < n / 2; i++) {
            if (array[i] < array[n - 1 - i]) {
                swap(array, i, n - 1 - i);
            }
        }
        return new int[]{largest(array, 0, n / 2), smallest(array, n / 2, n - 1)};
    }
    private void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
    private int largest(int[] array, int l, int r) {
        int largest = array[l];
        for (int i = l + 1; i <= r; i++) {
            largest = Math.max(largest, array[i]);
        }
        return largest;
    }
    private int smallest(int[] array, int l, int r) {
        int smallest = array[l];
        for (int i = l + 1; i <= r; i++) {
            smallest = Math.min(smallest, array[i]);
        }
        return smallest;
    }

    public int[] largestAndSecond(int[] array) {
        return largestAndSecondHelper(array, 0, array.length - 1);
    }
    private int[] largestAndSecondHelper(int[] array, int left, int right) {
        // base case
        if (left == right) {
            return new int[]{array[left], Integer.MIN_VALUE};
        }
        // recursive rule
        int mid = left + (right - left) / 2;
        int leftMax = largestAndSecondHelper(array, left, mid)[0];
        int leftSecond = largestAndSecondHelper(array, left, mid)[1];
        int rightMax = largestAndSecondHelper(array, mid + 1, right)[0];
        int rightSecond = largestAndSecondHelper(array, mid + 1, right)[1];
        int max = Math.max(leftMax, rightMax);
        // 1 (2 3)? 4
        int second = Math.max(Math.max(leftSecond, rightSecond), Math.min(leftMax, rightMax));
        return new int[]{max, second};
    }

    /*  Sliding window
        Data structure：
            queue
            poll/peek[.....]offer
            if queue.size() < k: add new elements into it;
            if queue.size() >= k:
                if new element is closer to target than queue.peek:
                    queue.poll, add new element into queue
                if not, we do nothing.
    * */
    public int[] closestKValues(TreeNode root, double target, int k) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        closestValuesHelper(root, queue, target, k);
        int[] result = new int[queue.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = queue.poll().key;
        }
        return result;
    }
    /*  We define the semantic of the helper method is that traversing every node under root, find max(k, number of nodes) nodes whose numbers are closest to target.
    * */
    private void closestValuesHelper(TreeNode root, Queue<TreeNode> queue, double target, int k) {
        if (root == null) {
            return;
        }
        // in-order traversal
        // left subtree
        closestValuesHelper(root.left, queue, target, k);
        // check if root is a valid node
        if (queue.size() < k) {
            queue.offer(root);
        } else if (Math.abs(root.key - target) < Math.abs(queue.peek().key - target)) {
            queue.poll();
            queue.offer(root);
        }
        // right subtree
        closestValuesHelper(root.right, queue, target, k);
    }


}
