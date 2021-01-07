//662. Word Ladder II
//        input-> String start, String end, List<String> dict
//        output -> List<List<String>> ladders
//        similar assumptions with Word Ladder I.

import java.util.*;

public class WordLadderII {
    static class NeighborFinder {
        private List<String> dict;
        private Map<String, Integer> wordIndex = new HashMap<>();

        public NeighborFinder(List<String> dict) {
            this.dict = dict;
            for (int i = 0; i < dict.size(); i++) {
                wordIndex.put(dict.get(i), i);
            }
        }

        public List<Integer> findNeighbors(int index) {
            List<Integer> neighbors = new ArrayList<>();
            StringBuilder sb = new StringBuilder(dict.get(index));


            for (int i = 0; i < sb.length(); i++) {
                char orig = sb.charAt(i);
                for (char c = 'a'; c <= 'z'; c++) {
                    sb.setCharAt(i, c);
                    int neighbor = wordIndex.getOrDefault(sb.toString(), -1);
                    if (neighbor != -1) {
                        neighbors.add(neighbor);
                    }
                    sb.setCharAt(i, orig);
                }
            }
            return neighbors;
        }
    }
    static class Tracer {
        private List<String> dict;
        private List<List<Integer>> preds;

        public Tracer(List<String> dict) {
            this.dict = dict;

            preds = new ArrayList<>();
            for (int i = 0; i < dict.size(); i++) {
                preds.add(new ArrayList<>());
            }
        }

        public void addPredecessor(int x, int y) {
            preds.get(x).add(y);
        }

        public List<List<String>> getLadders(int start, int end) {
            List<List<String>> ladders = new ArrayList<>();
            List<String> trace = new ArrayList<>();

            trace.add(dict.get(end));
            getLaddersHelper(start, end, trace, ladders);
            return ladders;
        }

        private void getLaddersHelper(int start, int end, List<String> trace, List<List<String>> ladders) {
            if (end == start) {
                List<String> ladder = new ArrayList<>(trace);
                Collections.reverse(ladder);
                ladders.add(ladder);
                return;
            }
            for (int pred : preds.get(end)) {
                trace.add(dict.get(pred));
                getLaddersHelper(start, pred, trace, ladders);
                trace.remove(trace.size() - 1);
            }
        }
    }

    public List<List<String>> findLadders(String start, String end, List<String> words) {
        // corner cases

        int endIdx = words.indexOf(end);
        if (endIdx == -1) {
            return new ArrayList<>();
        }

        List<String> dict;
        int startIdx = words.indexOf(start);
        if (startIdx == -1) {
            // in case words is immutable.
// (get from Arrays.asList())
            dict = new ArrayList<>(words);
            dict.add(start);
            startIdx = dict.size() - 1;
        } else {
            dict = words;
        }

        int[] steps = new int[dict.size()];
        Arrays.fill(steps, -1);

        NeighborFinder finder = new NeighborFinder(dict);
        Tracer tracer = new Tracer(dict);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(startIdx);
        steps[startIdx] = 1;

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if (curr == endIdx) {
                return tracer.getLadders(startIdx, curr);
            }
            for (int neighbor : finder.findNeighbors(curr)) {
                if (steps[neighbor] == -1) {
                    queue.offer(neighbor);
                    steps[neighbor] = steps[curr] + 1;
                }
                if (steps[neighbor] == steps[curr] + 1) {
                    tracer.addPredecessor(neighbor, curr);
                }
            }
        }
        return new ArrayList<>();
    }
}
