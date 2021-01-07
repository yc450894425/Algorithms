//198. largest rectangle in histogram

import java.util.ArrayDeque;
import java.util.Deque;
//input -> int[] array, array[i] represents the height of column at index i.
//        output -> int result, the area of largest rectangle
//        input not null, not empty, fit in memory
//
//        iterative way
//        stack: stores all the indices of columns that form an ascending order.
//
//
//        initialize: Deque<Integer> stack = new ArrayDeque<>();
//
//        for each step:
//        we traverse the array and insert every index of columns into stack;
//        every time we want to insert a new column, we pop out all columns which is higher than the new column and update the largest area if needed.
//        Then insert the new column.
//
//        int cur = array[i];
//        while (!stack.isEmpty() && array[stack.peekFirst()] > cur) {
//        int height = array[stack.pollFirst()];
//        int left = stack.isEmpty() ? 0 : stack.peekFirst() + 1;
//        globalMax = Math.max(globalMax, height * (i - left));
//        }
//        stack.offerFirst(i);
//        termination: if i == array.length
//        post-process: we need to pop out all columns from stack.
//        if i == array.length, we should enter the for loop and set cur = 0;
//
//        time: O(n) because every column can only be inserted and popped out of the stack once and only once;
//        space: O(n) because the max possible size of stack is n.
//
//        keypoint:
//        stack   bottom[...i, j...
//        if j is right at the top of i in stack (they are adjacent), then we can say all columns in (i, j) are higher than array[j] ⇔ The first lower column at the left side of array[j] is array[i] ⇔ The left bound for height array[j] must be i + 1;
//
//        We can prove it by contradiction. If there is a column, whose index k satisfies i < k < j, and array[k] < array[j].Then index k must be in the stack and between i and j. Then i and j are not adjacent, which is contrary with our proposition. So the assumption is false. There is no such a column whose index is in (i, j) and its height is smaller than array[j].
//
//        Particularly, if i is the last element in stack, i must be 0 or array[i] must be smaller than array[0].Then its left bound must be 0.


public class LargestRectangle {
    public int largest(int[] array) {
        int max = Integer.MIN_VALUE;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i <= array.length; i++) {
            int curHeight = i == array.length ? 0 : array[i];
            while (!stack.isEmpty() && array[stack.peekFirst()] > curHeight) {
                int height = array[stack.pollFirst()];
                int left = stack.isEmpty() ? 0 : stack.peekFirst() + 1;
                int right = i;
                max = Math.max(max, height * (right - left));
            }
            stack.offerFirst(i);
        }
        return max;
    }
}

