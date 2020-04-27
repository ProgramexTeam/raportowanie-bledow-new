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

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
public class TicketDAO {
    public static Ticket getProduct(long id) {
        PreparedStatement ps = null;
        Connection con = null;
        Ticket ticket = null;
        try {
            con = DataConnect.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM tickets LEFT JOIN categories ON products.category_id = categories.category_id WHERE products.product_id=?";
                ps = con.prepareStatement(sql);
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ticket = new Ticket(
                            rs.getInt("ticket_id"),
                            rs.getInt("author_id"),
                            rs.getInt("project_id"),
                            rs.getString("status"),
                            rs.getString("title"),
                            rs.getString("description"));
                }
            }
        } catch (Exception ex) {
            System.out.println("Product request error when executing query; ProductDAO.getProduct() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; ProductDAO.getProduct() -->" + ex.getMessage()); }
        }
        return ticket;
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
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; ProductDAO.amountOfProducts() -->" + ex.getMessage()); }
        }
        return amount;
    }
    public static long amountOfProductsOfPattern(String pattern, int searchOption) {
        Connection con = null;
        long amount = 0;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT COUNT(*) AS `amount` FROM products WHERE product_name LIKE ?");
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
            System.out.println("Error while getting products data from db; ProductDAO.amountOfProductsOfPattern() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; ProductDAO.amountOfProductsOfPattern() -->" + ex.getMessage());
                }
            }
        }
        return amount;
    }
    public static ArrayList<Ticket> getProductsList(long startPosition, long amount) {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Ticket> productsList = new ArrayList<>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM tickets LEFT JOIN projects ON products.category_id = categories.category_id ORDER BY products.product_id LIMIT ?, ?");
            ps.setLong(1, startPosition);
            ps.setLong(2, amount);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket temp = new Ticket(rs.getInt("ticket_id"),
                        rs.getInt("author_id"),
                        rs.getInt("project_id"),
                        rs.getString("status"),
                        rs.getString("title"),
                        rs.getString("description"));
                productsList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; ProductDAO.getProductsList() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; ProductDAO.getProductsList() -->" + ex.getMessage()); }
        }
        return productsList;
    }
    public static ArrayList<Ticket> getProductsListByCategory(long ticket_id, long startPosition, long amount) {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Ticket> productsList = new ArrayList<>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM tikcets LEFT JOIN categories ON products.category_id = categories.category_id WHERE products.category_id = ? ORDER BY products.product_id LIMIT ?, ?");
            ps.setLong(1, ticket_id);
            ps.setLong(2, startPosition);
            ps.setLong(3, amount);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket temp = new Ticket(rs.getInt("ticket_id"),
                        rs.getInt("author_id"),
                        rs.getInt("project_id"),
                        rs.getString("status"),
                        rs.getString("title"),
                        rs.getString("description"));
                productsList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; ProductDAO.getProductsList() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; ProductDAO.getProductsList() -->" + ex.getMessage()); }
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
                Ticket temp = new Ticket(rs.getInt("ticket_id"),
                        rs.getInt("author_id"),
                        rs.getInt("project_id"),
                        rs.getString("status"),
                        rs.getString("title"),
                        rs.getString("description"));
                productsList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; ProductDAO.getProductsListCustomStatement() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try {
                ps.close();
            } catch (Exception ex) {
                System.out.println("Product delete error when closing database connection or prepared statement; ProductDAO.getProductsListCustomStatement() -->" + ex.getMessage());
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
            System.out.println("Error while getting products data from db; ProductDAO.getAmountOfProductsCustomStatement() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try {
                ps.close();
            } catch (Exception ex) {
                System.out.println("Product delete error when closing database connection or prepared statement; ProductDAO.getAmountOfProductsCustomStatement() -->" + ex.getMessage());
            }
        }
        return amountOfProducts;
    }
    public static ArrayList<Ticket> getFeaturedProductsList(long amount) {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Ticket> productsList = new ArrayList<>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM tickets WHERE featured = ? ORDER BY product_id LIMIT ?");
            ps.setBoolean(1, true);
            ps.setLong(2, amount);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket temp = new Ticket(rs.getInt("ticket_id"),
                        rs.getInt("author_id"),
                        rs.getInt("project_id"),
                        rs.getString("status"),
                        rs.getString("title"),
                        rs.getString("description"));
                productsList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; ProductDAO.getFeaturedProductsList() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; ProductDAO.getFeaturedProductsList() -->" + ex.getMessage()); }
        }
        return productsList;
    }
    public static JsonArray getProductsList(String statement) {
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
                        .add("ticket_id", rs.getString("ticket_id"))
                        .add("author_id", rs.getString("author_id"))
                        .add("project_id", rs.getString("project_id"))
                        .add("status", rs.getString("status"))
                        .add("title", rs.getString("title"))
                        .add("description", rs.getString("description")));
            }
            productsList = builder.build();
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; ProductDAO.getProductsList() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; ProductDAO.getProductsList() -->" + ex.getMessage()); }
        }
        return productsList;
    }
    public static ArrayList<Ticket> getProductListOfPattern(long startPosition, long amountPerPage, String searchByProductName, int searchOption) {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Ticket> ticketList = new ArrayList<>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM tickets LEFT JOIN categories ON products.category_id = categories.category_id WHERE products.product_name LIKE ? ORDER BY products.product_id LIMIT ?, ?");
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
                Ticket temp = new Ticket(rs.getInt("ticket_id"),
                        rs.getInt("author_id"),
                        rs.getInt("project_id"),
                        rs.getString("status"),
                        rs.getString("title"),
                        rs.getString("description"));
                ticketList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; ProductDAO.getProductListOfPattern() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            if (ps != null) { try { ps.close(); } catch (SQLException ex) { System.out.println("Error while closing PreparedStatement; ProductDAO.getProductListOfPattern() -->" + ex.getMessage()); } }
        }
        return ticketList;
    }
    public static boolean deleteSingleProduct(String deleteId) {
        Connection con = null;
        PreparedStatement ps = null;
        if(checkIfProductExists(deleteId)) {
            try {
                con = DataConnect.getConnection();
                ps = con.prepareStatement("DELETE FROM tickets WHERE ticket_id = ?");
                ps.setString(1, deleteId);
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error while deleting product from db; ProductDAO.deleteSingleProduct() -->" + ex.getMessage());
                return false;
            } finally {
                DataConnect.close(con);
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Error while closing PreparedStatement; ProductDAO.deleteSingleProduct() -->" + ex.getMessage());
                    }
                }
            }
            return true;
        } else {
            System.out.println("Product with given ID doesn't exist; ProductDAO.deleteSingleProduct()");
            return false;
        }
    }
    public static boolean checkIfProductExists(String id) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM tickets WHERE ticket_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking if product exists in db; ProductDAO.checkIfProductExists() -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; ProductDAO.checkIfProductExists() -->" + ex.getMessage());
                }
            }
        }
        return false;
    }
    public static Ticket getSingleProductData(String productId) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM tickets LEFT JOIN categories ON products.category_id = categories.category_id WHERE products.product_id = ?");
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ticket singleTicket = new Ticket(rs.getInt("ticket_id"),
                        rs.getInt("author_id"),
                        rs.getInt("project_id"),
                        rs.getString("status"),
                        rs.getString("title"),
                        rs.getString("description"));
                return singleTicket;
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking if product exists in db; ProductDAO.getSingleProductData() -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; ProductDAO.getSingleProductData() -->" + ex.getMessage());
                }
            }
        }
        return null;
    }
    public static Ticket getSingleProductDataForCart(String ticketId, Integer quantity) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM tickets LEFT JOIN categories ON products.category_id = categories.category_id WHERE products.product_id = ?");
            ps.setString(1, ticketId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ticket singleTicket = new Ticket(rs.getInt("ticket_id"),
                        rs.getInt("author_id"),
                        rs.getInt("project_id"),
                        rs.getString("status"),
                        rs.getString("title"),
                        rs.getString("description"));
                return singleTicket;
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking if product exists in db; ProductDAO.getSingleProductData() -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; ProductDAO.getSingleProductData() -->" + ex.getMessage());
                }
            }
        }
        return null;
    }
    public static boolean editGivenProduct(String author_id, String project_id, String status, String title, String description, String featured) {
        if (author_id != null || project_id != null) {
            PreparedStatement ps = null;
            Connection con = null;

            try {
                con = DataConnect.getConnection();
                if (con != null) {
                    ps = con.prepareStatement("UPDATE tickets SET author_id = ?, project_id = ?, status = ?, title = ?" +
                            " description = ? WHERE ticket_id = ? ");
                    ps.setString(1, author_id);
                    ps.setString(2, project_id);
                    ps.setString(3, status);
                    ps.setString(4, title);
                    ps.setString(5, description);
                    int boolToInt;
                    if(featured.equals("true")) { boolToInt = 1; }
                    else { boolToInt = 0; }
                    ps.setString(7, String.valueOf(boolToInt));
                    ps.setString(8, author_id);
                    ps.executeUpdate();
                }
            } catch (Exception ex) {
                System.out.println("Error while updating user data; ProductDAO.editGivenProduct() -->" + ex.getMessage());
            } finally {
                DataConnect.close(con);
                if (ps != null) { try { ps.close(); } catch (SQLException ex) { System.out.println("Error while closing PreparedStatement; ProductDAO.editGivenProduct() -->" + ex.getMessage()); } }
                return true;
            }
        } else {
            return false;
        }
    }
    public static boolean addProduct(String author_id, String project_id, String status, String title, String description, String featured) {
        if (author_id != null || project_id != null) {
            PreparedStatement ps = null;
            Connection con = null;
            try {
                con = DataConnect.getConnection();
                if (con != null) {
                    ps = con.prepareStatement("INSERT INTO tickets (author_id, project_id, status, title " +
                            "description, featured) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setString(1, author_id);
                    ps.setString(2, project_id);
                    ps.setString(3, status);
                    ps.setString(4, title);
                    ps.setString(5, description);
                    int boolToInt;
                    if(featured.equals("true")) { boolToInt = 1; }
                    else { boolToInt = 0; }
                    ps.setString(7, String.valueOf(boolToInt));
                    ps.executeUpdate();
                }
            } catch (Exception ex) {
                System.out.println("Registration error when executing query; ProductDAO.addProduct() -->" + ex.getMessage());
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    DataConnect.close(con);
                } catch (Exception ex) {
                    System.out.println("Adding product error when closing database connection or prepared statement; ProductDAO.addProduct() -->" + ex.getMessage());
                } finally {
                    return true;
                }
            }
        } else {
            System.out.println("All data must be delivered to this method; ProductDAO.addProduct() -->");
            return false;
        }
    }
}