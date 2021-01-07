import java.util.List;

public class Permutation {
    private void DFS(char[] array, int index, List<String> result) {
        // base case
        if (index == array.length) {
            result.add(new String(array));
        }
    }
}
