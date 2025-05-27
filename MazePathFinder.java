import java.util.ArrayList;
import java.util.List;

public class MazePathFinder {
    /**
     * core logic: given a grid of 0/1, find the unique path of 1s
     * that starts on one wall, ends on an adjacent wall,
     * makes at least one 90° turn, and moves only orthogonally.
     */
    public static List<String> findPath(ArrayList<ArrayList<Integer>> map) {
        List<String> path = new ArrayList<>();
        int R = map.size();
        if (R == 0) return path;
        int C = map.getFirst().size();

        // find start on walls: top → left → right → bottom
        int sr = -1, sc = -1;
        for (int c = 0; c < C && sr < 0; c++) if (map.getFirst().get(c) == 1) { sr=0; sc=c; }
        for (int r = 0; r < R && sr < 0; r++) if (map.get(r).getFirst() == 1) { sr=r; sc=0; }
        for (int r = 0; r < R && sr < 0; r++) if (map.get(r).get(C-1) == 1) { sr=r; sc=C-1; }
        for (int c = 0; c < C && sr < 0; c++) if (map.get(R-1).get(c) == 1) { sr=R-1; sc=c; }
        if (sr<0) return path;

        boolean[][] vis = new boolean[R][C];
        dfs(map, vis, path, -1, -1, sr, sc, sr, sc, false);
        return path;
    }

    private static boolean dfs(ArrayList<ArrayList<Integer>> m, boolean[][] v, List<String> p, int pr, int pc, int r, int c, int sr, int sc, boolean turned) {
        int R = m.size(), C = m.getFirst().size();
        if (r<0||r>=R||c<0||c>=C||m.get(r).get(c)!=1||v[r][c]) return false;
        v[r][c] = true;
        p.add("A["+r+"]["+c+"]");

        boolean t = turned;
        if (pr>=0 && p.size()>=3) {
            int dr = r-pr, dc = c-pc;
            String b = p.get(p.size()-2);
            int br = Integer.parseInt(b.substring(b.indexOf('[')+1,b.indexOf(']')));
            int bc = Integer.parseInt(b.substring(b.lastIndexOf('[')+1,b.lastIndexOf(']')));
            int dr2 = pr-br, dc2 = pc-bc;
            if (dr!=dr2 || dc!=dc2) t = true;
        }

        if (isEnd(r,c,sr,sc,R,C,t)) return true;
        int[][] D = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] d : D) {
            if (dfs(m, v, p, r, c, r+d[0], c+d[1], sr, sc, t)) return true;
        }

        p.removeLast();
        v[r][c] = false;
        return false;
    }

    private static boolean isEnd(int r, int c, int sr, int sc, int R, int C, boolean t) {
        if (!t) return false;
        if (r==sr && c==sc) return false;
        boolean onWall = (r==0||r==R-1||c==0||c==C-1);
        if (!onWall) return false;
        if (sr==0 && r==R-1) return false;
        if (sr==R-1 && r==0) return false;
        if (sc==0 && c==C-1) return false;
        return sc != C - 1 || c != 0;
    }
}