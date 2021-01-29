import java.sql.Time;

public class Class2RecursionIAndSortingAlgorithms {

    public static int fibonacci(int k) {
        // corner cases
        if (k <= 0) {
            return 0;
        }
        if (k == 1) {
            return 1;
        }
        return fibonacci(k - 1) + fibonacci(k - 2);
    }

    //    624. Fibonacci Number Lite
//    C:	input => int a, int b
//    output => long result
//    A:	b is >= 0
//    result fits in memory and is not overflowed;
//	0^0 is 1
//    R:	Recursion
//    long half = a ^ (b / 2);
//	if b is odd, return half * half * a;
//	if b is even, return half * half;
//    recursion tree
//            b
//		    |
//            b/2
//            |
//            b/4
//            |
//            ...
//            2
//            |
//            1
//            |
//            0 => return 1
//    levels: log2(b) + 1
//    nodes: log2(b) + 1
//    time in each level: O(1)
//    space in each level: O(1) for half
//    Time Complexity: O(logb)
//    Space Complexity: O(logb)
    public static long power(int a, int b) {
        // corner cases
        if (b == 0) {
            return 1;
        }
        if (a == 0) {
            return 0;
        }
        long half = power(a, b / 2);
        if (b % 2 == 0) {
            return half * half;
        } else {
            return half * half * a;
        }
    }

    //    C:	input => int[] array
//    output => int[] result
//    A:	array not null, not empty
//    fit in memory
//    R:
//    HighLevel:
//    Iteration; Selection Sort;
//    In every iteration, we pick up the smallest ele in the unsorted area, and move it to the head of the unsorted area;
//
//    DataStructure:
//    int i => outer loop, to determine the correct ele at index i
//    int j => inner loop, to find the curr smallest ele
//    int currMin => to record the smallest ele
//		[i, n - 1] => the unsorted area
//
//    Time: i == 0, inner loop n times;
//    i == 1, inner loop n - 1 times;
//		...
//    i == n - 2, inner loop 2 times;
//
//    Time complexity: O(2 + 3 + ... + n - 1 + n) = O(n/2 * (n + 1) - 1) = O(n^2)
//    Space complexity: O(1) for i, j, currMin
    public static int[] selectionSort(int[] array) {
        // corner cases
        if (array == null || array.length <= 1) {
            return array;
        }

        for (int i = 0; i < array.length - 1; i++) {
            int currMin = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[currMin]) {
                    currMin = j;
                }
            }
            swap(array, i, currMin);
        }
        return array;
    }

    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

//    Highlevel: We cut the input array into two parts, and assume the two subarray is somehow magically sorted. Then we merge the two subarray into one array.
//
// 				5 2 7 4 1 3 8 6
// 	    		/				\		            O(1)
// 		    5 2 7 4				1 3 8 6
//     		/	\				/	\               O(2)
// 		  52	  74	      13	  86
//     	/	|	|	\		/	|	|	\		    O(4)
// 											     ...O(n/2)
//     	5	2	7	4		1	3	8	6
// 										         ...O(n)
//     	\	|	|	/		\	|	|	/	        O(n)
// 	    25	    47	          13	 68
// 	    	\	/				\	/               O(n)
// 		    2 4 5 7				1 3 6 8
// 		    	\				/                   O(n)
// 	  		    1 2 3 4 5 6 7 8
//
//    Time Complexity: O(nlogn)
//    top part: O(1 + 2 + 4 + ... + n/2) = O((1 - n)/(-1)) = O(n)
//    bottom part: O(n * logn) = O(nlogn)
//    Space Complexity: O(n)
//    top part: O(1) * logn = O(logn)
//    bottom part: O(1 + 2 + 4 + ... + n/2) = O(n)

    public static int[] mergeSortBadImplementation(int[] array) {
        // corner cases
        if (array == null || array.length <= 1) {
            return array;
        }

        return mergeSortHelperI(array, 0, array.length - 1);
    }

    private static int[] mergeSortHelperI(int[] array, int left, int right) {
        // base cases
        if (left == right) {
            return new int[]{array[left]};
        }
        // recursive rules
        int mid = left + (right - left) / 2;
        int[] leftResult = mergeSortHelperI(array, left, mid);
        int[] rightResult = mergeSortHelperI(array, mid + 1, right);
        return mergeI(leftResult, rightResult);
    }

    private static int[] mergeI(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0;
        int j = 0;
        for (int k = 0; k < result.length; k++) {
            if (j >= right.length || i < left.length && left[i] <= right[j]) {
                result[k] = left[i++];
            } else {
                result[k] = right[j++];
            }
        }
        return result;
    }

    public static int[] mergeSortGoodImplementation(int[] array) {
        // corner cases
        if (array == null || array.length <= 1) {
            return array;
        }
        int[] helper = new int[array.length];
        mergeSortHelperII(array, 0, array.length - 1, helper);
        return array;
    }

    private static void mergeSortHelperII(int[] array, int left, int right, int[] helper) {
        // base cases
        if (left == right) {
            return;
        }
        // recursive rules
        int mid = left + (right - left) / 2;
        mergeSortHelperII(array, left, mid, helper);
        mergeSortHelperII(array, mid + 1, right, helper);
        mergeII(array, left, mid, right, helper);
    }

    // 2 4 5 7 | 1 3 6 8
    // l     m         r
    // ls        rs
    private static void mergeII(int[] array, int left, int mid, int right, int[] helper) {
        // copy the content to helper array and we will merge from the helper array.
        copy(array, helper, left, right);
        int lStart = left;
        int rStart = mid + 1;
        int start = left;
        while (lStart <= mid && rStart <= right) {
            if (helper[lStart] <= helper[rStart]) {
                array[start++] = helper[lStart++];
            } else {
                array[start++] = helper[rStart++];
            }
        }
        // post-processing
        // if we still have some elements at left side, we need to copy them;
        while (lStart <= mid) {
            array[start++] = helper[lStart++];
        }
        // if there are some elements at right side, we don't need to copy them.
        // because they are already in their positions.
    }

    private static void copy(int[] origin, int[] copy, int left, int right) {
        while (left <= right && left < origin.length) {
            copy[left] = origin[left];
            left++;
        }
    }

    //    High level idea: We pick an element randomly as pivot, and put the pivot to its “correct” position, where all smaller elements are at its left side, and all larger or equal elements are at its right side. Then we repeat this operation toward its left side elements and right side elements till every element is at the “correct” position.
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
    public int[] quickSort(int[] array) {
        // corner cases
        if (array == null || array.length <= 1) {
            return array;
        }

        quickSort(array, 0, array.length - 1);
        return array;
    }

    private void quickSort(int[] array, int left, int right) {
        // base cases
        if (left >= right) {
            return;
        }
        // recursive cases
        int pivot = partition(array, left, right);
        quickSort(array, left, pivot - 1);
        quickSort(array, pivot + 1, right);
    }

    // pick a random pivot, put it to the correct position, then return the pivot index.
    private int partition(int[] array, int left, int right) {
        int pivot = left + (int) (Math.random() * (right - left + 1));
        swap(array, pivot, right);
        // elements at left side of i (not including i) are elements smaller than pivot
        int i = left;
        // elements at right side of j (not including j) are elements larger than or equal to pivot
        int j = right - 1;
        // [i, j] is the unknown area
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

//    int i: all elements at left side of i (not including i) are non-0 integers;
//    int j: all elements at right side of j (not including j) are 0s;
//    [i, j] is the unknown area;
    public int[] moveZero(int[] array) {
        // corner cases
        if (array == null || array.length <= 1) {
            return array;
        }

        int i = 0;
        int j = array.length - 1;
        while (i <= j) {
            if (array[i] != 0) {
                i++;
            } else if (array[j] == 0) {
                j--;
            } else {
                swap(array, i++, j--);
            }
        }
        return array;
    }

//    Rainbow Sort
//    int i: all elements in [0, i) is -1;
//    int j: all elements in [i, j) is 0;
//    int k: all elements in (k, array.length - 1) is 1;
//    [j, k] is the unknown area;
//
//    -1 -1 0 0 xxxx 1 1 1
//          i   j  k
    public int[] rainbowSort(int[] array) {
        // corner cases
        if (array == null || array.length <= 1) {
            return array;
        }

        int i = 0;
        int j = 0;
        int k = array.length - 1;
        while (j <= k) {
            if (array[j] == -1) {
                swap(array, i++, j++);
            } else if (array[j] == 0) {
                j++;
            } else {
                swap(array, j, k--);
            }
        }
        return array;
    }
    
}
