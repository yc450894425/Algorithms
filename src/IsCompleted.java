
//47.
//        input -> TreeNode root, not null
//        output -> boolean
//        breadth first search
//        data structure:
//        queue -> store nodes
//        boolean flag is true if we have seen a bubble (null node)
//        initialize: Queue<TreeNode> queue = new LinkedList<>();
//        For each step:
//        get the size of queue -> s
//        expand a node from queue
//        if it’s not null and flag is true -> not completed
//        if it’s null -> flag = true
//        else -> put its children into queue if it has children
//        repeat operations above size times
//        Termination condition: queue is empty
//        if this tree has n nodes
//        tc: O(n)
//        sc: size of queue
//        In the worst case, the size of the queue is the number of nodes in the bottom level.
//        1 + 2 + 4 + 8 +...+ #nodes of bottom = n
//        #nodes of bottom = 2^(logn) -> O(n)
//        space complexity is O(n) in worst case


import java.util.LinkedList;
import java.util.Queue;

public class IsCompleted {
    public boolean isCompleted(TreeNode root) {
        // corner cases
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean flag = false;
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if (curr != null && flag) {
                return false;
            } else if (curr == null) {
                flag = true;
            } else {
                queue.offer(curr.left);
                queue.offer(curr.right);
            }
        }
        return true;
    }
}
