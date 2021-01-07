import java.util.*;
//194 Kth Closest Point To Origin
//        input -> three int[] a, b, c, not null, not empty, only 0 or positive; int k, 1 <= k <=  a.len * b.len * c.len
//        output -> List<Integer> coordinates of the point
//        data structure:
//        We will need a min heap, with a comparator to compare distance.
//        Point {
//        int x; //we are using index in a as value of x;
//        int y; // index in b
//        int z; // index in c
//        double distance; // to simplify the problem, not real distance, it’s distance square
//public Point(int x, int y, int z) {
//        this.x = x;
//        this.y = y;
//        this.z = z;
//        this.distance = x * x + y * y + z * z;
//        }
//        }

public class KthClosestPointToOrigin {
    public List<Integer> closest(int[] a, int[] b, int[] c, int k) {
        PriorityQueue<Point> minHeap = new PriorityQueue<>(2 * k, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                // to simplify the problem, not real distance, it’s distance square
                double distance1 = a[p1.x] * a[p1.x] + b[p1.y] * b[p1.y] + c[p1.z] * c[p1.z];
                double distance2 = a[p2.x] * a[p2.x] + b[p2.y] * b[p2.y] + c[p2.z] * c[p2.z];
                if (distance1 == distance2) {
                    return 0;
                }
                return distance1 < distance2 ? - 1 : 1;
            }
        });
        // Set<String> visited = new HashSet<>(); // avoid overriding equals() and hashCode() of Point
        Set<Point> visited = new HashSet<>();
        // cur is the smallest element
        Point cur = new Point(0, 0, 0);
        minHeap.offer(cur);
        visited.add(cur);
        while (k > 0) {
            cur = minHeap.poll();
            Point next = new Point(cur.x + 1, cur.y, cur.z);
            if (next.x < a.length && visited.add(next)) {
                minHeap.offer(next);
            }
            next = new Point(cur.x, cur.y + 1, cur.z);
            if (next.y < b.length && visited.add(next)) {
                minHeap.offer(next);
            }
            next = new Point(cur.x, cur.y, cur.z + 1);
            if (next.z < c.length && visited.add(next)) {
                minHeap.offer(next);
            }
            k--;
        }
        return Arrays.asList(a[cur.x], b[cur.y], c[cur.z]);
    }

    static class Point {
        int x; //we are using index in a as value of x;
        int y; // same with x
        int z; // same with x
	    public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object obj) {
	        if (obj == this) {
	            return true;
            }
	        if (!(obj instanceof Point)) {
	            return false;
            }
	        Point another = (Point)obj;
            return this.x == another.x && this.y == another.y &&
                    this.z == another.z;
        }

        @Override
        public int hashCode() {
            return x * 31 * 31 + y * 31 + z;
        }
    }
}
