package user;

import dao.ProjectDAO;
import dao.UserDAO;
import objects.Project;
import objects.User;
import util.CookiesController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/project-manager/edit-project")
public class EditProject extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String projectId;
        if(request.getParameter("projectId")!=null) {
            projectId = request.getParameter("projectId");
        } else {
            projectId = (String) request.getAttribute("projectId");
        }
        Project singleProject;
        CookiesController c = new CookiesController();
        if(ProjectDAO.checkIfProjectExists(projectId)){
            singleProject = ProjectDAO.getSingleProjectData(Integer.parseInt(projectId));
            String user_id = c.getCookie(request, "user_id").getValue();
            List<User> users = UserDAO.getUsersInProject(Integer.parseInt(projectId));
            for (User user : users) {
                if(singleProject.getAuthor_id() != Integer.parseInt(user_id) && user.getId() != Integer.parseInt(c.getCookie(request, "user_id").getValue())) {
                    response.sendRedirect("/no-access");
                    break;
                }
                else {
                    request.setAttribute("singleProject", singleProject);
                    request.getRequestDispatcher("/WEB-INF/user/edit-project.jsp").forward(request, response);
                    break;
                }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        String project_title = request.getParameter("project_title");
        String project_description = request.getParameter("project_description");
        int user_id;

        if(projectId == null){ request.setAttribute("msg", "Nie rozpoznano id edytowanego projektu. Spróbuj ponownie wyszukać projekt " +
                "w menadżerze projektów i zedytuj jego dane jeszcze raz.");
        } else if(project_title == null){ request.setAttribute("msg", "Nie podano nazwy projektu");
        } else if(project_description == null){ request.setAttribute("msg", "Nie podano opisu projektu");
        } else {
            boolean done = ProjectDAO.editGivenProject(projectId, project_title, project_description);
            boolean usersRemoved = false;
            boolean dbUpdated = false;

            for (int i = 0; i <= 3; i++){
                user_id = Integer.parseInt(request.getParameter("user" +i));
                if (user_id > 0 && !usersRemoved){
                    ProjectDAO.removeUsersAndProjects(projectId);
                    usersRemoved = true;
                    dbUpdated = ProjectDAO.editUsersAndProjects(user_id, Integer.parseInt(projectId));
                }
                else if (user_id > 0){
                    dbUpdated = ProjectDAO.editUsersAndProjects(user_id, Integer.parseInt(projectId));
                }
            }
            if (dbUpdated || done){
                request.setAttribute("msg", "Pomyślnie zedytowano projekt");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie edycji projektu");
            }
        }

        doGet(request, response);
    }
}
