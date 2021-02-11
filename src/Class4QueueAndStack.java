import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Class4QueueAndStack {
    //    C:	input => stack s1
//    output => void
//    A: 	fit in memory, not null;
//    Ascending? yes
//    Duplicates? yes
//    R:
//    like selection sort. We move all elements in s1 to s2, and record the smallest element as global min, then we move all elements except global min back to s1, and leave global min in s2. Then we repeat this operation until s1 is empty.
//
//    Time Complexity: O(n + n-1 + n-2 + ... + 2 + 1) = O(n^2)
//    Auxiliary Space Complexity: O(1)
    public void SortWith2Stacks(LinkedList<Integer> s1) {
        Deque<Integer> s2 = new LinkedList<>();
        while (!s1.isEmpty()) {

            // Initialize globalMin and count.
            int globalMin = Integer.MAX_VALUE;
            int count = 0;

            // Move elements from s1 to s2, update globalMin and count.
            while (!s1.isEmpty()) {
                int cur = s1.pollFirst();
                if (cur < globalMin) {
                    globalMin = cur;
                    count = 1;
                } else if (cur == globalMin) {
                    count++;
                }
                s2.offerFirst(cur);
            }

            // Move elements except globalMin back to s1.
            while (!s2.isEmpty() && s2.peekFirst() >= globalMin) {
                int cur = s2.pollFirst();
                if (cur > globalMin) {
                    s1.offerFirst(cur);
                }
            }

            // Add globalMin to s2.
            while (count-- > 0) {
                s2.offerFirst(globalMin);
            }
        }
        // Move all elements to s1
        while (!s2.isEmpty()) {
            s1.offerFirst(s2.pollFirst());
        }
    }

    public static class QueueByTwoStacks {

        Deque<Integer> in;
        Deque<Integer> out;

        public QueueByTwoStacks() {
            in = new ArrayDeque<>();
            out = new ArrayDeque<>();
        }

        public void offer(int element) {
            in.offerFirst(element);
        }

        public Integer peek() {
            if (out.isEmpty()) {
                move();
            }
            return out.peekFirst();
        }

        public Integer poll() {
            if (out.isEmpty()) {
                move();
            }
            return out.pollFirst();
        }

        public int size() {
            return in.size() + out.size();
        }

        public boolean isEmpty() {
            return in.isEmpty() && out.isEmpty();
        }

        private void move() {
            while (!in.isEmpty()) {
                out.offerFirst(in.pollFirst());
            }
        }
    }

    public static class StackWithMin {

        private Deque<Integer> stack;
        private Deque<Pair> minStack;

        public StackWithMin() {
            stack = new ArrayDeque<>();
            minStack = new ArrayDeque<>();
        }

        public int pop() {
            if (stack.isEmpty()) {
                return -1;
            }
            if (stack.size() == minStack.peekFirst().size) {
                minStack.pollFirst();
            }
            return stack.pollFirst();
        }

        public void push(int element) {

            stack.offerFirst(element);

            if (minStack.isEmpty() || element < minStack.peek().minVal) {
                minStack.offerFirst(new Pair(element, stack.size()));
            }
        }

        public int top() {
            if (stack.isEmpty()) {
                return -1;
            }
            return stack.peekFirst();
        }

        public int min() {
            if (minStack.isEmpty()) {
                return -1;
            }
            return minStack.peekFirst().minVal;
        }

        static class Pair {
            public Integer minVal;
            public Integer size;

            public Pair(int minVal, int size) {
                this.minVal = minVal;
                this.size = size;
            }
        }
    }

    public static class StackByQueue {
        //    We use a stack to store elements and another minStack to store minValue and the size of stack after the minValue was pushed into stack as a pair <minVal, size>.u788888888888888888888888888888887u66u87hhh6\12\`=-08\\`````````````12112\\`=-0\`=-public static class StackByQueue {
        private Queue<Integer> queue;

        public StackByQueue() {
            queue = new ArrayDeque<>();
        }

        public void push(int x) {
            queue.offer(x);
        }

        public Integer pop() {
            if (queue.isEmpty()) {
                return null;
            }

            int size = queue.size();
            while (--size != 0) {
                queue.offer(queue.poll());
            }
            return queue.poll();
        }

        public Integer top() {
            if (queue.isEmpty()) {
                return null;
            }

            int size = queue.size();
            while (--size != 0) {
                queue.offer(queue.poll());
            }

            Integer result = queue.poll();
            queue.offer(result);
            return result;
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static class DequeByThreeStacks {

        // left is first, right is last;
        Deque<Integer> left;
        Deque<Integer> right;
        Deque<Integer> buffer;

        public DequeByThreeStacks() {
            left = new ArrayDeque<>();
            right = new ArrayDeque<>();
            buffer = new ArrayDeque<>();
        }

        public void offerFirst(int element) {
            left.offerFirst(element);
        }

        public void offerLast(int element) {
            right.offerFirst(element);
        }

        public Integer peekFirst() {
            move(right, left);
            return left.isEmpty() ? null : left.peekFirst();
        }

        public Integer peekLast() {
            move(left, right);
            return right.isEmpty() ? null : left.peekFirst();
        }

        public Integer pollFirst() {
            move(right, left);
            return left.isEmpty() ? null : left.pollFirst();
        }

        public Integer pollLast() {
            move(left, right);
            return right.isEmpty() ? null : right.pollFirst();
        }

        public boolean isEmpty() {
            return left.isEmpty() && right.isEmpty();
        }

        public int size() {
            return left.size() + right.size();
        }

        private void move(Deque<Integer> src, Deque<Integer> dest) {
            if (!dest.isEmpty()) {
                return;
            }

            int halfSize = src.size() / 2;

            for (int i = 0; i < halfSize; i++) {
                buffer.offerFirst(src.pollFirst());
            }

            while (!src.isEmpty()) {
                dest.offerFirst(src.pollFirst());
            }

            while (!buffer.isEmpty()) {
                src.offerFirst(buffer.pollFirst());
            }
        }
    }


}
