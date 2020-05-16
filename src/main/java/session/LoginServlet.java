package session;

import dao.LoginDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String pwd;
	private String user;
	private String id;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.user = request.getParameter("user");
		this.pwd = request.getParameter("pwd");
		int valid = -1;
		try {
			System.out.println("1");
			valid = LoginDAO.validate(user, pwd);
			System.out.println("2");
		} catch (Exception e) {
			System.out.println("3");
			e.printStackTrace();
		}
		System.out.println("4");

		if(valid == 1){
			ArrayList<String> userData = null;
			try {
				userData = LoginDAO.checkUserData(user, pwd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String user_login = userData.get(0);
			String user_email = userData.get(1);
			String user_activation_key = userData.get(2);
			String user_role = userData.get(3);
			String user_id = userData.get(4);
			HttpSession session = request.getSession();

			if(user_activation_key.isEmpty()) {
				session.setAttribute("user_login", user_login);
				session.setAttribute("user_role", user_role);
				session.setAttribute("user_email", user_email);
				session.setAttribute("user_id", user_id);

				//expiration after: 30 mins
				session.setMaxInactiveInterval(30*60);
				Cookie userName = new Cookie("user_login", user_login);
				Cookie userRole = new Cookie("user_role", user_role);
				Cookie userEmail = new Cookie("user_email", user_email);
				Cookie userId = new Cookie("user_id", user_id);

				userName.setMaxAge(30*60);
				userRole.setMaxAge(30*60);
				userEmail.setMaxAge(30*60);
				userId.setMaxAge(30*60);

				response.addCookie(userName);
				response.addCookie(userRole);
				response.addCookie(userEmail);
				response.addCookie(userId);

				response.sendRedirect("/");
			} else {
				session.setAttribute("user_activation_key", user_activation_key);
				session.setAttribute("user_email", user_email);

				//expiration after: 30 mins
				session.setMaxInactiveInterval(30*60);
				Cookie userActivationKey = new Cookie("user_activation_key", user_activation_key);
				Cookie userEmail = new Cookie("user_email", user_email);

				userActivationKey.setMaxAge(30*60);
				userEmail.setMaxAge(30*60);

				response.addCookie(userActivationKey);
				response.addCookie(userEmail);

				response.sendRedirect("/account-not-verified");
			}
		} else if (valid == 0) {
			request.setAttribute("msg", "Logowanie nie powiodło się. Podałeś złe dane.");
			doGet(request, response);
		}
		else {
			request.setAttribute("msg", "Połączenie z bazą danych nie powiodło się.");
			doGet(request, response);
		}
	}

}
