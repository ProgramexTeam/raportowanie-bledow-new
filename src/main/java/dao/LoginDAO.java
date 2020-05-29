package dao;

import util.BCrypt;
import util.DataConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection", "ConstantConditions"})
public class LoginDAO {
    public static int validate(String user, String password) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select user_login, user_pass from users where user_login = ?");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if(BCrypt.checkpw(password, rs.getString("user_pass"))) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        } catch (Exception ex) {
            System.out.println("Login error; LoginDAO.validate() -->" + ex.getMessage());
            return -1;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                DataConnect.close(con);
            }
        }
        return 0;
    }

    public static ArrayList<String> checkUserData(String user, String password) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<String> userData = new ArrayList<>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM users WHERE user_login = '" + user + "'");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if(BCrypt.checkpw(password, rs.getString("user_pass"))) {
                    userData.add(rs.getString("user_login"));
                    userData.add(rs.getString("user_email"));
                    userData.add(rs.getString("user_activation_key"));
                    userData.add(rs.getString("user_role"));
                    userData.add(rs.getString("ID"));
                    return userData;
                }
            }
        } catch (Exception ex) {
            System.out.println("Login error while checking auth key; LoginDAO.checkAuth() -->" + ex.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                DataConnect.close(con);
            }
        }
        return null;
    }
}