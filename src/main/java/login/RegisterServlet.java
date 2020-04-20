package login;

import dao.RegisterDAO;
import util.ActivationEmail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String user_login = request.getParameter("userlogin");
        String user_pass = request.getParameter("pwd");
        String first_name = request.getParameter("firstname");
        String last_name = request.getParameter("lastname");
        String user_email = request.getParameter("email");
        String role = request.getParameter("role");



        if(!RegisterDAO.validateUserLogin(user_login)) {
            request.setAttribute("message", "Istnieje już uzytkownik o podanym loginie");
            doGet(request, response);
        } else if(!RegisterDAO.validateUserEmail(user_email)) {
            request.setAttribute("message", "Istnieje już uzytkownik o podanym emailu");
            doGet(request, response);
        } else {
            String activation_key = RegisterDAO.getAlphaNumericString(20);
            boolean done = RegisterDAO.addUser(user_login, user_pass, first_name, last_name, user_email, activation_key, role);
            if(done) {
                ActivationEmail.sendActivationEmail(activation_key, user_email);
                response.sendRedirect("/register-success");
            } else {
                request.setAttribute("message", "Coś poszło nie tak w trakcie dodawania użytkownika do bazy. Spróbuj ponownie lub skontaktuj się z administratoracją sklepu.");
                doGet(request, response);
            }
        }
    }
}
