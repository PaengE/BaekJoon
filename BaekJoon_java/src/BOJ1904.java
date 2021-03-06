/*
    no.1904 : 01 tile
    hint : 타일이 00으로 시작할 경우와 1로 시작할 경우를 나눠서 생각
        -> 00로 시작 : n-2 길이의 가짓 수와 동일
        -> 1로 시작 : n-1 길이의 가짓 수와 동일
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ1904 {
    static int arr[];

    public static void dp(int n){
        if (n == 1){
            System.out.println(arr[0] % 15746);
        } else if (n == 2){
            System.out.println(arr[1] % 15746 );
        } else {
            for (int i = 2; i < n; i++){
                arr[i] = (arr[i-2] + arr[i-1]) % 15746;
            }
            System.out.println(arr[n - 1]);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        arr = new int[n+1];
        arr[0] = 1;
        arr[1] = 2;

        dp(n);

        br.close();
    }
}
