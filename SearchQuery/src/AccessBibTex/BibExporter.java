/**
 * author Zhengyu Lin
 * TODO extract all paper's information from bib file
 */
package AccessBibTex;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public final class BibExporter {

    public static ArrayList<Paper> Export (String filename, int initpaperID) throws FileNotFoundException {
        ArrayList<Paper> allPapers = new ArrayList<>();
        Paper paper = new Paper();

        //information of one paper
        int paperID = initpaperID;
        String line = "";
        int year;
        String bibtexEntry = "";

        FileReader fr = new FileReader(filename);
        Scanner br = new Scanner(fr);
        String conference = filename.substring(0, filename.indexOf("."));
        while(br.hasNextLine()) {
            String str = br.nextLine();
            line = (line + str).trim();

            if( line.startsWith("@") ) {
                paper.setBibentry(bibtexEntry);
                allPapers.add(paper);
                paper = new Paper();
                paperID++;
                paper.setPaperID(paperID);
                line = str.substring(line.indexOf("{") +1);
                paper.setTopLine(line);
                paper.setConference(conference);
                line = "";
                bibtexEntry = "";
            }else if(line.startsWith("volume")){
                if(line.contains("{")){
                    line = getContent(line);
                }
                else {
                    line = line.substring(line.indexOf("=")+4, line.length()-1);
                }
                paper.setVolume(line);
                line ="";
            }
            else if(!line.contains("}") && !str.equals("")){ continue;}
            else if( line.startsWith("title")){
                line = getContent(line);
                paper.setTitle(line);
                line = "";
            }else if( line.startsWith("booktitle")){
                line = getContent(line);
                paper.setBooktitle(line);
                line = "";
            }else if( line.startsWith("month")){
                line = getContent(line);
                paper.setMonth(line);
                line = "";
            }else if(line.startsWith("year")){
                year = Integer.parseInt(getContent(line));
                paper.setYear(year);
                line = "";
            }else if(line.startsWith("series")){
                line = getContent(line);
                paper.setSeries(line);
                line = "";
            }else if(line.startsWith("url")){
                line = getContent(line);
                paper.setURL(line);
                line = "";
            }else if(line.startsWith("pages")){
                line = getContent(line);
                paper.setPages(line);
                line = "";
            }else if (line.startsWith("abstract")){
                line = getContent(line);
                paper.setAbstract(line);
                line = "";
            }else if (line.startsWith("publisher")){
                line = getContent(line);
                paper.setPublisher(line);
                line = "";
            }else if(line.startsWith("pdf")){
                //some bibtex format include url of pdf and url of page, we can record pdf as url
                line = getContent(line);
                paper.setURL(line);
                line = "";
            }else if(line.startsWith("editor")){
                line = getContent(line);
                paper.setEditor(line);
                line = "";
            }else if(line.startsWith("author")){
                line = getContent(line);
                paper.setAuthors(line);
                line = "";
            }else if (str.trim().equals("}")){
                // initial elements in the new paper
                line = "";
            }
            else {
                //Ignore note and br line
                line = "";
            }
            bibtexEntry = bibtexEntry + str + "\n";
        }
        allPapers.remove(0);
        return allPapers;
    }

    private static String getContent(String line){ //content of elements
        if (line.contains("{") && (line.indexOf("{") < line.lastIndexOf("}"))){
            return line.substring(line.indexOf("{")+1, line.lastIndexOf("}"));
        }
        else{
            System.out.println(line);
            return "null";
        }
    }
}
