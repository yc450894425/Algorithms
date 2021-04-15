public class Class14DPIII {
    /*	m[i] represents the length of the longest subarray that ends at a[i] and contains only 1.
        base case:
            m[0] = a[0]
        induction rule:
            m[i] = 0, if a[i] = 0;
            m[i] = m[i - 1] + 1, if a[i] = 1;
        return:
            globMax
        time: O(n), where n is the length of a
        space: O(n) => O(1)
    */
    public int logest(int[] array) {
        if (array.length == 0) {
            return 0;
        }

        int[] m = new int[array.length];
        m[0] = array[0];
        int globMax = m[0];
        for (int i = 1; i < array.length; i++) {
            m[i] = array[i] == 1 ? m[i - 1] + 1 : 0;
            globMax = Math.max(globMax, m[i]);
        }
        return globMax;
    }
    public int logestII(int[] array) {
        int curr = 0;
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            curr = array[i] == 1 ? curr + 1 : 0;
            result = Math.max(curr, result);
        }
        return result;
    }

}
