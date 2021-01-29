public class Sort {

    // high level: Every time we select the smallest elements in the subarray of (i, array.length - 1) and put it in the current place, then move forward; Everytime we select the smallest elements in the unexplored area, and swap it with the first position of unexplored area.
    // time complexity: n + (n - 1) + (n - 2) + ... + 2 + 1 = n/2 * (n + 1) = O(n^2);
    // space complexity: O(1), in place
    public int[] selectionSort(int[] array) {
        // corner cases
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            swap(array, i, minIndex);
        }
        return array;
    }

    private void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    // high level: Recursion. We divide the input array into left portion and right portion. And we assume every portion is sorted “magically”. Then we merge the two sorted portions into a sorted array. And we repeat this process toward the left portion and the right portion.
    // time complexity:
    //			8 6 7 3 5 2 4 1						O(1)
    //			8673		5241						O(2)...
    //      86		73	52		41			O(2^(logn))
    //     8 	6	7	3	5	2	4	1			O(n)
    //      68		37	25		14				O(n)
    //          3678		1245						O(n)...
    //            12345678
    //
    //
    //    O(1 + 2 + 4 + ... + 2^(logn)) + O(n)*logn = (1-2^logn)/(-1)+O(nlogn)
    //= O(n) + O(nlogn) = O(nlogn)
    // space conplexity: O(1 + 2 + 4 + ... + 2^(logn - 1)) = O((1-n/2)/(-1)) = O(n)
    public int[] mergeSort(int[] array) {
        // corner cases
        if (array == null || array.length <= 1) {
            return array;
        }

        return mergeSortHelper(array, 0, array.length - 1);
    }

    // The semantic of mergeSortHelper is sort the [left, right] area of the input array.
    private int[] mergeSortHelper(int[] array, int left, int right) {
        // base cases
        if (left == right) {
            return new int[]{array[left]};
        }

        // recursive rules
        int mid = left + (right - left) / 2;
        // [left, mid]
        int[] leftResult = mergeSortHelper(array, left, mid);
        // (mid, right]
        int[] rightResult = mergeSortHelper(array, mid + 1, right);

        return merge(leftResult, rightResult);
    }

    private int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left.length || j < right.length) {
            if (j >= right.length || i < left.length && left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        return result;
    }
// High level idea: We pick an element randomly as pivot, and put the pivot to its “correct” position, where all smaller elements are at its left side, and all larger or equal elements are at its right side. Then we repeat this operation toward its left side elements and right side elements till every element is at the “correct” position.
//    Time Complexity:
//
//            86735241						    O(n)
//		4312	   5(p)		876					O(n)
//	1	2(p)  43   5(p)    6(p)    87			O(n)
//	1	2	3 	4	5   6   7	8
//
//    average: logn levels, O(nlogn)
//    worst: n levels, O(n^2)
//    Space Complexity:
//    heap => O(1), in place
//    stack => O(logn) in average, O(n) in worst case
    public int[] quickSort(int[] array){
        // corner cases
        if (array == null || array.length <= 1) {
            return array;
        }

        quickSortHelper(array, 0, array.length - 1);
        return array;
    }

    private void quickSortHelper(int[] array, int left, int right){
        // base cases
        if (left >= right) {
            return;
        }

        // recursive rules
        int pivotIndex = partition(array, left, right);
        quickSortHelper(array, left, pivotIndex - 1);
        quickSortHelper(array, pivotIndex + 1, right);
    }

    // pick a random pivot, put it to the correct position, then return the pivot index.
    private int partition(int[] array, int left, int right){
        int pivotIndex = (int)(Math.random() * (right - left + 1)) + left;
        int pivot = array[pivotIndex];
        swap(array, pivotIndex, right);
        // elements at left side of i (not including i) are elements smaller than pivot
        int i = left;
        // elements at right side of j (not including j) are elements larger than or equal to pivot
        int j = right - 1;
        // [i, j] is the unknown area

        while (i <= j) {
            if (array[i] < pivot) {
                i++;
            } else if (array[j] >= pivot) {
                j--;
            } else {
                // array[i] >= pivot && array[j] < pivot
                swap(array, i++, j--);
            }
        }
        swap(array, i, right);
        return i;
    }
}
