package HttpServer;

import java.sql.*;
import java.util.ArrayList;

public class JavaDB {
    public static Connection dbcon;

    JavaDB() {
        dbcon = getConnection();
        createTable();
    }


    private static Connection getConnection() {
        try {
            String driver = "org.sqlite.JDBC";
            String url = "jdbc:sqlite:HttpDB.db";
            Class.forName(driver);

            Connection con = DriverManager.getConnection(url);
            System.out.println("connected to DataBase");
            return con;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createTable() {
        try {
            PreparedStatement create = dbcon.prepareStatement("CREATE TABLE IF NOT EXISTS Accounts(username varchar(255) ,password varchar(255), PRIMARY KEY (username))");
            create.executeUpdate();
            System.out.println("Table Created   ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insert( ArrayList<StockState> stateList ) {
        try {
            PreparedStatement inserted = dbcon.prepareStatement("INSERT INTO  Accounts(username, password) VALUES ('" + username + "','" + password + "')");
            inserted.executeUpdate();
            System.out.println("insert completed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void delete(String username){
        try {
            PreparedStatement deleted = dbcon.prepareStatement("DELETE FROM Accounts WHERE username ='" + username + "'");
            deleted.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * checks whether or not a username exists in system
     *
     * @return
     */
    public boolean IsUsernameExists(String username) {
        try {
            System.out.println("in db IsUsernameExists::"+username);
            PreparedStatement selected = dbcon.prepareStatement("SELECT username FROM Accounts WHERE  username = '"+username+"'");
            ResultSet result = selected.executeQuery();
            if (result.next()) {
                System.out.println(result.getString("username"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//TODO::delete this
    public static void main(String[] args) {
        JavaDB db = new JavaDB();
        db.createTable();
        db.insert("moriel", "pass");
    }
}


