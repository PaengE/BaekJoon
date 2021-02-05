import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InstallPillarAndBeam {
    static int n;
    static boolean[][] pillars;
    static boolean[][] beams;

    public int[][] solution(int n, int[][] build_frame) {
        this.n = n;
        pillars = new boolean[n + 3][n + 3];    // 범위를 벗어날 수 있으므로 padding
        beams = new boolean[n + 3][n + 3];
        int ansCount = 0;   // 반환할 answer의 크기

        // t[0] = x, t[1] = y
        // t[2] = 0: 기둥, 1: 보
        // t[3] = 0: 삭제, 1: 설치
        for (int[] t : build_frame) {
            // 조건 검사를 용이하게 하기 위해 padding
            int x = t[0] + 1;
            int y = t[1] + 1;

            if (t[2] == 0) {    // 기둥일 때,
                if (t[3] == 1 && checkCanInstallPillar(x, y)) {    // 설치
                    pillars[x][y] = true;
                    ansCount++;
                } else if (t[3] == 0 && checkCanRemove(x, y, 0)) {    // 삭제
                    pillars[x][y] = false;
                    ansCount--;
                }
            } else {    // 보일 때,
                if (t[3] == 1 && checkCanInstallBeam(x, y)) {    // 설치
                    beams[x][y] = true;
                    ansCount++;
                } else if (t[3] == 0 && checkCanRemove(x, y, 1)) {    // 삭제
                    beams[x][y] = false;
                    ansCount--;
                }
            }
        }

        int[][] answer = new int[ansCount][3];
        int idx = 0;
        for (int i = 1; i <= n + 1; i++) {
            for (int j = 1; j <= n + 1; j++) {
                if (pillars[i][j]) {
                    answer[idx++] = new int[]{i - 1, j - 1, 0};
                }

                if (beams[i][j]) {
                    answer[idx++] = new int[]{i - 1, j - 1, 1};
                }

            }
        }
        return answer;
    }

    // 기둥을 설치할 수 있는지를 판단
    private boolean checkCanInstallPillar(int x, int y) {
        return (y == 1 || pillars[x][y - 1] || beams[x - 1][y] || beams[x][y]);
    }

    // 보를 설치할 수 있는지를 판단
    private boolean checkCanInstallBeam(int x, int y) {
        return pillars[x][y - 1] || pillars[x + 1][y - 1] || (beams[x - 1][y] && beams[x + 1][y]);
    }

    // 삭제할 수 있는지를 판단
    private boolean checkCanRemove(int x, int y, int type) {
        boolean check = true;
        // 일단 없애보고
        if (type == 0) {
            pillars[x][y] = false;
        } else {
            beams[x][y] = false;
        }

        // 나머지 것들이 조건을 만족하는지 각각 검사
        loop:
        for (int i = 1; i <= n + 1; i++) {
            for (int j = 1; j <= n + 1; j++) {
                if (pillars[i][j] && !checkCanInstallPillar(i, j)) {
                    check = false;
                    break loop;
                }

                if (beams[i][j] && !checkCanInstallBeam(i, j)) {
                    check = false;
                    break loop;
                }
            }
        }

        // 다시 원래대로 되돌려 놓음
        if (type == 0) {
            pillars[x][y] = true;
        } else {
            beams[x][y] = true;
        }
        return check;
    }

    @Test
    public void test() {
        Assertions.assertArrayEquals(new int[][]{{1, 0, 0}, {1, 1, 1}, {2, 1, 0}, {2, 2, 1}, {3, 2, 1}, {4, 2, 1}, {5, 0, 0}, {5, 1, 0}}
                , solution(5, new int[][]{{1, 0, 0, 1}, {1, 1, 1, 1}, {2, 1, 0, 1}, {2, 2, 1, 1}, {5, 0, 0, 1}, {5, 1, 0, 1}, {4, 2, 1, 1}, {3, 2, 1, 1}}));
        Assertions.assertArrayEquals(new int[][]{{0, 0, 0}, {0, 1, 1}, {1, 1, 1}, {2, 1, 1}, {3, 1, 1}, {4, 0, 0}}
                , solution(5, new int[][]{{0, 0, 0, 1}, {2, 0, 0, 1}, {4, 0, 0, 1}, {0, 1, 1, 1}, {1, 1, 1, 1}, {2, 1, 1, 1}, {3, 1, 1, 1}, {2, 0, 0, 0}, {1, 1, 1, 0}, {2, 2, 0, 1}}));
    }
}
