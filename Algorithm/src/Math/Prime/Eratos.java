package Math.Prime;

public class Eratos {
	public static int MAX_NUM = 1000001;
	static boolean isPrime[] = new boolean[MAX_NUM];
	static boolean isChecked[] = new boolean[MAX_NUM];

	public static void main(String[] args) {
		// 0,1 은 소수가 아니므로 제외하고, 모두 소수라고 표기 후 시작한다.
		for (int i = 2; i < MAX_NUM; i++) {
			isPrime[i] = true;
		}

		eratos();
		
		for (int i = 0; i < 100; i++) {
			if (isPrime[i])
				System.out.println(i);
		}
	}

	public static void eratos() {
		// 최초 2이상 모든 수가 소수라고 표시하고 시작
		// 0,1은 소수가 아니므로 무시
		for (int i = 2; i < MAX_NUM; i++) {
			if (isPrime[i]) {
				// 소수라면 그의 배수들은 전부 소수가 아님을 체크해준다. (뒤에 4,6 등은 이미 소수가 아님이 표시되어 건너뛰어 지는 원리)
				for (int j = 2 * i; j < MAX_NUM; j += i) {
					isPrime[j] = false;
				}
			}
		}
	}

}
