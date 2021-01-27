import javax.xml.crypto.Data;
import java.sql.Time;

public class BinarySearch {

    //    Input => int[] array, int target
//    output => int index
//    Assumptions: array not null, fit in memory, return -1 if cannot find.
//    If there are duplicate elements, anyone is ok.
//
//    High level: Two pointers. The size of search space [l, r] is reduced roughly by one half of the previous iteration and the sp is reduced to 2, and then to 1 eventually.
//    Data structure: Two pointers l and r. [l, r] is the search space.
//    Initialization: int left = 0; int right = 0;
//    For each step:
//    int mid = left + (right - left) / 2; // to avoid overflow
//	if (array[mid] == target) {
//        return mid;
//    } else if (array[mid] < target) {
//        left = mid + 1;
//    } else {
//        right = mid - 1;
//    }
//    Termination: left > right
//    Post-processing: if cannot find, return -1.
//
//    If array size is n.
//    Time Complexity: O(log(2)n). Because we shrink the search space to half till the search space size is reduced to 2, 1.
//    Auxiliary Space Complexity: O(1).
    public static int classicalBinarySearch(int[] array, int target) {
        // corner cases
        if (array == null || array.length == 0) {
            return -1;
        }
        int l = 0;
        int r = array.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }

    //    C: 	input => int[][] matrix, int target
//    output => int[] coord
//    A:	matrix not null, not empty, fit in memory
//	return arbitrary one if there are duplicate elements
//	return -1 if not exist
//
//    R:	High level: We start at the bottom-left corner element of the matrix, compare the target with this element. If target is larger, delete all elements at right side of the element from search space; If target is smaller, delete all elements on top of the element from sp. If target is equal to this element, return its coordinate.
//    Data Structure: Two indices.
//    Initilization: int i = matrix.length - 1; int j = 0;
//    For each step:
//            if (m[i][j] == target) {
//        return new int[]{i, j}
//    } else if (m[i][j] < target) {
//        j++;
//    } else {
//        i--;
//    }
//    Termination: i < 0 || j >= matrix[0].length
//    Post-processing: return new int[]{-1, -1};
//
//    If we assume m rows and n cols.
//    Time Complexity: O(m + n)
//    Auxiliary Space Complexity: O(2) => O(1)
    public static int[] searchInSortedMatrix(int[][] m, int target) {
        // corner cases
        if (m == null || m.length == 0 || m[0].length == 0) {
            return new int[]{-1, -1};
        }

        int i = m.length - 1;
        int j = 0;
        while (i >= 0 && j <= m[0].length - 1) {
            if (m[i][j] == target) {
                return new int[]{i, j};
            } else if (m[i][j] > target) {
                i--;
            } else {
                j++;
            }
        }
        return new int[]{-1, -1};
    }

    public static int closest(int[] array, int target) {
        // corner cases
        if (array == null || array.length == 0) {
            return -1;
        }
        int l = 0;
        int r = array.length - 1;
        while (l < r - 1) {
            int mid = l + (r - l) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] > target) {
                r = mid;
            } else {
                l = mid;
            }
        }
        if (Math.abs(array[l] - target) <= Math.abs(array[r] - target)) {
            return l;
        } else {
            return r;
        }
    }

    public static int firstOccur(int[] array, int target) {
        // corner cases
        if (array == null || array.length == 0) {
            return -1;
        }
        int l = 0;
        int r = array.length - 1;
        while (l < r - 1) {
            int mid = l + (r - l) / 2;
            if (array[mid] >= target) {
                r = mid;
            } else {
                // l = mid + 1 is ok
                l = mid;
            }
        }

        // post-processing
        if (array[l] == target) {
            return l;
        } else if (array[r] == target) {
            return r;
        } else {
            return -1;
        }
    }

    public static int[] kClosestMethodOne(int[] array, int target, int k) {
        // corner cases
        if (array == null || array.length == 0) {
            return new int[]{};
        }

        int left = closest(array, target);
        int right = left + 1;
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            // if there is a tie, the smaller elements are always preferred.
            if (right >= array.length || left >= 0 && (target - array[left]) <= (array[right] - target)) {
                result[i] = array[left--];
            } else {
                result[i] = array[right++];
            }
        }
        return result;
    }

    // It's correct but not good practice.
    public static int[] kClosetMethodTwo(int[] array, int target, int k) {
        // corner cases
        if (array == null || array.length == 0) {
            return new int[]{};
        }
        int l = 0;
        int r = array.length - 1;

        while (l < r - 1) {
            int mid = l + (r - l) / 2;
            if (array[mid] == target) {
                l = mid;
                r = l + 1;
                break;
            } else if (array[mid] < target) {
                l = mid;
            } else {
                r = mid;
            }
        }

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            // if there is a tie, the smaller elements are always preferred.
            if (r >= array.length || l >= 0 && Math.abs(target - array[l]) <= Math.abs(array[r] - target)) {
                result[i] = array[l--];
            } else {
                result[i] = array[r++];
            }
        }
        return result;
    }

    public static int[] kClosestMethodThree(int[] array, int target, int k) {
        // corner cases
        if (array == null || array.length == 0) {
            return new int[]{};
        }

        int l = largestSmaller(array, target);
        int r = l + 1;
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            // add array[l] to result
            if (r >= array.length || l >= 0 && target - array[l] <= array[r] - target) {
                result[i] = array[l--];
            } else {
                result[i] = array[r++];
            }
        }
        return result;
    }

    public static int largestSmaller(int[] array, int target) {
        // corner cases
        if (array == null || array.length == 0) {
            return -1;
        }

        int l = 0;
        int r = array.length - 1;
        while (l < r - 1) {
            int mid = l + (r - l) / 2;
            if (array[mid] < target) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }

        if (array[r] < target) {
            return r;
        } else if (array[l] < target) {
            return l;
        } else {
            return -1;
        }
    }

    public static int smallestElementLargerThanTarget(int[] array, int target) {
        // corner cases
        if (array == null || array.length == 0) {
            return -1;
        }

        int l = 0;
        int r = array.length - 1;
        while (l < r - 1) {
            int mid = l + (r - l) / 2;
            if (array[mid] > target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        // post-processing
        if (array[l] > target) {
            return l;
        }
        if (array[r] > target) {
            return r;
        }
        return -1;
    }



}
