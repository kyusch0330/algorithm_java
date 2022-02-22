package heap;
import java.util.Collections;
import java.util.PriorityQueue;

public class Heap {

	public static void main(String[] args) {
		//직접 구현 최대 힙
		MaxHeap mh = new MaxHeap();
		
		int[] arr = {9,3,2,5,6,11};
		
		for(int i=0; i<arr.length; i++) {
			mh.add(arr[i]);
		}
		
		for(int i=0; i<arr.length; i++) {
			arr[i] = mh.pop();
			System.out.print(arr[i]+" ");
		}
		
		System.out.println();
		//우선순위 큐 import하여 최소힙 구현
		int[] arr2 = {9,3,2,5,6,11};
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for(int i=0; i<arr2.length; i++) {
			pq.add(arr2[i]);
		}
		
		for(int i=0; i<arr2.length; i++) {
			if(!pq.isEmpty())
				arr2[i] = pq.poll();
			System.out.print(arr2[i]+" ");
		}
		

		System.out.println();
		//우선순위 큐 import하여 최대힙 구현
		int[] arr3 = {9,3,2,5,6,11};
		PriorityQueue<Integer> pq2 = new PriorityQueue<>(Collections.reverseOrder());
		for(int i=0; i<arr3.length; i++) {
			pq2.add(arr3[i]);
		}
		
		for(int i=0; i<arr.length; i++) {
			if(!pq2.isEmpty())
				arr3[i] = pq2.poll();
			System.out.print(arr3[i]+" ");
		}
		
		System.out.println();
		// NameNode라는 클래스 객체를 이름의 길이 순으로 정렬하는 최대힙 구현
		PriorityQueue<NameNode> nq = new PriorityQueue<>((a,b)-> {
			return b.name.length()- a.name.length(); 
		});
		String[] names = {"james","tom","kim","paris","yasuo","minsu","shakespears","yi","dijkstra"};
		for(int i=0; i<names.length; i++) {
			nq.add(new NameNode(names[i]));
		}
		for(int i=0; i<names.length; i++) {
			names[i] = nq.poll().name;
			System.out.print(names[i]+" ");
		}
		
	}
}

class NameNode {
	public String name = "";
	public NameNode(String name) {
		this.name = name;
	}
}

class MaxHeap {
	private static final int HEAP_CAPACITY = 100;
	private HeapNode[] arr = new HeapNode[HEAP_CAPACITY];
	private int size;

	public MaxHeap() {
		this.size = 0;
	}

	public int getParentIndex(int index) {
		// 루트노드 인덱스가 1
		return index / 2;
	}

	public int getLeftChildIndex(int index) {
		return index * 2;
	}
	
	public int getRightChildIndex(int index) {
		return index * 2 + 1;
	}
	
	public boolean isEmpty() {
		if(this.size == 0) return true;
		else return false;
	}
	
	public void add (int key) {
		HeapNode newHeapNode = new HeapNode(key);
		
		arr[++this.size] = newHeapNode;
		
		// 마지막 노드부터  그 조상 노드들을 모두  heapify
		for(int i=size/2; i>0; i/=2)
			heapify(i);
	}
	
	public int pop () {
		int maxValue = arr[1].key;
		
		arr[1] = arr[this.size--];
		heapify(1);
		return maxValue;
	}
	
	public void heapify(int index) {
		int leftChildIndex = getLeftChildIndex(index);
		int rightChildIndex = getRightChildIndex(index);
		
		int largest = leftChildIndex;
		if(leftChildIndex > size) return;
		
		if(rightChildIndex <= size) {
			if(arr[leftChildIndex].key < arr[rightChildIndex].key) {
				largest = rightChildIndex;
			}
		}
		
		if(arr[largest].key > arr[index].key) {
			
			HeapNode tmp = arr[index];
			arr[index] = arr[largest];
			arr[largest] = tmp;
			heapify(largest);
		}
		
	}
	
	
}

class HeapNode {
	int key;

	public HeapNode(int key) {
		this.key = key;
	}
}
