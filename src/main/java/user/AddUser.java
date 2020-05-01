package user;

import dao.RegisterDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/user-manager/add-user")
public class AddUser extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("/WEB-INF/user/add-user.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int user_id = Integer.parseInt(request.getParameter("user"));

        if(title == null){ request.setAttribute("msg", "Nie podano tytułu");
        } else if (description == null){ request.setAttribute("msg", "Nie podano opisu");
        } else if (user_id < 0){ request.setAttribute("msg", "Nie podano użytkownika");
        } else {
            boolean done = RegisterDAO.addProject(title, description, user_id);
            if(done) {
                request.setAttribute("msg", "Pomyślnie dodano projekt do bazy");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie dodawania uzytkownika do bazy, spróbuj ponownie, albo zweryfikuj logi serwera");
            }
        }

        doGet(request, response);
    }
}
