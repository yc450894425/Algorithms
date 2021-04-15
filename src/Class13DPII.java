import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Class13DPII {
    /*  A: there's at least one element in array.
        m[i] represents the min steps from array[i] to array[length - 1];
        m[i] = -1 if cannot jump to array[length - 1] from array[i];
        base case:
            m[length - 1] = 0;
        induction rule:
            m[i] = Math.min(m[j]) + 1, i < j < length
        result:
            m[0]
        Time complexity: O(n^2)
    * */
    public int minJump(int[] array) {
        int[] m = new int[array.length];
        m[m.length - 1] = 0;
        for (int i = m.length - 2; i >= 0; i--) {
            m[i] = -1;
            for (int j = i + 1; j <= Math.min(m.length - 1, i + array[i]); j++) {
                if (m[j] != -1 && (m[i] == -1 || m[i] > m[j] + 1)) {
                    m[i] = m[j] + 1;
                }
            }
        }
        return m[0];
    }

    /*  A: at least 1 element
        m[i] represents the sum of the subarray that has the greatest sum and ends at i;
        base case:
            m[0] = array[0];
        induction rule:
            if m[i - 1] <= 0, m[i] = array[i];
            else, m[i] = m[i - 1] + array[i];
        result:
            max element in m
        time complexity: O(n), where n is length of array.
    * */
    public int largestSum(int[] array) {
        int[] m = new int[array.length];
        m[0] = array[0];
        int result = m[0];
        for (int i = 1; i < array.length; i++) {
            m[i] = m[i - 1] <= 0 ? array[i] : array[i] + m[i - 1];
            result = Math.max(result, m[i]);
        }
        return result;
    }
    public int largestSumII(int[] array) {
        int result = array[0];
        int curr = array[0];
        for (int i = 1; i < array.length; i++) {
            curr = Math.max(curr + array[i], array[i]);
            result = Math.max(result, curr);
        }
        return result;
    }
    /*  How to return the left-right border of the solution?
        Maintain a left and a right pointers, and a globLeft and a globRight.
        left: 0, update to i when m[i - 1] <= 0;
        right: i
        globLeft: 0, update to left when m[i] > result
        globRight: 0, update to right when m[i] > result
    * */
    public List<Integer> largestSumIII(int[] array) {
        List<Integer> result = new ArrayList<>();
        int max = array[0];
        int curr = array[0];
        int globL = 0;
        int globR = 0;
        int left = 0;
        for (int i = 1; i < array.length; i++) {
            if (curr <= 0) {
                left = i;
                curr = array[i];
            } else {
                curr += array[i];
            }
            if (curr > max) {
                globL = left;
                globR = i;
                max = curr;
            }
        }
        result.add(globL);
        result.add(globR);
        result.add(max);
        return result;
    }

    /*  isWord[i] represents whether the word that is composed by the FIRST i letters can satisfy the requirement.
        base case:
            isWord[0] = true;
        induction rule:
            If there is a j, 0 <= j < i, such that isWord[j] is true and subString(input, j, i), [the (j+1)-th, to the i-th letter], can be found in the dict:
                isWord[i] = true;
            else:
                isWord[i] = false;
        return:
            isWord[input.length()];
    * */
    public boolean canBreak(String input, String[] dict) {
        int len = input.length();
        Set<String> set = toSet(dict);
        boolean[] isWord = new boolean[len + 1];
        isWord[0] = true;
        for (int i = 1; i <= len; i++) {
            for (int j = 0; j < i; j++) {
                if (isWord[j] && set.contains(input.substring(j, i))) {
                    isWord[i] = true;
                    break;
                }
            }
        }
        return isWord[len];
    }
    private Set<String> toSet(String[] dict) {
        Set<String> set = new HashSet<>();
        for (String s : dict) {
            set.add(s);
        }
        return set;
    }

    /*  int d[i][j] represents the minimum number of operations to convert the first i letters of s1 to the first j letters of s2.
        s1: xxx f
               i-th
        s2: yyyyyy g
                  j-th
        replace: d[i - 1][j - 1] + 1;
        delete: d[i - 1][j] + 1;
        insert: d[i][j - 1] + 1;

        base case:
            d[i][0] = i, 0 <= i <= one.length();
            d[0][j] = j, 0 <= j <= two.length();
        induction rule:
            if s1[i - 1](i-th char) == s2[j - 1](j-th char):
                d[i][j] = d[i - 1][j - 1];
            else:
                int replace = d[i - 1][j - 1] + 1;
                int delete = d[i - 1][j] + 1;
                int insert = d[i][j - 1] + 1;
                d[i][j] = min(r, d, i);
        how to fill out the matrix?
            left => right, top => right
        return:
            d[one.length()][two.length()]

        time complexity:
            O(m*n), m,n are length of one and two
        space complexity:
            O(m*n)
    * */
    public int editDistance(String one, String two) {
        int m = one.length();
        int n = two.length();

        int[][] dist = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dist[i][j] = j;
                } else if (j == 0) {
                    dist[i][j] = i;
                } else if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    dist[i][j] = dist[i - 1][j - 1];
                } else {
                    int replace = dist[i - 1][j - 1] + 1;
                    int delete = dist[i - 1][j] + 1;
                    int insert = dist[i][j - 1] + 1;
                    dist[i][j] = Math.min(replace, Math.min(delete, insert));
                }
            }
        }
        return dist[m][n];
    }

}
