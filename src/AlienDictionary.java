
//501. Alien Dictionary
//        input -> String[] words
//        output -> String order
//        assumptions:

import java.util.*;

public class AlienDictionary {
    public String alienOrder(String[] words) {
        StringBuilder order = new StringBuilder();
        // corner cases
        Map<Character, Set<Character>> map = toMap(words);

        // if order is valid, we assign map null.
        if (map == null) {
            return "";
        }
        // map.get(a) is {c, e, o} means a is prior to c, e, o.

        int numChars = map.size();
        int[] incomingEdges = new int[26];
        for (int i = 0; i < incomingEdges.length; i++) {
            if (map.containsKey((char)('a' + i))) {
                incomingEdges[i] = 0;
            } else {
                incomingEdges[i] = -1;
            }
        }
        for (Map.Entry<Character, Set<Character>> entry : map.entrySet()) {
            for (char c : entry.getValue()) {
                if (incomingEdges[c - 'a'] != -1) {
                    incomingEdges[c - 'a']++;
                }
            }
        }

        Queue<Character> queue = new ArrayDeque<>();
        for (int i = 0; i < incomingEdges.length; i++) {
            if (incomingEdges[i] == 0) {
                queue.offer((char)('a' + i));
            }
        }

        while (!queue.isEmpty()) {
            char curr = queue.poll();
            order.append(curr);
            for (char c : map.get(curr)) {
                if (--incomingEdges[c - 'a'] == 0) {
                    queue.offer(c);
                }
            }
        }

        return order.length() == numChars ? order.toString() : "";
    }

    private Map<Character, Set<Character>> toMap(String[] words) {
        Map<Character, Set<Character>> map = new HashMap<>();
        if (words.length <= 1) {
            return map;
        }
        for (int i = 1; i < words.length; i++) {
            String curr = words[i];
            String prev = words[i - 1];
            int len = Math.min(curr.length(), prev.length());
            for (int j = 0; j < len; j++) {
                if (curr.charAt(j) == prev.charAt(j)) {
                    continue;
                }
                if (map.get(curr) != null && map.get(curr).contains(prev)) {
                    return null;
                }
                Set<Character> prevSet = map.getOrDefault(prev.charAt(j), new HashSet<>());
                prevSet.add(curr.charAt(j));
                map.put(prev.charAt(j), prevSet);
                if (map.get(curr.charAt(j)) == null) {
                    map.put(curr.charAt(j), new HashSet<>());
                }
                break;
            }
        }
        return map;
    }
}
