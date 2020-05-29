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
	private boolean isRememberMeChecked;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.user = request.getParameter("user");
		this.pwd = request.getParameter("pwd");
		if(request.getParameter("remember_me") != null) {
			if(request.getParameter("remember_me").equals("on")) {
				isRememberMeChecked = true;
			}
			else {
				isRememberMeChecked = false;
			}
		}

		int valid = -1;
		try {
			valid = LoginDAO.validate(user, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}

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

				//expiration after: 60 mins
				session.setMaxInactiveInterval(60*60);
				Cookie userName = new Cookie("user_login", user_login);
				Cookie userRole = new Cookie("user_role", user_role);
				Cookie userEmail = new Cookie("user_email", user_email);
				Cookie userId = new Cookie("user_id", user_id);

				if(isRememberMeChecked) {
					userName.setMaxAge(365*24*60*60);
					userRole.setMaxAge(365*24*60*60);
					userEmail.setMaxAge(365*24*60*60);
					userId.setMaxAge(365*24*60*60);
//					Cookie rememberUser = new Cookie("remember_role", user_role);
//					rememberUser.setMaxAge(365*24*60*60);
//					response.addCookie(rememberUser);
				}
				else {
					userName.setMaxAge(60*60);
					userRole.setMaxAge(60*60);
					userEmail.setMaxAge(60*60);
					userId.setMaxAge(60*60);
				}

				response.addCookie(userName);
				response.addCookie(userRole);
				response.addCookie(userEmail);
				response.addCookie(userId);

				response.sendRedirect("/");
			} else {
				session.setAttribute("user_activation_key", user_activation_key);
				session.setAttribute("user_email", user_email);

				//expiration after: 30 mins
				session.setMaxInactiveInterval(60*60);
				Cookie userActivationKey = new Cookie("user_activation_key", user_activation_key);
				Cookie userEmail = new Cookie("user_email", user_email);

				userActivationKey.setMaxAge(60*60);
				userEmail.setMaxAge(60*60);

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
