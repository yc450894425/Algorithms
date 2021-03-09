import java.util.*;

public class Class9StringII {
//    time O(n)
//    space O(1)
    public static String reverse(String input) {
        if (input.length() <= 1) {
            return input;
        }
        char[] array = input.toCharArray();
        for (int i = 0, j = array.length - 1; i < j; i++, j--) {
            swap(array, i++, j--);
        }
        return new String(array);
    }

//    A: no heading or tailing spaces; words separated by SINGLE space;
//    1. reverse the whole input string at first;
//    2. then reverse every word.
    public static String reverseWords(String input) {
        // corner cases
        if (input == null || input.length() <= 1) {
            return input;
        }
        // reverse the whole string
        char[] array = input.toCharArray();
        reverse(array, 0, array.length - 1);
        int start = 0;
        for (int end = 0; end < array.length; end++) {
            // the start index of a word
            if (array[end] != ' ' && (end == 0 || array[end - 1] == ' ')) {
                start = end;
            }
            // the end index of a word
            if (array[end] != ' ' && (end == array.length - 1 || array[end + 1] == ' ')) {
                reverse(array, start, end);
            }
        }
        return new String(array);
    }
    private static void reverse(char[] array, int i, int j) {
        while (i < j) {
            swap(array, i++, j--);
        }
    }
    private static void swap(char[] array, int i, int j) {
        char tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

//    if n >= length, n = n % length;
//    abcde shift 2 => deabc
//1. reverse the whole string => edcba
//2. reverse the first 2(n) letters => de cba
//2. reverse the remaining letters => de abc
    public static String rightShift(String input, int n) {
        if (input == null || input.length() <= 1) {
            return input;
        }
        char[] array = input.toCharArray();
        n = n % array.length;
        reverse(array, 0, array.length - 1);
        reverse(array, 0, n - 1);
        reverse(array, n, array.length - 1);
        return new String(array);
    }
//    two pointers: i, j
//    case1. if result is shorter than (equal to) original array:
//            left of i (excluding i) should be kept in result;
//            left of j (excluding j) has been processed;
//            [j, length - 1] unexplored
//            [i, j) we don't care
//    case2. if result is longer than original array:
//            1. traverse to record the index of occurrence of s;
//	        2. build a new array whose length is larger than original array
//	        3. copy and replace
//    time: O(n)
//    space: O(n) because we use a ArrayList to store occurrences.
    public static String replace(String input, String s, String t) {
        // corner cases
        if (input.length() == 0) {
            return input;
        }
        char[] array = input.toCharArray();
        return s.length() >= t.length() ? replaceShorter(array, s, t) : replaceLonger(array, s, t);
    }
    private static String replaceShorter(char[] array, String s, String t) {
        int i = 0;
        int j = 0;
        while(j < array.length) {
//            check if array[j] is a start of String s
//            if no, array[i++] = array[j++];
//            if yes,
//                copy String t to index i, i += t.length()
//                j += s.length()
//            terminate: j == array.length
            if (j + s.length() <= array.length && isIdentical(array, j, s)) {
                copy(array, i, t);
                i += t.length();
                j += s.length();
            } else {
                array[i++] = array[j++];
            }
        }
        return new String(array, 0, i);
    }
    private static String replaceLonger(char[] array, String s, String t) {
        int i = 0;
        List<Integer> occurs = new ArrayList<>();
        while (i + s.length() <= array.length) {
            if (isIdentical(array, i, s)) {
                occurs.add(i + s.length() - 1);
                i += s.length();
            } else {
                i++;
            }
        }
        char[] result = new char[array.length + (t.length() - s.length()) * occurs.size()];
        int slow = result.length - 1;
        int fast = array.length - 1;
        int occurIdx = occurs.size() - 1;
        while (fast >= 0) {
//            check if fast == occurs[occurIdx]
//            if true:
//            reverseCopy(result, slow, t);
//            fast -= s.length();
//            slow -= t.length();
//            occurIdx--;
//            if false:
//            result[slow--] = array[fast--];
            if (occurIdx >= 0 && fast == occurs.get(occurIdx)) {
                copy(result, slow - t.length() + 1, t);
                fast -= s.length();
                slow -= t.length();
                occurIdx--;
            } else {
                result[slow--] = array[fast--];
            }
        }
        return new String(result);
    }
    private static void copy(char[] array, int start, String t) {
        for (int i = 0; i < t.length(); i++) {
            array[start + i] = t.charAt(i);
        }
    }
    private static boolean isIdentical(char[] array, int start, String s) {
        for (int i = 0; i < s.length(); i++) {
            if (array[start + i] != s.charAt(i)) {
                return false;
            }
        }
        return true;
    }

//    use a set to record used letters
    public static List<String> permutations(String input) {
        List<String> result = new ArrayList<>();
        if (input == null) {
            return result;
        }
        char[] array = input.toCharArray();
        DFSHelper(array, 0, result);
        return result;
    }
    private static void DFSHelper(char[] array, int index, List<String> result) {
        // base cases
        if (index == array.length) {
            result.add(new String(array));
            return;
        }
        // recursive rules
        Set<Character> used = new HashSet<>();
        for (int i = index; i < array.length; i++) {
            if (used.add(array[i])) {
                swap(array, index, i);
                DFSHelper(array, index + 1, result);
                swap(array, index, i);
            }
        }
    }

    public static int[] reorder(int[] array) {
        // corner cases
        if (array == null || array.length <= 1) {
            return array;
        }
        // if odd number of elements, we ignore the last one.
        if (array.length % 2 == 0) {
            reorderHelper(array, 0, array.length - 1);
        } else {
            reorderHelper(array, 0, array.length - 2);
        }
        return array;
    }
    private static void reorderHelper(int[] array, int left, int right) {
        int size = right - left + 1;
        // base cases
        // if there are only 2 or less elements, we don't need do anything;
        if (size <= 2) {
            return;
        }
        // recursive rules
        int m = left + size / 2;
        int lm = left + size / 4;
        int rm = m + size / 4;
        reverse(array, lm, m - 1);
        reverse(array, m, rm - 1);
        reverse(array, lm, rm - 1);
        reorderHelper(array, left, left + 2 * (lm - left) - 1);
        reorderHelper(array, left + 2 * (lm - left), right);
    }
    private static void reverse(int[] array, int i, int j) {
        while (i < j) {
            int tmp = array[i];
            array[i++] = array[j];
            array[j--] = tmp;
        }
    }

//    Two pointers: i, j
//    1. deal with the letters whose adjacent occurrence >= 2
//    2. deal with letters whose ao == 1
    public static String compress(String input) {
        // corner cases
        if (input == null || input.isEmpty()) {
            return input;
        }
        char[] array = input.toCharArray();
        // deal with the letters whose adjacent occurrence >= 2
        int i = 0; // left of i (excluding i) are processed elements
        int j = 0; // left of j (excluding j) are traversed elements
        int newLength = 0; // record the needed length of the new array
        while (j < array.length) {
            int start = j;
            while (j < array.length && array[j] == array[start]) {
                j++;
            }
            array[i++] = array[start];
            if (j - start == 1) {
                newLength += 2;
            } else {
                int len = copyDigit(array, i, j - start);
                i += len;
                newLength += len + 1;
            }
        }
        char[] result = new char[newLength];
        int fast = i - 1;
        int slow = result.length - 1;
        while (fast >= 0) {
//            case1. array[fast] is a digit:
//                copy digits from array to result until we met a letter;
//            case2. array[fast] is a letter
//                array[slow--] = '1';
//                array[slow--] = array[fast--];
            if (Character.isDigit(array[fast])) {
                while (fast >= 0 && Character.isDigit(array[fast])) {
                    result[slow--] = array[fast--];
                }
            } else {
                result[slow--] = '1';
            }
            result[slow--] = array[fast--];
        }
        return new String(result);
    }
    private static int copyDigit(char[] array, int index, int count) {
        int len = 0;
        for (int i = count; i > 0; i /= 10) {
            len++;
        }
        index += len;
        for (int i = count; i > 0; i /= 10) {
            array[--index] = (char)('0' + i % 10);
        }
        return len;
    }

//    Two pointers: slow, fast
//    1. deal with letters whose adjacent repeated occurrence <= 2. (shorter than or equal to origin input)
//    2. deal with letters whose adjacent repeated occurrence > 2. (longer than origin input)
    public static String decompress(String input) {
        if (input == null || input.length() <= 1) {
            return input;
        }
        char[] array = input.toCharArray();
        // 1. deal with letters whose adjacent repeated occurrence <= 2. (shorter than or equal to origin input)
        int slow = 0;
        int extraLen = 0;
        int fast = 0;
        for (; fast < array.length; fast += 2) {
            int count = array[fast + 1] - '0';
            if (count <= 2) {
                for (int i = 0; i < count; i++) {
                    array[slow++] = array[fast];
                }
            } else {
                array[slow++] = array[fast];
                array[slow++] = array[fast + 1];
                extraLen += array[fast + 1] - '0' - 2;
            }
        }

        // 2. deal with letters whose adjacent repeated occurrence > 2.
        char[] result = new char[slow + extraLen];
        fast = slow - 1;
        slow = result.length - 1;
        while (fast >= 0) {
//            case1. array[f] is a letter:
//                copy it to result.
//            case2. array[f] is a digit (e.g. n):
//                f must >= 1
//                copy n array[f - 1]s to result;
            if (Character.isDigit(array[fast])) {
                int count = array[fast] - '0';
                for (int i = 0; i < count; i++) {
                    result[slow--] = array[fast - 1];
                }
                fast -= 2;
            } else {
                result[slow--] = array[fast--];
            }
        }
        return new String(result);
    }

//    sliding window
//    slow: left border of sw.
//    fast: right border of sw.
//    hashset: <key: letter>, maintain all letters in the sliding window.
//    globalMax: maintain the length of the longest subString
//
//    case 1: if input[f + 1] is not in the set
//        f++, add it to set, update glbMax;
//
//    case 2: input[f + 1] in the set
//        s++, remove input[s - 1] from set;
//
//    terminate: f == length - 1
//
//    time: O(n) in average, O(n^2) in worst case where n is length of input
//    extra space: O(n) in worst case

    public static int longest(String input) {
        // corner cases
        if (input.length() <= 1) {
            return input.length();
        }
        int slow = 0;
        int fast = -1;
        int result = Integer.MIN_VALUE;
        HashSet<Character> set = new HashSet<>();
        while (fast < input.length() - 1) {
            if (set.contains(input.charAt(fast + 1))) {
                set.remove(input.charAt(slow++));
            } else {
                set.add(input.charAt(++fast));
                result = Math.max(result, fast - slow + 1);
            }
        }
        return result;
    }

//    sliding window
//
//    Data structure:
//    s: the left border (including s), 0
//    f: the right border (not including f), 0
//    [s, f) is the window.
//    hashmap: records the number of occurrences of each chars in [s, f] (only chars that are in sh).
//    unMatch: the number of unmatching chars in [s, f]. unMatch == 0 means this is an anagram.
//
//    pre-processing:
//        add all letters of sh into hashmap;
//    For each step:
//        add lo[f] into hashmap, update unMatch if necessary;
//        f++;
//	    if f > sh.length:
//            s++;
//            update unMatch if necessary;
//	    else: do nothing;
//	    if f >= sh.length && unMatch is 0, add s into result;
//
//    terminate: f == lo.length
//
//    time: O(m) + O(n) in average, O(m^2) + O(n*m) in worst case where m is sh.length, n is lo.length;
//    auxiliary space: O(m)
    public static List<Integer> allAnagrams(String sh, String lo) {
        List<Integer> result = new ArrayList<>();
        // corner cases
        if (lo.isEmpty()) {
            return result;
        }
        int slow = 0;
        int fast = 0;
        Map<Character, Integer> freq = toMap(sh);
        int unMatch = freq.size();

//        while (fast < lo.length()) {
//            // update right border
//            char tmp = lo.charAt(fast);
//            Integer count = freq.get(tmp);
//            if (count != null) {
//                freq.put(tmp, count - 1);
//                if (count == 1) {
//                    unMatch--;
//                }
//            }
//            fast++;
//            // update left border
//            if (fast > sh.length()) {
//                tmp = lo.charAt(slow);
//                count = freq.get(tmp);
//                if (count != null) {
//                    freq.put(tmp, count + 1);
//                    if (count == 0) {
//                        unMatch++;
//                    }
//                }
//                slow++;
//            }
//            // check if is an anagram
//            if (unMatch == 0) {
//                result.add(slow);
//            }
//        }
        // better implementation
        for (; fast < lo.length(); fast++) {
            // update right border
            char tmp = lo.charAt(fast);
            Integer count = freq.get(tmp);
            if (count != null) {
                freq.put(tmp, count - 1);
                if (count == 1) {
                    unMatch--;
                }
            }
            // update left border
            if (fast >= sh.length()) {
                tmp = lo.charAt(slow);
                count = freq.get(tmp);
                if (count != null) {
                    freq.put(tmp, count + 1);
                    if (count == 0) {
                        unMatch++;
                    }
                }
                slow++;
            }
            // check if is an anagram
            if (unMatch == 0) {
                result.add(slow);
            }
        }
        return result;
    }
    private static Map<Character, Integer> toMap(String input) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            map.put(input.charAt(i), map.getOrDefault(input.charAt(i), 0) + 1);
        }
        return map;
    }





}
