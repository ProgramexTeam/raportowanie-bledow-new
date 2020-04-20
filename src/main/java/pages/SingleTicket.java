package pages;


import dao.TicketDAO;
import objects.Ticket;
import util.ConstantValues;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet("/sklep/produkt")
public class SingleTicket extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            Ticket ticket = TicketDAO.getSingleProductData(id);
            if (ticket != null) {
                request.setAttribute("product", ticket);
                request.getRequestDispatcher("/WEB-INF/pages/single-ticket.jsp").forward(request, response);
            } else {
                response.sendRedirect("/sklep");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("/sklep");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //ShoppingCart shoppingCartBean = (ShoppingCart) session.getAttribute("shoppingCart");

        String id = request.getParameter("id");
        String amountString = request.getParameter("amount");

        if(amountString != null && amountString.length() > 0) {
            if(id != null && id.length() > 0){
                //shoppingCartBean.addToCart(id, amountString);
            }
        }

        //session.setAttribute("shoppingCart", shoppingCartBean);
        doGet(request, response);
    }
}