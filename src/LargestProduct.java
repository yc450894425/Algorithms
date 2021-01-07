//191 largest product of length
//
//        input -> String[] dict, not null, not empty, only lowercase ‘a-z’, no duplicates, fit in memory
//        output -> int result
//
//        iterative way
//
//        for for loops, compare all pair of strings:
//        if no common letters, try to update the result.
//
//        simplify:
//        26 letters, 32 bits int to store the letters info for a word
//        zx...........dcba
//        00000000.....0101
//        int[] set = new int[dict.length];
//        set[i] stores the occurrence of letters in dict[i];
//        compare: O(n) -> O(1)

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class LargestProduct {
    public int largestProduct(String[] dict) {
        // corner cases

        // pre-processing
        int[] set = new int[dict.length];
        for (int i = 0; i < dict.length; i++) {
            for (int j = 0; j < dict[i].length(); j++) {
                set[i] |= 1 << (dict[i].charAt(j) - 'a');
            }
        }
        int result = 0;
// compare
        for (int i = 0; i < dict.length; i++) {
            for (int j = i + 1; j < dict.length; j++) {
                if ((set[i] & set[j]) == 0) {
                    result = Math.max(result, dict[i].length() * dict[j].length());
                }
            }
        }
        return result;
    }

    public int betterSolution(String[] dict) {
        int result = 0;
        Map<String, Integer> bitMasks = getBitMask(dict);
        Arrays.sort(dict, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (s1.length() == s2.length()) {
                    return 0;
                }
                return s1.length() > s2.length() ? -1 : 1;
            }
        });

        for (int i = 1; i < dict.length; i++) {
            for (int j = 0; j < i; j++) {
                int prod = dict[i].length() * dict[j].length();
                if (prod <= result) {
                    break;
                }
                int iBitMask = bitMasks.get(dict[i]);
                int jBitMask = bitMasks.get(dict[j]);
                if ((jBitMask & iBitMask) == 0) {
                    result = prod;
                }
            }
        }
        return result;
    }

    private Map<String, Integer> getBitMask(String[] array) {
        Map<String, Integer> map = new HashMap<>();
        for (String s : array) {
            int bits = 0;
            for (int i = 0; i < s.length(); i++) {
                bits |= 1 << (s.charAt(i) - 'a');
                map.put(s,bits);
            }
        }
        return map;
    }

}
