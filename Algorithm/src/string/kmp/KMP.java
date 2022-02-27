package string.kmp;

import java.util.ArrayList;

public class KMP {

	/**
	 * 문자열 검색 알고리즘
	 * 
	 * 브루트 포스로 어떤 패턴이 문자열에 존재하는지 확인한다면? -> 첫 글자부터 패턴의 문자열과 비교하고, 중간에 틀리면 다시 두번째 글자부터
	 * 패턴의 문자열과 비교하고... 반복 => O(NM) (N:문자열 길이, M:패턴 문자열 길이) 오래 걸린다.
	 * 
	 * KMP 알고리즘을 이용하면 O(N+M)에 해결 가능 -> 사실상, 문자열을 선형으로 한 번만 읽는 것과 동일한 시간이다.
	 * 
	 * 
	 * 핵심 아이디어 
	 * 브루트 포스 방식에서는 i번째 글자부터 시작해서 중간에 패턴과 틀리면 다시 i+1번째 글자부터 하나씩 패턴과 비교해야 하지만,
	 *  KMP에서는 접두사, 접미사와 관련된 방법으로 만든 배열의 값을 통해 다시 패턴과 비교를 시작할 위치를 효율적으로 건너뛰어서
	 * 시간을 단축시킨다.
	 * 
	 * ex) ABAABA 라는 패턴을 문자열 ABABAABA 에서 찾아본다면,
	 * 
	 * ABABAABA 
	 * ABAABA -> 네번째 글자 B에서 틀리게 된다.
	 * 
	 * 브루트 포스 방식이면 : 
	 * ABABAABA 
	 *  ABAABA -> 다시 이렇게 다음 글자부터 비교를 하겠지만,
	 * 
	 * KMP 에서는 : 
	 * ABABAABA
	 *   ABAABA -> 이렇게 틀리기 전에 앞에까지 패턴과 일치했던 정보를 이용해 적절한 위치에서 다시 비교를
	 * 수행할 수 있다.
	 * 
	 * -> 패턴 길이의 정수형 배열에 패턴의 i번째 글자에서 틀리면 패턴을 문자열의 어느 위치에서 다시 비교할지 정하는 정수값을 저장한다?
	 * 
	 * -> 이 배열을 만드는데 접두사, 접미사의 개념을 이용한다.
	 * 
	 * 패턴을 앞에서부터 한글자씩 추가하여 각 부분문자열에서 접두사, 접미사가 몇 글자나 일치하는지 계산하여 배열에 저장한다.
	 * ex) 위의 패턴
	 * ABAABA는 A -> 한 글자이므로 접두사, 접미사가 하나도 일치하지 않는다. -> 0 
	 * AB -> 접두사는 A, 접미사는 B 하나뿐인데 일치하지 않으므로 0 
	 * ABA -> 접두사는 A,AB 접미사는 BA,A 인데 가장 길게 일치하는 길이가 A -> 1이므로 1을 저장 
	 * ABAA -> A,AB,ABA / A,AA,BAA -> A: 1 
	 * ABAAB -> A,AB,ABA,ABAA / B,AB,AAB,BAAB -> AB:2
	 * ABAABA-> A,AB,ABA,ABAA,ABAAB / A,BA,ABA,AABA,BAABA -> ABA: 3 (-> 문자열을 절반으로
	 * 나누어서 양부분을 양끝에서 같은 글자수를 비교해서 최대 몇글자나 같은지를 보면 된다. 홀수개면 중간은 빼고)
	 * 
	 * => 이런식으로 배열에 저장한다.
	 * 
	 * 실제 알고리즘은 위와 같은 결과를 내는 방식을 사용하여 배열에 저장하게 된다.
	 * 
	 * 패턴과의 비교에서 패턴의 i번째 글자에서 틀리게 되는 경우, 배열의 i번 째에 저장된 숫자에 해당하는 패턴의 글자가 틀린위치로 오게 패턴을
	 * 문자열에 위치시킨다. 
	 * ex) 1이 배열의 i번째에 있었다면 
	 * OOOX-----(X가 틀린부분) 
	 *    12345 (패턴의 1번째 글자가 해당 위치로 오게)
	 * 
	 * 0이라면 틀린 위치 한칸 뒤에 패턴의 첫글자가 오면 된다.
	 */

	public static void main(String[] args) {

		String text = "AABABBABABBAAABABABAABAAAAABAABBABABABAABAA";
		String pattern = "ABAABA";
		int next[] = new int[pattern.length()]; // 패턴의 i번째 글자에서 틀린 경우 재검사 위치를 얼마나 옮겨야 하는지 저장하는 배열

		next[0] = 0; // 첫글자에서 틀리면 무조건 바로 다음 글자부터 재검사를 해야한다.

		// next 배열에 접근하는 인덱스 i -> 0은 미리 채웠으므로 1부터 채우면 된다. (패턴을 i번째 글자까지 잘랐을 때 접두사, 접미사의최대 일치 길이를 구한다.)
		// -> i는 새롭게 추가된 접미사 마지막 글자의 인덱스가 된다.
		// j는 접두사 영역에서 접미사와 몇번째 글자까지 일치하는지 표시하는 인덱스이다.-> 현재 j-1까지 접두사는 접미사와 일치한다.
		for (int i = 1, j = 0; i < pattern.length(); i++) {

			while (j > 0 && pattern.charAt(i) != pattern.charAt(j) )
				j = next[j - 1];

			if (pattern.charAt(i) == pattern.charAt(j))
				next[i] = ++j; // 일치하는 접두사, 접미사 글자수 +1 후 저장
			else
				next[i] = 0;
		}
		
		int matchCnt = 0;
		ArrayList<Integer> matchIndex = new ArrayList<Integer>();
		
		// text 글자 수 만큼 반복
		for(int i=0,p=0; i<text.length(); i++) { 
			
			while(p>0 && text.charAt(i) != pattern.charAt(p)) p = next[p-1]; 
			
			if(text.charAt(i) == pattern.charAt(p)) { // text와 패턴의 글자가 일치한다면
				if(p == pattern.length()-1) { // 패턴의 마지막 글자까지 일치한 경우 
					matchCnt++; // 카운트 증가
					matchIndex.add((i+1)-pattern.length());  
					p=next[p];  
				}else { 
					p++; // 패턴의 다음 글자를 비교 
				}
			}
		}
		
		System.out.println(matchCnt);
		System.out.println(matchIndex);
	}

}
