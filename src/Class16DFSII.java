import java.sql.Array;
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

    /*  Assumptions: No duplicates.
        How many levels in recursion tree? What does it store in each level?
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

    public List<String> validParenthesesII(int l, int m, int n) {
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
        for (int i = 0; i < remains.length; i++) {
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
    public List<List<Integer>> factorCombinations(int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (target <= 1) {
            return result;
        }
        List<Integer> cur = new ArrayList<>();
        List<Integer> factors = getFactors(target);
        factorCombinationsHelper(target, factors, 0, cur, result);
        return result;
    }

    private void factorCombinationsHelper(int target, List<Integer> factors, int index, List<Integer> cur, List<List<Integer>> result) {
        // base case
        if (index == factors.size() || target == 1) {
            if (target == 1) {
                result.add(new ArrayList<>(cur));
            }
            return;
        }
        // recursive rule
        factorCombinationsHelper(target, factors, index + 1, cur, result);
        int factor = factors.get(index);
        int size = cur.size();
        while (target % factor == 0) {
            cur.add(factor);
            target /= factor;
            factorCombinationsHelper(target, factors, index + 1, cur, result);
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

    /*  Assumptions: No duplicates.
        How many levels in recursion tree? What does it store in each level?
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

    private void swap(char[] array, int left, int right) {
        char tmp = array[left];
        array[left] = array[right];
        array[right] = tmp;
    }

    public int minDifference(int[] array) {
        int[] minDiff = new int[]{Integer.MAX_VALUE};
        int totalSum = 0;
        for (int n : array) {
            totalSum += n;
        }
        minDifferenceHelper(minDiff, array, 0, 0, 0, totalSum);
        return minDiff[0];
    }

    private void minDifferenceHelper(int[] minDiff, int[] array, int index, int count, int sum, int totalSum) {
        // base case
        if (count == array.length / 2 || index == array.length) {
            if (count == array.length / 2) {
                minDiff[0] = Math.min(minDiff[0], Math.abs(2 * sum - totalSum));
            }
            return;
        }
        // recursive rule
        // add this number
        sum += array[index];
        minDifferenceHelper(minDiff, array, index + 1, count + 1, sum, totalSum);
        sum -= array[index];
        minDifferenceHelper(minDiff, array, index + 1, count, sum, totalSum);
        // Don't add this number
    }

    /*  Keypoint: how to deduplicate?
            The canonical way to solve this is to enforce a generation order.
            First, I convert the input set to a char array then sort the array so duplicates will be adjacent.
            Then I will enforce the following order when I generate the set of possible choices for a given stage:
                For one kind of letter, if I choose not to pick it, then I should ignore all its following duplicates.
    * */
    public List<String> subSetsIIOfSizeK(String set, int k) {
        List<String> result = new ArrayList<>();
        char[] array = set.toCharArray();
        Arrays.sort(array);
        subSetsIIOfSizeKHelper(array, k, 0, new StringBuilder(), result);
        return result;
    }

    private void subSetsIIOfSizeKHelper(char[] set, int k, int index, StringBuilder curr, List<String> result) {
        // base case
        if (curr.length() == k || index == set.length) {
            if (curr.length() == k) {
                result.add(curr.toString());
            }
            return;
        }
        // recursive rule
        // Add
        subSetsIIOfSizeKHelper(set, k, index + 1, curr.append(set[index]), result);
        curr.deleteCharAt(curr.length() - 1);
        // Don't add, skip all duplicates
        while (index + 1 < set.length && set[index + 1] == set[index]) {
            index++;
        }
        subSetsIIOfSizeKHelper(set, k, index + 1, curr, result);
    }

    /* 	How many levels in the recursion tree? What does it store in each level?
            2*(l+m+n) levels.
            Each level stores the parenthesis of each position.
        How many branches/different states should we try to put on each level?
            6 branches.
            (, ), <, >, {, }.
        Data structure:
            stack: stores the INDICES of left parentheses that are not paired.
    */
    public List<String> validParenthesesIII(int l, int m, int n) {
        List<String> result = new ArrayList<>();
        int[] num = new int[]{l, l, m, m, n, n};
        validParenthesesIIIHelper(num, new StringBuilder(), result, new ArrayDeque<>(), l * 2 + m * 2 + n * 2);
        return result;
    }
    private void validParenthesesIIIHelper(int[] remain, StringBuilder curr, List<String> result, Deque<Integer> stack, int targetLen) {
        // base case
        if (curr.length() == targetLen) {
            result.add(new String(curr));
            return;
        }
        // recursive rule
        for (int i = 0; i < PS.length; i++) {
            if (i % 2 == 0) { // left parenthesis
                if (remain[i] > 0 && (stack.isEmpty() || stack.peekFirst() > i)) {
                    stack.offerFirst(i);
                    curr.append(PS[i]);
                    remain[i]--;
                    validParenthesesIIIHelper(remain, curr, result, stack, targetLen);
                    stack.pollFirst();
                    curr.deleteCharAt(curr.length() - 1);
                    remain[i]++;
                }
            } else { // right parenthesis
                if (!stack.isEmpty() && stack.peekFirst() == i - 1) {
                    stack.pollFirst();
                    curr.append(PS[i]);
                    remain[i]--;
                    validParenthesesIIIHelper(remain, curr, result, stack, targetLen);
                    stack.offerFirst(i - 1);
                    curr.deleteCharAt(curr.length() - 1);
                    remain[i]++;
                }
            }
        }
    }

    /*  High level idea:
            1. use a hashmap to store each number and its first appearance.
            2. use a hashset to store appeared number
            3. use a boolean[] to record appeared number
    * */
    public int[] keepDistanceI(int k) {
        int[] used = new int[k + 1];
        int[] array = new int[2 * k];
        return keepDistanceIHelper(used, array, 0) ? array : null;
    }
    private boolean keepDistanceIHelper(int[] used, int[] array, int index) {
        // base case
        if (index == array.length) {
            return true;
        }
        // recursive rule
        for (int n = 1; n < used.length; n++) {
            // if this number can be put at array[index]:
            //  put it here, update used[num], and call helper function, if it returns true, the caller returns true.
            //  if not true, reset used[num]
            if (used[n] == 0 || (used[n] == 1 && index - n - 1 >= 0 && array[index - n - 1] == n)) {
                // add
                array[index] = n;
                used[n]++;
                if (keepDistanceIHelper(used, array, index + 1)) {
                    return true;
                }
                // delete
                used[n]--;
            }
        }
        return false;
    }
    /*  High level idea:
            DFS
            How many level? What does it store in each level?
                k levels.
                Each level stores one pair of numbers.
            How many branches should we try to put on each level?
                depending on how many valid positions are left.
                3xxx3x, x3xxx3              => 2 branches
                2xx2xx, x2xx2x, xx2xx2      => 3 branches
    * */
    public int[] keepDistanceII(int k) {
        int[] array = new int[2 * k];
        return keepDistanceIIHelper(array, k) ? array : null;
    }
    private boolean keepDistanceIIHelper(int[] array, int n) {
        // base case
        if (n == 0) {
            return true;
        }
        // recursive rule
        for (int i = 0; i <= array.length - n - 2; i++) {
            if (array[i] != 0 || array[i + n + 1] != 0) {
                continue;
            }
            // add
            array[i] = n;
            array[i + n + 1] = n;
            if (keepDistanceIIHelper(array, n - 1)) {
                return true;
            }
            // delete
            array[i] = 0;
            array[i + n + 1] = 0;
        }
        return false;
    }

    /*
        0 represents corridor
        1 represents wall
        Key insight:
            Moving 2 positions each time can guarantee that there is no 2 * 2 corridor or wall.

        0 1 0 0 0
        0 1 0 1 0
        0 1 0 1 0
        0 1 0 1 0
        0 0 0 1 0
    * */
    public int[][] maze(int n) {
        int[][] maze = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // generate a maze where only (0,0) is corridor and other positions are walls.
                // later we will break walls to generate corridors.
                maze[i][j] = i == 0 && j == 0 ? 0 : 1;
            }
        }
        generate(maze, 0, 0);
        return maze;
    }
    private void generate(int[][] m, int x, int y) {
        Dir[] dirs = Dir.values();
        shuffle(dirs);
        for (Dir dir : dirs) {
            int nextX = dir.nextX(x, 2);
            int nextY = dir.nextY(y, 2);
            if (isValid(m, nextX, nextY)) {
                // break walls(1) to generate corridors(0);
                m[dir.nextX(x, 1)][dir.nextY(y, 1)] = 0;
                m[nextX][nextY] = 0;
                generate(m, nextX, nextY);
            }
        }
    }
    private boolean isValid(int[][] maze, int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] == 1;
    }
    enum Dir {
        NORTH(-1, 0), SOUTH(1, 0), EAST(0, 1), WEST(0, -1);
        int deltaX;
        int deltaY;

        Dir(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }

        public int nextX(int x, int steps) {
            return x + steps * deltaX;
        }
        public int nextY(int y, int steps) {
            return y + steps * deltaY;
        }
    }
    private void shuffle(Dir[] dirs) {
        for (int i = 0; i < dirs.length; i++) {
            int another = (int) (Math.random() * dirs.length);
            Dir tmp = dirs[i];
            dirs[i] = dirs[another];
            dirs[another] = tmp;
        }
    }

    /*
        How many levels? What does it store in each level?
            n, where n is length of number.
            It stores a letter for the current number.
        How many branches/different states should we try to put on each level?
            depending on how many chars the number has.
            2 => "abc", 3 branches
            9 => "wxyz", 4 branches
    * */
    private static final String[] LETTERS = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    public String[] combinations(int number) {
        List<String> result = new ArrayList<>();
        int[] digits = getDigits(number);
        combinationsHelper(digits, 0, new StringBuilder(), result);
        return result.toArray(new String[0]);
    }
    private void combinationsHelper(int[] digits, int index, StringBuilder sb, List<String> result) {
        // base case
        if (index == digits.length) {
            result.add(sb.toString());
            return;
        }
        // recursive rule
        String curr= LETTERS[digits[index]];
        for (int i = 0; i < curr.length(); i++) {
            sb.append(curr.charAt(i));
            combinationsHelper(digits, index + 1, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
    // convert a number to an array of digit and remove digits smaller than 2
    private int[] getDigits(int num) {
        int len = 0;
        for (int i = num; i > 0; i /= 10) {
            if (i % 10 > 1) {
                len++;
            }
        }
        int[] array = new int[len];
        for (int i = num; i > 0; i /= 10) {
            if (i % 10 > 1) {
                array[--len] = i % 10;
            }
        }
        return array;
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
        List<String> result = new ArrayList<>();
        char[] array = ip.toCharArray();
        restoreHelper(array, 0, 0, new StringBuilder(), result);
        return result;
    }

    private void restoreHelper(char[] array, int curr, int level, StringBuilder sb, List<String> result) {
        // base case
        if (level == 4) {
            if (curr == array.length) {
                result.add(sb.substring(0, sb.length() - 1));
            }
            return;
        }
        // recursive rule
        // 1 digit
        if (curr < array.length) {
            restoreHelper(array, curr + 1, level + 1, sb.append(array[curr]).append('.'), result);
            sb.delete(sb.length() - 2, sb.length());
        }
        // 2 digits
        if (curr + 1 < array.length) {
            if (array[curr] != '0') {
                restoreHelper(array, curr + 2, level + 1, sb.append(array[curr]).append(array[curr + 1]).append('.'), result);
                sb.delete(sb.length() - 3, sb.length());
            }
        }
        // 3 digits
        if (curr + 2 < array.length) {
            char a = array[curr];
            char b = array[curr + 1];
            char c = array[curr + 2];
            if (a == '1' || a == '2' && b >= '0' && b <= '4' || a == '2' && b == '5' && c >= '0' && c <= '5') {
                restoreHelper(array, curr + 3, level + 1, sb.append(array[curr]).append(array[curr + 1]).append(array[curr + 2]).append('.'), result);
                sb.delete(sb.length() - 4, sb.length());
            }
        }
    }

}
