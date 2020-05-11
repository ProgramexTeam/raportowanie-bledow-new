package user;

import dao.TicketDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet("/user/ticket-manager/add-ticket")
public class AddTicket extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "src\\main\\webapp\\assets\\files\\tickets\\";
    String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
    File uploadDir = new File(uploadPath);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        request.setAttribute("currentDate", dateFormat.format(date));
        request.getRequestDispatcher("/WEB-INF/user/add-ticket.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        int author_id =  Integer.parseInt(session.getAttribute("user_id").toString());
        int project_id = Integer.parseInt(request.getParameter("project_id"));
        String status = request.getParameter("status");
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        if(project_id == -1){ request.setAttribute("msg", "Nie przypisano ticketu do projektu");
        } else if(status == null){ request.setAttribute("msg", "Nie podano statusu ticketu");
        } else if(title == null){ request.setAttribute("msg", "Nie podano tytułu ticketu");
        } else if(description == null){ request.setAttribute("msg", "Nie podano opisu ticketu");
        } else {
            boolean done = TicketDAO.addTicket(author_id, project_id, status, title, description);
            if(done) {
                request.setAttribute("msg", "Pomyślnie dodano ticket do bazy");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie dodawania ticketu do bazy, spróbuj ponownie, albo zweryfikuj logi serwera");
            }
        }

        doGet(request, response);
    }
}
