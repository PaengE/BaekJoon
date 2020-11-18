import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
    no.11051 : 이항 계수 2
 */

public class BOJ11051 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int k = Integer.parseInt(s[1]);
        int t = Math.min(k, n-k);
        long[][] arr = new long[n+1][n+1];

        arr[1][0] = 1;
        arr[1][1] = 1;
        for(int i = 2; i <= n; i++){
            arr[i][0] = 1;
            arr[i][i] = 1;
            for(int j = 1; j <= t; j++){
                arr[i][j] = (arr[i-1][j-1] + arr[i-1][j]) % 10007;
            }
        }
        System.out.println(arr[n][t]);

    }
}
