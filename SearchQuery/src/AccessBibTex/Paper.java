package AccessBibTex;
/**
 * author lin
 * TODO Paper class, all info about the paper
 */

import java.util.ArrayList;

public class Paper {

    private ArrayList<Author> authors;
    private String editor;
    private int paperID;
    private String topLine;
    private String title;
    private String booktitle;
    private String month;
    private int year;
    private String series;
    private String publisher;
    private String url;
    private String volume;
    private String pages;
    private String abst;
    private String conference;
    private String bibentry;

    public Paper(){}
    public Paper(int paperID, String topLine, String title, String booktitle, String month,
                 int year, String series, String publisher, String url, String volume,
                 String pages, String abst, String editor, ArrayList<Author> authors, String conference){
        this.paperID = paperID;
        this.topLine = topLine;
        this.title = title;
        this.booktitle = booktitle;
        this.month = month;
        this.year = year;
        this.series = series;
        this.publisher = publisher;
        this.url = url;
        this.volume = volume;
        this.pages = pages;
        this.abst = abst;
        this.editor = editor;
        this.authors = authors;
        this.conference = conference;
    }

    public void setAuthors(String authors) {
        ArrayList<Author> paperauthors = new ArrayList<>();
        String[] authorarray = authors.split(" and ");
        String authorname = null;
        for(String name :authorarray){
            name = name.trim();
            if (!name.contains(",")){
                authorname = name;
            }else {
                try {
                    authorname = name.substring(name.indexOf(",") + 2) + " " + name.substring(0, name.indexOf(","));
                } catch (Exception e) {
                    authorname = name.substring(0, name.indexOf(",")); //The bibfile has some errors format with name
                }
            }
            Author author = new Author(authorname);
            paperauthors.add(author);
        }
        this.authors = paperauthors;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
    public String getEditor() {
        return editor;
    }

    public void setPaperID(int paper_ID) {
        this.paperID = paper_ID;
    }

    public int getPaperID() {
        return paperID;
    }

    public void setTopLine(String topLine) {
        this.topLine = topLine;
    }

    public String getTopLine() {
        return topLine;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setSeries(String address) {
        this.series = address;
    }

    public String getSeries() {
        return series;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getVolume() {
        return volume;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPages() {
        return pages;
    }

    public void setAbstract(String abst) {
        this.abst = abst;
    }

    public String getAbstract() {
        return abst;
    }

    public void setConference(String conference) { this.conference = conference; }

    public String getConference() {return conference;}

    public void setBibentry(String bibentry) {
        this.bibentry = bibentry;
    }

    public String getBibentry() {
        return bibentry;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "editor='" + editor + '\'' +
                ", paperID=" + paperID +
                ", topLine='" + topLine + '\'' +
                ", title='" + title + '\'' +
                ", booktitle='" + booktitle + '\'' +
                ", month='" + month + '\'' +
                ", year=" + year +
                ", series='" + series + '\'' +
                ", publisher='" + publisher + '\'' +
                ", url='" + url + '\'' +
                ", volume='" + volume + '\'' +
                ", pages='" + pages + '\'' +
                ", abst='" + abst + '\'' +
                ", conference ='" + conference + '\'' +
                '}';
    }
}
