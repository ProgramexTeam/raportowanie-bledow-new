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
        String category_name = request.getParameter("category_name");
        String category_url = request.getParameter("category_url");

        if(projectId == null){ request.setAttribute("msg", "Nie rozpoznano id edytowanej kategorii. Spróbuj ponownie wyszukać kategorię " +
                "w menadżerze kategorii i zedytuj jej dane jeszcze raz.");
        } else if(category_name == null){ request.setAttribute("msg", "Nie podano nazwy kategorii");
        } else if(category_url == null){ request.setAttribute("msg", "Nie podano url kategorii");
        } else {
            boolean done = ProjectDAO.editGivenCategory(projectId, category_name, category_url);
            if(done){
                request.setAttribute("msg", "Pomyślnie zedytowano kategorię");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie dodawania zedytowanych danych kategorii do bazy. Spróbuj ponownie, albo zweryfikuj logi serwera");
            }
        }

        doGet(request, response);
    }
}
