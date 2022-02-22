package graph.dijkstra;

import java.io.*;
import java.util.*;

public class DijkstraWithHeap {
	static int V, E;

	// 노드 번호와 가는데 드는 비용을 저장 int[]
	static ArrayList<ArrayList<AdjNode>> adjList = new ArrayList<>();
	static int dist[]; // 노드로 가는 최단 거리를 저장하는 일종의 dp
//	static boolean visited[]; // 힙을 쓰면 필요없다?

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
		
		//최소힙이 현재 최단거리를 구해주고, visited의 역할도 대신한다. (방문하면 제거)
		// int[0] = 노드 인덱스, int[1] = 최단거리
		PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->{
			return a[1] - b[1];
		});

		/************/
		int start = 1; // 1로부터의 최단거리를 구한다.
		/************/

		dist[start] = 0;
		pq.add(new int[] {start,0}); // 자기 자신의 최단 거리를 0으로 하고 시작


		// 모든 노드를 한번씩 방문하며 최단거리 갱신
		while(!pq.isEmpty()) {
			int[] minDistNode = pq.poll();
			int nodeIndex = minDistNode[0];
			int d = minDistNode[1];

			// 해당 노드 방문 체크
//			visited[minDistNode] = true; // 우선순위 큐에서 제거됨으로써 방문체크 된 것이나 같다.
			if(dist[nodeIndex] < d ) continue; // 아니면 그냥 visited로 해도 된다.
			
			// 인접 노드를 순회하며 dist값 갱신
			for (int i = 0; i < adjList.get(nodeIndex).size(); i++) {
				AdjNode adjNode = adjList.get(nodeIndex).get(i);
				// 현재 최단 거리와 비교해서 더 작은 거리로 갱신
				if(dist[adjNode.num] > dist[nodeIndex] + adjNode.weight) {
					dist[adjNode.num] = dist[nodeIndex] + adjNode.weight;
					pq.add(new int[] {adjNode.num, dist[adjNode.num]}); // 우선순위 큐에 추가
				}
			}

		} // 종료
		
		System.out.println(Arrays.toString(dist));

	}
}
