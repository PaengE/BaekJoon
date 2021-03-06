import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *  No.15681: 트리와 쿼리
 *  URL: https://www.acmicpc.net/problem/15681
 *  Hint: Tree + DP
 */

public class BOJ15681 {
    static int[] dp;
    static ArrayList<ArrayList<Integer>> graph;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        graph = new ArrayList<>();

        int n = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        dp = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // 트리 간선 생성
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        // 트리 생성
        buildTree(r, -1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            int u = Integer.parseInt(br.readLine());
            sb.append(dp[u]).append("\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static int buildTree(int cur, int parent) {
        // dp[cur] 이 0이 아니면 연결된 노드이므로
        if (dp[cur] != 0) {
            return dp[cur];
        }
        dp[cur] = 1;

        for (int child : graph.get(cur)) {
            // parent 와 child 가 같으면 안되므로
            // 사이클이 없고 부모는 무조건 하나 이므로 가능
            if (parent != child) {
                dp[cur] += buildTree(child, cur);
            }
        }
        return dp[cur];
    }
}
