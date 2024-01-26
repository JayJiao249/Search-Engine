import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author: Lin
 * TODO initial the IndexLibrary
 */

public class initialLibrary {

    private IndexLibrary Lib = new IndexLibrary();
    private int amount; // count amount keywords of document
    private HashMap<String, ArrayList<Doc>> data = new HashMap<>();

    //Access the Database and get the information to initial Index Library
    public initialLibrary(String DB_URL, String USER, String PASSWORD){
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql_title = "SELECT PAPER_ID, TITLE FROM Papers";
        String sql_author = "SELECT PAPER_ID, FULL_NAME FROM Authors";
        String sql_conference = "SELECT PAPER_ID, CONFERENCE FROM Papers";
        String sql_abstract = "SELECT PAPER_ID, ABSTRACT FROM Papers";
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting DataBase for initiate IndexLibrary...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected.");

            ps = conn.prepareStatement(sql_title);
            rs = ps.executeQuery();
            //insert title of author to IndexLibrary
            insertKeywords(rs);

            ps = conn.prepareStatement(sql_author);
            rs = ps.executeQuery();
            //insert author name to IndexLibrary
            insertKeywords(rs);

            ps = conn.prepareStatement(sql_conference);
            rs = ps.executeQuery();
            //insert conference to IndexLibrary
            insertKeywords(rs);

            ps = conn.prepareStatement(sql_abstract);
            rs = ps.executeQuery();
            //insert abstract to IndexLibrary
            insertKeywords(rs);

            Lib.setData(data);
            Lib.setDatasize(amount);
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

    public IndexLibrary getLibrary() {
        return Lib;
    }

    private void insertKeywords(ResultSet rs) throws SQLException {
        while(rs.next()) {
            String str = rs.getString(2);
            if (str != null) {
                ArrayList<String> keywords = Tokenizer.Token(str);
                int position = 0;
                for (String keyword : keywords) {
                    amount++;
                    position++;
                    //Documents: PaperID: int; counterWord: int, indicate number of words in Paper; Keyword position: int;
                    Doc doc = new Doc(rs.getInt(1), keywords.size(), position);
                    ArrayList<Doc> docs;
                    if (!data.containsKey(keyword)) {
                        docs = new ArrayList<>();
                    } else {
                        docs = data.get(keyword);
                    }
                    docs.add(doc);
                    data.put(keyword, docs);
                }
            }
        }
    }
}

