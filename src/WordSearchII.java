import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordSearchII {

    static final int[][] DIRS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public List<String> findWordsDFS(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        // corner cases
        if (board == null || board.length == 0 || board[0].length == 0 || words == null || words.length == 0) {
            return result;
        }
        for (String word : words) {
            if (exist(board, word)) {
                result.add(word);
            }
        }
        return result;
    }

    private boolean exist(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;
        boolean[][] visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (DFS(board, i, j, 0, word, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean DFS(char[][] board, int x, int y, int index, String word, boolean[][] visited) {
        // base cases
        if (index == word.length()) {
            return true;
        }

        int rows = board.length;
        int cols = board[0].length;
        if (x < 0 || x >= rows || y < 0 || y >= cols || visited[x][y] || board[x][y] != word.charAt(index)) {
            return false;
        }
        visited[x][y] = true;
        for (int[] dir : DIRS) {
            int neiX = dir[0] + x;
            int neiY = dir[1] + y;
            if (DFS(board, neiX, neiY, index + 1, word, visited)) {
                return true;
            }
        }
        visited[x][y] = false;
        return false;
    }

    public Set<String> findWordsTrie(char[][] board, String[] words) {
        Set<String> result = new HashSet<>();
        // corner cases
        if (board == null || board.length == 0 || board[0].length == 0 || words == null || words.length == 0) {
            return result;
        }
        TrieNode root = buildTrie(words);
        int rows = board.length;
        int cols = board[0].length;
        boolean[][] visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TrieDFS(board, i, j, result, visited, root, new StringBuilder());
            }
        }
        return result;
    }

    private void TrieDFS(char[][] board, int x, int y, Set<String> result, boolean[][] visited, TrieNode node, StringBuilder sb) {

        if (x < 0 || y < 0 || x >= board.length || y >= board[0].length || visited[x][y] || node.children[board[x][y] - 'a'] == null) {
            return;
        }
        node = node.children[board[x][y] - 'a'];
        sb.append(board[x][y]);
        visited[x][y] = true;
        /*  为什么要在这里check isWord而不是刚进来就判断呢？
            物 理 意 义 !!!
            TrieNode.isWord是指 从root到该node的path上的char能不能组成一个word
            所以要先更新node: node = node.children[board[x][y] - 'a'];
            确认board[x][y]这个char在node的上面，
            把这个char加入stringbuilder
            这样node.isWord代表的才是包括了board[x][y]这个char的string是不是word
            如果是的话就可以直接把stringbuilder加入result
        * */
        if (node.isWord) {
            result.add(sb.toString());
        }
        for (int[] dir : DIRS) {
            int neiX = x + dir[0];
            int neiY = y + dir[1];
            TrieDFS(board, neiX, neiY, result, visited, node, sb);
        }
        visited[x][y] =false;
        sb.deleteCharAt(sb.length() - 1);
    }

    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode curr = root;
            for (int i = 0; i < word.length(); i++) {
                TrieNode next = curr.children[word.charAt(i) - 'a'];
                if (next == null) {
                    TrieNode newNode = new TrieNode();
                    curr.children[word.charAt(i) - 'a'] = newNode;
                    next = curr.children[word.charAt(i) - 'a'];
                }
                curr = next;
            }
            curr.isWord = true;
        }
        return root;
    }
}
