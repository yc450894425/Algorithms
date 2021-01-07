import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class SevenPuzzle {
    private final static int[][] DIR = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static class Board {
        private final static int R = 2;
        private final static int C = 4;
        private int[][] board = new int[R][C];

        public Board() {
        }

        public Board(int[] values) {
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    board[i][j] = values[i * C + j];
                }
            }
        }
        public int[] findZero() {
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if (board[i][j] == 0) {
                        return new int[]{i, j};
                    }
                }
            }
            return null;
        }

        public boolean outOfBound(int i, int j) {
            return i < 0 || i >= R || j < 0 || j >= C;
        }

        public void swap(int i1, int j1, int i2, int j2) {
            int tmp = board[i1][j1];
            board[i1][j1] = board[i2][j2];
            board[i2][j2] = tmp;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Board)) {
                return false;
            }
            Board another = (Board) obj;
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if (this.board[i][j] != another.board[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        @Override
        public int hashCode() {
            int code = 0;
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    code = code * 31 + board[i][j];
                }
            }
            return code;
        }
        // Deep Copy
        @Override
        public Board clone() {
            Board c = new Board();
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    c.board[i][j] = board[i][j];
                }
            }
            return c;
        }
    }



    public int numOfSteps(int[] values) {
        // corner cases
        Map<Board, Integer> steps = new HashMap<>();
        Queue<Board> queue = new ArrayDeque<>();

        Board start = new Board(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        steps.put(start, 0);
        queue.offer(start);

        while (!queue.isEmpty()) {
            Board curr = queue.poll();
            int step = steps.get(curr);

            int[] zero = curr.findZero();
            int zeroI = zero[0];
            int zeroJ = zero[1];

            for (int[] dir : DIR) {
                int i = zeroI + dir[0];
                int j = zeroJ + dir[1];

                if (!curr.outOfBound(i, j)) {
                    curr.swap(zeroI, zeroJ, i, j);
                    if (!steps.containsKey(curr)) {
                        Board next = curr.clone();
                        steps.put(next, step + 1);
                        queue.offer(next);
                    }
                    curr.swap(zeroI, zeroJ, i, j);
                }
            }
        }
        return steps.getOrDefault(new Board(values), -1);
    }
}
