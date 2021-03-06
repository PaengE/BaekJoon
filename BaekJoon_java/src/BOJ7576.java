import java.io.*;
import java.util.*;

/**
 * no.7576: 토마토 (2차원)
 * description: BFS 로 토마토를 익히는 문제 (시작점이 여러개인 문제)
 * note: 모든 시작점을 enqueue 하고 BFS
 */

public class BOJ7576 {
    static int m, n;
    static int[][] box;
    static boolean[][] visited;
    static int dx[] = {0, 0, 1, -1};
    static int dy[] = {1, -1, 0, 0};
    static Queue<int[]> q = new LinkedList<int[]>();
    static ArrayList<int[]> startPoint = new ArrayList<int[]>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        box = new int[n][m];
        visited = new boolean[n][m];


        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                int t = Integer.parseInt(st.nextToken());
                box[i][j] = t;
                if (t == 1) {
                    startPoint.add(new int[]{i, j});
                }
            }
        }

        bfs();

        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 배열에 0이 존재하면 익을 수 없는 토마토가 있으므로 -1 출력 후 종료
                if (box[i][j] == 0) {
                    bw.write("-1\n");
                    bw.flush();
                    br.close();
                    bw.close();
                    System.exit(0);
                } 
                // 0이 없다면 배열 중 제일 큰 수를 저장
                else {
                    answer = Math.max(answer, box[i][j]);
                }
            }
        }
        // 배열에서 가장 큰 수 - 1 이 답안
        bw.write(String.valueOf(answer - 1));
        bw.flush();
        br.close();
        bw.close();

    }

    static void bfs() {
        // 모든 시작점을 enqueue
        for (int[] a : startPoint) {
            q.offer(new int[]{a[0], a[1]});
            visited[a[0]][a[1]] = true;
        }

        while (!q.isEmpty()) {
            int cx = q.peek()[0];
            int cy = q.peek()[1];
            q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (nx >= 0 && ny >= 0 && nx < n && ny < m) {
                    if (box[nx][ny] == 0 && !visited[nx][ny]) {
                        q.offer(new int[]{nx, ny});
                        visited[cx][cy] = true;
                        box[nx][ny] = box[cx][cy] + 1;
                    }
                }
            }
        }

    }
}
