import java.util.Arrays;

public class KSmallestInUnsortedArray {
    public int[] kSmallest(int[] array, int k) {
        // corner cases
        if (array.length == 0 || k == 0) {
            return new int[0];
        }
        quickSelect(array, 0, array.length - 1, k - 1);
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = array[i];
        }
        Arrays.sort(result);
        return result;
    }

    private void quickSelect(int[] array, int left, int right, int target) {
        int mid = partition(array, left, right);

        // key point: left <= mid <= right, left <= target <= right;
        // is there any possibility that mid - 1 or mid + 1 is outofbound?
        // No. Because only if mid <= 0 or mid >= k - 1, they will be outofbound;
        // But since mid > target (target >= 0), mid cannot be 0 or negative;
        // Since mid < target (target <= k - 1), mid cannot be k - 1 or larger;
        // Thus, there is no possibility of outofbound.
        // Similarly, left cannot be smaller than right;
        // because mid - 1 cannot < left; mid + 1 cannot > right;
        if (mid > target) {
            // why “mid - 1” or "mid + 1"? Is it Ok if just “mid”?
            // NO!
            // 1. mid cannot be the target here;
            // 2. we must guarantee the search space decreases over time;
            // 	if use “mid” not “mid - 1” or "mid + 1", the search space might
            //	not decrease and dead loop occurs
            // 	when mid == right || mid == left;
            // 3. we must guarantee the target cannot be ruled out
            // accidentally;
            //	Since the mid cannot be the target, we can safely rule
            //	it out;
            quickSelect(array, left, mid - 1, target);
        } else if (mid < target) {
            quickSelect(array, mid + 1, right, target);
        }
    }

    private int partition(int[] array, int left, int right) {
        int pivot = (int)(Math.random() * (right - left + 1)) + left;
        swap(array, pivot, right);
        int i = left;
        int j = right - 1;
        while (i <= j) {
            if (array[i] < array[right]) {
                i++;
            } else if (array[j] >= array[right]) {
                j--;
            } else {
                swap(array, i++, j--);
            }
        }
        swap(array, i, right);
        return i;
    }

    private void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
