import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.1967: 트리의 지름
 *  URL: https://www.acmicpc.net/problem/1967
 *  Description: BFS or DFS 로 트리에서 가장 먼 두 점을 찾는 문제
 *  Hint: A 에서 가장 먼 B 를 구함 -> B 에서 가장 먼 노드를 구함 = 최대 길이
 */

public class BOJ1967 {
    static int n;
    static ArrayList<ArrayList<Tree>> list;
    static int[] dist;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());

        list = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            list.add(new ArrayList<>());
        }

        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            list.get(a).add(new Tree(b, w));
            list.get(b).add(new Tree(a, w));
        }

        int longestLengthIndex = bfs(1);
        int ansIndex = bfs(longestLengthIndex);

        bw.write(String.valueOf(dist[ansIndex]));
        bw.close();
        br.close();
    }

    // 가장 멀리 있는 노드의 index 를 구해서 return
    static int bfs(int start) {
        int max = 0;
        dist = new int[n + 1];
        Queue<Tree> q = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];
        q.offer(new Tree(start, 0));

        // BFS
        while (!q.isEmpty()) {
            Tree cur = q.poll();

            if (visited[cur.to]) {
                continue;
            }
            visited[cur.to] = true;

            for (Tree next : list.get(cur.to)) {
                if (!visited[next.to]) {
                    q.offer(next);
                    dist[next.to] = dist[cur.to] + next.dist;
                    max = Math.max(max, dist[next.to]);
                }
            }
        }

        int maxIdx = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == max) {
                maxIdx = i;
                break;
            }
        }
        return maxIdx;
    }

    static class Tree {
        int to, dist;

        Tree(int to, int dist) {
            this.to = to;
            this.dist = dist;
        }
    }
}
