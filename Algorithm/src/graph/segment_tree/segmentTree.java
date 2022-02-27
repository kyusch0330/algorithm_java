package graph.segment_tree;

import java.util.*;
import java.io.*;

public class segmentTree {

	/**
	 * 세그먼트 트리는 배열의 특정 구간에 대한 연산 저장에 특화된 트리이다. 특정 구간에 대한 연산값을 조회하는데 O(logN)의 시간복잡도를
	 * 가진다.
	 * 
	 * 세그먼트 트리는 이진트리 구조이다. 현재 범위를 양분하여 양 자식 노드로 가진다. -> 왼쪽 자식 : start ~ mid -> 오른쪽
	 * 자식 : mid+1 ~ end
	 * 
	 * 각 노드에는 sum, min 혹은 max 등의 결과가 담길 수 있다.
	 * 
	 * 구현) 이진트리이기 때문에 일차원 배열로 구현이 가능하다. 배열의 크기는? -> 최대의 경우 마지막 레벨에 크기 1인 구간 N개가 들어가기
	 * 때문에
	 * 
	 * h = ceil(log2(N)); 가 되고 (트리 마지막 레벨) 2^(h+1) 크기의 배열을 생성해주어야 한다.
	 */

	static int[] nums;
	static int[] tree; // 세그먼트 트리

	public static void main(String[] args) {
		// 구간 연산을 할 일차원 배열
		nums = new int[] { 3, 7, 5, 1, 2, 9, 10, 99, 77, 42, 1000 };

		int N = nums.length;
		int h = (int) Math.ceil(Math.sqrt(N));
		System.out.println("h : " + h);
		tree = new int[(int) Math.pow(2, h + 1)];
		System.out.println("N : " + N);
		System.out.println(tree.length);

		// init
		init(0, N - 1, 1); // 구간 인덱스 start, end / 트리 노드번호 1부터 시작(루트)
		printTree();

		// 0~3 구간합 구하기
		System.out.println("0~3 구간합");
		System.out.println(sum(0, 3, 1, 0, N - 1));
		// 5~7 구간합 구하기
		System.out.println("5~7 구간합");
		System.out.println(sum(5, 7, 1, 0, N - 1));
		// 9~10 구간합 구하기
		System.out.println("9~10 구간합");
		System.out.println(sum(9, 10, 1, 0, N - 1));
		// 10~10 구간합 구하기
		System.out.println("10~10 구간합");
		System.out.println(sum(10, 10, 1, 0, N - 1));

		System.out.println("-------------update--------------");
		// 업데이트
		// 인덱스 9 값 업데이트
		int updateNum = 100;
		int diff = updateNum - nums[9];
		nums[9] = updateNum;

		// 세그먼트 트리 업데이트
		update(0, N - 1, 1, diff, 9);
		// 9~10 구간합 구하기
		System.out.println("9~10 구간합");
		System.out.println(sum(9, 10, 1, 0, N - 1));

		// 인덱스 10 값 업데이트
		updateNum = 500;
		diff = updateNum - nums[10];
		nums[10] = updateNum;

		// 세그먼트 트리 업데이트
		update(0, N - 1, 1, diff, 10);
		// 9~10 구간합 구하기
		System.out.println("9~10 구간합");
		System.out.println(sum(9, 10, 1, 0, N - 1));
	}

	// 세그먼트 트리 초기화
	static int init(int start, int end, int current) {
		if (start == end) {
			// 구간이 1인 경우
			tree[current] = nums[start];
			return tree[current];
		}

		// 구간이 1보다 큰 경우 나눈다.
		int mid = (start + end) / 2;
		tree[current] = init(start, mid, 2 * current) + init(mid + 1, end, 2 * current + 1);
		return tree[current];
	}

	// 구간 합 조회 (start, end는 범위 확인용으로, 계속 유지)
	static int sum(int start, int end, int current, int left, int right) {

		// 범위 내의 구간이라면 총합에 세그먼트 트리의 해당 구간 합을 더해준다.
		if (left >= start && right <= end) {
			System.out.println("correct segment : " + left + " ~ " + right + " -> " + tree[current]);
			return tree[current];
		}

		// 범위가 아예 어긋난 구간이면 0을 리턴해 총합에 영향이 없도록 한다.
		if (left > end || right < start)
			return 0;

		// 범위가 일부만 어긋나는 구간이면 올바른 구간이 되도록 나눠준다.
		int mid = (left + right) / 2;
		return sum(start, end, current * 2, left, mid) + sum(start, end, current * 2 + 1, mid + 1, right);

	}

	// 구간 합 수정 (changeIdx = 바뀐 원소의 인덱스 (하나뿐이므로 left, right가 아니다.))
	static void update(int start, int end, int current, int diff, int changeIdx) {

		// 바뀐 원소가 포함되지 않은 구간이라면 종료
		if (changeIdx < start || changeIdx > end) {
			return;
		}

		// 해당 구간에 바뀐 원소가 있으면 세그먼트 트리 노드 수정
		tree[current] += diff;
		if (start == end)
			return; // 수정된 원소가 포함된 구간은 전부 다 수정했으므로 종료

		// 수정된 원소가 포함된 구간을 찾아 나눠 내려간다.
		int mid = (start + end) / 2;
		update(start, mid, current * 2, diff, changeIdx);
		update(mid + 1, end, current * 2 + 1, diff, changeIdx);
	}

	// 출력용
	static void printTree() {
		System.out.println();
		System.out.println("###########################################################################");
		Queue<Integer> q = new LinkedList<>();
		q.add(1);
		int level = 1;
		while (!q.isEmpty()) {
			System.out.println("<level " + level + ">");
			int qSize = q.size();
			while (qSize-- > 0) {
				int current = q.poll();

				System.out.print(" " + tree[current] + " ");

				if (current * 2 < tree.length)
					q.add(current * 2);
				if (current * 2 + 1 < tree.length)
					q.add(current * 2 + 1);
			}
			level++;
			System.out.println();
		}
		System.out.println("###########################################################################");
		System.out.println();
	}

}
