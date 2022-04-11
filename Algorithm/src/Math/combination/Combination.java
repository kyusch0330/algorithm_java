package Math.combination;

import java.util.*;
import java.io.*;

public class Combination {

	static int N, R, P = 1000000007;
	static long f[];

	public static void main(String args[]) throws Exception {
		System.setIn(new FileInputStream("res/data.txt")); // 제출 시 삭제
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		// p로 모듈러 연산을 한 결과를 도출하는 경우
		
		// 기본 조합 공식
		// nCr = n! / (n-r)!*r! 
		// 
		// #페르마 소정리를 이용한 조합
		//  nCr = n! / (n-r)!*r!
		//   -> 모듈러 연산에서 나눗셈에 적용하려면 역수를 구해야한다.
		
		// a^p ≡ a (mod p) -> 페르마 소정리 (p는 소수) 를 이용하여,
		//  a^(p-1) ≡ 1 (mod p)
		//  a^(p-2) ≡ 1/a (mod p)
		//  즉, a의 역수는 a^(p-2)가 된다.
		
		/**
		 *  위의 공식을 이용해 조합 nCr을 구하면
		 *  nCr = n! * ( n! * (n-r)! )^(p-2) 가 된다. (+중간 중간 %p 를 계속 해줘야 한다.)
		 *   factorial을 메모이제이션하여 구하고, pow는 분할 정복으로 구현하여 시간을 단축한다.
		 */

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		if (N == R || N <= 1 || R == 0) {
			// 0! = 1
			System.out.println(1);
			return;
		}

		f = new long[N + 1];
		f[0] = 1;
		f[1] = 1;
		// 팩토리얼 메모이제이션
		for (int n = 2; n <= N; n++) {
			f[n] = (n * f[n - 1]) % P;
		}

		// 공식으로 정답 출력
		int answer = (int) ((f[N] * pow((int) ((f[R] * f[N - R]) % P), P - 2)) % P);

		System.out.println(answer);
	} // main end

	// 분할정복 pow
	static int pow(int n, int r) {
		if (r == 1)
			return n;

		long x = pow(n, r / 2);
		if (r % 2 == 0) {
			return (int) ((x * x) % P);
		} else {
			return (int) (((x * x) % P * n) % P);
		}
	}

}
