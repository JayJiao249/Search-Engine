/**
 * author Zhengyu Lin
 * initiate TrieTree
 */
package TrieStructure;
import java.sql.*;


public class initTrie {
    private TrieTree trieTree;

    public initTrie(String DB_URL, String USER, String PASSWORD){
        trieTree = new TrieTree();
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql_title = "SELECT TITLE FROM Papers";
        String sql_author = "SELECT FULL_NAME FROM Authors";
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting DataBase for initiate TrieTree...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected.");
            ps = conn.prepareStatement(sql_title);
            rs = ps.executeQuery();
            String str = null;
            //insert title into the TrieTree
            while(rs.next()){
                str = rs.getString(1).toLowerCase();
                trieTree.insert(str);
            }
            ps = conn.prepareStatement(sql_author);
            rs = ps.executeQuery();
            //insert author name into the TrieTree
            while(rs.next()){
                str = rs.getString(1).toLowerCase();
                trieTree.insert(str);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if( conn != null){
                    conn.close();
                }
                if (ps != null){
                    ps.close();
                }
                if (rs != null){
                    rs.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public TrieTree getTrieTree(){
        return trieTree;
    }
}
