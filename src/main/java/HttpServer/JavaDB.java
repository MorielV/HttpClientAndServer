package HttpServer;

import java.sql.*;

public class JavaDB {
    public static Connection dbcon;

    JavaDB() {
        dbcon = getConnection();
        createTable("IDs");
        createTable("Accounts");

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

    public void createTable(String tableName) {
        PreparedStatement create;
        try {
            if (tableName.equals("IDs"))
                create = dbcon.prepareStatement("CREATE TABLE IF NOT EXISTS IDs( id INTEGER PRIMARY KEY)");
            else
                create = dbcon.prepareStatement("CREATE TABLE IF NOT EXISTS Accounts(stockName varchar(255) ,value DOUBLE ,id INTEGER , FOREIGN KEY(id) REFERENCES IDs(id))");
            create.executeUpdate();
            System.out.println("Table Created   ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(PreparedStatement inserted) throws SQLException {
        //PreparedStatement inserted = dbcon.prepareStatement("INSERT INTO  Accounts(username, password) VALUES ('" + username + "','" + password + "')");
        inserted.executeUpdate();
        System.out.println("insert completed");
    }

    public int addUser() {
        try {
            System.out.println("adding user!");
            PreparedStatement inserted = dbcon.prepareStatement("INSERT INTO  IDs(id) VALUES (null)");
            System.out.println("prepared!!");
            insert(inserted);
            System.out.println("now adding");
            PreparedStatement selected = dbcon.prepareStatement("SELECT last_insert_rowid()");
            ResultSet result = selected.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addState(String stockName, Double value, int id) {
        PreparedStatement inserted = null;
        try {
            inserted = dbcon.prepareStatement("INSERT INTO  Accounts(stockName, value,id) VALUES ('" + stockName + "','" + value + "','"+id+"')");
            insert(inserted);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id ) {  //deleting a user
        try {
            PreparedStatement deleted = dbcon.prepareStatement("DELETE FROM IDs WHERE id ='" + id + "'");
            deleted.executeUpdate();
            deleted= dbcon.prepareStatement("DELETE FROM Accounts WHERE  id ='" + id + "'");
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
            System.out.println("in db IsUsernameExists::" + username);
            PreparedStatement selected = dbcon.prepareStatement("SELECT username FROM Accounts WHERE  username = '" + username + "'");
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
        db.createTable("IDs");
    }


}


