import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

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
}
