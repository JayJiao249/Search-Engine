/**
 * author Lin
 * Author class, record author's first name, surname and fullname,
 */
package AccessBibTex;

public class Author {
    private String Firstname;
    private String Surname;
    private String Fullname;

    public Author(){};
    public Author(String fullname){
        Fullname = fullname;
        if (fullname.contains(" ")){
            Firstname = Fullname.substring(0, fullname.lastIndexOf(" "));
            Surname = Fullname.substring(Fullname.lastIndexOf(" ")+1);
        }else {
            Firstname = "";
            Surname = "";
        }
    }

    public Author(String firstname, String surname){
        Firstname = firstname;
        Surname = surname;
        Fullname = Firstname + " " + Surname;
    }

    public String getFirstname() {
        return Firstname;
    }
    public String getSurname(){
        return Surname;
    }
    public String getName(){
        return Fullname;
    }

    @Override
    public String toString() {
        return "Author{" +
                "Firstname='" + Firstname + '\'' +
                ", Surname='" + Surname + '\'' +
                ", Fullname='" + Fullname + '\'' +
                '}';
    }
}

