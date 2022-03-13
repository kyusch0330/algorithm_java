package dp;

import java.util.*;
import java.io.*;

/**
 * 백준 12865 평범한 배낭 
 * https://www.acmicpc.net/problem/12865
 */

public class Knapsack {

	static int N, K;
	static Item items[];
	static int dp[];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		dp = new int[K + 1]; // 무게 0 ~ K 일때 가질 수 있는 최대 가치를 저장한다.

		items = new Item[N];
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			int weight = Integer.parseInt(st.nextToken());
			int value = Integer.parseInt(st.nextToken());
			items[n] = new Item(weight, value);
		}

		for (int i = 0; i < N; i++) {
			// 각 아이템에 대해서
			// 현재 i번째 아이템은 선택되지 않은 상태로 dp가 저장되어있다.
			for (int j = K; j - items[i].weight >= 0; j--) {
				// 무게 j=K 부터 j를 선택하기 전의 최대가치를 고를 수 있는 각 무게에서 (현 아이템 무게가 j보다 작거나 같아야함)
				// 현재 아이템을 담지 않고 무게 j에 도달할 수 있는 최고 가치와
				// 현재 아이템을 담고서야 무게 j에 도달할 수 있는 최고 가치 중 최대 값을 선택한다.
				// j-(i 무게)일때의 최고가치에서 현재 아이템의 가치를 더하면 현재 아이템을 선택했을 때의 가치가 된다.
				// -> 현 아이템을 선택한 경우와 선택하지 않는 경우 중 최고 가치를 저장한다.
				dp[j] = Math.max(dp[j], dp[j - items[i].weight] + items[i].value);
			}
		}
		// dp[K]를 출력하는 이유?
		// -> 무게 K이하에서 K를 넘지않고 각 아이템을 선택하는 경우, 안 하는 경우를 모두 고려하여 저장했으므로 정답이 된다.
		System.out.println(dp[K]);
	} // main end

}

class Item {
	public int weight;
	public int value;

	public Item(int weight, int value) {
		this.weight = weight;
		this.value = value;
	}
}