package dao;

import objects.File;
import util.DataConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileDAO {
    public static File getGallery(long id) {
        PreparedStatement ps = null;
        Connection con = null;
        File file = null;
        try {
            con = DataConnect.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM photo_gallery WHERE gallery_id=?";
                ps = con.prepareStatement(sql);
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    file = new File(rs.getInt("gallery_id"),
                            rs.getString("photo_1"),
                            rs.getString("photo_2"),
                            rs.getString("photo_3"),
                            rs.getString("photo_4"));
               }
            }
        } catch (Exception ex) {
            System.out.println("Product request error when executing query; ProductDAO.requestProductName() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; ProductDAO.addProduct() -->" + ex.getMessage()); }
        }
        return file;
    }


    public static long amountOfProducts() {
        Connection con = null;
        long amount = 0;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT COUNT(*) AS `amount` FROM products");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                amount = rs.getLong("amount");
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting product data from db; ProductDAO.amountOfProducts() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; ProductDAO.addProduct() -->" + ex.getMessage()); }
        }
        return amount;
    }


}