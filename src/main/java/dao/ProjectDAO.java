package dao;

import objects.Project;
import util.DataConnect;

import javax.xml.crypto.Data;
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
            System.out.println("Error while getting projects data from db; ProjectDAO.getProjectsList() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            try { ps.close(); } catch (Exception ex) { System.out.println("Product delete error when closing database connection or prepared statement; ProjectDAO.getProjectsList() -->" + ex.getMessage()); }
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
    public static boolean deleteSingleProject(String deleteId) {
        Connection con = null;
        PreparedStatement ps = null;
        if(checkIfProjectExists(deleteId)) {
            try {
                con = DataConnect.getConnection();
                ps = con.prepareStatement("DELETE FROM projects WHERE ID = ?");
                ps.setString(1, deleteId);
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error while deleting project from db; ProjectDAO.deleteSingleProject() -->" + ex.getMessage());
                return false;
            } finally {
                DataConnect.close(con);
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Error while closing PreparedStatement; ProjectDAO.deleteSingleProject() -->" + ex.getMessage());
                    }
                }
            }
            return true;
        } else {
            System.out.println("Project with given ID doesn't exist; ProjectDAO.deleteSingleProject()");
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
            System.out.println("Error while checking if project exists in db; ProjectDAO.checkIfProjectExists() -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; ProjectDAO.checkIfProjectExists() -->" + ex.getMessage());
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

    public static String getSingleProjectName(int projectId) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM projects WHERE ID = ?");
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new String(rs.getString("title"));
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting project bane in db; ProjectDAO.getSingleProjectName() -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; ProjectDAO.getSingleProjectName() -->" + ex.getMessage());
                }
            }
        }
        return null;
    }

    public static boolean editGivenProject(String project_id, String project_title, String project_description) {
        if (project_id != null || project_title != null || project_description != null) {
            PreparedStatement ps = null;
            Connection con = null;

            try {
                con = DataConnect.getConnection();
                if (con != null) {
                    ps = con.prepareStatement("UPDATE projects SET title = ?, description = ? WHERE ID = ? ");
                    ps.setString(1, project_title);
                    ps.setString(2, project_description);
                    ps.setString(3, project_id);
                    ps.executeUpdate();
                }
            } catch (Exception ex) {
                System.out.println("Error while updating user data; ProjectDAO.editGivenProject() -->" + ex.getMessage());
            } finally {
                DataConnect.close(con);
                if (ps != null) { try { ps.close(); } catch (SQLException ex) { System.out.println("Error while closing PreparedStatement; ProjectDAO.editGivenProject() -->" + ex.getMessage()); } }
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean addProject(String title, String description, int user_id, int author_ID) {
        if (title != null || description != null || user_id < 0 ) {
            PreparedStatement ps = null;
            Connection con = null;
            try {
                con = DataConnect.getConnection();
                if (con != null) {
                    String sql = "INSERT INTO projects(title, description, author_ID) VALUES(?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, title);
                    ps.setString(2, description);
                    ps.setInt(3, author_ID);
                    ps.executeUpdate();
                }
            } catch (Exception ex) {
                System.out.println("Add project error when executing query; ProjectDAO.addProject() -->" + ex.getMessage());
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    DataConnect.close(con);
                } catch (Exception ex) {
                    System.out.println("Add project error when closing database connection or prepared statement; ProjectDAO.addProject() -->" + ex.getMessage());
                }
            }
            return true;
        } else {
            System.out.println("All data must be delivered to this method; ProjectDAO.addProject() -->");
            return false;
        }
    }

    public static boolean removeUsersAndProjects(String projectID){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("DELETE FROM users_has_projects WHERE project_ID = ?");
            ps.setInt(1, Integer.parseInt(projectID));
            ps.executeUpdate();
        }  catch (SQLException ex) {
            System.out.println("Error when executing query; ProjectDAO.removeUsersAndProjects() -->" + ex.getMessage());
        } finally {
            try {
                if (ps != null){
                    ps.close();
                }
                DataConnect.close(con);
            } catch (Exception ex){
                System.out.println("Error when closing database connection or prepared statement; ProjectDAO.removeUsersAndProjects() -->" + ex.getMessage());
            }
        }
        return true;
    }


    public static boolean addUsersAndProjects(int user_ID){
        if (user_ID >= 0) {
            Connection con = null;
            PreparedStatement ps = null;
            int project_ID = getLastProjectID();
            try {
                con = DataConnect.getConnection();
                if (con != null){
                    if(checkUsersAndProjects(user_ID, project_ID)){
                        return true;
                    }
                    else {
                        ps = con.prepareStatement("INSERT INTO users_has_projects (user_ID, project_ID) VALUES (?, ?)");
                        ps.setInt(1, user_ID);
                        ps.setInt(2, project_ID);
                        ps.executeUpdate();
                    }
                }
            } catch (Exception ex){
                System.out.println("Error when executing query; ProjectDAO.addUsersAndProjects() -->" + ex.getMessage());
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    DataConnect.close(con);
                } catch (Exception ex) {
                    System.out.println("Error when closing database connection or prepared statement; ProjectDAO.addUsersAndProjects() -->" + ex.getMessage());
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean checkUsersAndProjects(int user_ID, int project_ID){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT * FROM users_has_projects WHERE user_ID = ? AND project_ID = ?");
            ps.setInt(1, user_ID);
            ps.setInt(2, project_ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking if user and project exist in db; ProjectDAO.checkUsersAndProjects() -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; ProjectDAO.checkUsersAndProjects() -->" + ex.getMessage());
                }
            }
        }
        return false;
    }

    public static int getLastProjectID(){
        Connection con = null;
        PreparedStatement ps = null;
        int project_ID;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("SELECT MAX(ID) AS 'lastID' FROM projects");
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                project_ID = rs.getInt("lastID");
                return project_ID;
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking if user and project exist in db; ProjectDAO.checkUsersAndProjects() -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Error while closing PreparedStatement; ProjectDAO.checkUsersAndProjects() -->" + ex.getMessage());
                }
            }
        }
        return -1;
    }
}
