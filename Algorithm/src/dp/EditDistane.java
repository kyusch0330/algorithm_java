package dp;

import java.io.*;
import java.util.*;

public class EditDistane {

	static int s1Len, s2Len;
	static int dp[][];

	public static void main(String args[]) throws Exception {

		/**
		 * 편집 거리 알고리즘
		 * 
		 * 두 문자열의 유사도를 판단하는 알고리즘
		 * 어떤 문자열에서 다른 문자열을 만들기 위해, 삽입/삭제/수정을 얼마나 해야하는지 나타낸다.
		 *  -> 편집 거리
		 * 
		 */

		String s1 = " " + "absolute";
		String s2 = " " + "apple";

		s1Len = s1.length();
		s2Len = s2.length();
		
		dp = new int[s1Len][s2Len];

		// 편집거리 초기화 : 빈 문자열에서 해당 문자열을 만들기 위한 추가 비용
		for (int r = 0; r < s1Len; r++) {
			dp[r][0] = r;
		}
		for (int c = 0; c < s2Len; c++) {
			dp[0][c] = c;
		}

		/**
		 * dp[i][j] -> s1의 i번째 글자, s2의 j번째 글자를 끝으로 하는 문자열끼리의 편집거리 기준 문자열, 비교 문자열의 각 끝글자를 비교하여,
		 * 같다면? -> 각자 한 글자 전까지의 편집거리를 유지 
		 * 다르다면? -> 이전 편집거리에서 현재 문자를 붙인 뒤에 처리할 연산을 +1
		 * 
		 */

		for (int r = 1; r < s1Len; r++) {
			for (int c = 1; c < s2Len; c++) {

				if (s1.charAt(r)== s2.charAt(c)) {
					// 두 글자가 같다면, 각각 이전 글자를 끝으로 하는 문자열에서 각각 현 문자를 추가할 때에 삽입,수정,삭제로 고칠 필요가 없다.
					// ex) abc, xyc -> ab와 xy와의 편집거리를 유지하면 된다. (c가 동일하므로)
					dp[r][c] = dp[r - 1][c - 1];
				} else {
					// 두 글자가 다르다면, 삽입, 수정, 삭제로 고치는 경우를 고려한다.

					dp[r][c] = Integer.MAX_VALUE;
					// s2의 c번째 글자를 제외했을 때 편집거리에서 c번째 글자를 포함했으므로, c번째 글자를 삭제하는 경우 고려
					dp[r][c] = Math.min(dp[r][c], dp[r][c - 1] + 1);
					// s1의 r번째 글자를 제외했을 때 편집거리에서 r번째 글자를 포함했으므로, r번째 글자를 삭제하는 경우 고려
					dp[r][c] = Math.min(dp[r][c], dp[r - 1][c] + 1);
					// s1의 r번째 글자, s2의 c번째 글자를 제외했을 때 편집거리에서 r,c번째 글자를 포함했으므로 둘중 하나를 수정해주는 경우를 고려
					dp[r][c] = Math.min(dp[r][c], dp[r - 1][c - 1] + 1);
					
					// 한번에 하면
					//dp[r][c] = Math.min(dp[r-1][c-1], Math.min(dp[r-1][c], dp[r][c-1]));
				}

			}
		}

		for (int r = 0; r < s1Len; r++) {
			for (int c = 0; c < s2Len; c++) {
				System.out.print(dp[r][c] + " ");
			}
			System.out.println();
		}

		System.out.println(dp[s1Len - 1][s2Len - 1]);

	} // main end

}