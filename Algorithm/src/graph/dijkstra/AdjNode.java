package graph.dijkstra;

public class AdjNode {
	public int num;
	public char name;
	public int weight;
	
	public AdjNode(int num, int weight) {
		this.num = num;
		this.weight = weight;
	}
	public AdjNode(char name, int weight) {
		this.name = name;
		this.weight = weight;
	}
}
