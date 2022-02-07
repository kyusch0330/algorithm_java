package string.trie;

public class Trie {

	/**
	 * Trie는 빈문자열을 가지는 루트 노드로부터 시작된다.
	 */
	private TrieNode rootNode;
	
	public Trie() {
		this.rootNode = new TrieNode();
	}
	
	/**
	 * insert
	 * 삽입할 string의 각 char에 따라 동일한 TrieNode를 따라 내려간다.
	 * 따라갈 노드가 없다면 새로 생성하면서 끝 문자까지 삽입을 완료한다.
	 *  => 마지막 문자에는 한 단어의 끝 문자라는 표시로 isLastChar를 true로 변경해준다.
	 */
	
	public void insert(String word) {
		TrieNode currentNode = this.rootNode; // 루트 노드부터 시작
		
		for(int i=0; i<word.length(); i++) {
			char currentChar = word.charAt(i);
			// 현재 트라이 노드의 자식들 중 현재 문자가 있는지 체크
			// 없다면 추가해준다.
			if(!currentNode.getChildNodes().containsKey(currentChar)) {
				currentNode.getChildNodes().put(currentChar, new TrieNode());
			}
			// 자식 노드를 따라간다.
			currentNode = currentNode.getChildNodes().get(currentChar);
		}
		// 한 단어의 마지막 문자임을 표시해준다.
		currentNode.setIsLastChar(true);
	}
	
	/**
	 * contains
	 * 해당 단어가 Trie에 존재하는지 확인
	 * 기본적인 확인법은
	 *  1. 끝문자까지 자식노드가 존재하는지 확인
	 *  2. 끝문자에서 isLastChar가 true여야 완전히 동일한 문자열임을 확인 가능
	 */
	
	public boolean contains(String word) {
		TrieNode currentNode = this.rootNode;
		
		for(int i=0; i<word.length();i++) {
			char currentChar = word.charAt(i);
			// 어떤 지점에서 해당 문자에 해당하는 자식 노드가 없으면 동일한 문자열이 없음을 확인하고 false 리턴
			if(!currentNode.getChildNodes().containsKey(currentChar)) {
				return false;
			}
			// 해당하는 문자 자식 노드를 따라간다.
			currentNode = currentNode.getChildNodes().get(currentChar);
		}
		// 마지막 문자의 isLastChar가 true 여야 완전히 동일한 문자임을 알 수 있다.
		if(!currentNode.isLastChar()) return false;
		else return true;
	}
	
	/**
	 * delete
	 * 삭제 : 난이도 있어서 추후 공부
	 * @param args
	 */
	
	public static void main(String[] args) {
		Trie t = new Trie();
		t.insert("Hello");
		t.insert("World");
		System.out.println(t.contains("Hell"));
		System.out.println(t.contains("Hello"));
	}
}
