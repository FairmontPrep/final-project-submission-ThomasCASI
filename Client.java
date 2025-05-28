import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        List<List<Integer>> map = Arrays.asList(
                Arrays.asList(1, 0, 0, 0, 0, 0),
                Arrays.asList(0, 0, 0, 0, 0, 0),
                Arrays.asList(1, 1, 1, 1, 0, 0),
                Arrays.asList(9, 0, 0, 1, 1, 1),
                Arrays.asList(0, 1, 1, 1, 0, 0),
                Arrays.asList(0, 1, 0, 1, 0, 0),
                Arrays.asList(0, 1, 0, 1, 0, 0),
                Arrays.asList(0, 1, 0, 1, 0, 0),
                Arrays.asList(0, 1, 0, 0, 0, 0),
                Arrays.asList(0, 1, 0, 0, 0, 0)
        );
        List<String> path = MazePathFinder.findPath(map);
        MazePathFinder.printPathOnMap(map, path);
    }

    static class MazePathFinder {
        private static final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        public static List<String> findPath(List<List<Integer>> map) {
            int R = map.size();
            if (R == 0) return List.of();
            int C = map.getFirst().size();

            List<int[]> starts = new ArrayList<>();
            for (int c = 0; c < C; c++) {
                if (map.getFirst().get(c) == 1) starts.add(new int[]{0, c});
                if (map.get(R - 1).get(c) == 1) starts.add(new int[]{R - 1, c});
            }
            for (int r = 0; r < R; r++) {
                if (map.get(r).getFirst() == 1) starts.add(new int[]{r, 0});
                if (map.get(r).get(C - 1) == 1) starts.add(new int[]{r, C - 1});
            }

            for (int[] start : starts) {
                int sr = start[0], sc = start[1];
                List<String> path = new ArrayList<>();
                boolean[][] vis = new boolean[R][C];
                if (dfs(map, vis, path, sr, sc, sr, sc, -1, false)) {
                    return path;
                }
            }

            return List.of();
        }

        private static boolean dfs(List<List<Integer>> map, boolean[][] vis, List<String> path, int r, int c, int sr, int sc, int prevDir, boolean turned) {
            int R = map.size(), C = map.getFirst().size();
            if (r < 0 || r >= R || c < 0 || c >= C || map.get(r).get(c) != 1 || vis[r][c]) return false;
            vis[r][c] = true;
            path.add("A[" + r + "][" + c + "]");
            if (turned && onWall(r, c, R, C) && !(r == sr && c == sc) && !isOpposite(r, c, sr, sc, R, C)) {
                return true;
            }
            for (int d = 0; d < DIRS.length; d++) {
                int nr = r + DIRS[d][0], nc = c + DIRS[d][1];
                boolean newTurn = turned || (prevDir != -1 && prevDir != d);
                if (dfs(map, vis, path, nr, nc, sr, sc, d, newTurn)) {
                    return true;
                }
            }
            vis[r][c] = false;
            path.removeLast();
            return false;
        }

        private static boolean onWall(int r, int c, int R, int C) {
            return r == 0 || r == R - 1 || c == 0 || c == C - 1;
        }

        private static boolean isOpposite(int r, int c, int sr, int sc, int R, int C) {
            if (sr == 0 && r == R - 1) return true;
            if (sr == R - 1 && r == 0) return true;
            if (sc == 0 && c == C - 1) return true;
            return sc == C - 1 && c == 0;
        }

        private static void printPathOnMap(List<List<Integer>> map, List<String> path) {
            int R = map.size(), C = map.getFirst().size();
            boolean[][] isPath = new boolean[R][C];

            for (String s : path) {
                int r = Integer.parseInt(s.substring(s.indexOf('[') + 1, s.indexOf(']')));
                int c = Integer.parseInt(s.substring(s.lastIndexOf('[') + 1, s.lastIndexOf(']')));
                isPath[r][c] = true;
            }

            System.out.print("   ");
            for (int c = 0; c < C; c++) {
                System.out.printf("%2d", c);
            }
            System.out.println();

            for (int r = 0; r < R; r++) {
                System.out.printf("%2d ", r);
                for (int c = 0; c < C; c++) {
                    if (isPath[r][c]) {
                        System.out.print("■ ");
                    } else if (map.get(r).get(c) == 1) {
                        System.out.print("· ");
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
        }
    }
}