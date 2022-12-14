package database;

import java.sql.*;
import java.io.IOException;
import java.util.UUID;


public class MYSQLConnector {
    PreparedStatement pst;
    ResultSet rs;
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost/TomRater", "root", "");
                System.out.println("Connection Successful");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return con;
    }
    public static void main(String[] args) {
        getConnection();
    }

    //function to create user
    public void create_User(String name, String email, String username, String password) throws IOException {

        String query2 = "INSERT into user (id,name, email, uname, password) values (?,?,?,?,?)";

        try {
            pst = getConnection().prepareStatement(query2);
            pst.setString(1, String.valueOf(UUID.randomUUID()));
            pst.setString(2, name);
            pst.setString(3, email);
            pst.setString(4, username);
            pst.setString(5, password);

            if (isExist(email)) {
                //if user already exists
                System.out.println("User already exist");
            } else {
                if (pst.executeUpdate() != 0) {
                    System.out.println("Registration Successful, please sign in");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    // function to check if user exist
    public boolean isExist(String email) throws IOException {
        String query = "SELECT * FROM `user` WHERE email = ?";
        boolean user_exist = false;
        try {
            pst = getConnection().prepareStatement(query);
            pst.setString(1, email);

            rs = pst.executeQuery();
            if (rs.next()) {
                user_exist = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user_exist;
    }
    //function to add BOOK
    public boolean add_Book(String ISBN, String Title, String Author, String Password, String Subject) throws IOException {

        String query3 = "INSERT into Book (ISBN, Title, Author, Subject) values (?,?,?,?)";

        try {
            pst = getConnection().prepareStatement(query3);

            pst.setString(1, ISBN);
            pst.setString(2, Title);
            pst.setString(3, Author);
            pst.setString(4, Subject);

                if (pst.executeUpdate() != 0) {
                    System.out.println("Book Added Successfully");
                }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //function to create BOOK list
    public boolean create_bookList(String Username, String ListID, String ListName, String Books) throws IOException {

        String query3 = "INSERT into Book List (Username, ListID, ListName, Books) values (?,?,?,?)";

        try {
            pst = getConnection().prepareStatement(query3);

            pst.setString(1, Username);
            pst.setString(2, ListID);
            pst.setString(3, ListName);
            pst.setString(4, Books);

            if (pst.executeUpdate() != 0) {
                System.out.println("List Created");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
