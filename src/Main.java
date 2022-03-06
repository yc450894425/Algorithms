import java.util.*;

public class Main {

    /*  Problem 1
        Data structure:
            class News,
            PriorityQueue<News> maxHeap, to rank news from top to low,
            Map<id, News> appended, to get news by id;
    * */
    private static final PriorityQueue<News> maxHeap = new PriorityQueue<>(new Comparator<News>() {
        @Override
        public int compare(News o1, News o2) {
            if (o1.hotness == o2.hotness) {
                return o1.id < o2.id ? -1 : 1;
            }
            return o1.hotness > o2.hotness ? -1 : 1;
        }
    });
    private static final Map<Integer, News> appended = new HashMap<>();
    private static void query() {
        List<News> result = new ArrayList<>();
        for (int i = 0; i < 10 && !maxHeap.isEmpty(); i++) {
            News curr = maxHeap.poll();
            result.add(curr);
            System.out.print(curr.id);
            if (i != 9 && !maxHeap.isEmpty()) {
                System.out.print(" ");
            }
        }
        if (result.size() == 0) {
            System.out.print("null");
        }
        for (int i = 0; i < result.size(); i++) {
            maxHeap.offer(result.get(i));
        }
        System.out.println();
    }
    private static void append(int id, int hotnessUp) {
        News news = appended.get(id);
        if (news == null) {
            News curr = new News(id, hotnessUp);
            appended.put(id, curr);
            maxHeap.offer(curr);
        } else {
            maxHeap.remove(news);
            news.hotness += hotnessUp;
            maxHeap.offer(news);
        }
    }
    static class News{
        int id;
        int hotness;
        public News(int id, int hotness) {
            this.id = id;
            this.hotness = hotness;
        }
    }

    /*  Problem 2
        Maintain two integer, int A, int B, as their strengths;
        Define the semantic of method "int getSum(int num, int val, int min)" as returning the sum of all values that are >= min.
        Finally, compare A and B
    * */
    private static long getSum(long num, long val, int min) {
        return val >= min ? num * val : 0;
    }

    /*  Problem 3
        Maintain a Map<id, Node> to store nodes;
        Maintain a min and a max to record the min and max values of nodes' keys.
        set num as [min, max], traverse the tree.
    * */
    private static final Map<Integer, Node> nodes = new HashMap<>();
    private static int visitedNum = 0;
    private static void traverse(Node root, long num) {
        while (root != null) {
            if (!root.visited) {
                root.visited = true;
                visitedNum++;
            }
            if (num >= root.key) {
                root = root.right;
            } else {
                root = root.left;
            }
        }
    }
    private static void linkNodes(int p, int l, int r) {
        Node parent = nodes.get(p);
        Node left = nodes.get(l);
        Node right = nodes.get(r);
        parent.left = left;
        parent.right = right;
    }
    static class Node {
        long key;
        Node left;
        Node right;
        boolean visited;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int trainsNum = scanner.nextInt();
        for (int i = 0; i < trainsNum; i++) {
            int opera = scanner.nextInt();
            if (opera == 1) {
                append(scanner.nextInt(), scanner.next());
            } else {
                swap(scanner.next(), scanner.next());
            }
        }
        printTrains(head);
    }
    /*  Problem 4
        Maintain a class Train:
            String id;
            Train prev;
            Train next;
            List<Integer> rooms;
         Maintain a head and a tail;

         void append(int roomId, String trainId)
         void swap(String one, String two)
    * */
    private static final Map<String, Train> trains = new HashMap<>();
    private static Train head = null;
    private static Train tail = null;
    static class Train {
        Train next;
        List<Integer> rooms;
        public Train() {
            rooms = new ArrayList<>();
        }
    }
    private static void append(int roomId, String trainId) {
        Train train = trains.get(trainId);
        if (train == null) {
            train = new Train();
            trains.put(trainId, train);
            if (head == null && tail == null) {
                head = train;
                tail =train;
            } else {
                tail.next = train;
                tail = train;
            }
        }
        train.rooms.add(roomId);
    }
    /*  dummy <> A <> B <> C > null
        swap two nodes:
            swap all fields of the two nodes
    * */
    private static void swap(String a, String b) {
        Train one = trains.get(a);
        Train two = trains.get(b);
        List<Integer> tmp = one.rooms;
        one.rooms = two.rooms;
        two.rooms = tmp;
        trains.put(a, two);
        trains.put(b, one);
    }
    private static void printTrains(Train head) {
        while (head != null) {
            for (int i = 0; i < head.rooms.size(); i++) {
                System.out.print(head.rooms.get(i));
                if (head.next != null || i != head.rooms.size() - 1) {
                    System.out.print(" ");
                }
            }
            head = head.next;
        }
    }
}
