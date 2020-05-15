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
        try {
            int id = Integer.parseInt(request.getParameter("projectId"));
            project = ProjectDAO.getSingleProjectData(id);
            if (project != null) {
                request.setAttribute("singleProject", project);
                request.getRequestDispatcher("/WEB-INF/user/single-project.jsp").forward(request, response);
            } else {
                //response.sendRedirect("/portal");
            }
        } catch (NumberFormatException e) {
            //response.sendRedirect("/portal");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String projectID = project.getId()+"";
        Cookie[] cookies = request.getCookies();
        String userID = null;
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("user_id"))
                userID = cookie.getValue();
        }
        System.out.println(userID);
        //String userID = request.getAttribute("user_id").toString();
        String text = request.getParameter("comment");

        if(projectID == null){ request.setAttribute("msg", "Nie rozpoznano id edytowanego projektu. Spróbuj ponownie wyszukać projekt " +
                "w menadżerze projektów i zedytuj jego dane jeszcze raz.");
        } else {
            boolean done = CommentDAO.addComment(Integer.parseInt(projectID), Integer.parseInt(userID), text, "project");
            if(done){
                request.setAttribute("msg", "Pomyślnie zedytowano projekt");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie dodawania zedytowanych danych projektów do bazy. Spróbuj ponownie, albo zweryfikuj logi serwera");
            }
        }

        doGet(request, response);
    }
}