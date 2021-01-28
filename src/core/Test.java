package core;

import core.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

    static String username = "jam";
    static String password = "14561";

    public static void main(String[] args) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE usr_name = ? AND usr_pass = ?";
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            System.out.println("Users loaded.");

            if (rs.next()) {
                //Setting user credentials for further processing
                System.out.println(rs.getString("usr_name") + "     " + rs.getString("usr_pass"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE usr_name = ? AND usr_pass = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "ahmed");
            ps.setString(2, "451");

            ResultSet rs = ps.executeQuery();
            System.out.println("Users loaded.");

            if (rs.next()) {
                //Setting user credentials for further processing
                System.out.println(rs.getString("usr_name") + "     " + rs.getString("usr_pass"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
    }

}
