package user;


import dao.CommentDAO;
import dao.TicketDAO;
import objects.Ticket;
import org.apache.commons.io.FilenameUtils;
import util.ContextOperations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/user/ticket-manager/single-ticket")
public class SingleTicket extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "target\\error-reporting-portal\\assets\\files\\tickets\\";
    Ticket ticket;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uploadPathTarget;

        int id = -1;
        try {
            if(request.getParameter("ticketId")!=null) {
                id = Integer.parseInt(request.getParameter("ticketId"));
            } else {
                id = Integer.parseInt(request.getAttribute("ticketId").toString());
            }
            ticket = TicketDAO.getSingleTicketData(id);

            uploadPathTarget = ContextOperations.getPathToRoot(getServletContext().getRealPath("")) + UPLOAD_DIRECTORY + "\\" + id + "\\";
            File uploadDirTarget = new File(uploadPathTarget);
            if (!uploadDirTarget.exists()) {
                uploadDirTarget.mkdirs();
            }

            if (uploadDirTarget.listFiles() != null) {
                long counter = 0;
                File[] files = uploadDirTarget.listFiles();
                Arrays.sort(files, Comparator.comparingLong(File::lastModified));
                String[] linksTab = new String[files.length];
                int i = 0;
                for (File file : files) {
                    linksTab[i] = "/assets/files/tickets/" + id + "/" + file.getName();
                    i++;
                    counter++;
                }
                List<String> fileLinksList = Arrays.asList(linksTab);
                Collections.reverse(fileLinksList);
                request.setAttribute("fileLinksList", fileLinksList);
            } else {
                request.setAttribute("fileLinksList", null);
            }

            if (ticket != null) {
                request.setAttribute("singleTicket", ticket);
            } else {
                request.setAttribute("msg", "Ticket nie może być null");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "Zły format id projektu");
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