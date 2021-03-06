import java.io.*;

/**
 *  No.1285: 동전 뒤집기
 *  Hint: Bitmask + Greedy
 */

public class BOJ1285 {
    static int n;
    static char[][] map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        map = new char[n][n];
        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
        }

        int ans = Integer.MAX_VALUE;
        // 길이가 n인 2진수 비트로 어떤 행들을 뒤집을지 선택(모든 경우의 수를 다 수행함)
        for (int bit = 0; bit < (1 << n); bit++) {
            int sum = 0;
            // 뒤집기를 선택한 행들을 뒤집는다.
            for (int b = 0; b < n; b++) {
                int back = 0;
                // 단 열을 기준으로 뒤집는다.
                for (int a = 0; a < n; a++) {
                    char cur = map[a][b];

                    // 뒤집기로 선택한 행의 열이라면 뒤집는다.
                    if ((bit & (1 << a)) != 0) {
                        cur = reverse(a, b);
                    }

                    if (cur == 'T') {   // 동전이 뒷면이면 카운트한다.
                        back++;
                    }
                }
                // b열을 뒤집었을 때, 뒤집지 않았을 때 중 동전의 개수가 더 적은 것을 누적합한다.
                sum += Math.min(back, n - back);
            }
            // 선택된 행들을 뒤집었을 때, 뒷면 동전의 최소 개수를 구한다.
            ans = Math.min(ans, sum);
        }
        // 위의 로직이 가능한 이유는, 행끼리의 뒤집기는 서로 영향을 주지 않고
        // 열끼리의 뒤집기 또한 서로 영향을 주지 않기 때문이다.

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static char reverse(int y, int x) {
        if (map[y][x] == 'T') {
            return 'H';
        } else {
            return 'T';
        }
    }
}
