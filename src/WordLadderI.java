import java.util.*;
//661. Word Ladder
//        input-> String start, String end, List<String> dict
//        output -> int leastStep
//
//        Assumption:
//        not null, not empty;
//        dict size is n, all string length is m, no duplicates;
//        string consists of a - z, 26 letters;
//        start end not empty, not same;
//
//        I believe this problem can be modeled as a graph search problem. In this graph, every node is a string, and every edge is one change of char.
//        # of nodes: n
//        # of edges: 25 * m * n / 2 = 12.5*mn at most;
//        a   b   c   d
//        25  25  25  25
//
//        Breadth First Search
//        Data Structure:
//        Set<String> set
//        Queue<String> queue, to store nodes, size n at most
//        Map<String, Integer> steps, to record steps and de-duplication.
//
//        while (queue not empty) {					o(n)
//        String curr = queue;
//        int step = steps.get(curr);				O(m),		O(mn)hc
//        for (0 to curr.length() - 1) {			O(m)
//        for (a to z) {					O(26)
//
//        check if it’s in the dict set.		O(m),		O(mn)
//        if yes and !steps.containsKey(),	O(m)
//        put s into queue
//        put s, step + 1 into steps
//
//        }
//        }
//        }
//        return step.getOrDefault(end, 0);
//
//        TC: O(n * m^2) in average, O(n * (nm^2 + mn)) = O(n^2 * m^2) in worst case (hash collision).
//        SC: O(n)

public class WordLadderI {
    public int ladderLength(String start, String end, List<String> dict)
    {
        Set<String> set = toSet(dict);
        Queue<String> queue = new ArrayDeque<>();
        Map<String, Integer> steps = new HashMap<>();

        queue.offer(start);
        steps.put(start, 1);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            int step = steps.get(curr);

            StringBuilder sb = new StringBuilder(curr);
            for (int i = 0; i < curr.length(); i++) {

                char orig = sb.charAt(i);
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == orig) {
                        continue;
                    }
                    sb.setCharAt(i, c);
                    String newWord = sb.toString();
                    if (set.contains(newWord) && !steps.containsKey(newWord)) {
                        queue.offer(newWord);
                        steps.put(newWord, step + 1);
                    }
                    sb.setCharAt(i, orig);
                }
            }
        }
        return steps.getOrDefault(end, 0);
    }

    private Set<String> toSet(List<String> list) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            set.add(list.get(i));
        }
        return set;
    }
}
