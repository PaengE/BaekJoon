import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.17136: 색종이 붙이기
 *  URL: https://www.acmicpc.net/problem/17136
 *  Hint: BruteForce + Backtracking
 */

public class BOJ17136 {
    static int[][] arr;
    static int[] paper = {0, 5, 5, 5, 5, 5};
    static int ans = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        arr = new int[10][10];
        for (int i = 0; i < 10; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 10; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        bruteForce(0, 0, 0);

        bw.write(ans == Integer.MAX_VALUE ? "-1" : String.valueOf(ans));
        bw.close();
        br.close();
    }

    static void bruteForce(int x, int y, int cnt) {
        // 마지막 위치까지 갔을 경우
        if (x == 9 && y == 10) {
            ans = Math.min(ans, cnt);
            return;
        }

        // 현재 cnt 가 ans 이상이면 더이상 진행할 필요 X
        if (cnt >= ans) {
            return;
        }

        // 오른쪽 범위를 벗어나면 아랫줄을 탐색
        if (y > 9) {
            bruteForce(x + 1, 0, cnt);
            return;
        }

        // 색종이를 붙일 수 있으면
        if (arr[x][y] == 1) {
            for (int i = 5; i >= 1; i--) {
                // i크기 사이즈의 종이가 남아있는지 & 그 종이를 붙일 수 있는지 판단
                if (paper[i] > 0 && ifCanBeAttached(x, y, i)) {
                    paper[i]--;
                    attachOrDetach(x, y, i, 0); // 색종이를 붙임
                    bruteForce(x, y + 1, cnt + 1);
                    attachOrDetach(x, y, i, 1); // 색종이를 뗌
                    paper[i]++;
                }
            }

        } else {    // 색종이를 붙일 수 없으면, 다음 칸 탐색
            bruteForce(x, y + 1, cnt);
        }
    }

    // flag 로 arr 을 덮음
    static void attachOrDetach(int x, int y, int size, int flag) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arr[x + i][y + j] = flag;
            }
        }
    }

    // size 크기의 색종이를 붙일 수 있는지 검사
    static boolean ifCanBeAttached(int x, int y, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (x + i > 9 || y + j > 9) {
                    return false;
                }

                if (arr[x + i][y + j] != 1) {
                    return false;
                }
            }
        }
        return true;
    }
}
