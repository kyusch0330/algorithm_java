package graph.union_find;

import java.io.*;
import java.util.*;

public class Dijkstra {

	static int V, E;

	// 노드 번호와 가는데 드는 비용을 저장 int[]
	static ArrayList<ArrayList<AdjNode>> adjList = new ArrayList<>();
	static int dist[]; // 노드로 가는 최단 거리를 저장하는 일종의 dp
	static boolean visited[]; // 힙을 쓰면 필요없다?

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/graph.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 0번은 의미 없고 1번부터 저장
		for (int i = 0; i <= V; i++) {
			adjList.add(new ArrayList<>());
		}

		// 간선 정보(양방향)
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int n1 = Integer.parseInt(st.nextToken());
			int n2 = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			adjList.get(n1).add(new AdjNode(n2, weight));
			adjList.get(n2).add(new AdjNode(n1, weight));
		}

		// 시작

		// 최단 거리 저장 배열 초기화
		dist = new int[V + 1]; // V+1인 이유는 단순히 인덱스를 1부터 시작해서
		Arrays.fill(dist, Integer.MAX_VALUE);

		visited = new boolean[V + 1];

		/************/
		int start = 1; // 1로부터의 최단거리를 구한다.
		/************/

		dist[start] = 0; // 자기 자신은 최단거리가 0


		// 모든 노드를 한번씩 방문하며 최단거리 갱신
		for (int v = 0; v < V; v++) {

			int minDistNode = -1;
			int minDist = Integer.MAX_VALUE;
			for (int i = 1; i <= V; i++) {
				if (!visited[i] && minDist > dist[i]) {
					minDist = dist[i];
					minDistNode = i;
				}
			}

			// 해당 노드 방문 체크
			visited[minDistNode] = true;

			// 인접 노드를 순회하며 dist값 갱신
			for (int i = 0; i < adjList.get(minDistNode).size(); i++) {
				AdjNode adjNode = adjList.get(minDistNode).get(i);
				// 현재 최단 거리와 비교해서 더 작은 거리로 갱신
				dist[adjNode.num] = Math.min(dist[adjNode.num], dist[minDistNode] + adjNode.weight);
			}

		} // 종료
		
		System.out.println(Arrays.toString(dist));

	}
}

