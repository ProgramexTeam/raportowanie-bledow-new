package dao;

import objects.Ticket;
import util.DataConnect;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection", "ConstantConditions"})
public class TicketDAO {
    public static Ticket getProduct(int id) {
        PreparedStatement ps = null;
        Connection con = null;
        Ticket ticket = null;
        try {
            con = DataConnect.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM tickets LEFT JOIN categories ON products.category_ID = categories.category_ID WHERE products.product_ID=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ticket = new Ticket(
                            rs.getInt("ID"),
                            rs.getInt("author_ID"),
                            rs.getInt("project_ID"),
                            rs.getString("status"),
                            rs.getString("title"),
                            rs.getString("description"));
                }
            }
        } catch (Exception ex) {
            System.out.println("Product request error when executing query; TicketDAO.getProduct() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; TicketDAO.getProduct() -->" + ex.getMessage()); }
        }
        return ticket;
    }
    public static long amountOfTickets() {
        Connection con = null;
        long amount = 0;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT COUNT(*) AS `amount` FROM tickets");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                amount = rs.getLong("amount");
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting product data from db; TicketDAO.amountOfTickets() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; TicketDAO.amountOfTickets() -->" + ex.getMessage()); }
        }
        return amount;
    }
    public static long amountOfTicketsOfPattern(String pattern, int searchOption) {
        Connection con = null;
        long amount = 0;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT COUNT(*) AS `amount` FROM tickets WHERE title LIKE ?");
            if(searchOption==1){
                ps.setString(1, pattern + "%"); //zaczyna się na
            } else if(searchOption==3) {
                ps.setString(1, "%" + pattern); //kończy się na
            } else {
                ps.setString(1, "%" + pattern + "%"); //zawiera
            }
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                amount = rs.getLong("amount");
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; TicketDAO.amountOfTicketsOfPattern() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; TicketDAO.amountOfTicketsOfPattern() -->" + ex.getMessage());
                }
            }
        }
        return amount;
    }
    public static ArrayList<Ticket> getTicketList(long startPosition, long amount, int author_id) {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Ticket> tickets = new ArrayList<>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM tickets LEFT JOIN projects ON tickets.project_ID = projects.ID LEFT JOIN users_has_projects ON projects.ID = users_has_projects.project_ID WHERE tickets.author_ID = ? OR users_has_projects.user_ID = ? ORDER BY tickets.ID DESC LIMIT ?, ?");
            ps.setLong(1, author_id);
            ps.setLong(2, author_id);
            ps.setLong(3, startPosition);
            ps.setLong(4, amount);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket temp = new Ticket(rs.getInt("ID"),
                        rs.getInt("author_ID"),
                        rs.getInt("project_ID"),
                        rs.getString("status"),
                        rs.getString("title"),
                        rs.getString("description"));
                tickets.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; TicketDAO.getTicketList() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; TicketDAO.getProductsList() -->" + ex.getMessage()); }
        }
        return tickets;
    }
    public static ArrayList<Ticket> getProductsListByCategory(String id, String startPosition, String amount) {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Ticket> productsList = new ArrayList<>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM tikcets LEFT JOIN categories ON products.category_ID = categories.category_ID WHERE products.category_ID = ? ORDER BY products.product_ID LIMIT ?, ?");
            ps.setString(1, id);
            ps.setString(2, startPosition);
            ps.setString(3, amount);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket temp = new Ticket(rs.getInt("ID"),
                        rs.getInt("author_ID"),
                        rs.getInt("project_ID"),
                        rs.getString("status"),
                        rs.getString("title"),
                        rs.getString("description"));
                productsList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; TicketDAO.getTicketList() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; TicketDAO.getTicketList() -->" + ex.getMessage()); }
        }
        return productsList;
    }
    public static ArrayList<Ticket> getProductsListCustomStatement(String statement) {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Ticket> productsList = new ArrayList<>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket temp = new Ticket(rs.getInt("ID"),
                        rs.getInt("author_ID"),
                        rs.getInt("project_ID"),
                        rs.getString("status"),
                        rs.getString("title"),
                        rs.getString("description"));
                productsList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; TicketDAO.getProductsListCustomStatement() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try {
                ps.close();
            } catch (Exception ex) {
                System.out.println("Product delete error when closing database connection or prepared statement; TicketDAO.getProductsListCustomStatement() -->" + ex.getMessage());
            }
        }
        return productsList;
    }
    public static long getAmountOfProductsCustomStatement(String statement) {
        Connection con = null;
        PreparedStatement ps = null;
        long amountOfProducts = 0;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                amountOfProducts = rs.getLong("amountOfPages");
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; TicketDAO.getAmountOfProductsCustomStatement() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try {
                ps.close();
            } catch (Exception ex) {
                System.out.println("Product delete error when closing database connection or prepared statement; TicketDAO.getAmountOfProductsCustomStatement() -->" + ex.getMessage());
            }
        }
        return amountOfProducts;
    }

    public static JsonArray getTicketList(String statement) {
        Connection con = null;
        PreparedStatement ps = null;
        JsonArray productsList = null;

        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                builder.add(Json.createObjectBuilder()
                        .add("ID", rs.getInt("ID"))
                        .add("author_ID", rs.getInt("author_ID"))
                        .add("project_ID", rs.getInt("project_ID"))
                        .add("status", rs.getString("status"))
                        .add("title", rs.getString("title"))
                        .add("description", rs.getString("description")));
            }
            productsList = builder.build();
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; TicketDAO.getProductsList() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; TicketDAO.getProductsList() -->" + ex.getMessage()); }
        }
        return productsList;
    }
    public static ArrayList<Ticket> getTicketsListOfPattern(long startPosition, long amountPerPage, String searchByProductName, int searchOption) {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Ticket> ticketList = new ArrayList<>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM tickets WHERE title LIKE ? ORDER BY ID DESC LIMIT ?, ?");
            if(searchOption==1){
                ps.setString(1, searchByProductName + "%");
            } else if(searchOption==3) {
                ps.setString(1, "%" + searchByProductName);
            } else {
                ps.setString(1, "%" + searchByProductName + "%");
            }
            ps.setLong(2, startPosition);
            ps.setLong(3, amountPerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket temp = new Ticket(rs.getInt("ID"),
                        rs.getInt("author_ID"),
                        rs.getInt("project_ID"),
                        rs.getString("status"),
                        rs.getString("title"),
                        rs.getString("description"));
                ticketList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; TicketDAO.getTicketsListOfPattern() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            if (ps != null) { try { ps.close(); } catch (SQLException ex) { System.out.println("Error while closing PreparedStatement; TicketDAO.getTicketsListOfPattern() -->" + ex.getMessage()); } }
        }
        return ticketList;
    }
    public static boolean deleteSingleProduct(String deleteId) {
        Connection con = null;
        PreparedStatement ps = null;
        if(checkIfTicketExists(deleteId)) {
            try {
                con = DataConnect.getConnection();
                ps = con.prepareStatement("DELETE FROM tickets WHERE ID = ?");
                ps.setString(1, deleteId);
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error while deleting product from db; TicketDAO.deleteSingleProduct() -->" + ex.getMessage());
                return false;
            } finally {
                DataConnect.close(con);
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Error while closing PreparedStatement; TicketDAO.deleteSingleProduct() -->" + ex.getMessage());
                    }
                }
            }
            return true;
        } else {
            System.out.println("Product with given ID doesn't exist; TicketDAO.deleteSingleProduct()");
            return false;
        }
    }
    public static boolean checkIfTicketExists(String id) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM tickets WHERE ID = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking if product exists in db; TicketDAO.checkIfProductExists() -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; TicketDAO.checkIfProductExists() -->" + ex.getMessage());
                }
            }
        }
        return false;
    }
    public static Ticket getSingleTicketData(String id) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM tickets LEFT JOIN projects ON tickets.project_ID = projects.ID WHERE tickets.ID = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Ticket(rs.getInt("ID"),
                        rs.getInt("author_ID"),
                        rs.getInt("project_ID"),
                        rs.getString("status"),
                        rs.getString("title"),
                        rs.getString("description"));
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking if product exists in db; TicketDAO.getSingleTicketData() -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; TicketDAO.getSingleTicketData() -->" + ex.getMessage());
                }
            }
        }
        return null;
    }
      public static boolean editGivenTicket(int ID, int author_ID, int project_ID, String status, String title, String description) {
        if (author_ID != -1 || project_ID != -1) {
            PreparedStatement ps = null;
            Connection con = null;

            try {
                con = DataConnect.getConnection();
                if (con != null) {
                    ps = con.prepareStatement("UPDATE tickets SET author_ID = ?, project_ID = ?, status = ?, title = ?," +
                            " description = ? WHERE ID = ? ");
                    ps.setInt(1, author_ID);
                    ps.setInt(2, project_ID);
                    ps.setString(3, status);
                    ps.setString(4, title);
                    ps.setString(5, description);
                    ps.setInt(6, ID);
                    ps.executeUpdate();
                }
            } catch (Exception ex) {
                System.out.println("Error while updating user data; TicketDAO.editGivenTickets() -->" + ex.getMessage());
            } finally {
                DataConnect.close(con);
                if (ps != null) { try { ps.close(); } catch (SQLException ex) { System.out.println("Error while closing PreparedStatement; TicketDAO.editGivenTickets() -->" + ex.getMessage()); } }
            }
            return true;
        } else {
            return false;
        }
    }
    public static int addTicket(int author_ID, int project_ID, String status, String title, String description) {
        if (author_ID != -1 || project_ID != -1) {
            PreparedStatement ps = null;
            Connection con = null;
            int id = -1;
            try {
                con = DataConnect.getConnection();
                if (con != null) {
                    ps = con.prepareStatement("INSERT INTO tickets (author_ID, project_ID, status, title, " +
                            "description) VALUES(?,?,?,?,?)");
                    ps.setInt(1, author_ID);
                    ps.setInt(2, project_ID);
                    ps.setString(3, status);
                    ps.setString(4, title);
                    ps.setString(5, description);
                    ps.executeUpdate();
                    ps = con.prepareStatement("SELECT MAX(ID) AS 'lastID' FROM tickets");
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        id = rs.getInt("lastID");
                    }
                }
            } catch (Exception ex) {
                System.out.println("Adding ticket error when executing query; TicketDAO.addTicket() -->" + ex.getMessage());
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    DataConnect.close(con);
                } catch (Exception ex) {
                    System.out.println("Adding ticket error when closing database connection or prepared statement; TicketDAO.addTicket() -->" + ex.getMessage());
                }
            }
            return id;
        } else {
            System.out.println("All data must be delivered to this method; TicketDAO.addTicket() -->");
            return -1;
        }
    }
}