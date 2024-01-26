/**
 * author Zhengyu Lin
 * TODO Test BibExporter
 */
package AccessBibTex;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ExporterTest {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Paper> allpapers= BibExporter.Export("ACL.bib",1);

        for(Paper p : allpapers){
            System.out.println(p.getPaperID());
            //System.out.println(p.getTopLine());
            System.out.println(p.getBibentry());

            //System.out.println(p.getYear());
        }


    }
}

