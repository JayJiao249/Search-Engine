/**
 * author Zhengyu Lin
 */
import AccessDB.DBTable;
import TrieStructure.initTrie;
import TrieStructure.TrieTree;
import java.io.FileNotFoundException;

public class test {
    public static void main(String[] args) throws FileNotFoundException {

        String DB_URL = "jdbc:mysql://localhost:3306/BibDB";
        String USER = "root";
        String PASSWORD = "123456lin";
        String CATALOG = "BibDB";

        DBTable dbTable = new DBTable(DB_URL, CATALOG, USER, PASSWORD); //initialize DBTable object
        /*
        dbTable.deleteTable("Authors");
        dbTable.deleteTable("Papers");

        dbTable.createTable(); //create table
        //dbTable.InsertData("/Users/lin/search-engine/SearchQuery/src/bib.txt"); //insertData to the table

        dbTable.InsertData("NeurIPS.bib");
        dbTable.InsertData("ICML.bib");
        dbTable.InsertData("ICCV.bib");
         */
        dbTable.InsertData("ACL.bib");
        TrieTree trieTree = new initTrie(DB_URL, USER, PASSWORD).getTrieTree(); //initialize TrieTree
        System.out.println(trieTree.getData("on communication")); //get the suggestions from the tree, e.g. paper title, author name



    }
}
