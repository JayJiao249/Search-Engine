import java.util.*;

/**
 * Author Lin
 * Invert Index Library
 * TODO processing the query and return the correlate paper ID
 *  ranking papers by score
 */

public class IndexLibrary{
    private HashMap<String, ArrayList<Doc>> Data; //Hashmap: K: keyword, V:Documents
    private int datasize; //the number of all keywords appear in all papers
    public IndexLibrary(){}

    public void setData(HashMap<String, ArrayList<Doc>> hashMap) {
        this.Data = hashMap;
    }

    public void setDatasize(int datasize) {
        this.datasize = datasize;
    }

    //Get the paperID which correlate with query
    public ArrayList<Integer> getResult (String query) {
        ArrayList<String> originalKeyWords = Tokenizer.Token(query);
        HashSet<String> similarWords = new HashSet<>();
        HashMap<Integer, Float> IDScore = new HashMap<>(); //K: PaperID; V: Score
        for (String keyword : originalKeyWords) {
            ArrayList<Doc> docs = Data.get(keyword); //The documents which corresponding keywords
            if(docs != null) {
                similarWords.addAll(Tokenizer.getSimilarWords(keyword));
                float prominence = (float) datasize / docs.size(); // The prominence of the keyword, proportional to the rarity of the word
                for (Doc doc : docs) {
                    float weight = (float) 1/ doc.getCountword(); //Weight: inverse proportional words of document
                    float score = prominence + prominence*weight;
                    int ID = doc.getPaperID();
                    if (!IDScore.containsKey(ID)) {
                        IDScore.put(ID, score);
                    } else {
                        score = IDScore.get(ID) + score;
                        IDScore.replace(ID, score);
                    }
                }
            }
        }
        originalKeyWords.forEach(similarWords::remove);
        HashMap<Integer, Float> IDScore2 = new HashMap<>();
        // Semantically sensitive search
        for (String simiword : similarWords){
            ArrayList<Doc> docs = Data.get(simiword);
            if(docs != null) {
                float prominence = docs.size(); // the prominence proportional to the size of corresponding documents size of similar words
                for (Doc doc : docs) {
                    float weight = (float) 1 / doc.getCountword();
                    float score = prominence + prominence * weight;
                    score = score/10; //suppose has 10 percents probability input in different tense
                    int ID = doc.getPaperID();
                    if (!IDScore2.containsKey(ID)) {
                        IDScore2.put(ID, score);
                    } else {
                        score = IDScore2.get(ID) + score;
                        IDScore2.replace(ID, score);
                    }
                }
            }
        }
        IDScore2.putAll(IDScore);
    return sortValues(IDScore2);
    }

    //order PaperID
    private static ArrayList<Integer> sortValues(HashMap<Integer, Float> map)  {

        List list = new LinkedList(map.entrySet());
        //Custom Comparator
        Collections.sort(list, new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(Iterator iterator = list.iterator(); iterator.hasNext();){
            Map.Entry entry = (Map.Entry) iterator.next();
            arrayList.add((Integer) entry.getKey());
        }
        return arrayList;
    }
}
