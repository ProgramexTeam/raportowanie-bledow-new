package dao;

import objects.Comment;
import util.DataConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    public static List<Comment> getComments(String stuff, int ID) throws SQLException {
        List<Comment> temp = new ArrayList<>();
        String comment = "SELECT * FROM comments_"+stuff+" WHERE project_ID = ? ORDER BY ID DESC";
        Connection conn = DataConnect.getConnection();
        PreparedStatement ps = conn.prepareStatement(comment);
        ps.setInt(1, ID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Comment c = new Comment(rs.getInt("ID"),
                                    rs.getInt("user_ID"),
                                    rs.getInt("project_ID"),
                                    rs.getString("text"),
                                    rs.getString("date"));
            temp.add(c);
        }
        return temp;
    }

    public static boolean addComment(int projectID, int userID, String text, String stuff) {
        if (text != null) {
            PreparedStatement ps = null;
            Connection con = null;
            try {
                con = DataConnect.getConnection();
                if (con != null) {
                    String sql = "INSERT INTO comments_"+stuff+"(projectID, userID, text) VALUES(?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, projectID);
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
                    DataConnect.close(con);
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
