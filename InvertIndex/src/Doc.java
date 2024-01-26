/**
 * Author Lin
 * Keyword corresponding documents
 */
public class Doc {
    private int paperID;
    private int countword;
    private int position;

    public Doc(){}
    public Doc(int paperID, int wordcount, int position){
        this.paperID = paperID;
        this.countword = wordcount;
        this.position = position;
    }

    public void setPaperID(int paperID) {
        this.paperID = paperID;
    }
    public void setPosition(int position) { this.position = position; }
    public void setCountword(int countword) {
        this.countword = countword;
    }
    public int getPaperID() {
        return paperID;
    }
    public int getPosition() { return position; }
    public int getCountword() {
        return countword;
    }
}
