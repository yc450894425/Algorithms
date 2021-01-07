import java.util.ArrayList;
import java.util.List;
//644 Common Elements in K Sorted Lists
//        n lists, m average length
//        l1, l2 -> l12(m)				O(2m)
//        l3  -> l13(m)		O(2m)
//        ...
//        ln(m) -> l1n  	O(2m)
//
//
//
//        tc: 2m * (n - 1) -> O(mn)
//        sc: O(m) -> O(1) (if the garbage collection is perfect)

public class CommonElementsInKSortedArray {
    public List<Integer> commonElementsInKSortedArrays(List<List<Integer>> input) {
        // corner cases
        List<Integer> result = new ArrayList<>();
        if (input.size() == 0) {
            return result;
        }
        result = input.get(0);
        for (int i = 1; i < input.size(); i++) {
            result = findCommon(result, input.get(i));
        }
        return result;
    }

    private List<Integer> findCommon(List<Integer> a, List<Integer> b) {
        List<Integer> result = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < a.size() && j < b.size()) {
            if (a.get(i) < b.get(j)) {
                i++;
            } else if (a.get(i) > b.get(j)) {
                j++;
            } else {
                result.add(a.get(i));
                i++;
                j++;
            }
        }
        return result;
    }
}
