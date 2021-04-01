public class Class12DPI {
    /* f[i] represents the i-th fibonacci number
    f[0] = 0, f[1] = 1;
    f[i] = f[i - 1] + f[i - 2]
    * */
    public long fibonacci(int k) {
        // corner cases
        if (k < 0) {
            return 0;
        }
        long[] fib = new long[k + 1];
        fib[0] = 0;
        fib[1] = 1;
        for (int i = 2; i <= k; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib[k];
    }
    public long fibonacciI(int k) {
        long a = 0;
        long b = 1;
        if (k < 1) {
            return 0;
        }
        for (int i = 2; i <= k; i++) {
            b = a + b;
            a = b - a;
        }
        return b;
    }

    /*  max represents the length of the longest ascending subarray
        m[i] represents the length of the longest ascending subarray which must end at array[i];
        base case: m[0] = 1;
        induction rule:
            m[i] = m[i - 1] + 1, if array[i] > array[i - 1].
            m[i] = 1, if array[i] <= array[i - 1];
            then, update max if necessary
        return max;
    * */
    public int longest(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int[] m = new int[array.length];
        m[0] = 1;
        int max = m[0];
        for (int i = 1; i < array.length; i++) {
            m[i] = array[i] > array[i - 1] ? m[i - 1] + 1 : 1;
            max = Math.max(max, m[i]);
        }
        return max;
    }
    /*  m[i] represents the max product of cutting i length rope (at least one cut);
    *   our target is m[length];
    *   base case: m[0] = 0, m[1] = 0; (at least one cut)
    *   induction rule:
    *       m[i] = min value;
    *       for (int j = 1; j < i; j++) {
    *           int curBest = Math.max(m[j], j) * (i - j);
    *           m[i] = Math.max(curBest, m[i]);
    *       }
    *   return m[length];
    * */
    public int maxProduct(int length) {
        int[] m = new int[length + 1];
        m[0] = 0;
        m[1] = 0;
        for (int i = 2; i < m.length; i++) {
            m[i] = Integer.MIN_VALUE;
            for (int j = 1; j < i; j++) {
                m[i] = Math.max(Math.max(m[j], j) * (i - j), m[i]);
            }
        }
        return m[length];
    }

    /*  canJump[i] represents whether we can reach the last index from index i;
    *   base case: canJump[array.length - 1] = true, since it's the last index;
    *   induction rule:
    *       canJump[i] is true, if we can find canJump[j] is true, i < j <= Math.min(i + array[i], array.length - 1);
    *       otherwise, it's false;
    *   return canJump[0];
    * */
    public boolean canJump(int[] array) {
        boolean[] canJump = new boolean[array.length];
        canJump[array.length - 1] = true;
        for (int i = array.length - 2; i >= 0; i--) {
            for (int j = i + 1; j < array.length && j <= i + array[i]; j++) {
                if (canJump[j]) {
                    canJump[i] = true;
                    break;
                }
            }
        }
        return canJump[0];
    }
}
