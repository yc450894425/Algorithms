import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Class7GraphSearchAlgorithmsIIDFS {
//    C:	input => String set, output => List<String> result
//    A: 	input not null; fit in memory; if input empty, return empty list;
//    Algorithm:	Depth First Search (recursive)
//
//    e.g. {'a’, 'b’, 'c’}
//    Recursion tree:
//
//    How many levels in the recursion tree?
//    n levels where n is the number of chars in the input string.
//
//    What does it store in each level?
//    For each level, it makes the decision on whether to put this element(char) into the final set.
//
//    How many different states should we try to put on each level?
//    2 states. Each state considers either selecting or not selecting the current element.
//
//                        {}
//				/				\
//    L1: a		{a}				{}
//			/		\		/		\
//    L2: b{a, b}		{a}		{b}		{}
//
//    L3: c{a, b, c}	{a, b}{a,c}{a}	{b,c} {b}{c}	{}
//
//    Time Complexity:
//        O(1) in each node. (except the bottom level)
//            how many nodes?
//            1 + 2 + 4 + ... + 2^(height - 2) = 1*(1 - 2^(height - 1))/(1 - 2) = 2^(height - 1) = 2^(n - 1)
//            time: O(1) * 2^(n - 1) => O(2^n)
//        Besides, the bottom level has 2^(n - 1) nodes, and O(n) in each node because of print.
//            time: O(n) * 2^(n - 1) => O(2^n*n)
//    Total Time: o(2^n*n) + O(2^n) => O(2^n*n)
//
//    Space Complexity:
//        Stack
//            O(1) for each callstack, and the height is n
//            so space: O(n)
//        Heap
//            O(n) for StringBuilder
//    Total Space: O(n)
    public static List<String> subSets(String set) {
        List<String> result = new ArrayList<>();
        if (set == null) {
            return result;
        }
        char[] array = set.toCharArray();
        subSetsDFS(array, new StringBuilder(), 0, result);
        return result;
    }
    private static void subSetsDFS(char[] array, StringBuilder sb, int index, List<String> result) {
        // base cases
        if (index == array.length) {
            result.add(sb.toString());
            return;
        }
        // recursive rules
        // not select current letter
        subSetsDFS(array, sb, index + 1, result);
        // select current letter
        subSetsDFS(array, sb.append(array[index]), index + 1, result);
        sb.deleteCharAt(sb.length() - 1);
    }

//    Algorithm: Depth First Search
//
//    How many levels in the recursion tree?
//    2 * n levels where n is the total number of “pair of ()” need to add.
//
//    What does it store in each level?
//    Each level considers one position.
//
//    How many different states should we try to put on this level?
//    2 states. Add either a left, or right parenthesis.
//
//    Time: O(2^(2n)*2n) => O(2^(2n)*n)
//    Space: O(2n) => O(n)
    public static List<String> validParentheses(int n) {
        List<String> result = new ArrayList<>();
        char[] cur = new char[2 * n];
        DFSHelper(cur, n, n, 0, result);
        return result;
    }
    // left represents the number of remaining left parenthesis to be added.
    // right represents the number of remaining right parenthesis to be added.
    private static void DFSHelper(char[] cur, int left, int right, int index, List<String> result) {
        // base cases
        if (left == 0 && right == 0) {
            result.add(new String(cur));
            return;
        }
        // recursive rules
        // if left > 0, add a '('
        if (left > 0) {
            cur[index] = '(';
            DFSHelper(cur, left - 1, right, index + 1, result);
        }
        // if right > 0 && right > left, add a ')’
        if (right > left) {
            cur[index] = ')';
            DFSHelper(cur, left, right - 1, index + 1, result);
        }
    }

//    Algorithm: Depth First Search
//
//    How many levels in this recursion tree?
//    n levels, where n is the number of coin values.
//    e.g. 1 cent, 5 cents, 10 cents, 25 cents. n = 4, 4 levels.
//
//    What does it store in each level?
//    For each level, we consider one coin value. It stores the number of the current coin.
//
//    How many different states should we try to put on this level?
//    It’s dynamically changing.
//
//    L0:						root(remaining = 99)
//       		            /		/			\		\
//    L1:(25cents)	0x25(99)	   1x25(74)	   2x25(49)   3x25(24)
//			        ||||||||||
//    L2:(10cents)0x10(99) 1x10(89)...9x10(9)
//		        ||||||
//    L3:(5cents) 0x5(99) 1x5(94)...19x5(4)
//		        |||||||
//    L4:(1cent)..........99x1(0)
//
//    Time: O(99^n*n)
//    Space: O(n)
    public static List<List<Integer>> combinations(int target, int[] coins) {
        List<List<Integer>> result = new ArrayList<>();
        DFSHelper(target, coins, 0, new ArrayList<>(), result);
        return result;
    }
    private static void DFSHelper(int remain, int[] coins, int index, List<Integer> cur, List<List<Integer>> result) {
        // base cases
        if (index == coins.length - 1) {
            if (remain % coins[coins.length - 1] == 0) {
                cur.add(remain / coins[coins.length - 1]);
                result.add(new ArrayList<>(cur));
                cur.remove(cur.size() - 1);
            }
            return;
        }
        // recursive rules
        int max = remain / coins[index];
        for (int i = 0; i <= max; i++) {
            cur.add(i);
            DFSHelper(remain - i * coins[index], coins, index + 1, cur, result);
            cur.remove(cur.size() - 1);
        }
    }

//    Assumption: no duplicates.
//    Algorithm: Depth First Search
//
//    How many levels in the recursion tree?
//    n levels where n is the length of input string.
//
//    What does it store on each level?
//    Each level represents one position.
//
//    How many different states should we try to put on this level?
//    remaining unused letter.
//    If we are in the i-th position, then we can try (n-i) branches.
//
//    e.g. input is “abc”
//
//    L0:					root(abc)
//    L1/p0:		abc			    bac				cba
//    L2/p1:	abc    acb		bac		bca		cba		cab
//    L3/p2:	abc	   acb		bac		bca		cba		cab
//
//    # of nodes:
//    L0: 1
//    L1: 3 (n)
//    L2: 6 = 3x2 (n*(n-1))
//            ...
//    Ln: n!
//
//    Additionally, we need to add all results into a List<String>, so time for each node in Ln is O(n).
//
//    Time: O(n!*n)
//    Space: O(n)
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
        for (int i = index; i < array.length; i++) {
            swap(array, i, index);
            DFSHelper(array, index + 1, result);
            swap(array, i, index);
        }
    }
    private static void swap(char[] array, int i, int j) {
        char tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
