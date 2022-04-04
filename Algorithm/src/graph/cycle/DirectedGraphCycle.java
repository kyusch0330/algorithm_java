package graph.cycle;

import java.util.*;
import java.io.*;

public class DirectedGraphCycle {

	public static void main(String[] args) {
		
		/**
		 * dfs로 방향 그래프의 사이클을 탐색할 수 있다.
		 * 무방향 그래프는 union-find로 가능
		 * 
		 * visited -> 해당 노드를 방문 했는지
		 * finished -> 해당 노드의 dfs가 끝났는지
		 */
		int V = 10;
		for(int i=0; i<V; i++) {
			if(!finished[i]) dfs(i);
		}
		if(isCycled) System.out.println("cycle");
	}
	
	static ArrayList<ArrayList<Integer>> adj = new ArrayList<>(); // 그래프 인접 리스트
	static boolean[] visited, finished;
	
	static boolean isCycled = false;
	static void dfs(int current) {
		
		visited[current] = true;
		
		for(int adjNode : adj.get(current)) {
			if(!visited[adjNode]) {
				// 방문한적 없다면, dfs 탐색
				dfs(adjNode);
			} else if (!finished[adjNode]) {
				// 다음 인접노드가 방문했던 노드라도, 사이클을 이루지 않는 경우가 존재한다.
				// dfs가 끝난 노드라면 : dfs로 끝까지 갔는데도 현재 노드를 방문한적 없었으므로, 역방향이 아니다.
				// dfs가 끝나지 않은 노드라면 : 해당 노드가 아직 dfs 실행 중인데도 다시 만났다면 역방향으로 만난 것이다.
				// 아직 해당 인접노드의 dfs 탐색이 끝나지 않았는데 또 만나면 사이클
//				System.out.println(current + " -> "+adjNode +" makes Cycle!!!");
				isCycled = true;
				return;
			}
		}
		
		// 현재 노드의 dfs 수행 완료 표시
		finished[current] = true;
	}

}
