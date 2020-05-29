<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="objects.Project" %>
<%@ page import="dao.UserDAO" %>
<%@ page import="objects.User" %>
<!-- Nagłówek -->
<jsp:include page="/WEB-INF/user/parts/overall-header.jsp"/>
<!-- Nawigacja sidebar -->
<jsp:include page="/WEB-INF/user/parts/sidebar-menu.jsp"/>
<!-- Kontent -->
<div class="content user-manager">
    <div class="content-inside">
        <h1 class="backend-page-title"><i class="fas fa-users"></i> Menadżer projektów</h1>
        <p class="info-msg"><% if(request.getAttribute("msg") != null){ out.println(request.getAttribute("msg")); request.setAttribute("msg", null); } %></p>
        <div class="filters">
            <%
                int amountPerPage, currentPage, searchOption;
                String searchByProjectName;
                if(request.getAttribute("amountOfProjects") != null) { out.println("<p>Ilość projektów spełniających wymagania: " + request.getAttribute("amountOfProjects") + "</p>"); }
                if(request.getAttribute("amountPerPage") != null){ amountPerPage = (int)((long)request.getAttribute("amountPerPage")); } else { amountPerPage = 0; }
                if(request.getAttribute("currentPage") != null){ currentPage = (int)((long)request.getAttribute("currentPage")); } else { currentPage = 0; }
                if(request.getAttribute("searchOption") != null){ searchOption = (int)request.getAttribute("searchOption"); } else { searchOption = 2; }
                if(request.getAttribute("searchByProjectName") != null){ searchByProjectName = (String) request.getAttribute("searchByProjectName"); } else { searchByProjectName = ""; }
            %>
            <form action="/user/project-manager" method="post">
                <p>Ile projektów na jedną stronę:
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
                <p>Wyszukaj projekt o nazwie, która
                    <select name="searchOption">
                        <option value="1" <% if(searchOption==1){ out.println("selected");} %>>zaczyna się na</option>
                        <option value="2" <% if(searchOption==2){ out.println("selected");} %>>zawiera</option>
                        <option value="3" <% if(searchOption==3){ out.println("selected");} %>>kończy się na</option>
                    </select>
                    <input type="text" name="searchByProjectName" value="<% if(searchByProjectName!=null){ out.println(searchByProjectName);} %>"></p>
                <input type="submit" value="Zastosuj">
            </form>
        </div>
        <table class="data">
            <%
                if(request.getAttribute("list") != null) {
                    ArrayList<Project> list = (ArrayList<Project>) request.getAttribute("list");

                    long i = 0;

                    String user_role = null;
                    if(session.getAttribute("user_role") != null){
                        user_role = session.getAttribute("user_role").toString();
                    }

                    out.println("<thead>" +
                            "<tr class=\"project-list-header\">" +
                            "<td class=\"project-list-header-item project-edit\">edytuj / usuń</td>" +
                            "<td class=\"project-list-header-item project-author\">Autor</td>" +
                            "<td class=\"project-list-header-item project-name\">Tytuł</td>" +
                            "</tr>" +
                            "</thead>" +
                            "<tbody>");
                    if (!list.isEmpty()) {
                        for (Project project : list) {
                            ArrayList<User> users =  UserDAO.getUsersInProject(project.getId());
                            Cookie cookies[] = request.getCookies();
                            int userID = -1;

                            if (cookies != null) {
                                for (Cookie cookie : cookies) {
                                    if (cookie.getName().equals("user_id"))
                                        userID = Integer.parseInt(cookie.getValue());
                                    }
                            }

                            for (User u: users) {
                                if (u.getId() == userID || project.getAuthor_id() == userID) {
                                    if (!user_role.equals("analyst")) {
                                        out.println("<tr class=\"project-row project-no-" + i + "\">" +
                                                "<td class=\"project-row-item project-edit\">" +
                                                "<a href=\"" + request.getContextPath() + "/user/project-manager/single-project?projectId=" + project.getId() + "\">szczegóły</a> / " +
                                                "<a href=\"" + request.getContextPath() + "/user/project-manager/edit-project?projectId=" + project.getId() + "\">edytuj</a> / " +
                                                "<a href=\"" + request.getContextPath() + "/user/project-manager?page=" + currentPage + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByProjectName=" + searchByProjectName + "&deleteId=" + project.getId() + "\">usuń</a>" +
                                                "</td>" +
                                                "<td class=\"project-row-item project-name\">" + UserDAO.getSingleUserLogin(project.getAuthor_id()) + "</td>" +
                                                "<td class=\"project-row-item project-name\">" + project.getTitle() + "</td>" +
                                                "</tr>");
                                        i++;
                                    }
                                    else {
                                        out.println("<tr class=\"project-row project-no-" + i + "\">" +
                                                "<td class=\"project-row-item project-edit\">" +
                                                "<a href=\"" + request.getContextPath() + "/user/project-manager/single-project?projectId=" + project.getId() + "\">szczegóły</a> " +
                                                "</td>" +
                                                "<td class=\"project-row-item project-name\">" + UserDAO.getSingleUserLogin(project.getAuthor_id()) + "</td>" +
                                                "<td class=\"project-row-item project-name\">" + project.getTitle() + "</td>" +
                                                "</tr>");
                                        i++;
                                    }
                                    break;
                                }
                            }
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
                                out.println("<a href=\"" + request.getContextPath() + "/user/project-manager?page=" + (currentPage - 1) + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByProjectName=" + searchByProjectName + "\">" +
                                        "<div class=\"link-no-0 previous-page\">Poprzednia</div>" +
                                        "</a>");
                            }

                            if(currentPage == 0){
                                out.println("<a href=\"" + request.getContextPath() + "/user/project-manager?page=0&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByProjectName=" + searchByProjectName + "\">" +
                                        "<div class=\"link-no-1 first-page pagination-active\">1</div>" +
                                        "</a>");
                            } else {
                                out.println("<a href=\"" + request.getContextPath() + "/user/project-manager?page=0&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByProjectName=" + searchByProjectName + "\">" +
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
                                    out.println("<a href=\"" + request.getContextPath() + "/user/project-manager?page=" + j + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByProjectName=" + searchByProjectName + "\">" +
                                            "<div class=\"link-no-" + (j + 1) + " pagination-active\">" + (j + 1) + "</div>" +
                                            "</a>");
                                } else {
                                    out.println("<a href=\"" + request.getContextPath() + "/user/project-manager?page=" + j + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByProjectName=" + searchByProjectName + "\">" +
                                            "<div class=\"link-no-" + (j + 1) + "\">" + (j + 1) + "</div>" +
                                            "</a>");
                                }
                            }

                            if(pagesToPrint-currentPage>4) {
                                out.println("<div class=\"three-dots-sep\">...</div>");
                            }

                            if(currentPage==pagesToPrint-1){
                                out.println("<a href=\"" + request.getContextPath() + "/user/project-manager?page=" + (pagesToPrint - 1) + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByProjectName=" + searchByProjectName + "\">" +
                                        "<div class=\"link-no-" + (pagesToPrint - 1) + " last-page pagination-active\">" + pagesToPrint + "</div>" +
                                        "</a>");
                            } else {
                                out.println("<a href=\"" + request.getContextPath() + "/user/project-manager?page=" + (pagesToPrint - 1) + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByProjectName=" + searchByProjectName + "\">" +
                                        "<div class=\"link-no-" + (pagesToPrint - 1) + " last-page\">" + pagesToPrint + "</div>" +
                                        "</a>");
                            }

                            if(currentPage!=pagesToPrint-1) {
                                out.println("<a href=\"" + request.getContextPath() + "/user/project-manager?page=" + (currentPage + 1) + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByProjectName=" + searchByProjectName + "\">" +
                                        "<div class=\"link-no-" + (pagesToPrint + 1) + " next-page\">Następna</div>" +
                                        "</a></div>");
                            }
                        } else {
                            if(currentPage!=0) {
                                out.println("<a href=\"" + request.getContextPath() + "/user/project-manager?page=" + (currentPage - 1) + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByProjectName=" + searchByProjectName + "\">" +
                                        "<div class=\"link-no-0 previous-page\">Poprzednia</div>" +
                                        "</a>");
                            }

                            for (int j=0; j<pagesToPrint; j++) {
                                if(currentPage==j) {
                                    out.println("<a href=\"" + request.getContextPath() + "/user/project-manager?page=" + j + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByProjectName=" + searchByProjectName + "\">" +
                                            "<div class=\"link-no-" + (j+1) + " pagination-active\">" + (j+1) + "</div></a>");
                                } else {
                                    out.println("<a href=\"" + request.getContextPath() + "/user/project-manager?page=" + j + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByProjectName=" + searchByProjectName + "\">" +
                                            "<div class=\"link-no-" + (j+1) + "\">" + (j+1) + "</div></a>");
                                }
                            }

                            if(currentPage!=pagesToPrint-1) {
                                out.println("<a href=\"" + request.getContextPath() + "/user/project-manager?page=" + (currentPage + 1) + "&amountPerPage=" + amountPerPage + "&searchOption=" + searchOption + "&searchByProjectName=" + searchByProjectName + "\">" +
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