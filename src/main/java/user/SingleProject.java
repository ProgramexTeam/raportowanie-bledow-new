package user;

import dao.CommentDAO;
import dao.ProjectDAO;
import objects.Project;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/project-manager/single-project")
public class SingleProject extends HttpServlet {
    Project project;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = -1;
        try {
            if(request.getParameter("projectId")!=null) {
                id = Integer.parseInt(request.getParameter("projectId"));
            } else {
                id = Integer.parseInt(request.getAttribute("projectId").toString());
            }
            project = ProjectDAO.getSingleProjectData(id);
            if (project != null) {
                request.setAttribute("singleProject", project);
            } else {
                //response.sendRedirect("/portal");
            }
        } catch (NumberFormatException e) {
            //response.sendRedirect("/portal");
        }
        request.getRequestDispatcher("/WEB-INF/user/single-project.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int projectID = project.getId();
        request.setAttribute("projectId", projectID);
        Cookie[] cookies = request.getCookies();
        String userID = null;
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("user_id"))
                userID = cookie.getValue();
        }
        String text = request.getParameter("comment");

        if(projectID < 0){ request.setAttribute("msg", "Nie rozpoznano ID projektu.");
        } else {
            boolean done = CommentDAO.addComment(projectID, Integer.parseInt(userID), text, "project");
            if(done){
                request.setAttribute("msg", "Pomyślnie dodano komentarz.");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie dodawania komentarza. Spróbuj ponownie albo zweryfikuj logi serwera");
            }
        }

        doGet(request, response);
    }
}