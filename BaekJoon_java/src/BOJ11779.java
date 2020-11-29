import java.io.*;
import java.util.*;

/**
 *  No.11779: 최소비용 구하기 2
 *  URL: https://www.acmicpc.net/problem/11779
 *  Description: 간선에 가중치가 있을 때 최단경로를 출력하는 문제
 *  Hint: Dijkstra + Graph
 */

public class BOJ11779 {
    static ArrayList<Node>[] graph;
    static int n, m, count;
    static int[] dist, preCity;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());
        graph = new ArrayList[n + 1];
        preCity = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph[start].add(new Node(end, cost));
        }

        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        dijkstra(s);

        StringBuilder sb = new StringBuilder();
        sb.append(dist[e] + "\n");

        Stack<Integer> stk = new Stack<>();
        stk.push(e);
        while (preCity[e] != 0) {
            count += 1;
            stk.push(preCity[e]);
            e = preCity[e];
        }

        sb.append((count + 1) + "\n");
        while (!stk.isEmpty()) {
            sb.append(stk.pop() + " ");
        }

        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static void dijkstra(int start) {
        Queue<Node> q = new LinkedList<>();
        dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[start] = 0;
        q.offer(new Node(start, 0));

        while (!q.isEmpty()) {
            Node curNode = q.poll();
            int cur = curNode.to;

            for (Node next : graph[cur]) {
                if (dist[next.to] > dist[cur] + next.cost) {
                    dist[next.to] = dist[cur] + next.cost;
                    preCity[next.to] = cur;
                    q.offer(new Node(next.to, dist[next.to]));
                }
            }
        }
    }

    static class Node implements Comparable<Node> {
        public int to, cost;

        Node(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return cost - o.cost;
        }
    }
}
