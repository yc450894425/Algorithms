//202. Kth Smallest In Two Sorted Arrays
public class KthSmallestInTwoSortedArrays {
    public int kth(int[] a, int[] b, int k) {
        // corner cases
        if (k <= 0 || k > a.length + b.length) {
            return -1;
        }
        return kthHelper(a, 0, b, 0, k);
    }

    private int kthHelper(int[] a, int aLeft, int[] b, int bLeft, int k)
    {
        // base cases
        if (aLeft >= a.length) {
            return b[bLeft + k - 1];
        }

        if (bLeft >= b.length) {
            return a[aLeft + k - 1];
        }

        if (k == 1) {
            return Math.min(a[aLeft], b[bLeft]);
        }

// recursive rules
        int aRight = aLeft + k / 2 - 1;
        int bRight = bLeft + k / 2 - 1;

        int aVal = aRight >= a.length ? Integer.MAX_VALUE : a[aRight];
        int bVal = bRight >= b.length ? Integer.MAX_VALUE : b[bRight];

        if (aVal <= bVal) {
            return kthHelper(a, aRight + 1, b, bLeft, k - k / 2);
        } else {
            return kthHelper(a, aLeft, b, bRight + 1, k - k / 2);
        }

    }
}
