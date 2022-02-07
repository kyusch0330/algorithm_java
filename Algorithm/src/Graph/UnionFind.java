package Graph;

public class UnionFind {
	public static int MAX_SIZE = 10;
	// 부모 노드의 정보만 갖는 노드 배열
	public static int parent[] = new int[MAX_SIZE];
	
	public static void main(String[] args) {
		
		/* 
		 * 각자 자기 자신을 부모 노드로 초기화 해준다.
		 * 여기서 자기 자신이 부모인 노드는 루트 노드임을 의미한다.
		 */
		for(int i=0; i<MAX_SIZE; i++) 
			parent[i]=i;
		
		
	}
	/*************/
	/*find 함수 구현*/
	/*************/
//	public int find(int x) {
//		if(x==parent[x]) {
//			return x;
//		}
//		else {
//			return find(parent[x]);
//		}
//	}
	/**
	 * 위는 잘못된 방법이다.
	 * 일렬로 된 트리라면 루트노드까지 N-1개 만큼 거슬러 올라갈 수도 있으므로 시간 복잡도가 O(N)이 된다.
	 * 이는 tree로 구현하는 이점을 없앤다.
	 */
	
	public static int find(int x) {
		if(x==parent[x]) return x; // 현재 값이 자신의 부모와 같다면 루트 노드이다.
		else { // 루트노드가 아니라면
			int y = find(parent[x]); // 타고타고 올라가서 결국에는 루트노드 y를 찾게 되고, 
			parent[x] = y; // x는 다른건 다 무시하고 y가 루트인 트리에 속한다는 것만 기억한다. 
			return y; // 자신을 호출한 자식 노드에게도 루트노드의 존재를 알린다.
			// 한 줄로 : return parent[x] = find(parent[x]);
		}
	}
	// 이렇게 하면 일렬로 된 트리에 있는 노드가 전부 루트 노드에 레벨1 자식노드가 되는 것이나 마찬가지이다.
	// 각 노드들에 대해 find()를 실행해주어 각자의 루트를 정해준다.
	/**
	 *     O y                    O   y
	 *     |                     / \
	 *     O x   -> parent : y  O   O  parent : y
	 *     |                   
	 *     O w
	 */    
	
	// -=*=-=*=-=*=-=*=-=*=-=*=-=*=-=*=-=*=-=*=-=*=-=*=-=*=-=*=-=*=-=*=-=*=-=*=-=*
	
	/**************/
	/*Union 함수 구현*/
	/*************/
	// y의 루트를 x로 지정해주면 하나의 트리가 된다.
	
	public static void union(int x,int y) {
		x = find(x);
		y = find(y);
		if(x!=y) parent[y] =x;
	}
}
