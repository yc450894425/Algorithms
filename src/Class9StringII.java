import java.util.ArrayList;
import java.util.List;

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


}
