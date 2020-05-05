package user;

import dao.ProjectDAO;
import dao.RegisterDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/project-manager/add-project")
public class AddProject extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("/WEB-INF/user/add-project.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int user_id = Integer.parseInt(request.getParameter("user0"));

        if(title == null){ request.setAttribute("msg", "Nie podano tytułu");
        } else if (description == null){ request.setAttribute("msg", "Nie podano opisu");
        } else if (user_id < 0){ request.setAttribute("msg", "Nie podano użytkownika");
        } else {
            boolean done = ProjectDAO.addProject(title, description, user_id);
            if(done) {
                request.setAttribute("msg", "Pomyślnie dodano projekt do bazy");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie dodawania uzytkownika do bazy, spróbuj ponownie, albo zweryfikuj logi serwera");
            }

            for (int i = 0; i <= 3; i++){
                user_id = Integer.parseInt(request.getParameter("user" +i));

                if (user_id > 0){
                    boolean dbUpdated = ProjectDAO.addUsersAndProjects(user_id);
                    if (dbUpdated){
                        request.setAttribute("msg", "Pomyślnie zaktualizowano bazę danych");
                    } else {
                        request.setAttribute("msg", "Wystąpił problem w trakcie aktualizacji bazy danych");
                    }
                }
            }
        }

        doGet(request, response);
    }
}
