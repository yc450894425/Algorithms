

import java.util.ArrayList;
import java.util.List;
//        input -> int n
//        output -> List<List<Integer>> result
//        every queen occupies one row.
//        List<Integer> list[i] represents the queen is at the i-th row and the list[i]-th column.
//        e.g. list[2] = 3, means this queen is at [2][3].
//
//        0xxxxxxx
//        xx1xxxxx
//
//
//        DFS:
//        n levels
//        every node n branches.
//        every level determines one queen’s position.
//        every node is a calling of the recursion function
//        base case:
//        index == n, print add this solution to result
//        recursive rule:
//        we try every possible position for this row and check if it’s valid. If valid, we add it into the current solution.
//        for check, traverse the solution, so time O(solution.length) = O(n);
//        for printing, the time O(solution.length) = O(n);
//
//        Time: (n + n^2 + n^3 + … + n^(n - 1)) * n + n^n * n = O(n^(n + 1)) = O(n^n).
//        Space: for the recursion function, O(1), and n levels, space is O(n).
//
//
//        DFS(0)
//        /		||||||		\		(n nodes)
//        DFS(1)
//        ||||||||							(n^2 nodes)
//        DFS(2)
//        |
//        …
//        |
//        DFS(n - 1)
//        |
//        DFS(n)							(n^n nodes)
//        O(n) for printing


public class NQueen {
    public List<List<Integer>> NQueen(int n) {
        // corner cases
        List<List<Integer>> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        NQueen(n, 0, new ArrayList<>(), result);
        return result;
    }

    private void NQueen(int n, int index, List<Integer> sol, List<List<Integer>> result) {
        // base cases
        if (index == n) {
            result.add(new ArrayList<>(sol));
            return;
        }

// recursive rule
        for (int i = 0; i < n; i++) {
            if (isValid(sol, i)) {
                sol.add(i);
                NQueen(n, index + 1, sol, result);
                sol.remove(sol.size() - 1);
            }
        }
    }

    private boolean isValid(List<Integer> sol, int i) {
        for (int j = 0; j < sol.size(); j++) {
            if (sol.get(j) == i || Math.abs(i - sol.get(j)) == sol.size() - j) {
                return false;
            }
        }
        return true;
    }

}
