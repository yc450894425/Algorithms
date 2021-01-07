import java.util.ArrayList;
import java.util.List;

public class WorkPlan {
    public List<List<Integer>> workPlan(int maxDays, int limit) {
        List<List<Integer>> result = new ArrayList<>();
        // corner cases
        DFS(1, limit, maxDays, result, new ArrayList<>());
        return result;
    }

    private void DFS(int index, int limit, int maxDays, List<List<Integer>> result, List<Integer> sol) {
        // base cases
        if (index == 32 || sol.size() == maxDays) {
            if (sol.size() == maxDays) {
                result.add(new ArrayList<>(sol));
            }
            return;
        }

// recursive rules
        if (sol.size() >= limit && index - sol.get(sol.size() - limit) == limit) {
            DFS(index + 1, limit, maxDays, result, sol);
            return;
        }
        sol.add(index);
        DFS(index + 1, limit, maxDays, result, sol);
        sol.remove(sol.size() - 1);
        DFS(index + 1, limit, maxDays, result, sol);
    }
}
