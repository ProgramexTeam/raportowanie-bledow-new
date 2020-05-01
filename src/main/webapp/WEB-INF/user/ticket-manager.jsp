<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="objects.Ticket" %>
<%@ page import="objects.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.w3c.dom.UserDataHandler" %>
<%@ page import="dao.UserDAO" %>
<!-- Nagłówek -->
<jsp:include page="/WEB-INF/user/parts/overall-header.jsp"/>
<!-- Nawigacja sidebar -->
<jsp:include page="/WEB-INF/user/parts/sidebar-menu.jsp"/>
<!-- Kontent -->
<div class="content ticket-manager">
    <div class="content-inside">
        <h1 class="backend-page-title"><i class="fas fa-shopping-bag"></i> Menadżer ticketów</h1>
        <p class="info-msg"><% if(request.getAttribute("msg") != null){ out.println(request.getAttribute("msg")); request.setAttribute("msg", null); } %></p>
        <div class="filters">
            <%
                int amountPerPage, currentPage, searchOption;
                String searchByTicketName;
                if(request.getAttribute("amountOfTickets") != null) { out.println("<p>Ilość dodanych ticketów: " + request.getAttribute("amountOfTickets") + "</p>"); }
                if(request.getAttribute("amountPerPage") != null){ amountPerPage = (int)((long)request.getAttribute("amountPerPage")); } else { amountPerPage = 0; }
                if(request.getAttribute("currentPage") != null){ currentPage = (int)((long)request.getAttribute("currentPage")); } else { currentPage = 0; }
                if(request.getAttribute("searchOption") != null){ searchOption = (int)request.getAttribute("searchOption"); } else { searchOption = 2; }
                if(request.getAttribute("searchByTicketName") != null){ searchByTicketName = (String) request.getAttribute("searchByTicketName"); } else { searchByTicketName = ""; }
            %>
            <form action="/user/ticket-manager" method="post">
                <p>Ile ticketów na jedną stronę:
                    <select name="amountPerPage">
                        <option <% if(amountPerPage==5){ out.println("selected");} %>>5</option>
                        <option <% if(amountPerPage==10){ out.println("selected");} %>>10</option>
                        <option <% if(amountPerPage==20){ out.println("selected");} %>>20</option>
                        <option <% if(amountPerPage==50){ out.println("selected");} %>>50</option>
                        <option <% if(amountPerPage==100){ out.println("selected");} %>>100</option>
                        <% if(amountPerPage!=5 && amountPerPage!=10 && amountPerPage!=20 && amountPerPage!=50 && amountPerPage!=100){
                            out.println("<option selected>" + amountPerPage + "</option>");
                        } %>
                    </select></p>
                <p>Wyszukaj ticket o nazwie, która
                    <select name="searchOption">
                        <option value="1" <% if(searchOption==1){ out.println("selected");} %>>zaczyna się na</option>
                        <option value="2" <% if(searchOption==2){ out.println("selected");} %>>zawiera</option>
                        <option value="3" <% if(searchOption==3){ out.println("selected");} %>>kończy się na</option>
                    </select>
                    <input type="text" name="searchByTicketName" value="<% if(searchByTicketName!=null){ out.println(searchByTicketName);} %>"></p>
                <input type="submit" value="Zastosuj">
            </form>
        </div>
        <table class="data">
            <%
                if(request.getAttribute("list") != null){
                    ArrayList<Ticket> list = (ArrayList<Ticket>) request.getAttribute("list");

                    long i = 0;

                    out.println("<thead>" +
                            "<tr class=\"ticket-list-header\">" +
                            "<td class=\"ticket-list-header-item ticket-edit\">edytuj / usuń</td>" +
                            "<td class=\"ticket-list-header-item ticket-price\">Autor</td>" +
                            "<td class=\"ticket-list-header-item ticket-name\">Tytuł</td>" +
                            "<td class=\"ticket-list-header-item ticket-project\">Projekt</td>" +
                            "<td class=\"ticket-list-header-item ticket-price\">Status</td>" +
                            "</tr>" +
                            "</thead>" +
                            "<tbody>");
                    if (!list.isEmpty()) {
                        for (Ticket ticket : list) {
                            User user = UserDAO.getSingleUserData(ticket.getAuthor_id());
                            out.println("<tr class=\"ticket-row ticket-no-" + i + "\">" +
                                    "<td class=\"ticket-row-item ticket-edit\">" +
                                    "<a href=\"" + request.getContextPath() + "/user/ticket-manager/edit-ticket?productId=" + ticket.getId() + "\">edytuj</a> / " +
                                    "<a href=\"" + request.getContextPath() + "/user/ticket-manager?page=" + currentPage + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByTicketName=" + searchByTicketName + "&deleteId=" + ticket.getId() + "\">usuń</a>" +
                                    "</td>" +
                                    "<td class=\"ticket-row-item ticket-name\">" + user.getUser_login() + "</td>" +
                                    "<td class=\"ticket-row-item ticket-name\">" + ticket.getTitle() + "</td>" +
                                    "<td class=\"ticket-row-item ticket-project\">" + ticket.getProject_id() + "</td>" +
                                    "<td class=\"ticket-row-item ticket-name\">" + ticket.getStatus() + "</td>" +
                                    "</tr>");
                            i++;
                        }
                    }
                    out.println("</tbody>");
                } else {
                    out.println("Coś poszło nie tak przy odbieraniu danych z bazy...");
                }

                if(request.getAttribute("pagesToPrint") != null) {
                    int pagesToPrint = (int) request.getAttribute("pagesToPrint");

                    if(currentPage<pagesToPrint){
                        %>

                        </table>
                        <div class="pages-list">

                        <% if (pagesToPrint>12) {
                            if (currentPage != 0) {
                                out.println("<a href=\"" + request.getContextPath() + "/user/ticket-manager?page=" + (currentPage - 1) + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByTicketName=" + searchByTicketName + "\">" +
                                        "<div class=\"link-no-0 previous-page\">Poprzednia</div>" +
                                        "</a>");
                            }

                            if(currentPage == 0){
                                out.println("<a href=\"" + request.getContextPath() + "/user/ticket-manager?page=0&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByTicketName=" + searchByTicketName + "\">" +
                                        "<div class=\"link-no-1 first-page pagination-active\">1</div>" +
                                        "</a>");
                            } else {
                                out.println("<a href=\"" + request.getContextPath() + "/user/ticket-manager?page=0&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByTicketName=" + searchByTicketName + "\">" +
                                        "<div class=\"link-no-1 first-page\">1</div>" +
                                        "</a>");
                            }

                            if(currentPage>3) {
                                out.println("<div class=\"three-dots-sep\">...</div>");
                            }

                            //Korekcja paginacji
                            int a=2, b=2;
                            switch (currentPage){
                                case 2: a=1; b=3;
                                    break;
                                case 1: a=0; b=4;
                                    break;
                                case 0: a=-1; b=5;
                                    break;
                            }
                            switch (pagesToPrint-currentPage){
                                case 0: a=6; b=-2;
                                    break;
                                case 1: a=5; b=-1;
                                    break;
                                case 2: a=4; b=0;
                                    break;
                                case 3: a=3; b=1;
                                    break;
                            }

                            for(int j=currentPage-a; j<=currentPage+b; j++) {
                                if(j==currentPage) {
                                    out.println("<a href=\"" + request.getContextPath() + "/user/ticket-manager?page=" + j + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByTicketName=" + searchByTicketName + "\">" +
                                            "<div class=\"link-no-" + (j + 1) + " pagination-active\">" + (j + 1) + "</div>" +
                                            "</a>");
                                } else {
                                    out.println("<a href=\"" + request.getContextPath() + "/user/ticket-manager?page=" + j + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByTicketName=" + searchByTicketName + "\">" +
                                            "<div class=\"link-no-" + (j + 1) + "\">" + (j + 1) + "</div>" +
                                            "</a>");
                                }
                            }

                            if(pagesToPrint-currentPage>4) {
                                out.println("<div class=\"three-dots-sep\">...</div>");
                            }

                            if(currentPage==pagesToPrint-1){
                                out.println("<a href=\"" + request.getContextPath() + "/user/ticket-manager?page=" + (pagesToPrint - 1) + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByTicketName=" + searchByTicketName + "\">" +
                                        "<div class=\"link-no-" + (pagesToPrint - 1) + " last-page pagination-active\">" + pagesToPrint + "</div>" +
                                        "</a>");
                            } else {
                                out.println("<a href=\"" + request.getContextPath() + "/user/ticket-manager?page=" + (pagesToPrint - 1) + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByTicketName=" + searchByTicketName + "\">" +
                                        "<div class=\"link-no-" + (pagesToPrint - 1) + " last-page\">" + pagesToPrint + "</div>" +
                                        "</a>");
                            }

                            if(currentPage!=pagesToPrint-1) {
                                out.println("<a href=\"" + request.getContextPath() + "/user/ticket-manager?page=" + (currentPage + 1) + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByTicketName=" + searchByTicketName + "\">" +
                                        "<div class=\"link-no-" + (pagesToPrint + 1) + " next-page\">Następna</div>" +
                                        "</a></div>");
                            }
                        } else {
                            if(currentPage!=0) {
                                out.println("<a href=\"" + request.getContextPath() + "/user/ticket-manager?page=" + (currentPage - 1) + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByTicketName=" + searchByTicketName + "\">" +
                                        "<div class=\"link-no-0 previous-page\">Poprzednia</div>" +
                                        "</a>");
                            }

                            for (int j=0; j<pagesToPrint; j++) {
                                if(currentPage==j) {
                                    out.println("<a href=\"" + request.getContextPath() + "/user/ticket-manager?page=" + j + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByTicketName=" + searchByTicketName + "\">" +
                                            "<div class=\"link-no-" + (j+1) + " pagination-active\">" + (j+1) + "</div></a>");
                                } else {
                                    out.println("<a href=\"" + request.getContextPath() + "/user/ticket-manager?page=" + j + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByTicketName=" + searchByTicketName + "\">" +
                                            "<div class=\"link-no-" + (j+1) + "\">" + (j+1) + "</div></a>");
                                }
                            }

                            if(currentPage!=pagesToPrint-1) {
                                out.println("<a href=\"" + request.getContextPath() + "/user/ticket-manager?page=" + (currentPage + 1) + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByTicketName=" + searchByTicketName + "\">" +
                                        "<div class=\"link-no-" + (pagesToPrint + 1) + " next-page\">Następna</div>" +
                                        "</a>");
                            }
                        } %>
                        </div>
                    <% }
                    }%>
    </div>
</div>
<!-- Stopka -->
<jsp:include page="/WEB-INF/user/parts/overall-footer.jsp"/>