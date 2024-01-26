/**
 * author Zhengyu Lin
 */

package TrieStructure;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class TrieTree {

    private ArrayList<String> arrayList = new ArrayList<>(); //This arraylist contains the all autosuggestion sentence
    public static final Node root = new Node();
    public static final int MAX = 10; // The Maximum number of autosuggestion in search bar

    //Insert data to the Tree
    public void insert(String word) {
        if (word == null) { return; }
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            String str = String.valueOf(word.charAt(i));
            if (node.next.get(str) == null){
                node.next.put(str, new Node());
            }
            node = node.next.get(str);
        }
        node.setEnd(true); //The sentence is ended, set the end label as true
    }

    // Check the word exist in the tree or not
    public boolean startWith(String Word) {
        Node node = root;
        for (int i = 0; i < Word.length(); i++) {
            String str = "" + Word.charAt(i);
            if (node.next.get(str) == null){
                return false;
            }
            node = node.next.get(str);
        }
        return true;
    }

    //Add the correlation data(title or author name) in the list
    //This method can use multithreading, it will search quicker.
    private void addSuggestion(Node node, StringBuilder str){
        //if this sentence is a complete content, list add it.
        if (arrayList.size() == MAX){
            return;
        }
        if (node.isEnd()) {
            arrayList.add(str.toString());
            if (node.next.isEmpty()) { return; } //if after the preword without any content, stop.
        }
        for (String s : node.next.keySet()) {
            str.append(s);
            addSuggestion(node.next.get(s), str);
            str.delete(str.length() - 1, str.length());
        }
    }

    public ArrayList<String> getData(String preword) {
        arrayList.clear();
        if (!startWith(preword)) {
            return null;
        }
        else {
            StringBuilder str = new StringBuilder(preword);
            Node node = root;
            for (int i = 0; i < preword.length(); i++){
                node = node.next.get("" + preword.charAt(i));
            }
            addSuggestion(node, str);
        }
        return arrayList;
    }
}