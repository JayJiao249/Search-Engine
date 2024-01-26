/**
 * author Zhengyu Lin
 * TODO operate Database table
 */
package AccessDB;

import AccessBibTex.Author;
import AccessBibTex.BibExporter;
import AccessBibTex.Paper;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;

public class DBTable {

    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private String DB_URL;// = "jdbc:mysql://localhost:3306/BibDB";
    private String USER;// = "root";
    private String PASSWORD;// = "123456lin";
    private String CATALOG;// = "BibDB"

    private static final String CREATE_PaperTable = "CREATE TABLE Papers("
                                                    +"PAPER_ID INT NOT NULL,"
                                                    +"TOPLINE VARCHAR(512) NOT NULL,"
                                                    +"TITLE VARCHAR(512) NOT NULL,"
                                                    +"BOOKTITLE VARCHAR(512),"
                                                    +"YEAR INT NOT NULL,"
                                                    +"SERIES VARCHAR(512),"
                                                    +"PUBLISHER VARCHAR(512),"
                                                    +"URL VARCHAR(512),"
                                                    +"MONTH VARCHAR(50),"
                                                    +"EDITOR VARCHAR(512),"
                                                    +"VOLUME VARCHAR(30),"
                                                    +"PAGES VARCHAR(100),"
                                                    +"ABSTRACT TEXT,"
                                                    +"CONFERENCE VARCHAR(100),"
                                                    +"BiBTex TEXT,"
                                                    +"PRIMARY KEY (PAPER_ID))";

    public static final String CREATE_AuthorsTable = "CREATE TABLE Authors("
                                                    +"ITEM INT UNSIGNED NOT NULL AUTO_INCREMENT,"
                                                    +"PAPER_ID INT NOT NULL,"
                                                    +"FULL_NAME VARCHAR(256),"
                                                    +"FIRST_NAME VARCHAR(256),"
                                                    +"SURNAME VARCHAR(256),"
                                                    +"PRIMARY KEY (ITEM))";
    public static final String lastPaperID_SQL = "SELECT PAPER_ID FROM Papers order by PAPER_ID desc limit 1";

    public DBTable(){}
    public DBTable(String DB_URL, String CATALOG, String USER, String PASSWORD){
        this.DB_URL = DB_URL;
        this.CATALOG = CATALOG;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
     }
    //Create papers table and Authors table
    //PaperTable: Paper's information but not include authors
    //Authors Table: PaperID, authors
    public void createTable() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            ps = conn.prepareStatement(CREATE_PaperTable);
            rs = conn.getMetaData().getTables(CATALOG, null, "Papers", null);
            if (rs.next()) {
                System.out.println("Table: Papers, already exist");
            } else {
                System.out.println("Creating Table: Papers...");
                ps.executeUpdate();
                System.out.println("Created Table:Papers");
            }
            ps = conn.prepareStatement(CREATE_AuthorsTable);
            rs = conn.getMetaData().getTables(CATALOG, null, "Authors", null);
            if (rs.next()){
                System.out.println("Table: Authors, already exist");
            } else{
                System.out.println("Creating Table: Authors");
                ps.executeUpdate();
                System.out.println("Created Table:Authors ");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally{
            close(conn, ps, rs);
        }
    }

    //add a paper to database
    public void addPaper(Paper p){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            int paperID = 1;
            ps = conn.prepareStatement(lastPaperID_SQL);
            rs = ps.executeQuery();
            if(rs.next()) {
                paperID = rs.getInt(1) + 1;
            }
            p.setPaperID(paperID);

            String Insert_Authors_SQL = "INSERT INTO PaperID_Authors(PAPER_ID, FULL_NAME," +
                    "FIRST_NAME, SURNAME) values(?,?,?,?)";
            ps = conn.prepareStatement(Insert_Authors_SQL);
            if(p.getAuthors() != null) {
                for (Author a : p.getAuthors()) {
                    ps.setInt(1, p.getPaperID());
                    ps.setString(2, a.getName());
                    ps.setString(3, a.getFirstname());
                    ps.setString(4, a.getSurname());
                    ps.execute();
                }
            }

            String Insert_Papers_SQL = "INSERT INTO Papers values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(Insert_Papers_SQL);
            ps.setInt(1, p.getPaperID());
            ps.setString(2, p.getTopLine());
            ps.setString(3, p.getTitle());
            ps.setString(4, p.getBooktitle());
            ps.setInt(5, p.getYear());
            ps.setString(6, p.getSeries());
            ps.setString(7, p.getPublisher());
            ps.setString(8, p.getURL());
            ps.setString(9, p.getMonth());
            ps.setString(10, p.getEditor());
            ps.setString(11, p.getVolume());
            ps.setString(12, p.getPages());
            ps.setString(13, p.getAbstract());
            ps.setString(14, p.getConference());
            ps.setString(15, p.getBibentry());
            ps.execute();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
    }

    //insert batch data
    public void InsertData(String filename) throws FileNotFoundException {
        String url = DB_URL + "?rewriteBatchedStatements=true";
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement psa = null;
        ResultSet rs = null;
        int initpaperID = 0;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(url, USER, PASSWORD);
            ps = conn.prepareStatement(lastPaperID_SQL);
            rs = ps.executeQuery();
            if(rs.next()) {
                initpaperID = rs.getInt(1); // The last paper ID
            }
            ArrayList<Paper> papers = BibExporter.Export(filename, initpaperID);
            String Insert_Papers_SQL = "INSERT INTO Papers values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String Insert_Authors_SQL = "INSERT INTO Authors(PAPER_ID, FULL_NAME," +
                    "FIRST_NAME, SURNAME) values(?,?,?,?)";
            ps = conn.prepareStatement(Insert_Papers_SQL);
            psa = conn.prepareStatement(Insert_Authors_SQL);
            conn.setAutoCommit(false);
            System.out.println("Inserting data to table: Paper & Authors");
            for (Paper p : papers) {
                if(p.getAuthors() != null) {
                    for (Author a : p.getAuthors()) {
                        psa.setInt(1, p.getPaperID());
                        psa.setString(2, a.getName());
                        psa.setString(3, a.getFirstname());
                        psa.setString(4, a.getSurname());
                        psa.addBatch();
                    }
                }
                ps.setInt(1, p.getPaperID());
                if(p.getTopLine() == null){
                    System.out.println(p.getTitle());
                }
                ps.setString(2, p.getTopLine());
                ps.setString(3, p.getTitle());
                ps.setString(4, p.getBooktitle());
                ps.setInt(5, p.getYear());
                ps.setString(6, p.getSeries());
                ps.setString(7, p.getPublisher());
                ps.setString(8, p.getURL());
                ps.setString(9, p.getMonth());
                ps.setString(10, p.getEditor());
                ps.setString(11, p.getVolume());
                ps.setString(12, p.getPages());
                ps.setString(13, p.getAbstract());
                ps.setString(14, p.getConference());
                ps.setString(15, p.getBibentry());
                ps.addBatch();
            }
            psa.executeBatch();
            ps.executeBatch();
            conn.commit();
            System.out.println("Inserted");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if(psa != null){
                    psa.close();
                }
                close(conn,ps,rs);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void deleteTable(String name){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            rs = conn.getMetaData().getTables(CATALOG, null, name, null);
            if (rs.next()) {
                String sql = "DROP TABLE " + name;
                System.out.println("Deleting Table:" + name + "...");
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                System.out.println("Table: " + name + " Deleted.");
            }
            else{
                System.out.println("The table doesn't exist.");
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
    }

    private void close(Connection conn, PreparedStatement ps, ResultSet rs){
        try {
            if (conn != null){
                conn.close();
            }
            if (ps != null){
                ps.close();
            }
            if (rs !=null){
                rs.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setDB_URL(String DB_URL) {
        this.DB_URL = DB_URL;
    }
    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
    public void setCATALOG(String CATALOG) {
        this.CATALOG = CATALOG;
    }
    public void setUSER(String USER) {
        this.USER = USER;
    }
}
