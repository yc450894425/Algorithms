import java.util.*;


public class Class16DFSII {
    /*  How many levels in recursion tree? What does it store in each level?
            n levels, where n is length of set.
            It stores the decision whether we choose the current char or not.
        How many branches/different states should we try to put on each level?
            2. choose or not.
    */
    public List<String> subSets(String set) {
        List<String> result = new ArrayList<>();
        if (set == null) {
            return result;
        }
        char[] array = set.toCharArray();
        Arrays.sort(array);
        subSetsHelper(array, 0, new StringBuilder(), result);
        return result;
    }

    private void subSetsHelper(char[] array, int index, StringBuilder sb, List<String> result) {
        // base case
        if (index == array.length) {
            result.add(sb.toString());
            return;
        }
        // recursive rule
        // add
        sb.append(array[index]);
        subSetsHelper(array, index + 1, sb, result);
        sb.deleteCharAt(sb.length() - 1);
        // don't add
        while (index + 1 < array.length && array[index] == array[index + 1]) {
            index++;
        }
        subSetsHelper(array, index + 1, sb, result);
    }

    /*  How many levels in recursion tree? What does it store in each level?
            n levels, where n is length of set.
            It stores the decision whether we choose the character or not.
        How many branches/different states should we try to put on each level?
            2. choose or not.
    */
    public List<String> subSetsOfSizeK(String set, int k) {
        List<String> result = new ArrayList<>();
        if (set == null) {
            return result;
        }
        char[] array = set.toCharArray();
        sizeKHelper(array, 0, new StringBuilder(), result, k);
        return result;
    }

    private void sizeKHelper(char[] array, int index, StringBuilder sb, List<String> result, int k) {
        // base case
        if (index == array.length || sb.length() == k) {
            if (sb.length() == k) {
                result.add(new String(sb));
            }
            return;
        }
        // recursive rule
        sb.append(array[index]);
        sizeKHelper(array, index + 1, sb, result, k);
        sb.deleteCharAt(sb.length() - 1);
        sizeKHelper(array, index + 1, sb, result, k);
    }

    /* 	How many levels in the recursion tree? What does it store in each level?
	2*(l+m+n) levels.
	Each level stores/decides one parenthesis.
How many branches/different states should we try to put on each level?
	6 branches.
	(, ), <, >, {, }.
*/
// l for (), m for <>, n for {}
    private static final char[] PS = new char[]{'(', ')', '<', '>', '{', '}'};
    public List<String> validParentheses(int l, int m, int n) {
        List<String> result = new ArrayList<>();
        int[] remains = new int[]{l, l, m, m, n, n};
        Deque<Character> stack = new ArrayDeque<>();
        validParenthesesHelper(stack, remains, new StringBuilder(), result, 2 * (l + m + n));
        return result;
    }
    private void validParenthesesHelper(Deque<Character> stack, int[] remains, StringBuilder sb, List<String> result, int total) {
        // base case
        if (sb.length() == total) {
            result.add(sb.toString());
            return;
        }
        // recursive rule
        for (int i = 0; i < remains.length; i ++) {
            // add left
            if (i % 2 == 0 && remains[i] > 0) {
                stack.offerFirst(PS[i]);
                sb.append(PS[i]);
                remains[i]--;
                validParenthesesHelper(stack, remains, sb, result, total);
                remains[i]++;
                sb.deleteCharAt(sb.length() - 1);
                stack.pollFirst();
            }
            // add right
            if (i % 2 == 1 && !stack.isEmpty() && stack.peekFirst() == PS[i - 1]) {
                stack.pollFirst();
                sb.append(PS[i]);
                remains[i]--;
                validParenthesesHelper(stack, remains, sb, result, total);
                remains[i]++;
                sb.deleteCharAt(sb.length() - 1);
                stack.offerFirst(PS[i - 1]);
            }
        }
    }

    /* 	How many levels in the recursion tree? What does it store in each level?
	factors.size() levels.
Each level decides the number of a factor.
How many branches/different states should we try to put on each level?
	It’s dynamically changing.
*/
    public List<List<Integer>> combinations(int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (target <= 1) {
            return result;
        }
        List<Integer> cur = new ArrayList<>();
        List<Integer> factors = getFactors(target);
        combinationsHelper(target, factors, 0, cur, result);
        return result;
    }
    private void combinationsHelper(int target, List<Integer> factors, int index, List<Integer> cur, List<List<Integer>> result) {
        // base case
        if (index == factors.size() || target == 1) {
            if (target == 1) {
                result.add(new ArrayList<>(cur));
            }
            return;
        }
        // recursive rule
        combinationsHelper(target, factors, index + 1, cur, result);
        int factor = factors.get(index);
        int size = cur.size();
        while (target % factor == 0) {
            cur.add(factor);
            target /= factor;
            combinationsHelper(target, factors, index + 1, cur, result);
        }
        cur.subList(size, cur.size()).clear();
    }
    private List<Integer> getFactors(int target) {
        List<Integer> factors = new ArrayList<>();
        for (int i = target / 2; i > 1; i--) {
            if (target % i == 0) {
                factors.add(i);
            }
        }
        return factors;
    }

    /*  How many levels in recursion tree? What does it store in each level?
            n levels, where n is length of set.
            It stores the letter in this position.
        How many branches/different states should we try to put on each level?
            Dynamically changing. Level 1 has n branches... Level n has 1 branch.
    */
    public List<String> allPermutationsOfSubsets(String set) {
        List<String> result = new ArrayList<>();
        if (set == null) {
            return result;
        }
        char[] array = set.toCharArray();
        allPermutationsOfSubsetsHelper(array, 0, result);
        return result;
    }
    private void allPermutationsOfSubsetsHelper(char[] set, int index, List<String> result) {
        result.add(new String(set, 0, index));
        // recursive rule
        for (int i = index; i < set.length; i++) {
            swap(set, index, i);
            allPermutationsOfSubsetsHelper(set, index + 1, result);
            swap(set, index, i);
        }
    }
    private void swap(char[] array, int left, int right){
        char tmp = array[left];
        array[left] = array[right];
        array[right] = tmp;
    }

        /*  How many levels in recursion tree? What does it store in each level?
            4 levels.
            It stores one part of an IP address.
        How many branches/different states should we try to put on each level?
            3 branches.
            x, xx, xxx.
            The first number of xx and xxx cannot be 0.
        Base case:
            level == 4
                if index == array.length, add this to result
        Recursive rule:
            try one letter
            two letters
            three letters

    */
    public List<String> restore(String ip) {

    }
    private void restoreHelper(char[] array, int index, int level, StringBuilder sb, List<String> result) {
        // base case
        if (level == 4 && index == array.length) {
            result.add(sb.toString());
        }
        // recursive rule
    }






}
