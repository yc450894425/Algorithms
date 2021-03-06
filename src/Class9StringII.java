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

}
