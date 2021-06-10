import java.io.*;
import java.util.*;

/**
 *  No.17089: 새 친구
 *  Hint: BruteForce + 시간복잡도에 대해 고민해보기 O(n(n+m))
 */

public class BOJ17089 {
    static int n, m;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        boolean[][] relations = new boolean[n + 1][n + 1];
        int[] count = new int[n + 1];

        // 친구 관계 및 수 구성
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            relations[a][b] = true;
            relations[b][a] = true;
            count[a]++;
            count[b]++;
        }


        int ans = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (relations[i][j]) {  // i와 j가 친구면
                    for (int k = j + 1; k <= n; k++) {
                        if (relations[i][k] && relations[j][k]) {   // i와 k가 친구이고 j와 k가 친구이면
                            ans = Math.min(ans, count[i] + count[j] + count[k] - 6);
                        }
                    }
                }
            }
        }

        bw.write(ans == Integer.MAX_VALUE ? "-1" : String.valueOf(ans));
        bw.close();
        br.close();
    }
}
