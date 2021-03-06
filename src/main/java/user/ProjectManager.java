package user;

import dao.ProjectDAO;
import objects.Project;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/user/project-manager")
public class ProjectManager extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long page, amountPerPage, amountOfProjects;
        String deleteId, searchByProjectName;
        int searchOption;

        if (request.getParameter("page") == null) {
            page = 0;
        } else {
            page = Long.parseLong(request.getParameter("page"));
        }
        if (request.getParameter("amountPerPage") == null) {
            amountPerPage = 20;
        } else {
            amountPerPage = Long.parseLong(request.getParameter("amountPerPage"));
        }
        if (request.getParameter("searchOption") != null) {
            searchOption = Integer.parseInt(request.getParameter("searchOption"));
        } else {
            searchOption = 2;
        }
        if (request.getParameter("deleteId") != null) {
            deleteId = String.valueOf(request.getParameter("deleteId"));
            if (ProjectDAO.deleteSingleProject(deleteId)) {
                request.setAttribute("msg", "Pomyślnie usunięto projekt");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie usuwania projektu");
            }
        }

        // Zwraca inną listę użytkowników w zależności od tego czy zostało coś wpisane w szukajkę
        if (request.getParameter("searchByProjectName") != null) {
            searchByProjectName = request.getParameter("searchByProjectName");
            ArrayList<Project> list = ProjectDAO.getProjectsListOfPattern(page * amountPerPage, amountPerPage, searchByProjectName, searchOption);

            amountOfProjects = ProjectDAO.amountOfProjectsOfPattern(searchByProjectName, searchOption);
            request.setAttribute("searchOption", searchOption);
            request.setAttribute("searchByProjectName", searchByProjectName);
            request.setAttribute("list", list);
        } else {
            ArrayList<Project> list = ProjectDAO.getProjectsList(page * amountPerPage, amountPerPage);
            amountOfProjects = ProjectDAO.amountOfProjects();
            request.setAttribute("list", list);
        }

        // Ile stron wydrukować
        int pagesToPrint = (int) Math.ceil((double) amountOfProjects / (double) amountPerPage);

        request.setAttribute("pagesToPrint", pagesToPrint);
        request.setAttribute("currentPage", page);
        request.setAttribute("amountPerPage", amountPerPage);
        request.setAttribute("amountOfProjects", amountOfProjects);

        request.getRequestDispatcher("/WEB-INF/user/project-manager.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
