package user;

import dao.TicketDAO;
import objects.Ticket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/ticket-manager/edit-ticket")
public class EditTicket extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String ticket_id;
        if(request.getParameter("productId")!=null) {
            ticket_id = request.getParameter("productId");
        } else {
            ticket_id = (String) request.getAttribute("productId");
        }
        if(TicketDAO.checkIfProductExists(ticket_id)){
            Ticket singleTicket = TicketDAO.getSingleProductData(ticket_id);
            request.setAttribute("singleTicket", singleTicket);
        }

        request.getRequestDispatcher("/WEB-INF/user/edit-ticket.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int ticket_id = Integer.parseInt(request.getParameter("ticket_id"));
        request.setAttribute("ticket_id", ticket_id);
        int project_id = Integer.parseInt(request.getParameter("projekt_id"));
        String status = request.getParameter("status");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        HttpSession session = request.getSession(false);
        int user_id = Integer.parseInt(session.getAttribute("user_id").toString());

        if(ticket_id == -1){ request.setAttribute("msg", "Nie rozpoznano id ticketu");
        } else if(project_id == -1){ request.setAttribute("msg", "Nie przypisano ticketu do ticketu");
        } else if(status == null){ request.setAttribute("msg", "Nie podano statusu ticketu");
        } else if(title == null){ request.setAttribute("msg", "Nie podano tytułu ticketu");
        } else if(description == null){ request.setAttribute("msg", "Nie podano opisu ticketu");
        } else {
            boolean done = TicketDAO.editGivenProduct(ticket_id, user_id, project_id, status, title, description);
            if(done){
                request.setAttribute("msg", "Pomyślnie dodano ticket");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie dodawania ticketu do bazy, spróbuj ponownie, albo zweryfikuj logi serwera");
            }
        }

        doGet(request, response);
    }
}
