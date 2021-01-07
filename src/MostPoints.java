import java.util.HashMap;
import java.util.Map;

public class MostPoints {
    public int most(Point[] points) {
        int result = 0;
        for (int i = 0; i < points.length; i++) {
            Point curr = points[i];
            int same = 0;
            int perpend = 0;
            int most = 0;
            // key: slope, value: counter
            Map<Double, Integer> map = new HashMap<>();
            for (int j = 0; j < points.length; j++) {
                Point tmp = points[j];
                if (tmp.x == curr.x && tmp.y == curr.y) {
                    same++;
                } else if (tmp.x == curr.x) {
                    perpend++;
                } else {
                    double slope = (double)(tmp.y - curr.y)/(double)(tmp.x - curr.x);
                    map.put(slope, map.getOrDefault(slope, 0) + 1);
                    most = Math.max(most, map.get(slope));
                }
            }
            most = Math.max(most, perpend) + same;
            result = Math.max(result,most);
        }
        return result;
    }
}
