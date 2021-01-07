// All Permutations II
// input: String, output: List<String>
// R: High level: recursion DFS
//	every level of recursion tree is one index of char array
//						root
//                                /		|		\
//                                level 1		abc			bac		cba				n
//                                /		\		/	\	/	\
//                                level 2	abc		acb		bac	bca	cba	cab		n*(n-1)
//                                |		|		|	|	|	|
//
//
//                                level 3	abc		acb		bac	bca	cba	cab		n!*n
//
//                                time: n + n*(n-1) + … + n!*n = O(n!*n)
//                                space: height = n, in every recursion function, we create a hashset(O(n)), so space is O(n^2)

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllPermutationsII {
    public List<String> permutations(String input) {
        List<String> result = new ArrayList<>();
        if (input == null) {
            return result;
        }
        char[] array = input.toCharArray();
        permutations(array, 0, result);
        return result;
    }

    private void permutations(char[] array, int index, List<String> result) {
        // base cases
        if (index == array.length) {
            result.add(new String(array));
            return;
        }
        // recursive rules
        Set<Character> used = new HashSet<>();
        for (int i = index; i < array.length; i++) {
            if (!used.contains(array[i])) {
                used.add(array[i]);
                swap(array, index, i);
                permutations(array, index + 1, result);
                swap(array, index, i);
            }
        }
    }
    private void swap(char[] array, int l, int r) {
        char tmp = array[l];
        array[l] = array[r];
        array[r] = tmp;
    }
}
