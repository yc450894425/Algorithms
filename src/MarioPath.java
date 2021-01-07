import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarioPath {
    public List<List<Integer>> path(int[][] cave, int maxSteps) {
        // corner cases
        List<List<List<Integer>>> result = new ArrayList<>();
        List<List<Integer>> defaultCoord = new ArrayList<>();
        defaultCoord.add(Arrays.asList(999, 999));
        result.add(defaultCoord);
        int[] maxGold = new int[]{0};
        boolean[][] visited = new boolean[cave.length][cave[0].length];
        DFS(maxSteps, maxGold, 0, new Cell(0, 0, cave[0][0] - 1), cave, result, new ArrayList<>(), visited);
        return result.get(0);
    }
    private void DFS(int steps, int[] maxGold, int gold, Cell curr, int[][] cave, List<List<List<Integer>>> result, List<List<Integer>> currPath, boolean[][] visited) {
        // base cases
        if (steps == 0 || success(curr, cave)) {
            if (success(curr, cave) && gold + curr.gold >= maxGold[0]) {
                maxGold[0] = gold + curr.gold;
                currPath.add(Arrays.asList(curr.x, curr.y));
                currPath.add(Arrays.asList(maxGold[0]));
                result.clear();
                result.add(new ArrayList<>(currPath));
                currPath.remove(currPath.size() - 1);
                currPath.remove(currPath.size() - 1);
            }
            return;
        }

        // recursive rules
        currPath.add(Arrays.asList(curr.x, curr.y));
        visited[curr.x][curr.y] = true;
        if (curr.x > 0 && !visited[curr.x - 1][curr.y] && cave[curr.x - 1][curr.y] > 0) {
            DFS(steps - 1, maxGold, gold + cave[curr.x - 1][curr.y] - 1, new Cell(curr.x - 1, curr.y,cave[curr.x - 1][curr.y] - 1), cave, result, currPath, visited);
        }
        if (curr.x < cave.length - 1 && !visited[curr.x + 1][curr.y] && cave[curr.x + 1][curr.y] > 0) {
            DFS(steps - 1, maxGold, gold + cave[curr.x + 1][curr.y] - 1, new Cell(curr.x + 1, curr.y,cave[curr.x + 1][curr.y] - 1), cave, result, currPath, visited);
        }
        if (curr.y > 0 && !visited[curr.x][curr.y - 1] && cave[curr.x][curr.y - 1] > 0) {
            DFS(steps - 1, maxGold, gold + cave[curr.x][curr.y - 1] - 1, new Cell(curr.x, curr.y - 1,cave[curr.x][curr.y - 1] - 1), cave, result, currPath, visited);
        }
        if (curr.y < cave[0].length - 1 && !visited[curr.x][curr.y + 1] && cave[curr.x][curr.y + 1] > 0) {
            DFS(steps - 1, maxGold, gold + cave[curr.x][curr.y + 1] - 1, new Cell(curr.x, curr.y + 1,cave[curr.x][curr.y + 1] - 1), cave, result, currPath, visited);
        }
        visited[curr.x][curr.y] = false;
        currPath.remove(currPath.size() - 1);
    }

    private boolean success(Cell curr, int[][] matrix) {
        return curr.x == matrix.length - 1 && curr.y == matrix[0].length - 1;
    }

    class Cell {
        int x;
        int y;
        int gold;
        public Cell(int x, int y, int gold) {
            this.x = x;
            this.y = y;
            this.gold = gold;
        }
    }
}
