
public class Librarytest {
    public static void main(String[] args) {
        String DB_URL = "jdbc:mysql://localhost:3306/BibDB";
        String USER = "root";
        String PASSWORD = "123456lin";

        IndexLibrary Library = new initialLibrary(DB_URL, USER, PASSWORD).getLibrary();
        String query = "Finito: A faster, permutable incremental gradient method for big data problems";
        query = "david";

        System.out.println(Library.getResult(query));

    }
}
