package graph.topological_sort;

import java.util.*;
import java.io.*;
// 위상 정렬

public class TopologicalSort {
	public static void main(String[] args) {

		/**
		 * 위상 정렬은 그래프 순서 정렬을 의미한다.
		 * 주로 어떤 작업의 선후 관계가 주어지는 문제에서 사용
		 *  => 순서가 지켜지는 것을 보장하는 결과를 도출할 수 있다.
		 * 
		 * 조건 : Directed Acyclic Graph (방향성이 있고, 사이클이 없는 그래프)
		 * 
		 * dfs 또는 indegree 배열로 구현 가능하다.
		 * indegree 배열 : 각 노드로 들어오는 차수를 저장  ( int[] )
		 * 
		 * 마치 bfs와 유사한 동작
		 * 
		 * Queue에 indegree 차수가 0인 노드를 저장한다.
		 * Queue에서 노드를 꺼내 차수 0인 노드가 가리키는 노드의 차수를 감소시킨다.
		 * Queue가 빌 때까지 반복
		 * 
		 * -> Queue에서 꺼내진 노드를 결과 배열(큐)에 저장하면, 순서대로 저장 가능
		 * -> 답은 여러가지가 나올 수 있다.
		 * 
		 */
		
		/**
		 * 작업이 N개 일 때, 작업 순서 M개가 주어지고 작업 순서를 지키며 수행하는 순서를 아무거나 출력
		 */
		int N = 5;
		int M = 5;
		
		String orders = "1 3|5 3|2 1|2 5|3 4";
		
		/**
		 * 그래프로 표현하면,
		 * 2 - 1 - 3 - 4
		 * 2 - 5 --┘  
		 * 
		 * -> 2 1 5 3 4 또는 2 5 1 3 4 가 정답이 될 수 있다.
		 */
		
		// 위상정렬 수행
		int[] indegree = new int[N+1];
		ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(orders,"|");
		
		for(int i=0; i<=N; i++) {
			adj.add(new ArrayList<>());
		}
		
		// 인접 리스트 및 indgree 저장
		for(int i=0; i<M; i++) {
			StringTokenizer st2 = new StringTokenizer(st.nextToken());
			int from = Integer.parseInt(st2.nextToken());
			int to = Integer.parseInt(st2.nextToken());
			
			adj.get(from).add(to);
			indegree[to] ++;
		}
		
		// indegree가 0인 노드들을 큐에 저장 : 큐 초기화
		Queue<Integer> zeroQ = new LinkedList<>();
		for(int i=1; i<=N; i++) {
			if(indegree[i]==0) zeroQ.add(i);
		}
		
		Queue<Integer> res = new LinkedList<>();
		while(!zeroQ.isEmpty()) {
			
			int zeroNode = zeroQ.poll();
			//indegree[zeroNode] = -1; // 다음부터 차수가 0인 노드에 포함되지 않게 -> 할 필요 없다. 어차피 인접노드의 차수를 감소시키고 0인지 검사를 하기 때문에
			res.add(zeroNode); // 결과에 포함
			
			// 현 노드를 선행으로 했던 노드들의 차수를 감소시켜준다.
			for(int adjNode : adj.get(zeroNode)) {
				indegree[adjNode]--;
				
				if(indegree[adjNode]==0) {
					// 차수가 0이 되면 큐에 추가
					zeroQ.add(adjNode);
				}
			}
		}
		
		for(int node : res) {
			System.out.print(node+" ");
		}
	}
}
