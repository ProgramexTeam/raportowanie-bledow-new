package dao;

import objects.Comment;
import util.DataConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection", "ConstantConditions"})
public class CommentDAO {
    public static List<Comment> getComments(String stuff, int ID) {
        Connection conn = null;
        PreparedStatement ps = null;
        List<Comment> temp = new ArrayList<>();

        try {
            String comment = "SELECT * FROM comments_"+stuff+" WHERE "+stuff+"_ID = ? ORDER BY ID DESC";
            conn = DataConnect.getConnection();
            ps = conn.prepareStatement(comment);
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Comment c = new Comment(rs.getInt("ID"),
                        rs.getInt("user_ID"),
                        rs.getInt(stuff + "_ID"),
                        rs.getString("text"),
                        rs.getString("date"));
                temp.add(c);
            }
        } catch (Exception ex){
            System.out.println("Error while getting comments from db; CommentDAO.getComments() --> " +ex.getMessage());
        } finally {
            if (conn != null){
                DataConnect.close(conn);
            }
            if (ps != null){
                try {
                    ps.close();
                } catch (Exception ex){
                    System.out.println("Error while closing PreparedStatement; CommentDAO.getComments() --> " +ex.getMessage());
                }
            }
        }
        return temp;
    }

    public static boolean addComment(int ID, int userID, String text, String stuff) {
        if (text != null) {
            PreparedStatement ps = null;
            Connection con = null;
            try {
                con = DataConnect.getConnection();
                if (con != null) {
                    String sql = "INSERT INTO comments_"+stuff+"("+stuff+"_ID, user_ID, text) VALUES(?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, ID);
                    ps.setInt(2, userID);
                    ps.setString(3, text);
                    ps.executeUpdate();
                }
            } catch (Exception ex) {
                System.out.println("Add project error when executing query; CommentDAO.addComment() -->" + ex.getMessage());
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (con != null) {
                        DataConnect.close(con);
                    }
                } catch (Exception ex) {
                    System.out.println("Add project error when closing database connection or prepared statement; CommentDAO.addComment() -->" + ex.getMessage());
                }
            }
            return true;
        } else {
            System.out.println("All data must be delivered to this method; CommentDAO.addComment() -->");
            return false;
        }
    }
}
