package AccessDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CatalogManager {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private String DB_URL;// = "jdbc:mysql://localhost:3306/";
    private String USER;// = "root";
    private String PASSWORD;// = "123456lin";
    private String CatalogName;

    public CatalogManager(){}
    public CatalogManager(String DB_URL, String USER, String PASSWORD){
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public void setDB_URL(String DB_URL) {
        this.DB_URL = DB_URL;
    }

    public void Create(String CatalogName){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to DataBase...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String create_sql = "CREATE SCHEMA "+ CatalogName;
            System.out.println("Creating Catalog...");
            stmt = conn.createStatement();
            stmt.executeUpdate(create_sql);
            System.out.println("Catalog " + CatalogName + " Created");
            this.CatalogName = CatalogName;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) { conn.close(); }
                if (stmt != null) { stmt.close(); }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public String getCatalogName() {
        return CatalogName;
    }
}
