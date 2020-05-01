package dao;

import objects.Project;
import util.DataConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection", "ConstantConditions"})
public class ProjectDAO {
    public static Project getProject(String id) {
        PreparedStatement ps = null;
        Connection con = null;
        Project project = null;
        try {
            con = DataConnect.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM categories WHERE caegory_id=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    project = new Project(
                            rs.getInt("category_id"),
                            rs.getString("category_name"),
                            rs.getString("category_url"));
                }
            }
        } catch (Exception ex) {
            System.out.println("Category request error when executing query; CategoryDAO.getCategory() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; ProjectDAO.getCategory() -->" + ex.getMessage()); }
        }
        return project;
    }
    public static long amountOfProjects() {
        Connection con = null;
        long amount = 0;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT COUNT(*) AS `amount` FROM projects");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                amount = rs.getLong("amount");
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting project data from db; ProjectDAO.amountOfProjects() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Project delete error when closing database connection or prepared statement; ProjectDAO.amountOfProjects() -->" + ex.getMessage()); }
        }
        return amount;
    }
    public static long amountOfProjectsOfPattern(String pattern, int searchOption) {
        Connection con = null;
        long amount = 0;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT COUNT(*) AS `amount` FROM projects WHERE title LIKE ?");
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
            System.out.println("Error while getting projects data from db; ProjectDAO.amountOfProjectsOfPattern() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; ProjectDAO.amountOfProjectsOfPattern() -->" + ex.getMessage());
                }
            }
        }
        return amount;
    }
    public static ArrayList<Project> getProjectsList(long startPosition, long amount) {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Project> projects = new ArrayList<>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM projects ORDER BY ID LIMIT ?, ?");
            ps.setLong(1, startPosition);
            ps.setLong(2, amount);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Project temp = new Project(rs.getInt("ID"),
                        rs.getString("title"),
                        rs.getString("description"));
                projects.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; ProjectDAO.getProjectsList() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; ProjectDAO.getProjectsList() -->" + ex.getMessage()); }
        }
        return projects;
    }
    public static ArrayList<Project> getProjectsList() {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Project> categoriesList = new ArrayList<>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM projects ORDER BY ID");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Project temp = new Project(rs.getInt("ID"),
                        rs.getString("title"),
                        rs.getString("description"));
                categoriesList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting products data from db; ProjectDAO.getCategoriesList() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; ProjectDAO.getCategoriesList() -->" + ex.getMessage()); }
        }
        return categoriesList;
    }
    public static ArrayList<Project> getProjectsListOfPattern(long startPosition, long amountPerPage, String searchByProjectTitle, int searchOption) {
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Project> projectList = new ArrayList<>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM projects WHERE title LIKE ? ORDER BY ID DESC LIMIT ?, ?");
            if(searchOption==1){
                ps.setString(1, searchByProjectTitle + "%");
            } else if(searchOption==3) {
                ps.setString(1, "%" + searchByProjectTitle);
            } else {
                ps.setString(1, "%" + searchByProjectTitle + "%");
            }
            ps.setLong(2, startPosition);
            ps.setLong(3, amountPerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Project temp = new Project(rs.getInt("ID"),
                        rs.getString("title"),
                        rs.getString("description"));
                projectList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting projects data from db; ProjectDAO.getProjectsListOfPattern() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            if (ps != null) { try { ps.close(); } catch (SQLException ex) { System.out.println("Error while closing PreparedStatement; ProjectDAO.getProjectsListOfPattern() -->" + ex.getMessage()); } }
        }
        return projectList;
    }
    public static boolean deleteSingleCategory(String deleteId) {
        Connection con = null;
        PreparedStatement ps = null;
        if(checkIfProjectExists(deleteId)) {
            try {
                con = DataConnect.getConnection();
                ps = con.prepareStatement("DELETE FROM categories WHERE category_id = ?");
                ps.setString(1, deleteId);
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error while deleting product from db; ProjectDAO.deleteSingleCategory() -->" + ex.getMessage());
                return false;
            } finally {
                DataConnect.close(con);
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Error while closing PreparedStatement; ProjectDAO.deleteSingleCategory() -->" + ex.getMessage());
                    }
                }
            }
            return true;
        } else {
            System.out.println("Category with given ID doesn't exist; ProjectDAO.deleteSingleCategory()");
            return false;
        }
    }
    public static boolean checkIfProjectExists(String id) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM projects WHERE ID = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking if category exists in db; ProjectDAO.checkIfCategoryExists() -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; ProjectDAO.checkIfCategoryExists() -->" + ex.getMessage());
                }
            }
        }
        return false;
    }
    public static Project getSingleProjectData(int projectId) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM projects WHERE ID = ?");
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Project(rs.getInt("ID"),
                        rs.getString("title"),
                        rs.getString("description"));
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking if product exists in db; ProjectDAO.getSingleProjectData() -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; ProjectDAO.getSingleProjectData() -->" + ex.getMessage());
                }
            }
        }
        return null;
    }
    public static boolean editGivenCategory(String category_id, String category_name, String category_url) {
        if (category_id != null || category_name != null || category_url != null) {
            PreparedStatement ps = null;
            Connection con = null;

            try {
                con = DataConnect.getConnection();
                if (con != null) {
                    ps = con.prepareStatement("UPDATE categories SET category_name = ?, category_url = ? WHERE category_id = ? ");
                    ps.setString(1, category_name);
                    ps.setString(2, category_url);
                    ps.setString(3, category_id);
                    ps.executeUpdate();
                }
            } catch (Exception ex) {
                System.out.println("Error while updating user data; ProjectDAO.editGivenCategory() -->" + ex.getMessage());
            } finally {
                DataConnect.close(con);
                if (ps != null) { try { ps.close(); } catch (SQLException ex) { System.out.println("Error while closing PreparedStatement; ProjectDAO.editGivenCategory() -->" + ex.getMessage()); } }
            }
            return true;
        } else {
            return false;
        }
    }
    public static boolean addCategory(String category_name, String category_url) {
        if (category_name != null || category_url != null) {
            PreparedStatement ps = null;
            Connection con = null;
            try {
                con = DataConnect.getConnection();
                if (con != null) {
                    ps = con.prepareStatement("INSERT INTO categories (category_name, category_url) VALUES(?,?)");
                    ps.setString(1, category_name);
                    ps.setString(2, category_url);
                    ps.executeUpdate();
                }
            } catch (Exception ex) {
                System.out.println("Registration error when executing query; ProjectDAO.addCategory() -->" + ex.getMessage());
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    DataConnect.close(con);
                } catch (Exception ex) {
                    System.out.println("Adding product error when closing database connection or prepared statement; ProjectDAO.addCategory() -->" + ex.getMessage());
                }
            }
            return true;
        } else {
            System.out.println("All data must be delivered to this method; ProjectDAO.addCategory() -->");
            return false;
        }
    }
}