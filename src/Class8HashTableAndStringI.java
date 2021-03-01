import java.sql.Time;
import java.util.*;

public class Class8HashTableAndStringI {
    public static String[] topKFrequent(String[] combo, int k) {
        // corner cases
        if (combo.length == 0) {
            return new String[0];
        }

        Map<String, Integer> map = new HashMap<>();
        for (String s : combo) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                return e1.getValue().compareTo(e2.getValue());
            }
        });
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (minHeap.size() < k) {
                minHeap.offer(entry);
            } else if (entry.getValue() > minHeap.peek().getValue()) {
                minHeap.poll();
                minHeap.offer(entry);
            }
        }
        String[] result = new String[minHeap.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = minHeap.poll().getKey();
        }
        return result;
    }

    public static int missing(int[] array) {
        int n = array.length + 1;
        int xor = 0;
        for (int i = 1; i <= n; i++) {
            xor ^= i;
        }
        for (int num : array) {
            xor ^= num;
        }
        return xor;
    }

//    Assumption: a and b are ascending sorted; duplicates; fits in memory.
//            Time: O(m+n) where m is the length of a and n is length of b.
//    Space: O(1).
public static List<Integer> commonSolution1(int[] a, int[] b) {
    List<Integer> common = new ArrayList<>();
    int i = 0;
    int j = 0;
    while (i < a.length && j < b.length) {
//        case 1. a[i] == b[j]: add a[i] into list, i++, j++;
//        case 2. a[i] < b[j]: i++;
//        case 3. a[i] > b[j]: j++;
            if (a[i] < b[j]) {
                i++;
            } else if (a[i] > b[j]) {
                j++;
            } else {
                common.add(a[i]);
                i++;
                j++;
            }
    }
    return common;
}
//    Assumption: a and b are ascending sorted; duplicates; fits in memory.
//    m is a’s length, n is b’s length;
//    Time:
//            for a: O(m) in average, O(m^2) in worst case;
//            for b: O(n) in a, O(n*m) in w;
//            total: O(m + n) in a, O(m^2 + n*m) in w;
//            if we are sure that m < n,
//            total: O(n) in a, O(n*m);
//    Space: O(m);
    public static List<Integer> commonSolution2(int[] a, int[] b) {
        List<Integer> common = new ArrayList<>();
        Map<Integer, Integer> freqA = new HashMap<>();
        // store elements and their frequencies of array a
        for (int num : a) {
            freqA.put(num, freqA.getOrDefault(num, 0) + 1);
        }
        // traverse array b
        for (int num : b) {
            Integer freq = freqA.get(num);
            if (freq != null && freq != 0) {
                common.add(num);
                freqA.put(num, freq - 1);
            }
        }
        return common;
    }
//    Assumption: a and b are ascending sorted; NO duplicates; fits in memory.
//            Time: O(mlogn) where m is the length of the shorter array between a and b, n is the length of the longer one.
//    Space: O(1)
    public static List<Integer> commonSolution3(int[] a, int[] b) {
        List<Integer> common = new ArrayList<>();
        if (a.length == 0 || b.length == 0) {
            return common;
        }
        int[] targets = a.length <= b.length ? a : b;
        int[] candidates = a.length <= b.length ? b : a;
        for (int num : targets) {
            int index = Class1BinarySearch.classicalBinarySearch(candidates, num);
            if (index != -1) {
                common.add(num);
            }
        }
        return common;
    }


}
