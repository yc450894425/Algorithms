import java.util.*;
//input -> int[] a (m), int[] b (n)
//        output -> List<Integer> result (ascending)
//        assumptions: ab not null; there are duplicates; fit in memory; ab have same order of magnitude
//        method 1:
//        sort then use two pointers,
//        TC: O(nlogn) + O(n) + O(n) in average = O(nlogn) in average
//        SC: O(logn) in average
//
//        method 2:
//        use a map to store all elements and their frequency for a, then find common numbers in the map for b. Then sort the result.
//        TC: O(n) + O(nlogn) = O(nlogn) in average
//        SC: O(n) + O(logn) = O(n)

public class CommonWithDuplicate {
    public List<Integer> method1(int[] a, int[] b) {
        List<Integer> result = new ArrayList<>();
        //sort a, b
        Arrays.sort(a);
        Arrays.sort(b);
        int i = 0;
        int j = 0;
        while (i < a.length && j < b.length) {
            if (a[i] == b[j]) {
                result.add(a[i]);
                i++;
                j++;
            } else if (a[i] > b[j]) {
                j++;
            } else {
                i++;
            }
        }
        return result;
    }
    public List<Integer> method2(int[] a, int[] b) {
        List<Integer> result = new ArrayList<>();
        HashMap<Integer, Integer> map = toMap(a);
        for (int n : b) {
            if (map.containsKey(n) && map.get(n) > 0) {
                result.add(n);
                map.put(n, map.get(n) - 1);
            }
        }
        Collections.sort(result);
        return result;
    }

    private HashMap<Integer, Integer> toMap(int[] a) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int n : a) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        return map;
    }
}
