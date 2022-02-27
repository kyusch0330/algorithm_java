package graph.mst.kruskal;

import java.util.*;
import java.io.*;

public class Kruskal {

	public static void main(String[] args) {
		/**
		 * 간선 리스트를 이용해 최소 신장 트리를 만드는 알고리즘
		 * 
		 * 신장 트리 : 그래프에서 모든 정점을 지나고, 사이클을 만들지 않는 트리
		 * 최소 신장 트리 : 신장 트리 중에서 가중치 합이 가장 작은 트리
		 * 
		 * 간선을 가중치 올림차순으로 정렬한다.
		 * 정점의 수가 V일 때, 간선을 V-1개 만큼 선택하면 종료한다.
		 * 간선 선택 시, 사이클을 이루지 않아야 한다.
		 *  -> 이를 union-find를 이용해 같은 union이 아닌 경우에만 선택하면 된다.
		 *  
		 *  간선 수 E가 적을 때 유리하다.
		 *   간선이 많으면 정렬에 많은 시간이 든다. O(ElogE)
		 *   
		 */
		
		int V = 5; //정점 수
		int E = 6; //간선 수
		
		/**
		 * 그래프가 다음과 같을 때,
		 * 
		 *     4       1
		 * (1)----(2)----(4)
		 *  \      \     /
		 *   \ 2    \ 7 / 5
		 *    \      \ /
		 *    (3)----(5)
		 *         4
		 */
		
		/**
		 * 최소 신장 트리 MST는 다음과 같다.(가중치 합 11이 최소)
		 * 
		 *     4       1
		 * (1)----(2)----(4)
		 *  \     
		 *   \ 2    
		 *    \      
		 *    (3)----(5)
		 *         4
		 */
		
		// 위 그래프의 간선 정보 (노드, 노드, 가중치)
		String inputs[] = {
				"1 2 4",
				"2 4 1",
				"3 5 4",
				"3 1 2",
				"4 5 4",
				"5 2 7"
		};
		
		// 모든 간선을 저장 후 오름차순으로 정렬한다.
		ArrayList<int[]> edges = new ArrayList<>();
		for(int i=0; i<E; i++) {
			StringTokenizer st = new StringTokenizer(inputs[i]);
			int n1 = Integer.parseInt(st.nextToken());
			int n2 = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			edges.add(new int[] {n1,n2,cost});
		}
		
		// 오름차순 정렬
		edges.sort((e1,e2)->e1[2]-e2[2]);
		
		// 혹은 우선순위 큐에 간선 가중치 순으로 나올 수 있도록 담는다. (최소힙) -> 권장
//		PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)->e1[2]-e2[2]);
		
		/*
		 * 사이클 검사를 위한 union-find 준비
		 */
		parent = new int[V+1];
		for(int i=0; i<=V; i++) {
			parent[i] = i;
		}
		
		// 가장 작은 가중치의 간선부터 사이클을 이루지 않는다면 MST에 추가한다.
		int cnt = 0; // 간선이 V-1개 포함되면 MST가 완성됨
		int totalCost = 0; // MST에 포함된 간선들의 가중치 합 구하기
		for(int i=0; i<edges.size();i++) {
			int[] edge = edges.get(i);
			int n1 = edge[0];
			int n2 = edge[1];
			int cost = edge[2];
			
			if(find(n1)==find(n2)) continue; // 둘이 이미 같은 유니언이라면 간선 추가 시 사이클을 이루므로 이 간선은 MST에 포함하지 않는다.
			
			union(n1,n2);
			totalCost += cost;
			cnt++; // 간선이 추가됨을 표시
			if(cnt==V-1) break; // 간선이 V-1개가 포함되면 MST 완성
		}
		
		System.out.println(totalCost);
		
	}
	
	static int[] parent;
	
	static int find(int n) {
		if(n==parent[n]) return n;
		else return parent[n] = find(parent[n]);
	}
	
	static void union(int n1, int n2) {
		int r1 = find(n1);
		int r2 = find(n2);
		
		if(r1==r2) return; // 둘이 이미 같은 집합이면 더이상 진행할 필요가 없다. 
		
		int small = Math.min(r1, r2);
		int big = Math.max(r1, r2);
		parent[big] = small; // 더 작은 숫자 노드가 루트가 되도록
	}
}
