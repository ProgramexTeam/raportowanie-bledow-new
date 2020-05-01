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
        String categoryId;
        if(request.getParameter("categoryId")!=null) {
            categoryId = request.getParameter("categoryId");
        } else {
            categoryId = (String) request.getAttribute("categoryId");
        }
        if(ProjectDAO.checkIfCategoryExists(categoryId)){
            Project singleProject = ProjectDAO.getSingleProjectData(Integer.parseInt(categoryId));
            request.setAttribute("singleCategory", singleProject);
        }

        request.getRequestDispatcher("/WEB-INF/user/edit-project.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String categoryId = request.getParameter("categoryId");
        request.setAttribute("categoryId", categoryId);
        String category_name = request.getParameter("category_name");
        String category_url = request.getParameter("category_url");

        if(categoryId == null){ request.setAttribute("msg", "Nie rozpoznano id edytowanej kategorii. Spróbuj ponownie wyszukać kategorię " +
                "w menadżerze kategorii i zedytuj jej dane jeszcze raz.");
        } else if(category_name == null){ request.setAttribute("msg", "Nie podano nazwy kategorii");
        } else if(category_url == null){ request.setAttribute("msg", "Nie podano url kategorii");
        } else {
            boolean done = ProjectDAO.editGivenCategory(categoryId, category_name, category_url);
            if(done){
                request.setAttribute("msg", "Pomyślnie zedytowano kategorię");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie dodawania zedytowanych danych kategorii do bazy. Spróbuj ponownie, albo zweryfikuj logi serwera");
            }
        }

        doGet(request, response);
    }
}
