//199
//        input -> int[] array
//        output -> int water
//        assumptions: array not null, not empty, not negative, fit in memory.
//        iterative way
//        for every bar, find its left bound and right bound, the water at this bar is (min(left, right) - height of bar) (if > 0).
//        we need to traverse the array for every index, so time is n^2.

public class MaxTrapped {
    public int naive(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int water = 0;
        for (int i = 0; i < array.length; i++) {
            // find left bound
            int left = 0;
            for (int j = i - 1; j >= 0; j--) {
                left = array[j] > left ? array[j] : left;
            }
            // find right bound
            int right = 0;
            for (int k = i + 1; k < array.length; k++) {
                right = array[k] > right ? array[k] : right;
            }
            int curr = Math.min(left, right) - array[i];
            if (curr >= 0) {
                water += curr;
            }
        }
        return water;
    }

    public int dp(int[] array) {
        if (array.length == 0) {
            return 0;
        }
// left[i] represents the height of the highest bar at the left side of array[i] (including array[i] itself)
        int[] left = new int[array.length];
        int[] right = new int[array.length];
        left[0] = array[0];
        right[array.length - 1] = array[array.length - 1];
        for (int i = 1; i < left.length; i++) {
            left[i] = array[i] > left[i - 1] ? array[i] : left[i - 1];
        }
        for (int i = array.length - 2; i >= 0; i--) {
            right[i] = array[i] > right[i + 1] ? array[i] : right[i + 1];
        }
        int water = 0;
        for (int i = 0; i < array.length; i++) {
            int curr = Math.min(left[i], right[i]) - array[i];
            //curr must be >= 0
            water += curr;
        }
        return water;
    }

    public int maxTrapped(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int i = 0;
        int j = array.length - 1;
// leftMax represents height of the highest bar on the left of i, including i;
        int leftMax = 0;
// rightMax represents the height of the highest bar on the right of j, including j;
        int rightMax = 0;
        int water = 0;
        while (i <= j) {
            // maintain semantic of leftMax and rightMax
            leftMax = Math.max(leftMax, array[i]);
            rightMax = Math.max(rightMax, array[j]);
            if (leftMax <= rightMax) {
                water += (leftMax - array[i]);
                i++;
            } else {
                water += (rightMax - array[j]);
                j--;
            }
        }
        return water;
    }


}
