import java.util.ArrayList;
import java.util.List;

public class AllDecodeWays {

    public List<String> allDecodeWays(String string) {
        // corner cases
        if (string == null) {
            return new ArrayList<>();
        }

        char[] array = string.toCharArray();
        List<String> result = new ArrayList<>();
        DFSHelper(array, 0, new StringBuilder(), result);
        return result;
    }

    private void DFSHelper(char[] array, int index, StringBuilder sb, List<String> result) {
        // base cases
        if (index == array.length) {
            result.add(new String(sb));
            return;
        }
        // pruning
        if (array[index] == '0') {
            return;
        }
        // recursive rules
        for (int i = index; i < array.length; i++) {
            Character curr = isValid(array, index, i);
            if (curr == null) {
                break;
            }
            sb.append(curr);
            DFSHelper(array, i + 1, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    private Character isValid(char[] array, int s, int e) {
        int num = toNumber(array, s, e);
        if (num >= 1 && num <= 26) {
            return ((char)('a' + num - 1));
        }
        return null;
    }
    private int toNumber(char[] array, int s, int e) {
        int num = 0;
        for (int i = s; i <= e; i++) {
            num = num * 10 + (array[i] - '0');
        }
        return num;
    }
}
