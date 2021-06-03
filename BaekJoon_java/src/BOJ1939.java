import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.1939: 중량제한
 *  Hint: 이분탐색 + BFS
 */

public class BOJ1939 {
    static int n, m, s, e, ans;
    static ArrayList<Bridge>[] graph;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        // 그래프 구성
        int left = Integer.MAX_VALUE;
        int right = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph[v1].add(new Bridge(v2, w));
            graph[v2].add(new Bridge(v1, w));
            left = Math.min(left, w);   // 중량 최솟값
            right = Math.max(right, w); // 중량 최댓값
        }

        st = new StringTokenizer(br.readLine());
        s = Integer.parseInt(st.nextToken());   // 시작도시
        e = Integer.parseInt(st.nextToken());   // 도착도시

        binarySearch(left, right);

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static void binarySearch(int left, int right) {
        while (left <= right) {
            int mid = (left + right) / 2;

            // s -> e가 mid 중량으로 방문 가능하다면 left를 줄임 (최대 중량을 구하고 싶으므로)
            if (bfs(mid)) {
                left = mid + 1;
                ans = mid;
            } else {    // s -> e가 mid 중량으로 방문 불가능하다면 right를 줄임
                right = mid - 1;
            }
        }
    }

    // weight 보다 하중이 큰 다리로 s -> e가 가능한지 판단
    static boolean bfs(int weight) {
        Queue<Bridge> q = new ArrayDeque<>();
        boolean[] visited = new boolean[n + 1];
        q.offer(new Bridge(s, 0));

        while (!q.isEmpty()) {
            Bridge cur = q.poll();

            if (cur.to == e) {
                return true;
            }

            for (Bridge next : graph[cur.to]) {
                if (next.weight >= weight && !visited[next.to]) {
                    q.offer(next);
                    visited[next.to] = true;
                }
            }
        }

        return false;
    }

    static class Bridge {
        int to, weight;

        Bridge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
}
