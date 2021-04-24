import java.util.*;

public class Class17CrossTraining {

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

    /*  Assumption: no parent pointer; guaranteed to be in the BST.
     * */
    public TreeNode lcaInBST(TreeNode root, int p, int q) {
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
    public TreeNodeP lowestCommonAncestor(TreeNodeP one, TreeNodeP two) {
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


}
