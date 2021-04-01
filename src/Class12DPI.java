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
//    public int longest(int[] array) {}
//    public int maxProduct(int length) {}
//    public boolean canJump(int[] array) {}
}
