package user;


import dao.CommentDAO;
import dao.TicketDAO;
import objects.Ticket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/ticket-manager/single-ticket")
public class SingleTicket extends HttpServlet {
    Ticket ticket;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = -1;
        try {
            if(request.getParameter("ticketId")!=null) {
                id = Integer.parseInt(request.getParameter("ticketId"));
            } else {
                id = Integer.parseInt(request.getAttribute("ticketId").toString());
            }
            ticket = TicketDAO.getSingleTicketData(id);
            if (ticket != null) {
                request.setAttribute("singleTicket", ticket);
            } else {
                //response.sendRedirect("/portal");
            }
        } catch (NumberFormatException e) {
            //response.sendRedirect("/portal");
        }
        request.getRequestDispatcher("/WEB-INF/user/single-ticket.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int ticketID = ticket.getId();
        request.setAttribute("ticketId", ticketID);
        Cookie[] cookies = request.getCookies();
        String userID = null;
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("user_id"))
                userID = cookie.getValue();
        }
        String text = request.getParameter("comment");

        if(ticketID < 0){ request.setAttribute("msg", "Nie rozpoznano ID ticketu.");
        } else {
            boolean done = CommentDAO.addComment(ticketID, Integer.parseInt(userID), text, "ticket");
            if(done){
                request.setAttribute("msg", "Pomyślnie dodano komentarz.");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie dodawania komentarza. Spróbuj ponownie albo zweryfikuj logi serwera");
            }
        }

        doGet(request, response);
    }
}