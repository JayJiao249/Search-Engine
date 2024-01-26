/**
 * author Zhengyu Lin
 */
package TrieStructure;
import java.util.HashMap;
import java.util.Map;

class Node{
    public Map<String, Node> next;
    private Boolean End;
    public Node() {
        this.next = new HashMap<String, Node>();
        this.End = false;
    }

    public void setEnd(Boolean end) {
        End = end;
    }

    public Boolean isEnd() {
        return End;
    }

}
