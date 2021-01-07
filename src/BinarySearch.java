public class BinarySearch {
    public int classicalBinarySearch(int[] array, int target) {
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

    // O(m + n)
    public int[] searchInSortedMatrix(int[][] m, int target) {
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

    public int closest(int[] array, int target) {
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

    public int firstOccur(int[] array, int target) {
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

    public int[] kClosest(int[] array, int target, int k) {
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
    public int[] kClosetMethodTwo(int[] array, int target, int k) {
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

    public int largestSmaller(int[] array, int target) {
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
                r = mid;
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
}
