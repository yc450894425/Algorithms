//171 Common Elements in Three Sorted Array
//        input -> int[] a, b, c; not null, each one can fit in memory
//        output -> list<Integer>

import java.util.ArrayList;
import java.util.List;

public class CommonElementsInThreeSortedArray {
    public List<Integer> common(int[] a, int[] b, int[]c) {
        List<Integer> result = new ArrayList<>();
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < a.length && j < b.length && k < c.length) {
            if (a[i] < b[j] || a[i] < c[k]) {
                i++;
            }
            else if (b[j] < a[i] || b[j] < c[k]) {
                j++;
            }
            else if (c[k] < a[i] || c[k] < b[j]) {
                k++;
            }
            else {
                result.add(a[i]);
                i++;
                j++;
                k++;
            }
        }
        return result;
    }
}
