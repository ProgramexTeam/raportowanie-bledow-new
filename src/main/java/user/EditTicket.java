package user;

import dao.ProjectDAO;
import dao.TicketDAO;
import objects.Ticket;
import util.ContextOperations;
import util.CookiesController;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@MultipartConfig(maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 50 * 5)
@WebServlet("/user/ticket-manager/edit-ticket")
public class EditTicket extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "target\\error-reporting-portal\\assets\\files\\tickets\\";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String ticketId, deleteId;
        String uploadPathTarget;

        if (request.getParameter("ticketId") != null) {
            ticketId = request.getParameter("ticketId");
        } else {
            ticketId = (String) request.getAttribute("ticketId");
        }
        Ticket singleTicket;
        CookiesController c = new CookiesController();
        if (TicketDAO.checkIfTicketExists(ticketId)) {
            uploadPathTarget = ContextOperations.getPathToRoot(getServletContext().getRealPath("")) + UPLOAD_DIRECTORY + "\\" + ticketId + "\\";
            singleTicket = TicketDAO.getSingleTicketData(Integer.parseInt(ticketId));
            request.setAttribute("singleTicket", singleTicket);
            String user_id = c.getCookie(request, "user_id").getValue();
            Ticket ticketData = TicketDAO.getSingleTicketData(Integer.parseInt(ticketId));
            int projectID = ticketData.getProject_id();
            if(!ProjectDAO.checkUsersAndProjects(Integer.parseInt(user_id), projectID) && singleTicket.getAuthor_id() != Integer.parseInt(user_id)) {
                response.sendRedirect("/no-access");
            }
            else {
                if (request.getParameter("deleteId") != null) {
                    deleteId = request.getParameter("deleteId");
                    File fileToDelete = new File(uploadPathTarget + deleteId);
                    if (fileToDelete.delete()) {
                        request.setAttribute("msg", "Pomyślnie usunięto plik");
                    } else {
                        request.setAttribute("msg", "Wystąpił błąd podczas usuwania pliku!");
                    }
                }
                File uploadDirTarget = new File(uploadPathTarget);
                if (!uploadDirTarget.exists()) {
                    uploadDirTarget.mkdirs();
                }
                if (request.getContentType() != null && request.getContentType().toLowerCase().contains("multipart/form-data")) {
                    if (!request.getParts().isEmpty()) {
                        String fileName;
                        for (Part part : request.getParts()) {
                            fileName = getFileName(part);
                            if (fileName != null && !fileName.isEmpty()) {
                                String outputFilePathTarget = uploadPathTarget + fileName;
                                part.write(outputFilePathTarget);
                            }
                        }
                    }
                }
                if (uploadDirTarget.listFiles() != null) {
                    long counter = 0;
                    File[] files = uploadDirTarget.listFiles();
                    Arrays.sort(files, Comparator.comparingLong(File::lastModified));
                    String[] linksTab = new String[files.length];
                    int i = 0;
                    for (File file : files) {
                        linksTab[i] = file.getName();
                        i++;
                        counter++;
                    }
                    List<String> fileLinksList = Arrays.asList(linksTab);
                    Collections.reverse(fileLinksList);
                    request.setAttribute("fileLinksList", fileLinksList);
                } else {
                    request.setAttribute("fileLinksList", null);
                }
                request.getRequestDispatcher("/WEB-INF/user/edit-ticket.jsp").forward(request, response);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        request.setAttribute("ticketId", ticketId);
        int project_id = Integer.parseInt(request.getParameter("project"));
        String status = request.getParameter("status");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        HttpSession session = request.getSession(false);
        int user_id = Integer.parseInt(session.getAttribute("user_id").toString());

        if (ticketId == -1) {
            request.setAttribute("msg", "Nie rozpoznano id ticketu");
        } else if (project_id == -1) {
            request.setAttribute("msg", "Nie przypisano ticketu do projektu");
        } else if (status == null) {
            request.setAttribute("msg", "Nie podano statusu ticketu");
        } else if (title == null) {
            request.setAttribute("msg", "Nie podano tytułu ticketu");
        } else if (description == null) {
            request.setAttribute("msg", "Nie podano opisu ticketu");
        } else {
            boolean done = TicketDAO.editGivenTicket(ticketId, user_id, project_id, status, title, description);
            if (done) {
                request.setAttribute("msg", "Pomyślnie edytowano ticket");
            } else {
                request.setAttribute("msg", "Wystąpił problem w trakcie dodawania ticketu do bazy, spróbuj ponownie, albo zweryfikuj logi serwera");
            }
        }

        doGet(request, response);
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                try {
                    return new String(content.substring(content.indexOf("=") + 2, content.length() - 1).getBytes(), "utf8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
