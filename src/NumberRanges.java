import java.util.List;

public class NumberRanges {
    public String ranges(List<Integer> num) {
        StringBuilder sb = new StringBuilder();
        int size = num.get(0);
        int s = 1;
        int e = 1;
        while (e <= size) {
            while (e + 1 <= size && num.get(e + 1) == num.get(e) + 1) {
                e++;
            }
            if (s == e) {
                sb.append(num.get(s));
                sb.append(",");
            } else {
                sb.append(num.get(s));
                sb.append("-");
                sb.append(num.get(e));
                sb.append(",");
            }
            s = e + 1;
            e = e + 1;
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
