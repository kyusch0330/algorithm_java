package string.trie;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {

	private Map<Character, TrieNode> childNodes = new HashMap<>(); // 다음 글자에 따른 자식 노드들
	private boolean isLastChar; // 어떤 단어의 마지막 글자인지
	
	public Map<Character,TrieNode> getChildNodes() {
		return this.childNodes;
	}
	
	public boolean isLastChar() {
		return this.isLastChar;
	}

	public void setIsLastChar(boolean isLastChar) {
		this.isLastChar = isLastChar;
	}
}
