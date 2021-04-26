import java.util.HashMap;

public class Review1Algorithms {

    /*  DP + HashMap
        1. get prefix array m,
            m[i] represents the sum[0, i]. => O(n)
            m[i] - m[j] = sum[j + 1, i] = k, where 0 <= j <= i - 1
            m[j] = m[i] - k
            Every time we passed by index i, and got m[i], we should traverse all j in [0, i - 1]
            and check if there are js satisfy m[j] = m[i] - k.
                if yes, count + 1;
            To make it simpler, we can use a hashmap to store the value of m[j].
            The key is m[j], value is the occurrence, where 0 <= j <= length - 2

            Importantly, since j is in [0, length - 2], i is in [0, length - 1], and m[i] - m[j] = sum[j + 1, i],
            the max range of sum is [1, length - 1], not[0, length -1].
            So we need to put a (0, 1) into hashmap at first.
            It seems like we add a "0" at the left side of the input array.

            Time: O(n)
            Space: O(n)
    * */
    public int numOfSubarraySumK(int[] array, int k) {
        // corner case
        if (array == null || array.length == 0) {
            return 0;
        }
        int count = 0;
        HashMap<Integer, Integer> occur = new HashMap<Integer, Integer>();
        occur.put(0, 1);
        int pre = 0;
        for (int i = 0; i < array.length; i++) {
            pre = pre + array[i];
            if (occur.containsKey(pre - k)) {
                count += occur.get(pre - k);
            }
            occur.put(pre, occur.getOrDefault(pre, 0) + 1);
        }
        return count;
    }
}
