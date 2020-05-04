package user;

import dao.ProjectDAO;
import objects.Project;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if(ProjectDAO.checkIfProjectExists(projectId)){
            Project singleProject = ProjectDAO.getSingleProjectData(Integer.parseInt(projectId));
            request.setAttribute("singleProject", singleProject);
        }

        request.getRequestDispatcher("/WEB-INF/user/edit-project.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        String project_title = request.getParameter("project_title");
        String project_description = request.getParameter("project_description");

        if(projectId == null){ request.setAttribute("msg", "Nie rozpoznano id edytowanego projektu. Spróbuj ponownie wyszukać projekt " +
                "w menadżerze projektów i zedytuj jego dane jeszcze raz.");
        } else if(project_title == null){ request.setAttribute("msg", "Nie podano nazwy projektu");
        } else if(project_description == null){ request.setAttribute("msg", "Nie podano opisu projektu");
        } else {
            boolean done = ProjectDAO.editGivenProject(projectId, project_title, project_description);
            if(done){
                request.setAttribute("msg", "Pomyślnie zedytowano projekt");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie dodawania zedytowanych danych projektów do bazy. Spróbuj ponownie, albo zweryfikuj logi serwera");
            }
        }

        doGet(request, response);
    }
}
