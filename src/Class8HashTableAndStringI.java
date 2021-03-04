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

    //    transfer input string to char array, and string t to a set.
//    two pointers:
//        slow: left (excluding itself) are processed letters that should be kept in result string;
//        fast: left (excluding itself) are processed;
//    Initialize:
//        slow = 0;
//        fast = 0;
//    For each step:
//        array[fast] is in set, fast++;
//        array[fast] is not in set, array[slow++] = array[fast++];
//    terminate: fast == input’s length
//    post-processing: convert char array[0, slow - 1] into string.
//    time:
//        O(n) in a, where n is input length;
//        O(n*m) in w, m is length of t;
//    space:
//        O(m), m is length of t;
    public static String remove(String input, String t) {
        Set<Character> set = toSet(t);
        char[] array = input.toCharArray();
        int slow = 0;
        for (int fast = 0; fast < array.length; fast++) {
            if (!set.contains(array[fast])) {
                array[slow++] = array[fast];
            }
        }
        return new String(array, 0, slow);
    }
    private static Set<Character> toSet(String input) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < input.length(); i++) {
            set.add(input.charAt(i));
        }
        return set;
    }

    public static String removeSpaces(String input) {
        char[] array = input.toCharArray();
        int slow = 0;
        for (int fast = 0; fast < array.length; fast++) {
//            1. we ignore all spaces followed by another space.
//            2. we ignore all leading spaces.
            if (array[fast] == ' ' && (fast == 0 || array[fast - 1] == ' ')) {
                continue;
            }
            array[slow++] = array[fast];
        }
        // post-processing
        if (slow > 0 && array[slow - 1] == ' ') {
            slow--;
        }
        return new String(array, 0, slow);
    }

    //    [0, s) are letters that should be kept in result;
//    [s, f) are letters we don't care;
//    [f, input's length) are unprocessed letters;
//    for each step:
//        case 1. s == 0:
//            array[s++] = array[f++];
//	    case 2. s != 0 && array[f] == array[s - 1]
//            f++;
//	    case 3. s != 0 && array[f] != array[s - 1]
//            copy array[f++] to array[s++];
//    terminate: f == input's length
    public static String deDup(String input) {
        char[] array = input.toCharArray();
        int slow = 0;
        for (int fast = 0; fast < array.length; fast++) {
            if (slow == 0 || array[fast] != array[slow - 1]) {
                array[slow++] = array[fast];
            }
        }
        return new String(array, 0, slow);
    }

    //    convert input from String to char array
//    fast = 0; left side (excluding itself) are processed letters;
//    slow = 0; left side (excluding itself) are letters should kept in result;
//    for each step:
//        case1. slow == 0: copy f to s, s++, f++;
//        case2. array[f] == array[s-1]:
//            move f forward untill array[f] != array[s-1]
//            s--;
//        case3. array[f] != array[s-1]:
//            copy f to s, s++, f++
//    terminate: fast == length of input
//    return new String(array, 0, slow);
    public static String repeatedlyDeDup(String input) {
        // corner cases
        if (input == null || input.length() <= 1) {
            return input;
        }
        char[] array = input.toCharArray();
        int f = 0;
        int s = 0;
        while (f < array.length) {
            if (s != 0 && array[f] == array[s - 1]) {
                while (f < array.length && array[f] == array[s - 1]) {
                    f++;
                }
                s--;
            } else {
                array[s++] = array[f++];
            }
        }
        return new String(array, 0, s);
    }

    //    Rabin-Karp
//    Range of the starting index: [0, large.length - small.length];
//                    m              n
//    We have (large.length - small.length + 1) sliding windows;
//    if hash codes are equal, we must compare two strings for confirmation. O(n)
//    time:
//        hash small: O(n)
//        sliding window:
//            O(m) in average because every elements will be used once to calculate hash code;
//            O(mn) in worst case because we must compare m times in worst case;
//        total: O(m + n) in average, O(mn) in worst case
    public static int strstr(String large, String small) {
        int m = large.length();
        int n = small.length();
        // corner cases
        if (n > m) {
            return -1;
        }
        if (n == 0) {
            return 0;
        }
        int smallCode = hashCode(small, 0, n - 1);
        int largeCode = 0;
        for (int i = 0; i <= m - n; i++) {
            largeCode = i == 0 ? hashCode(large, 0, n - 1) : (largeCode - (large.charAt(i - 1) - 'a') * (int)Math.pow(26, n - 1)) * 26 + (large.charAt(i + n - 1) - 'a');

            if (largeCode == smallCode && isIdentical(large, i, small)) {
                return i;
            }
        }
        return -1;

    }
    private static int hashCode(String input, int s, int e) {
        int code = 0;
        while (s <= e) {
            code = code * 26 + (input.charAt(s++) - 'a');
        }
        return code;
    }
    private static boolean isIdentical(String large, int index, String small) {
        for (int i = 0; i < small.length(); i++) {
            if (small.charAt(i) != large.charAt(i + index)) {
                return false;
            }
        }
        return true;
    }

    public static int strstrRabinKarpBetterImplementation(String large, String small) {
        // corner cases
        int m = large.length();
        int n = small.length();
        if (m < n) {
            return -1;
        }
        if (n == 0) {
            return 0;
        }
        int largePrime = 101;
        int prime = 31;

        int seed = 1;
        int targetHash = small.charAt(0) % largePrime;
        for (int i = 1; i < n; i++) {
            seed = hashModule(seed, 0, prime, largePrime);
            targetHash = hashModule(targetHash, small.charAt(i), prime, largePrime);
        }

        int hash = 0;
        for (int i = 0; i < n; i++) {
            hash = hashModule(hash, large.charAt(i), prime, largePrime);
        }
        if (targetHash == hash && isIdentical(large, 0, small)) {
            return 0;
        }

        for (int i = 1; i <= m - n; i++) {
            hash = negativeModify(hash - seed * large.charAt(i - 1) % largePrime, largePrime);
            hash = hashModule(hash, large.charAt(i + n - 1), prime, largePrime);

            if (targetHash == hash && isIdentical(large, i, small)) {
                return i;
            }
        }
        return -1;
    }
    private static int hashModule(int hash, int addition, int prime, int largePrime) {
        return (hash * prime % largePrime + addition) % largePrime;
    }
    private static int negativeModify(int hash, int largePrime) {
        if (hash < 0) {
            hash += largePrime;
        }
        return hash;
    }

}
