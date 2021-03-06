package user;

import dao.TicketDAO;
import objects.Ticket;
import org.apache.commons.io.FileUtils;
import util.ContextOperations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/user/ticket-manager")
public class TicketManager extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "target\\error-reporting-portal\\assets\\files\\tickets\\";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long page, amountPerPage, amountOfTickets;
        String deleteId, searchByTicketName;
        int searchOption;
        Cookie cookies[] = request.getCookies();
        int author_ID = -1;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_id"))
                    author_ID = Integer.parseInt(cookie.getValue());
            }
        }

        if (request.getParameter("page") == null) {
            page = 0;
        } else {
            page = Long.parseLong(request.getParameter("page"));
        }
        if (request.getParameter("amountPerPage") == null) {
            amountPerPage = 20;
        } else {
            amountPerPage = Long.parseLong(request.getParameter("amountPerPage"));
        }
        if (request.getParameter("searchOption") != null) {
            searchOption = Integer.parseInt(request.getParameter("searchOption"));
        } else {
            searchOption = 2;
        }
        if (request.getParameter("deleteId") != null) {
            deleteId = String.valueOf(request.getParameter("deleteId"));
            if (TicketDAO.deleteSingleProduct(deleteId)) {
                String uploadPathTarget = ContextOperations.getPathToRoot(getServletContext().getRealPath("")) + UPLOAD_DIRECTORY + "\\" + deleteId + "\\";
                File fileToDelete = new File(uploadPathTarget);
                FileUtils.deleteDirectory(fileToDelete);
                request.setAttribute("msg", "Pomyślnie usunięto ticket.");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie usuwania ticketu");
            }
        }

        // Zwraca inną listę użytkowników w zależności od tego czy zostało coś wpisane w szukajkę
        if (request.getParameter("searchByTicketName") != null) {
            searchByTicketName = request.getParameter("searchByTicketName");
            ArrayList<Ticket> list = TicketDAO.getTicketsListOfPattern(page * amountPerPage, amountPerPage, searchByTicketName, searchOption, author_ID);
            amountOfTickets = TicketDAO.amountOfTicketsOfPattern(searchByTicketName, searchOption);
            request.setAttribute("searchOption", searchOption);
            request.setAttribute("searchByTicketName", searchByTicketName);
            request.setAttribute("list", list);
        } else {
            ArrayList<Ticket> list = TicketDAO.getTicketList(page * amountPerPage, amountPerPage, author_ID);
            amountOfTickets = TicketDAO.amountOfTickets();
            request.setAttribute("list", list);
        }

        // Ile stron wydrukować
        int pagesToPrint = (int) Math.ceil((double) amountOfTickets / (double) amountPerPage);

        request.setAttribute("pagesToPrint", pagesToPrint);
        request.setAttribute("currentPage", page);
        request.setAttribute("amountPerPage", amountPerPage);
        request.setAttribute("amountOfUsers", amountOfTickets);

        request.getRequestDispatcher("/WEB-INF/user/ticket-manager.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
