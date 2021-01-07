import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Common {
    public List<Integer> common(int[] a, int[] b) {
        List<Integer> result = new ArrayList<>();
        HashSet<Integer> set = toSet(a);
        for (int n : b) {
            if (set.contains(n)) {
                result.add(n);
            }
        }
        Collections.sort(result);
        return result;
    }

    private HashSet<Integer> toSet(int[] a) {
        HashSet<Integer> set = new HashSet<>();
        for (int n : a) {
            set.add(n);
        }
        return set;
    }
}
