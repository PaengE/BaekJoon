import java.io.*;
import java.util.*;

/**
 *  No.17472: 다리 만들기 2
 *  Hint: BFS으로 섬 넘버링 -> 섬과 섬을 잇는 간선 생성 -> union-find으로 하나의 집합으로 묶기
 */

public class BOJ17472 {
    static int n, m;
    static int[][] map, island;
    static boolean[][] visited;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        island = new int[n][m];
        visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        // -----------입력 끝-----------

        // 각각의 섬을 인덱싱
        int nthIsland = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 1 && !visited[i][j]) {
                    islandIndexing(i, j, nthIsland++);
                }
            }
        }

        // p 배열 초기화 (자기 자신의 부모는 자신노드)
        int[] p = new int[nthIsland];
        for (int i = 1; i < p.length; i++) {
            p[i] = i;
        }

        // 간선을 만든 후 pq에 저장
        PriorityQueue<Edge> pq = makeEdge(n, m, island);

        bw.write(String.valueOf(calculateBridgeLength(pq, p, nthIsland)));
        bw.close();
        br.close();
    }

    // union-find 를 이용하여 다리길이 계산
    static int calculateBridgeLength(PriorityQueue<Edge> pq, int[] p, int n) {
        int ans = 0;
        int bridgeCount = 0;

        while (!pq.isEmpty()) {
            Edge cur = pq.poll();

            if (findSet(p, cur.start) != findSet(p, cur.end)) {
                ans += cur.length;
                union(p, cur.start, cur.end);
                bridgeCount++;
            }
        }

        // n = 섬의 개수 + 1
        if (bridgeCount == n - 2) {
            return ans;
        } else {
            return -1;
        }
    }


    // 섬 x의 부모를 찾는 메소드
    static int findSet(int[] p, int x) {
        if (p[x] == x) {
            return x;
        } else {
            return p[x] = findSet(p, p[x]);
        }
    }

    // 하나의 섬으로 union
    static void union(int[] p, int a, int b) {
        a = findSet(p, a);
        b = findSet(p, b);
        if (a < b) {
            p[b] = a;
        } else {
            p[a] = b;
        }
    }

    // 섬 기준으로 상하좌우로 움직여서 다른 섬을 찾은 후, 그 두 섬을 잇는 간선 생성
    static PriorityQueue<Edge> makeEdge(int n, int m, int[][] island) {
        // length 기준 오름차순
        PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> o1.length - o2.length);
        int[] d = {1, -1};

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 바다면 continue
                if (island[i][j] == 0) {
                    continue;
                }

                for (int k = 0; k < 2; k++) {
                    // 상하 방향으로 섬이 있는지를 판단 후, 간선을 만듦
                    int t = i + d[k];
                    while (t >= 0 && t < n) {
                        if (island[i][j] == island[t][j]) {
                            break;
                        }
                        if (island[t][j] == 0) {
                            t += d[k];
                            continue;
                        }
                        if (Math.abs(t - i) > 2) {
                            pq.offer(new Edge(island[i][j], island[t][j], Math.abs(t - i) - 1));
                        }
                        break;
                    }
                    // 좌우 방향으로 섬이 있는지를 판단 후, 간선을 만듦
                    t = j + d[k];
                    while (t >= 0 && t < m) {
                        if (island[i][j] == island[i][t]) {
                            break;
                        }
                        if (island[i][t] == 0) {
                            t += d[k];
                            continue;
                        }
                        if (Math.abs(t - j) > 2) {
                            pq.offer(new Edge(island[i][j], island[i][t], Math.abs(t - j) - 1));
                        }
                        break;
                    }
                }
            }
        }
        return pq;
    }

    // BFS 섬 인덱싱
    static void islandIndexing(int startX, int startY, int nthIsland) {
        Queue<Integer> qx = new LinkedList<>();
        Queue<Integer> qy = new LinkedList<>();

        qx.offer(startX);
        qy.offer(startY);
        visited[startX][startY] = true;
        island[startX][startY] = nthIsland;

        while (!qx.isEmpty()) {
            int cx = qx.poll();
            int cy = qy.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (0 <= nx && nx < n && 0 <= ny && ny < m) {
                    if (map[nx][ny] == 1 && !visited[nx][ny]) {
                        qx.offer(nx);
                        qy.offer(ny);
                        visited[nx][ny] = true;
                        island[nx][ny] = nthIsland;
                    }
                }
            }
        }
    }

    // 간선 클래스
    static class Edge {
        int start, end, length;

        Edge(int start, int end, int length) {
            this.start = start;
            this.end = end;
            this.length = length;
        }
    }
}
