import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * no.10942: 팰린드롬?
 * description: 팰린드롬은 수열을 뒤집었을 때 원래 수열과 같은 수열을 말함
 * hint: DP 활용
 */

public class BOJ10942 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        boolean[][] dp = new boolean[n + 1][n + 1];

        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            Queue<Integer> start = new LinkedList<Integer>();
            Queue<Integer> end = new LinkedList<Integer>();

            // s 와 e 가 다르면 볼 것도 없이 false
            if (arr[s] != arr[e]) {
                bw.write("0\n");
            } 
            // dp[s][e]가 이전에 검사한 것에 의해 팰린드롬일 경우 true
            else if (dp[s][e]) {
                bw.write("1\n");
            } 
            // 위 두 경우가 아니면 아직 검사되지 않은 상태이므로
            // s+1, e-1부터 s++, e--을 하며 서로 같은지를 검사(arr[s]와 arr[e]는 같다고 위 조건식에서 검증됨)
            // 다르면 false, 같으면 e - s가 1이 될 때까지 검사
            // s ~ e가 팰린드롬이면, s+1 ~ e-1도 팰린드롬이므로 dp에 저장
            else {
                start.offer(s);
                end.offer(e);
                boolean pass = true;
                for (int a = s + 1, b = e - 1; b - a >= 1; a++, b--) {
                    if (arr[a] != arr[b]) {
                        bw.write("0\n");
                        pass = false;
                        break;
                    } else {
                        start.offer(a);
                        end.offer(b);
                    }
                }
                if (pass) {
                    while (!start.isEmpty() && !end.isEmpty()) {
                        dp[start.poll()][end.poll()] = true;
                    }
                    bw.write("1\n");
                }
            }
        }
        bw.flush();
        br.close();
        bw.close();
    }
}
