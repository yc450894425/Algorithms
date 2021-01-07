package binarytree;
import java.util.*;

class TreeNode<T extends Comparable<?>> {
    T key;
    int counter;
    TreeNode<T> left;
    TreeNode<T> right;
    TreeNode<T> parent;

    public TreeNode(T x) {
        this.key = x;
    }

    public TreeNode<T> deserializeFromList(List<T> arr) {
        if (arr == null || arr.size() == 0) {
            return null;
        }

        TreeNode<T> root = new TreeNode<>(arr.get(0));
        Queue<TreeNode<T>> queue = new ArrayDeque<>();
        queue.offer(root);

        int i = 1;
        while (i < arr.size()) {
            TreeNode<T> curNode = queue.poll();
            
            // find left child
            if (arr.get(i) != null) {
                curNode.left = new TreeNode<T>(arr.get(i));
                queue.offer(curNode.left);
             }
            i++; // move to the next element

             // find right child
            if (arr.get(i) != null) {
                curNode.right = new TreeNode<T>(arr.get(i));
                queue.offer(curNode.right);     
            }
            i++; // move to next round
        }
        return root;
    }
}