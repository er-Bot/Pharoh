package core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static  String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
    public static  String usr_name = "erbo";
    public static  String usr_pass = "erbo";

    public static Connection getConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(db_url, usr_name, usr_pass);
        } catch (SQLException e) {
            System.out.println("Database Connection Error!");
            e.printStackTrace();
            return null;
        }
        return connection;
    }

}
