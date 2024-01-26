/**
 * Author Lin
 * TODO test DBTable
 */
package AccessDB;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AccessDBTest {
    public static void main(String[] args) throws FileNotFoundException, SQLException {

        String DB_URL = "jdbc:mysql://localhost:3306/";
        String USER = "root";
        String PASSWORD = "123456lin";

        CatalogManager catalogManager = new CatalogManager(DB_URL, USER, PASSWORD);
        catalogManager.Create("test");
        String catalogname = catalogManager.getCatalogName();
        String DBCatalog_URL = DB_URL + catalogname;

        DBTable dbTable = new DBTable(DBCatalog_URL, "test", USER, PASSWORD); //initialize DBTable object
        dbTable.deleteTable("Authors");
        dbTable.deleteTable("Papers");
        dbTable.createTable(); //create table
        dbTable.InsertData("ICML.bib");

    }
}
