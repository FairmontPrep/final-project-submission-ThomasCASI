import java.util.ArrayList;

public class MazePathFinder {

    // return the list of path
    public static ArrayList<String> findPath(ArrayList<ArrayList<Integer>> map) {
        ArrayList<String> path = new ArrayList<>();

        int rows = map.size();
        int cols = map.getFirst().size();

        int startRow = -1, startCol = -1;
        for (int r = 0; r < rows; r++) {
            if (map.get(r).getFirst() == 1) {
                startRow = r;
                startCol = 0;
                break;
            }
        }
        if (startRow == -1) {
            for (int c = 0; c < cols; c++) {
                if (map.getFirst().get(c) == 1) {
                    startRow = 0;
                    startCol = c;
                    break;
                }
            }
        }
        // TODO: the third one

        if (startRow == -1) {
            System.out.println("未找到起点！");
            return path;
        }

        // DFS
        boolean[][] visited = new boolean[rows][cols];
        dfs(map, startRow, startCol, visited, path, -1, -1, startRow, startCol);

        return path;
    }

    private static boolean dfs(ArrayList<ArrayList<Integer>> map, int r, int c, boolean[][] visited, ArrayList<String> path,
                               int prevR, int prevC, int startR, int startC) {
        int rows = map.size();
        int cols = map.getFirst().size();

        if (r < 0 || r >= rows || c < 0 || c >= cols) return false;
        if (map.get(r).get(c) != 1 || visited[r][c]) return false;

        visited[r][c] = true;
        path.add("A[" + r + "][" + c + "]");

        if (isExit(map, r, c, startR, startC, path)) {
            return true;
        }

        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            if (dfs(map, nr, nc, visited, path, r, c, startR, startC)) {
                return true;
            }
        }

        path.removeLast();
        return false;
    }

    private static boolean isExit(ArrayList<ArrayList<Integer>> map, int r, int c, int startR, int startC, ArrayList<String> path) {
        int rows = map.size();
        int cols = map.getFirst().size();

        // 当前点必须是墙上的1（四边之一）
        boolean onWall = (r == 0 || r == rows - 1 || c == 0 || c == cols - 1);
        if (!onWall) return false;

        // 不能和起点在同一边（不能直通），必须在相邻墙面（左右或上下相邻）
        if ((startR == 0 && r == rows - 1) || (startR == rows -1 && r == 0)) return false;
        if ((startC == 0 && c == cols -1) || (startC == cols -1 && c == 0)) return false;
        return startR != r || startC != c;

        // TODO: detect the turn
    }
}